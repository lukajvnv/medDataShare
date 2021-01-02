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
public class ClinicalTrialAccessRequestDto {

    private String id;

    private Date time;
    private ClinicalTrialType clinicalTrialType;

    private String clinicalTrial;
    private String sender;
    private String receiver;

    private boolean anonymity;
    private Date from;
    private Date until;
    private AccessType accessDecision;
}
