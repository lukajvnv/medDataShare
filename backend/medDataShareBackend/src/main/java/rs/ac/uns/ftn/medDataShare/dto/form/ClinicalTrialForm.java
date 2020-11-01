package rs.ac.uns.ftn.medDataShare.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalTrialForm {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String introduction;

    @NotBlank
    private String conclusion;

    @NotNull
    private ClinicalTrialType clinicalTrialType;

    private String relevantParameters;

    @NotBlank
    private String patient;

    private String doctor;
}

