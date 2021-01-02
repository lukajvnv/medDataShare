package rs.ac.uns.ftn.medDataShare.dto.contract;

import lombok.*;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalTrialAccessRequestForm {

    @NotBlank
    private String id;

    @NotNull
    private AccessType accessDecision;

    private boolean anonymity;
    private Date from;
    private Date until;
}
