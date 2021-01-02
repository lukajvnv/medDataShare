package rs.ac.uns.ftn.medDataShare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedInstitutionDto;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;
import rs.ac.uns.ftn.medDataShare.dto.form.MedWorkerForm;
import rs.ac.uns.ftn.medDataShare.dto.user.UserDto;
import rs.ac.uns.ftn.medDataShare.repository.MedInstitutionRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.service.SuperAdminService;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {

    @Autowired
    private MedInstitutionRepository medInstitutionRepository;

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping
    public UserDto editUser(@Valid @RequestBody UserDto userDto, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        return superAdminService.editUser(userDto);
    }

    @GetMapping("/institution")
    public List<MedInstitutionDto> getAll(){
        return superAdminService.getAll();
    }

    @PostMapping("/institution")
    public MedInstitutionDto addNewInstitution(@Valid @RequestBody MedInstitutionDto medInstitutionDto, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        return superAdminService.addNewInstitution(medInstitutionDto);
    }

    @GetMapping("/institutionAdmin")
    public List<MedWorkerDto> getAllMedInstitutionAdmins(){
        return superAdminService.getAllMedInstitutionAdmins();
    }

    @PostMapping("/institutionAdmin")
    public MedWorkerDto addNewMedInstitutionAdmin(@Valid @RequestBody MedWorkerForm medWorkerDto, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        if(userDetailsService.getUser(medWorkerDto.getEmail()) != null){
            throw new AuthException("user with this username already exsists");
        }

        return superAdminService.addNewMedInstitutionAdmin(medWorkerDto);
    }
}
