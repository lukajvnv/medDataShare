package rs.ac.uns.ftn.medDataShare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.dto.form.EditClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.user.CommonUserDto;
import rs.ac.uns.ftn.medDataShare.service.CommonUserService;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/commonUser")
public class CommonUserController {

    @Autowired
    private CommonUserService commonUserService;

    @PostMapping
    public CommonUserDto editUser(@Valid @RequestBody CommonUserDto commonUserDto, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        return commonUserService.editUser(commonUserDto);
    }

    @GetMapping("/clinicalTrial")
    public List<ClinicalTrialDto> getUserClinicalTrials(
            @RequestParam(value = "accessType", required = false, defaultValue="") String accessType
    ){
        if(accessType.isEmpty()){
            return commonUserService.getUserClinicalTrials();
        } else {
            return commonUserService.getUserClinicalTrialsDefineAccess();
        }
    }

    @PostMapping("/clinicalTrial")
    public ClinicalTrialDto updateClinicalTrial(@Valid @RequestBody EditClinicalTrialForm editClinicalTrialForm, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        return commonUserService.updateClinicalTrial(editClinicalTrialForm);
    }
}
