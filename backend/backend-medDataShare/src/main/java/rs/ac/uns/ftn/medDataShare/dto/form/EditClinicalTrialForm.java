package rs.ac.uns.ftn.medDataShare.dto.form;

import lombok.*;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;

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

