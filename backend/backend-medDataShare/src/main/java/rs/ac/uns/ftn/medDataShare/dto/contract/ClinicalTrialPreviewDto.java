package rs.ac.uns.ftn.medDataShare.dto.contract;

import lombok.*;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalTrialPreviewDto {

    private String clinicalTrial;

    private Date time;
    private ClinicalTrialType clinicalTrialType;
    private AccessType accessType;

    private String institution;

    private String patientId;

    //relevant parameters
    //doctorId
    //patientId

}
