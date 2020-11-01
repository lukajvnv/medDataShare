package rs.ac.uns.ftn.medDataShare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.ClinicalTrial;

import java.util.List;

public interface ClinicalTrialRepository extends MongoRepository<ClinicalTrial, String> {
    ClinicalTrial findByIntroduction(String firstName);
    List<ClinicalTrial> findByConclusion(String lastName);
    List<ClinicalTrial> findByClinicalTrialType(ClinicalTrialType clinicalTrialType);

    List<ClinicalTrial> findByPatient(String patient);

    List<ClinicalTrial> findByPatientAndAccessType(String patient, AccessType accessType);
}
