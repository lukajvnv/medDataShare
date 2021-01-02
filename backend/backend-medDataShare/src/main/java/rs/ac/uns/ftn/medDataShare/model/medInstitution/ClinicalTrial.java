package rs.ac.uns.ftn.medDataShare.model.medInstitution;

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
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ClinicalTrial {

    @Id
    private String id;

    private Date time;
    private String introduction;
    private String relevantParameters;
    private String conclusion;
    private ClinicalTrialType clinicalTrialType;
    private AccessType accessType;

    private String patient;
    private String doctor;
}