package rs.ac.uns.ftn.medDataShare.contract;

import com.google.protobuf.ByteString;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ext.sbe.StateBasedEndorsement;
import org.hyperledger.fabric.shim.ext.sbe.impl.StateBasedEndorsementFactory;
import rs.ac.uns.ftn.medDataShare.component.ClinicalTrialContext;
import rs.ac.uns.ftn.medDataShare.dao.ClinicalTrialAccessRequestDAO;
import rs.ac.uns.ftn.medDataShare.dto.*;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrialAccessRequest;
import rs.ac.uns.ftn.medDataShare.enumeration.AccessType;
import rs.ac.uns.ftn.medDataShare.enumeration.AccessUserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Contract(name = "rs.ac.uns.ftn.clinicalTrial",
        info = @Info(
                title = "ClinicalTrial contract",
                description = "",
                version = "0.0.1",
                license = @License(name = "SPDX-License-Identifier: Apache-2.0", url = ""),
                contact = @Contact(email = "luka.ra109@uns.ac.rs", name = "Luka Jovanovic", url = "https://github.com/lukajvnv")
        )
)
@Default
public class ClinicalTrialContract implements ContractInterface {

    private enum ClinicalTrialContractErrors {
        CLINICAL_TRIAL_NOT_FOUND,
        CLINICAL_TRIAL_ACCESS_REQUEST_NOT_FOUND,
        UNAUTHORIZED_EDIT_ACCESS,
        VALIDATE_CLINICAL_TRIAL_ACCESS_ERROR
    }

    private final static Logger LOG = Logger.getLogger(ClinicalTrialContract.class.getName());

    @Override
    public Context createContext(ChaincodeStub stub) {
        LOG.info("invoke createContext");
        return new ClinicalTrialContext(stub);
    }

    @Override
    public void beforeTransaction(Context ctx) {
        List<String> paramList = ctx.getStub().getParameters();
        String params = String.join(",", paramList);
        String function = ctx.getStub().getFunction();

        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOG.info(String.format("Function name: %s, params: [%s]", function, params));
        clientIdentityInfo(ctx);
    }

