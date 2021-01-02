package rs.ac.uns.ftn.medDataShare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.medDataShare.dto.form.ClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.form.MedWorkerForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.user.CommonUserDto;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.service.DoctorService;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.ValidationException;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public MedWorkerDto editUser(@Valid @RequestBody MedWorkerForm medWorkerDto, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        return doctorService.editUser(medWorkerDto);
    }

    @GetMapping("/patients")
    public List<CommonUserDto> getPatients(){
        return doctorService.getPatients();
    }

    @PostMapping("/clinicalTrial")
    public ClinicalTrialDto addClinicalTrial(@Valid @ModelAttribute ClinicalTrialForm clinicalTrialForm, BindingResult result) throws IOException {
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new ValidationException(errorMsg);
        }

        if(!validInputFile(clinicalTrialForm.getFile())) {
            throw new ValidationException("Invalid file format");
        }

        return doctorService.addClinicalTrial(clinicalTrialForm);
    }

    private boolean validInputFile(MultipartFile file) throws IOException {
        if(file == null) return true;

        String contentType = file.getContentType();
        if(!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
            return false;
        }

        long l = 1l;
        try {
            l = file.getResource().contentLength();
            if(l > 2000000l) {
                return false;
            }
        } catch (IOException e1) {
            return false;
        }

        return true;
    }
}
