package rs.ac.uns.ftn.medDataShare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;
import rs.ac.uns.ftn.medDataShare.dto.form.MedWorkerForm;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.service.MedAdminService;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medAdmin")
public class MedAdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MedAdminService medAdminService;

    @GetMapping("/doctor")
    public List<MedWorkerDto> getAllMedInstitutionDoctors(){
        return medAdminService.getAllMedInstitutionDoctors();
    }

    @PostMapping("/doctor")
    public MedWorkerDto addNewMedInstitutionDoctor(@Valid @RequestBody MedWorkerForm medWorkerDto, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        if(userDetailsService.getUser(medWorkerDto.getEmail()) != null){
            throw new AuthException("user with this username already exsists");
        }

        return medAdminService.addNewMedInstitutionDoctor(medWorkerDto);
    }
}
