package rs.ac.uns.ftn.medDataShare.service;

import org.bouncycastle.util.encoders.Hex;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.medDataShare.chaincode.Config;
import rs.ac.uns.ftn.medDataShare.chaincode.client.RegisterUserHyperledger;
import rs.ac.uns.ftn.medDataShare.chaincode.dto.*;
import rs.ac.uns.ftn.medDataShare.chaincode.util.ConnectionParamsUtil;
import rs.ac.uns.ftn.medDataShare.chaincode.util.WalletUtil;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestDto;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessSendRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialPreviewDto;
import rs.ac.uns.ftn.medDataShare.dto.form.SearchClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.util.Constants;
import rs.ac.uns.ftn.medDataShare.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class HyperledgerService {

    @Autowired
    private SymmetricCryptography symmetricCryptography;

    private final static Logger LOG = Logger.getLogger(HyperledgerService.class.getName());

    private Contract getContract(User user) throws Exception {
        String userWalletIdentity = user.getEmail();
        String userIdentity = user.getId();

        String org = determineOrg(user);
        Map<String, String> connectionConfigParams = ConnectionParamsUtil.setOrgConfigParams(org);
        String connectionProfilePath = connectionConfigParams.get("networkConfigPath");

        Gateway gateway = connect(userWalletIdentity, connectionProfilePath, userIdentity, org);
        Network network = gateway.getNetwork(Config.CHANNEL_NAME);
        return network.getContract(Config.CHAINCODE_NAME);
    }

    private Gateway connect(String userWalletIdentity, String connectionProfilePath, String userIdentity, String org) throws Exception {
        Identity identity = RegisterUserHyperledger.enrollOrgAppUser(userWalletIdentity, org, userIdentity);
        if(identity == null){
            throw new Exception(String.format("Cannot find %s's idenitty", userWalletIdentity));
        }

        Gateway.Builder builder = Gateway.createBuilder();
        Path networkConfigPath = Paths.get(connectionProfilePath);
        WalletUtil walletUtil = new WalletUtil();
        builder.identity(walletUtil.getWallet(), userWalletIdentity).networkConfig(networkConfigPath).discovery(true);

        return builder.connect();
    }

    public ChaincodeClinicalTrial addClinicalTrial(User user, ClinicalTrialDto clinicalTrialDto) throws Exception {
        ChaincodeClinicalTrial chaincodeClinicalTrial = null;
        try {
            Contract contract  = getContract(user);
            LOG.info("Submit Transaction: AddClinicalTrial");
            String time = StringUtil.parseDate(clinicalTrialDto.getTime());
            String hashedValue = hashValue(clinicalTrialDto.toString());
            String offlineDataUrl = symmetricCryptography.putInfoInDb(clinicalTrialDto.getId());
            byte[] result = contract.submitTransaction(
                    "addClinicalTrial",
                    AccessType.IDLE.toString(),
                    clinicalTrialDto.getPatient(),
                    clinicalTrialDto.getDoctor(),
                    getMedInstitution(user).getId(),
                    time,
                    clinicalTrialDto.getClinicalTrialType().toString(),
                    offlineDataUrl,
                    hashedValue,
                    clinicalTrialDto.getRelevantParameters().isEmpty() ? Boolean.toString(false) : Boolean.toString(true)
            );
            chaincodeClinicalTrial = ChaincodeClinicalTrial.deserialize(result);
            LOG.info("result: " + chaincodeClinicalTrial);
        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return chaincodeClinicalTrial;
    }

    public ChaincodeClinicalTrial defineClinicalTrialAccess(User user, ClinicalTrialDto clinicalTrialDto, ClinicalTrialDto clinicalTrialBefore) throws Exception {
        ChaincodeClinicalTrial chaincodeClinicalTrial = null;
        try {
            Contract contract  = getContract(user);
            String offlineDataUrl = symmetricCryptography.putInfoInDb(clinicalTrialDto.getId());
            String accessType = clinicalTrialDto.getAccessType().toString();
            String hashData = hashValue(clinicalTrialDto.toString());
            String hashedBefore = hashValue(clinicalTrialBefore.toString());
            LOG.info(String.format("Submit Transaction: defineClinicalTrialAccess(%s, %s)", offlineDataUrl, accessType));
            byte[] result = contract.submitTransaction("defineClinicalTrialAccess",
                    offlineDataUrl,
                    accessType,
                    hashData,
                    hashedBefore
            );
            chaincodeClinicalTrial = ChaincodeClinicalTrial.deserialize(result);
            LOG.info("result: " + chaincodeClinicalTrial);
        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return chaincodeClinicalTrial;
    }

    public List<ClinicalTrialPreviewDto> getClinicalTrialsPreview(
            SearchClinicalTrialForm searchClinicalTrialForm,
            String page,
            String perPage,
            User user
    ) throws Exception {
        List<ClinicalTrialPreviewDto> clinicalTrialPreviewDtos = new ArrayList<>();
        try {
            Contract contract  = getContract(user);

            Map<String, String> searchParams = prepareSearchParams(searchClinicalTrialForm);

            byte[] result = contract.evaluateTransaction(
                    "getClinicalTrialsPreview",
                    searchParams.get("clinicalTrialType"),
                    searchParams.get("institution"),
                    searchParams.get("fromStr"),
                    searchParams.get("untilStr"),
                    searchParams.get("orderBy"),
                    searchParams.get("relevantParameters")
            );
            LOG.info(String.format("Evaluate Transaction: getClinicalTrialsPreview(%s, %s, %s, %s, %s, %s), result: %s",
                    searchParams.get("clinicalTrialType"),
                    searchParams.get("institution"),
                    searchParams.get("fromStr"),
                    searchParams.get("untilStr"),
                    searchParams.get("orderBy"),
                    searchParams.get("relevantParameters"), new String(result)));
            ClinicalTrialsPreviewResponse clinicalTrialsPreviewResponse = ClinicalTrialsPreviewResponse.deserialize(result);
            LOG.info("result: " + clinicalTrialsPreviewResponse);
            for (ChaincodeClinicalTrialDto chaincodeClinicalTrialDto : clinicalTrialsPreviewResponse.getClinicalTrialDtoList()){
                ClinicalTrialPreviewDto clinicalTrialPreviewDto = ClinicalTrialPreviewDto
                        .builder()
                        .time(StringUtil.createDate(chaincodeClinicalTrialDto.getTime()))
                        .clinicalTrialType(ClinicalTrialType.valueOf(chaincodeClinicalTrialDto.getClinicalTrialType()))
                        .clinicalTrial(chaincodeClinicalTrialDto.getKey())
                        .accessType(AccessType.valueOf(chaincodeClinicalTrialDto.getAccessType()))
                        .institution(chaincodeClinicalTrialDto.getMedInstitutionId())
                        .patientId(chaincodeClinicalTrialDto.getPatientId())
                        .build();
                clinicalTrialPreviewDtos.add(clinicalTrialPreviewDto);
            }

        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return clinicalTrialPreviewDtos;
    }



    public ClinicalTrialAccessRequest sendAccessRequest(User user, ClinicalTrialAccessSendRequestForm clinicalTrialAccessSendRequestForm) throws Exception {
        ClinicalTrialAccessRequest clinicalTrialAccessRequest = null;
        try {
            Contract contract = getContract(user);
            String clinicalTrialId = clinicalTrialAccessSendRequestForm.getClinicalTrial();
            String requesterId = clinicalTrialAccessSendRequestForm.getSender();
            String patient = clinicalTrialAccessSendRequestForm.getPatient();
            String time = StringUtil.parseDate(clinicalTrialAccessSendRequestForm.getTime());
            LOG.info(String.format("Submit Transaction: sendAccessRequest(%s, %s, %s, %s)", patient, time, requesterId, clinicalTrialId));
            byte[] result = contract.submitTransaction(
                    "sendAccessRequest",
                    patient,
                    time,
                    requesterId,
                    clinicalTrialId
            );
            clinicalTrialAccessRequest = ClinicalTrialAccessRequest.deserialize(result);
            LOG.info("result: " + clinicalTrialAccessRequest);

        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return clinicalTrialAccessRequest;
    }

    public ClinicalTrialAccessRequest trialAccessRequestDecision(User user, ClinicalTrialAccessRequestForm clinicalTrialAccessRequestForm) throws Exception {
        ClinicalTrialAccessRequest clinicalTrialAccessRequest = null;
        try {
            Contract contract = getContract(user);
            String decision =  clinicalTrialAccessRequestForm.getAccessDecision().toString();
            String key = clinicalTrialAccessRequestForm.getId();
            String from = StringUtil.parseDate(clinicalTrialAccessRequestForm.getFrom());
            String until = StringUtil.parseDate(clinicalTrialAccessRequestForm.getUntil());
            String anonymity = Boolean.toString(clinicalTrialAccessRequestForm.isAnonymity());
            LOG.info(String.format("Submit Transaction: defineClinicalTrialAccessRequest(%s, %s, %s, %s, %s)", key, decision, from, until, anonymity));
            byte[] result = contract.submitTransaction(
                    "defineClinicalTrialAccessRequest",
                    key,
                    decision,
                    from,
                    until,
                    anonymity
            );
            clinicalTrialAccessRequest = ClinicalTrialAccessRequest.deserialize(result);
            LOG.info("result: " + clinicalTrialAccessRequest);

        } catch (Exception e) {
            formatExceptionMessage(e);
        }

        return clinicalTrialAccessRequest;
    }

    public List<ClinicalTrialAccessRequestDto> getClinicalTrialAccessRequests(User user, String requestType) throws Exception {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtos = new ArrayList<ClinicalTrialAccessRequestDto>();

        Contract contract = null;
        try {
            contract = getContract(user);
            String invocationMethodName = determineInvocationMethodName(requestType);
            String userId = user.getId();
            byte[] result = contract.evaluateTransaction(invocationMethodName, userId);
            LOG.info(String.format("Evaluate Transaction: %s(%s)", invocationMethodName, userId));
            ClinicalTrialsAccessRequestResponse clinicalTrialsAccessRequestResponse = ClinicalTrialsAccessRequestResponse.deserialize(result);
            LOG.info("result: " + clinicalTrialsAccessRequestResponse);

            for (CCClinicalTrialAccessRequestDto clinicalTrialAccessRequestDto : clinicalTrialsAccessRequestResponse.getClinicalTrialAccessRequestDtoList()){
                ClinicalTrialAccessRequestDto clinicalTrialAccessRequest = ClinicalTrialAccessRequestDto
                        .builder()
                        .id(clinicalTrialAccessRequestDto.getKey())
                        .time(StringUtil.createDate(clinicalTrialAccessRequestDto.getTime()))
                        .clinicalTrialType(ClinicalTrialType.valueOf(clinicalTrialAccessRequestDto.getClinicalTrialType()))
                        .sender(clinicalTrialAccessRequestDto.getRequesterId())
                        .receiver(clinicalTrialAccessRequestDto.getPatientId())
                        .clinicalTrial(clinicalTrialAccessRequestDto.getClinicalTrial())
                        .accessDecision(AccessType.valueOf(clinicalTrialAccessRequestDto.getDecision()))
                        .anonymity(clinicalTrialAccessRequestDto.isAnonymity())
                        .from(StringUtil.createDate(clinicalTrialAccessRequestDto.getAccessAvailableFrom()))
                        .until(StringUtil.createDate(clinicalTrialAccessRequestDto.getAccessAvailableUntil()))
                        .build();
                clinicalTrialAccessRequestDtos.add(clinicalTrialAccessRequest);
            }

        } catch (Exception e) {
            formatExceptionMessage(e);
        }

        return clinicalTrialAccessRequestDtos;
    }

    public ClinicalTrialOfflineDto accessToClinicalTrial(
            User user,
            String key,
            String currentDate,
            String accessUserRole
    ) throws Exception {
        ClinicalTrialOfflineDto clinicalTrialOfflineDto = new ClinicalTrialOfflineDto();
        try {
            Contract contract = getContract(user);
            LOG.info(String.format("Evaluate Transaction: accessToClinicalTrial(%s, %s, %s)", key, currentDate, accessUserRole));
            byte[] result = contract.evaluateTransaction("accessToClinicalTrial", key, currentDate, accessUserRole);
            clinicalTrialOfflineDto =  ClinicalTrialOfflineDto.deserialize(result);
            String offlineDataUrl = symmetricCryptography.getInfoFromDb(clinicalTrialOfflineDto.getOfflineDataUrl());
            clinicalTrialOfflineDto.setOfflineDataUrl(offlineDataUrl);
            LOG.info("result: " + clinicalTrialOfflineDto);
        } catch (Exception e) {
            formatExceptionMessage(e);
        }
        return clinicalTrialOfflineDto;
    }

    private String determineOrg(User user){
        if(user.getRole().equals(Constants.ROLE_COMMON_USER) || user.getRole().equals(Constants.ROLE_SUPER_ADMIN)){
            return Config.COMMON_USER_ORG;
        } else {
            return getMedInstitution(user).getMembershipOrganizationId();
        }
    }

    private MedInstitution getMedInstitution(User user){
        MedWorker medWorker = (MedWorker) user;
        return medWorker.getMedInstitution();
    }

    private Map<String, String> prepareSearchParams(SearchClinicalTrialForm searchClinicalTrialForm){
        String clinicalTrialType = searchClinicalTrialForm.getClinicalTrialType() == null ? "" : searchClinicalTrialForm.getClinicalTrialType();
        String institution = searchClinicalTrialForm.getInstitutions() == null ? "" : searchClinicalTrialForm.getInstitutions();

        String fromStr;
        if(searchClinicalTrialForm.getFrom() == null){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -6);
            Date from = calendar.getTime();
            fromStr = StringUtil.parseDate(from);
        } else {
            fromStr = StringUtil.parseDate(searchClinicalTrialForm.getFrom());
        }
        String untilStr;
        if(searchClinicalTrialForm.getFrom() == null){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            Date until = calendar.getTime();
            untilStr = StringUtil.parseDate(until);
        } else {
            untilStr = StringUtil.parseDate(searchClinicalTrialForm.getUntil());
        }
        String orderBy = searchClinicalTrialForm.getOrderBy() == null ? "desc" : searchClinicalTrialForm.getOrderBy();
        String relevantParameters = searchClinicalTrialForm.isRelevantParameters() ?
                Boolean.toString(searchClinicalTrialForm.isRelevantParameters()) : "";

        return new HashMap<String, String>(){{
            put("clinicalTrialType", clinicalTrialType);
            put("institution", institution);
            put("fromStr", fromStr);
            put("untilStr", untilStr);
            put("orderBy", orderBy);
            put("relevantParameters", relevantParameters);
        }};
    }

    private String determineInvocationMethodName(String requestType){
        if(requestType.equals("history")){
            return "getTrialAccessRequestsHistory";
        } else if(requestType.equals("requested")){
            return "getRequestedTrialAccesses";
        } else {
            return "getTrialAccessRequests";
        }
    }

    private void formatExceptionMessage(Exception e) throws Exception{
        String msg= e.getMessage();
        String errorMsg = msg.substring(msg.lastIndexOf(":")+1);
        String localizedMsg = e.getLocalizedMessage();
        throw new Exception(errorMsg);
    }

    private String hashValue(String originalString){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    public boolean validData(String originalString, String savedHashValue){
        String originalStringHash = hashValue(originalString);
        return originalStringHash.equals(savedHashValue);
    }

}
