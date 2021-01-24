package rs.ac.uns.ftn.medDataShare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestDto;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessSendRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialPreviewDto;
import rs.ac.uns.ftn.medDataShare.dto.form.ResetPasswordForm;
import rs.ac.uns.ftn.medDataShare.dto.form.SearchClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedInstitutionDto;
import rs.ac.uns.ftn.medDataShare.dto.user.UserDto;
import rs.ac.uns.ftn.medDataShare.service.FhirService;
import rs.ac.uns.ftn.medDataShare.service.UserService;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FhirService fhirService;

    @GetMapping("/current")
    public UserDto getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid @RequestBody ResetPasswordForm resetPasswordForm, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new AuthException(errorMsg);
        }

        String newPassword = resetPasswordForm.getPassword();
        String newPasswordRepeated = resetPasswordForm.getPasswordRepeat();
        if(!ValidationUtil.passwordMatch(newPassword, newPasswordRepeated)){
            throw new AuthException("Password does not match");
        }

        userService.resetPassword(newPassword);

        return "Success";
    }

    @GetMapping("/trialAccessRequest")
    public List<ClinicalTrialAccessRequestDto> getClinicalTrialAccessRequests(
            @RequestParam(value = "requestType", required = false, defaultValue="") String requestType
    ) throws Exception {
        return userService.getClinicalTrialAccessRequests(requestType);
    }

    @PostMapping("/trialAccessRequest")
    public ClinicalTrialAccessRequestDto trialAccessRequestDecision(
            @Valid @RequestBody ClinicalTrialAccessRequestForm clinicalTrialAccessRequestForm,
            BindingResult result
    ) throws Exception {
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }
        return userService.trialAccessRequestDecision(clinicalTrialAccessRequestForm);
    }

    @GetMapping("/institution")
    public List<MedInstitutionDto> getMedInstitutionDatasource(){
        return userService.getMedInstitutionDatasource();
    }

    @PostMapping("/clinicalTrialPreview")
    public List<ClinicalTrialPreviewDto> getClinicalTrialsPreview(
            @Valid @RequestBody SearchClinicalTrialForm searchClinicalTrialForm,
            @RequestParam(value = "page", required = false, defaultValue="") String page,
            @RequestParam(value = "perPage", required = false, defaultValue="") String perPage,
            BindingResult result
    ) throws Exception {
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }
        return userService.getClinicalTrialsPreview(searchClinicalTrialForm, page, perPage);
    }

    @PostMapping("/sendAccessRequest")
    public ClinicalTrialAccessSendRequestForm sendAccessRequest(
            @Valid @RequestBody ClinicalTrialAccessSendRequestForm clinicalTrialAccessSendRequestForm,
            BindingResult result
    ) throws Exception {
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }
        return userService.sendAccessRequest(clinicalTrialAccessSendRequestForm);
    }

    @GetMapping("/clinicalTrial/{clinicalTrialId}")
    public ClinicalTrialDto getClinicalTrial(
            @PathVariable String clinicalTrialId,
            @RequestParam(value = "accessUserRole", required = false, defaultValue="requester") String accessUserRole
    ) throws Exception {
        return userService.getClinicalTrial(clinicalTrialId, accessUserRole);
    }

    @GetMapping("/image/{binaryId}")
    public byte[] getImage(@PathVariable String binaryId) {
        return fhirService.getImage(binaryId);
    }

    @GetMapping("/file/{clinicalTrialId}")
    public byte[] exportInPdf(@PathVariable String clinicalTrialId) {
        return userService.exportInPdf(clinicalTrialId);
    }
}
