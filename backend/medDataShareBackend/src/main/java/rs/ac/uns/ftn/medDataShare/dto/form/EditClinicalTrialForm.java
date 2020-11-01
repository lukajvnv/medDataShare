package rs.ac.uns.ftn.medDataShare.dto.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditClinicalTrialForm {
    @NotBlank
    private String id;

    @NotNull
    private AccessType accessType;
}

