package rs.ac.uns.ftn.medDataShare.dto.medInstitution;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalTrialDto {

    private String id;

    private Date time;
    private String introduction;
    private String relevantParameters;
    private String conclusion;
    private ClinicalTrialType clinicalTrialType;

    private String patient;
    private String doctor;

    private AccessType accessType;
}
