package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.medDataShare.converter.CommonUserConverter;
import rs.ac.uns.ftn.medDataShare.converter.declaration.UserInterface;
import rs.ac.uns.ftn.medDataShare.dto.form.EditClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.user.CommonUserDto;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;

import java.util.List;

@Service
public class CommonUserService implements UserInterface<CommonUser, CommonUserDto, CommonUserDto> {

    @Autowired
    private CommonUserConverter commonUserConverter;

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private FhirService fhirService;

    @Autowired
    private HyperledgerService hyperledgerService;

    @Override
    public CommonUserDto editUser(CommonUserDto object) {
        CommonUser commonUser = commonUserConverter.convertFromDto(object, true);
        return commonUserConverter.convertToDto(commonUserRepository.save(commonUser));
    }

    public List<ClinicalTrialDto> getUserClinicalTrials(){
        User user = (User) userDetailsService.getLoggedUser();
        return fhirService.searchImagingStudy(user.getId(), false);
    }

    public List<ClinicalTrialDto> getUserClinicalTrialsDefineAccess(){
        User user = (User) userDetailsService.getLoggedUser();
        return fhirService.searchImagingStudy(user.getId(), true);
    }

    public ClinicalTrialDto updateClinicalTrial(EditClinicalTrialForm editClinicalTrialForm) throws Exception {
        User user = (User) userDetailsService.getLoggedUser();
        ClinicalTrialDto clinicalTrialBefore = fhirService.getImagingStudy(editClinicalTrialForm.getId());
        ClinicalTrialDto clinicalTrial = fhirService.updateClinicalTrial(user.getId(), editClinicalTrialForm);
        hyperledgerService.defineClinicalTrialAccess(user, clinicalTrial, clinicalTrialBefore);
        return clinicalTrial;
    }
}
