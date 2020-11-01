package rs.ac.uns.ftn.medDataShare.converter;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.dto.form.ClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.ClinicalTrial;

import java.util.Date;

@Component
public class ClinicalTrialConverter implements ConverterInterface<ClinicalTrial, ClinicalTrialDto, ClinicalTrialForm> {

    @Override
    public ClinicalTrialDto convertToDto(ClinicalTrial object) {
        return ClinicalTrialDto
                .builder()
                .id(object.getId())
                .introduction(object.getIntroduction())
                .time(object.getTime())
                .conclusion(object.getConclusion())
                .relevantParameters(object.getRelevantParameters())
                .clinicalTrialType(object.getClinicalTrialType())
                .patient(object.getPatient())
                .doctor(object.getDoctor())
                .accessType(object.getAccessType())
                .build();
    }

    @Override
    public ClinicalTrial convertFromDto(ClinicalTrialForm object, boolean update) {
        if(update){
            return null;
        } else {
            return ClinicalTrial
                    .builder()
                    .introduction(object.getIntroduction())
                    .relevantParameters(object.getRelevantParameters())
                    .conclusion(object.getConclusion())
                    .clinicalTrialType(object.getClinicalTrialType())
                    .time(new Date())
                    .patient(object.getPatient())
                    .doctor(object.getDoctor())
                    .build();
        }
    }
}
