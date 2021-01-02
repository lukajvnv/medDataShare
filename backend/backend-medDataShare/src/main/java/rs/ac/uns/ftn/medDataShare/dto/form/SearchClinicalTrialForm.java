package rs.ac.uns.ftn.medDataShare.dto.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchClinicalTrialForm {

    private ClinicalTrialType clinicalTrialType;
    private boolean relevantParameters;
    private String institutions;
    private Date from;
    private Date until;
    private String orderBy;
}