    private void clientIdentityInfo(final Context ctx){
//        ByteString signature = ctx.getStub().getSignedProposal().getSignature();
//        byte[] c = ctx.getStub().getCreator();
        try {
            String userIdentityId = ctx.getClientIdentity().getAttributeValue("userIdentityId");
            String clientIdentityId = ctx.getClientIdentity().getId();
            String clientIdentityMspId = ctx.getClientIdentity().getMSPID();
            LOG.info(String.format("clientIdentityId: %s, clientIdentityMspId: %s, userIdentityId: %s", clientIdentityId, clientIdentityMspId, userIdentityId));
        } catch (Exception e) {
            String errorMessage = "Error during method ctx.getClientIdentity.getAttributeValue(...)";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.UNAUTHORIZED_EDIT_ACCESS.toString());
        }

    }

    @Override
    public void afterTransaction(Context ctx, Object result) {
        String function = ctx.getStub().getFunction();
        LOG.info(String.format("Function name: %s", function));
        if(result == null){
            LOG.info("retval is null");
        } else {
            LOG.info(String.format("object type: %s, else: ", result.getClass().getTypeName(), result.getClass().toString()));
            if(result.getClass().getTypeName().equals("java.util.ArrayList")){
                ArrayList<String> list = (ArrayList<String>) result;
                LOG.info(String.format("arraylist size: %d", list.size()));
            }
        }
        LOG.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END <<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transaction
    public void instantiate(ClinicalTrialContext ctx) {

    }

    @Transaction
    public ClinicalTrial addClinicalTrial(
            ClinicalTrialContext ctx,
            String accessType,
            String patientId,
            String doctorId,
            String medInstitutionId,
            String time,
            String clinicalTrialType,
            String offlineDataUrl,
            String hashData,
            boolean relevantParameters
    ) {
        String key = ctx.getStub().getTxId();
        ClinicalTrial clinicalTrial = ctx.getClinicalTrialDAO().addClinicalTrial(
                key,
                accessType,
                patientId,
                doctorId,
                medInstitutionId,
                time,
                clinicalTrialType,
                offlineDataUrl,
                hashData,
                relevantParameters
        );

        return clinicalTrial;
    }

    @Transaction
    public ClinicalTrial defineClinicalTrialAccess(
            ClinicalTrialContext ctx,
            String offlineDataUrl,
            String accessType,
            String updatedDataHash,
            String oldDataHash
    ) {
        ClinicalTrial clinicalTrial = ctx.getClinicalTrialDAO().getClinicalTrialByOfflineDataUrl(offlineDataUrl);
        if (clinicalTrial != null) {
            authorizeRequest(ctx, clinicalTrial.getPatientId(), "defineClinicalTrialAccess");
            String savedOldHash = clinicalTrial.getHashData();
            if(oldDataHash.equals(savedOldHash)){
                return ctx.getClinicalTrialDAO().defineClinicalTrialAccess(clinicalTrial.getKey(), accessType, updatedDataHash);
            } else {
                String errorMessage = String.format("ClinicalTrial with offlineUrl %s is corrupted!!!", offlineDataUrl);
                System.out.println(errorMessage);
                throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
            }
        } else {
            String errorMessage = String.format("ClinicalTrial with offlineUrl %s does not exist", offlineDataUrl);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
        }
    }

    private void authorizeRequest(ClinicalTrialContext ctx, String userIdentityInDb, String methodName){
        String userIdentityId = "";
        try {
            userIdentityId = ctx.getClientIdentity().getAttributeValue("userIdentityId");
        } catch (Exception e) {
            String errorMessage = "Error during method ctx.getClientIdentity.getAttributeValue(...)";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.UNAUTHORIZED_EDIT_ACCESS.toString());
        }
        if(!userIdentityId.equals(userIdentityInDb)) {
            String errorMessage = String.format("Error during method: %s , identified user does not have write rights", methodName);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.UNAUTHORIZED_EDIT_ACCESS.toString());
        }
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrial getClinicalTrial(ClinicalTrialContext ctx, String key) {
        if (ctx.getClinicalTrialDAO().clinicalTrialExist(key)) {
            return ctx.getClinicalTrialDAO().getClinicalTrial(key);
        } else {
            String errorMessage = String.format("ClinicalTrial %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
        }
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean clinicalTrialExist(final ClinicalTrialContext ctx, final String key) {
        return ctx.getClinicalTrialDAO().clinicalTrialExist(key);
    }

    @Transaction
    public ClinicalTrialAccessRequest addClinicalTrialAccessRequest(
            ClinicalTrialContext ctx,
            String patientId,
            String time,
            String requesterId,
            String decision,
            String accessAvailableFrom,
            String accessAvailableUntil,
            boolean anonymity,
            String clinicalTrialId
    ) {
        ClinicalTrial clinicalTrial = getClinicalTrial(ctx, clinicalTrialId);

        return ctx.getClinicalTrialAccessRequestDAO().addClinicalTrialAccessRequest(
                patientId,
                time,
                requesterId,
                decision,
                accessAvailableFrom,
                accessAvailableUntil,
                anonymity,
                clinicalTrial
        );
    }

    @Transaction
    public ClinicalTrialAccessRequest sendAccessRequest(
            ClinicalTrialContext ctx,
            String patientId,
            String time,
            String requesterId,
            String clinicalTrialId
    ) {
        ClinicalTrial clinicalTrial = getClinicalTrial(ctx, clinicalTrialId);

        authorizeRequest(ctx, requesterId, "addClinicalTrialAccessRequest(validate requester)");
        if(!patientId.equals(clinicalTrial.getPatientId())){
            throw new ChaincodeException("Patient does not match with saved one in trial", ClinicalTrialContractErrors.UNAUTHORIZED_EDIT_ACCESS.toString());
        }
        if(requesterId.equals(clinicalTrial.getPatientId())){
            throw new ChaincodeException("Requester is owner of this trial", ClinicalTrialContractErrors.UNAUTHORIZED_EDIT_ACCESS.toString());
        }

        if (clinicalTrial.getAccessType().equals(AccessType.FORBIDDEN)){
            LOG.info("forbidden");
            throw new ChaincodeException("Access to this trial is FORBIDDEN", ClinicalTrialContractErrors.VALIDATE_CLINICAL_TRIAL_ACCESS_ERROR.toString());
        } else if(clinicalTrial.getAccessType().equals(AccessType.UNCONDITIONAL)){
            return addClinicalTrialAccessRequest(
                    ctx, patientId, time, requesterId, AccessType.UNCONDITIONAL, "X", "X", false, clinicalTrialId);
        } else {
            return ctx.getClinicalTrialAccessRequestDAO().addClinicalTrialAccessRequest(
                    patientId,
                    time,
                    requesterId,
                    clinicalTrial
            );
        }
    }

    @Transaction
    public ClinicalTrialAccessRequest defineClinicalTrialAccessRequest(
            ClinicalTrialContext ctx,
            String key,
            String decision,
            String accessAvailableFrom,
            String accessAvailableUntil,
            boolean anonymity
    ) {
        ClinicalTrialAccessRequestDAO clinicalTrialAccessRequestDAO = ctx.getClinicalTrialAccessRequestDAO();
        if (clinicalTrialAccessRequestDAO.clinicalTrialAccessRequestExist(key)) {
            ClinicalTrialAccessRequest clinicalTrialAccessRequest = clinicalTrialAccessRequestDAO.getClinicalTrialAccessRequest(key);
            authorizeRequest(ctx, clinicalTrialAccessRequest.getPatientId(), "defineClinicalTrialAccessRequest(by patient)");
            return ctx.getClinicalTrialAccessRequestDAO().defineClinicalTrialAccessRequest(
                    key, decision, accessAvailableFrom, accessAvailableUntil, anonymity, clinicalTrialAccessRequest
            );
        } else {
            String errorMessage = String.format("ClinicalTrialAccessRequest %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_ACCESS_REQUEST_NOT_FOUND.toString());
        }
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialAccessRequest getClinicalTrialAccessRequest(ClinicalTrialContext ctx, String key) {
        ClinicalTrialAccessRequestDAO clinicalTrialAccessRequestDAO = ctx.getClinicalTrialAccessRequestDAO();
        if (clinicalTrialAccessRequestDAO.clinicalTrialAccessRequestExist(key)) {
            return clinicalTrialAccessRequestDAO.getClinicalTrialAccessRequest(key);
        }

        String errorMessage = String.format("ClinicalTrialAccessRequest %s does not exist", key);
        System.out.println(errorMessage);
        throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_ACCESS_REQUEST_NOT_FOUND.toString());
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean clinicalTrialAccessRequestExist(final ClinicalTrialContext ctx, final String key) {
        return ctx.getClinicalTrialAccessRequestDAO().clinicalTrialAccessRequestExist(key);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsPreviewResponse getClinicalTrialsPreview(
            ClinicalTrialContext ctx,
            String clinicalTrialType,
            String medInstitution,
            String from,
            String until,
            String sortingOrder,
            String relevantParameters
    ) {
        List<ClinicalTrialDto> clinicalTrials = ctx.getClinicalTrialDAO().getClinicalTrialsPreview(
                clinicalTrialType,
                medInstitution,
                from,
                until,
                sortingOrder,
                relevantParameters
        );
        return new ClinicalTrialsPreviewResponse(clinicalTrials.size(), clinicalTrials);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsAccessRequestResponse getRequestedTrialAccesses(
            ClinicalTrialContext ctx,
            String requester
    ) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequests =
                ctx.getClinicalTrialAccessRequestDAO().getRequestedTrialAccesses(
                requester
        );
        return new ClinicalTrialsAccessRequestResponse(clinicalTrialAccessRequests.size(), clinicalTrialAccessRequests);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsAccessRequestResponse getTrialAccessRequestsHistory(
            ClinicalTrialContext ctx,
            String patient
    ) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequests =
                ctx.getClinicalTrialAccessRequestDAO().getTrialAccessRequestsHistory(
                        patient
                );
        return new ClinicalTrialsAccessRequestResponse(clinicalTrialAccessRequests.size(), clinicalTrialAccessRequests);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsAccessRequestResponse getTrialAccessRequests(
            ClinicalTrialContext ctx,
            String patient
    ) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequests =
                ctx.getClinicalTrialAccessRequestDAO().getTrialAccessRequests(
                        patient
                );
        return new ClinicalTrialsAccessRequestResponse(clinicalTrialAccessRequests.size(), clinicalTrialAccessRequests);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialOfflineDto accessToClinicalTrial(
            ClinicalTrialContext ctx,
            String key,
            String currentDate,
            String accessUserRole
    ) {
        ClinicalTrialAccessRequest clinicalTrialAccessRequest = getClinicalTrialAccessRequest(ctx, key);
        if(accessUserRole.equals(AccessUserRole.SENDER)){
            authorizeRequest(ctx, clinicalTrialAccessRequest.getRequesterId(), "accessToClinicalTrial(requester auth)");
        } else if(accessUserRole.equals(AccessUserRole.RECEIVER)){
            authorizeRequest(ctx, clinicalTrialAccessRequest.getPatientId(), "accessToClinicalTrial(patient auth)");
        } else {
            throw new ChaincodeException("Inadequate accessUserRole", ClinicalTrialContractErrors.CLINICAL_TRIAL_ACCESS_REQUEST_NOT_FOUND.toString());
        }
        boolean anonymity = clinicalTrialAccessRequest.isAnonymity();

        ClinicalTrial clinicalTrial = getClinicalTrial(ctx, clinicalTrialAccessRequest.getClinicalTrial());
        if(accessUserRole.equals(AccessUserRole.SENDER)){
            //requester
            if(currentDate.isEmpty()){
                throw new ChaincodeException("Inadequate currentDate", ClinicalTrialContractErrors.VALIDATE_CLINICAL_TRIAL_ACCESS_ERROR.toString());
            }

            if(clinicalTrialAccessRequest.getDecision().equals(AccessType.UNCONDITIONAL)){
                if(clinicalTrialAccessRequest.getAccessAvailableFrom().equals("X")){   // UNCONDITIONALLY UNCONDITIONAL
                    return new ClinicalTrialOfflineDto(clinicalTrial.getOfflineDataUrl(), clinicalTrial.getHashData(), anonymity);
                } else {
                    int lowerLimit = currentDate.compareTo(clinicalTrialAccessRequest.getAccessAvailableFrom());
                    int upperLimit = currentDate.compareTo(clinicalTrialAccessRequest.getAccessAvailableUntil());
                    //date access requirements
                    if(lowerLimit >= 0 && upperLimit <= 0){
                        return new ClinicalTrialOfflineDto(clinicalTrial.getOfflineDataUrl(), clinicalTrial.getHashData(), anonymity);
                    } else {
                        throw new ChaincodeException("User has no access rights[date range]", ClinicalTrialContractErrors.VALIDATE_CLINICAL_TRIAL_ACCESS_ERROR.toString());
                    }
                }
            } else {
                throw new ChaincodeException("User has no access rights[decision]", ClinicalTrialContractErrors.VALIDATE_CLINICAL_TRIAL_ACCESS_ERROR.toString());
            }
        }
        //patient
        return new ClinicalTrialOfflineDto(clinicalTrial.getOfflineDataUrl(), clinicalTrial.getHashData(), anonymity);
    }
}
