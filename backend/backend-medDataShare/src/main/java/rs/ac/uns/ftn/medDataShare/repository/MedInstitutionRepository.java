package rs.ac.uns.ftn.medDataShare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;

public interface MedInstitutionRepository extends JpaRepository<MedInstitution, String> {

}
