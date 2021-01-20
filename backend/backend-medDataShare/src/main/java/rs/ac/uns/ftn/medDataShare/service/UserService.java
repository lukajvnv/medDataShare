package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.medDataShare.chaincode.dto.ClinicalTrialOfflineDto;
import rs.ac.uns.ftn.medDataShare.converter.AdminConverter;
import rs.ac.uns.ftn.medDataShare.converter.CommonUserConverter;
import rs.ac.uns.ftn.medDataShare.converter.MedInstitutionConverter;
import rs.ac.uns.ftn.medDataShare.converter.MedWorkerConverter;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestDto;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessSendRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialPreviewDto;
import rs.ac.uns.ftn.medDataShare.dto.form.SearchClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedInstitutionDto;
import rs.ac.uns.ftn.medDataShare.dto.user.UserDto;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.AdminRepository;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedInstitutionRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.util.Constants;
import rs.ac.uns.ftn.medDataShare.util.StringUtil;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private MedInstitutionRepository medInstitutionRepository;

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private MedWorkerConverter medWorkerConverter;

    @Autowired
    private AdminConverter adminConverter;

    @Autowired
    private CommonUserConverter commonUserConverter;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MedInstitutionConverter medInstitutionConverter;

    @Autowired
    private HyperledgerService hyperledgerService;

    @Autowired
    private FhirService fhirService;

    public UserDto getCurrentUser(){
        User user = (User) userDetailsService.getLoggedUser();
        UserDto userDto = convert(user);
        return userDto;
    }

    public String resetPassword(String newPassword){
        User user = (User) userDetailsService.getLoggedUser();

        if(userPasswordEncoder.matches(newPassword, user.getPassword())){
            throw new AuthException("Password does not change since new value is equal to current");
        }

        user.setPassword(userPasswordEncoder.encode(newPassword));
        switch (user.getRole()){
            case Constants.ROLE_SUPER_ADMIN:
                adminRepository.save((Admin)user);
                break;
            case Constants.ROLE_MED_ADMIN:
            case Constants.ROLE_DOCTOR:
                medWorkerRepository.save((MedWorker)user);
                break;
            case Constants.ROLE_COMMON_USER:
            default:
                commonUserRepository.save((CommonUser)user);
        }

        return "Success";
    }

    private UserDto convert(User user){
        switch (user.getRole()){
            case Constants.ROLE_SUPER_ADMIN:
                return adminConverter.convertToDto((Admin)user);
            case Constants.ROLE_MED_ADMIN:
            case Constants.ROLE_DOCTOR:
                return medWorkerConverter.convertToDto((MedWorker) user);
            case Constants.ROLE_COMMON_USER:
            default:
                return commonUserConverter.convertToDto((CommonUser)user);
        }
    }

    public List<ClinicalTrialAccessRequestDto> getClinicalTrialAccessRequests(String requestType) throws Exception {
        User user = (User) userDetailsService.getLoggedUser();
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtos = hyperledgerService.getClinicalTrialAccessRequests(user, requestType);

        return clinicalTrialAccessRequestDtos;
    }

    public ClinicalTrialAccessRequestDto trialAccessRequestDecision(ClinicalTrialAccessRequestForm clinicalTrialAccessRequestForm) throws Exception {
        User user = (User) userDetailsService.getLoggedUser();
        hyperledgerService.trialAccessRequestDecision(user, clinicalTrialAccessRequestForm);
        return new ClinicalTrialAccessRequestDto();
    }

    public List<MedInstitutionDto> getMedInstitutionDatasource(){
        return medInstitutionRepository.findAll().
                stream().map(this::convert).collect(Collectors.toList());
    }

    public List<ClinicalTrialPreviewDto> getClinicalTrialsPreview(
            SearchClinicalTrialForm searchClinicalTrialForm,
            String page,
            String perPage
    ) throws Exception {
        User user = (User) userDetailsService.getLoggedUser();
        List<ClinicalTrialPreviewDto> clinicalTrialPreviewDtos = hyperledgerService.getClinicalTrialsPreview(searchClinicalTrialForm, page, perPage, user);
        return clinicalTrialPreviewDtos;
    }

    public ClinicalTrialAccessSendRequestForm sendAccessRequest(ClinicalTrialAccessSendRequestForm clinicalTrialAccessSendRequestForm) throws Exception {
        User user = (User) userDetailsService.getLoggedUser();
        clinicalTrialAccessSendRequestForm.setSender(user.getId());
        clinicalTrialAccessSendRequestForm.setTime(new Date());
        hyperledgerService.sendAccessRequest(user, clinicalTrialAccessSendRequestForm);
        return clinicalTrialAccessSendRequestForm;
    }

    public ClinicalTrialDto getClinicalTrial(String clinicalTrialId, String accessUserRole) throws Exception {
        User user = userDetailsService.getLoggedUser();
        String currentDate = StringUtil.parseDate(new Date());
        ClinicalTrialOfflineDto clinicalTrialOfflineChaincodeDto = hyperledgerService.accessToClinicalTrial(user, clinicalTrialId, currentDate, accessUserRole);
        ClinicalTrialDto offlineClinicalTrial = fhirService.getImagingStudy(clinicalTrialOfflineChaincodeDto.getOfflineDataUrl());
        if(hyperledgerService.validData(offlineClinicalTrial.toString(), clinicalTrialOfflineChaincodeDto.getHashData())){
            if(clinicalTrialOfflineChaincodeDto.isAnonymity()){
                //anonymize data
                offlineClinicalTrial = anonymizeData(offlineClinicalTrial);
            }
            return offlineClinicalTrial;
        } else {
            throw new Exception("Data are corrupted!!!");
        }
    }

    public MedInstitutionDto convert(MedInstitution medInstitution){
        return medInstitutionConverter.convertToDto(medInstitution);
    }

    public ClinicalTrialDto anonymizeData(ClinicalTrialDto clinicalTrialDto){
        User patient = commonUserRepository.getOne(clinicalTrialDto.getPatient());
        String patientFirstName = patient.getFirstName();
        String patientLastName = patient.getLastName();
        String anonymizeIntroduction = StringUtil.anonymizePatientData(clinicalTrialDto.getIntroduction(), patientFirstName, patientLastName);
        String anonymizeRelevantParameters = StringUtil.anonymizePatientData(clinicalTrialDto.getRelevantParameters(), patientFirstName, patientLastName);;
        String anonymizeConclusion = StringUtil.anonymizePatientData(clinicalTrialDto.getConclusion(), patientFirstName, patientLastName);;

        clinicalTrialDto.setIntroduction(anonymizeIntroduction);
        clinicalTrialDto.setRelevantParameters(anonymizeRelevantParameters);
        clinicalTrialDto.setConclusion(anonymizeConclusion);
        return clinicalTrialDto;
    }
}
