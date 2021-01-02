package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import rs.ac.uns.ftn.medDataShare.converter.AdminConverter;
import rs.ac.uns.ftn.medDataShare.converter.CommonUserConverter;
import rs.ac.uns.ftn.medDataShare.converter.MedInstitutionConverter;
import rs.ac.uns.ftn.medDataShare.converter.MedWorkerConverter;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestDto;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessSendRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialPreviewDto;
import rs.ac.uns.ftn.medDataShare.dto.form.SearchClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedInstitutionDto;
import rs.ac.uns.ftn.medDataShare.dto.user.UserDto;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.repository.AdminRepository;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedInstitutionRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.util.Constants;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
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

    public List<ClinicalTrialAccessRequestDto> getClinicalTrialAccessRequests(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 6);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        ClinicalTrialAccessRequestDto clinicalTrialAccessRequestDto1 = ClinicalTrialAccessRequestDto
                .builder()
                .id("123456789")
                .time(new Date())
                .clinicalTrialType(ClinicalTrialType.CT)
                .sender("fjdkfdfdjfd")
                .receiver("jfdkljdfjdfd")
                .clinicalTrial("flkjfdkjfldk")
                .accessDecision(AccessType.UNCONDITIONAL)
                .anonymity(true)
                .from(new Date())
                .until(cal.getTime())
                .build();
        ClinicalTrialAccessRequestDto clinicalTrialAccessRequestDto2 = ClinicalTrialAccessRequestDto
                .builder()
                .id("9876543210")
                .time(new Date())
                .clinicalTrialType(ClinicalTrialType.CT)
                .sender("hahahahahahahh")
                .receiver("mamamamamamam")
                .clinicalTrial("qqrerewdsffdfds")
                .accessDecision(AccessType.IDLE)
                .anonymity(true)
                .from(new Date())
                .until(cal.getTime())
                .build();

        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtos = new ArrayList<ClinicalTrialAccessRequestDto>(){
            {
                add(clinicalTrialAccessRequestDto1);
                add(clinicalTrialAccessRequestDto2);
            }
        };

        return clinicalTrialAccessRequestDtos;
    }

    public ClinicalTrialAccessRequestDto trialAccessRequestDecision(ClinicalTrialAccessRequestForm clinicalTrialAccessRequestForm){
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
    ){
        ClinicalTrialPreviewDto clinicalTrialPreviewDto1 = ClinicalTrialPreviewDto
                .builder()
                .time(new Date())
                .clinicalTrialType(ClinicalTrialType.CT)
                .clinicalTrial("flkjfdkjfldk")
                .accessType(AccessType.UNCONDITIONAL)
                .institution("Institution1")
                .build();
        ClinicalTrialPreviewDto clinicalTrialPreviewDto2 = ClinicalTrialPreviewDto
                .builder()
                .time(new Date())
                .clinicalTrialType(ClinicalTrialType.CBC)
                .clinicalTrial("qqrerewdsffdfds")
                .accessType(AccessType.IDLE)
                .institution("Institution2")
                .build();

        List<ClinicalTrialPreviewDto> clinicalTrialAccessRequestDtos = new ArrayList<ClinicalTrialPreviewDto>(){
            {
                add(clinicalTrialPreviewDto1);
                add(clinicalTrialPreviewDto2);
            }
        };


        return clinicalTrialAccessRequestDtos;
    }

    public ClinicalTrialAccessSendRequestForm sendAccessRequest(ClinicalTrialAccessSendRequestForm clinicalTrialAccessSendRequestForm){
        User user = (User) userDetailsService.getLoggedUser();
        clinicalTrialAccessSendRequestForm.setSender(user.getId());
        clinicalTrialAccessSendRequestForm.setTime(new Date());
        return new ClinicalTrialAccessSendRequestForm();
    }

    public MedInstitutionDto convert(MedInstitution medInstitution){
        return medInstitutionConverter.convertToDto(medInstitution);
    }
}
