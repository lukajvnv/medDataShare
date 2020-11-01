package rs.ac.uns.ftn.medDataShare.dto.contract;

import lombok.*;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalTrialAccessSendRequestForm {

    @NotBlank
    private String clinicalTrial;
    private String sender;
    private Date time;

}
