package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rs.ac.uns.ftn.medDataShare.converter.*;
import rs.ac.uns.ftn.medDataShare.dto.form.EditClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.user.CommonUserDto;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.repository.ClinicalTrialRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommonUserService implements UserInterface<CommonUser, CommonUserDto, CommonUserDto> {

    @Autowired
    private CommonUserConverter commonUserConverter;

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private ClinicalTrialRepository clinicalTrialRepository;

    @Autowired
    private ClinicalTrialConverter clinicalTrialConverter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public CommonUserDto editUser(CommonUserDto object) {
        CommonUser commonUser = commonUserConverter.convertFromDto(object, true);
        return commonUserConverter.convertToDto(commonUserRepository.save(commonUser));
    }

    public List<ClinicalTrialDto> getUserClinicalTrials(){
        User user = (User) userDetailsService.getLoggedUser();
        return clinicalTrialRepository
                .findByPatient(user.getId())
                .stream().map(this::convert).collect(Collectors.toList());
    }

    public List<ClinicalTrialDto> getUserClinicalTrialsDefineAccess(){
        User user = (User) userDetailsService.getLoggedUser();
        return clinicalTrialRepository
                .findByPatientAndAccessType(user.getId(), AccessType.IDLE)
                .stream().map(this::convert).collect(Collectors.toList());
    }

    private ClinicalTrialDto convert(ClinicalTrial clinicalTrial){
        return clinicalTrialConverter.convertToDto(clinicalTrial);
    }

    public ClinicalTrialDto updateClinicalTrial(EditClinicalTrialForm editClinicalTrialForm){
        String clinicalTrailId = editClinicalTrialForm.getId();
        AccessType accessType = editClinicalTrialForm.getAccessType();
        Optional<ClinicalTrial> optionalClinicalTrial = clinicalTrialRepository.findById(clinicalTrailId);
        if(!optionalClinicalTrial.isPresent()){
            throw new ValidationException("Invalid id");
        }
        ClinicalTrial clinicalTrial = optionalClinicalTrial.get();
        clinicalTrial.setAccessType(accessType);
        return convert(clinicalTrialRepository.save(clinicalTrial));
    }
}
