package rs.ac.uns.ftn.medDataShare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;

import java.util.List;

public interface MedWorkerRepository extends JpaRepository<MedWorker, String> {

    MedWorker findByUsername(String username);

    List<MedWorker> findAllByRole(String role);
    List<MedWorker> findAllByMedInstitution(MedInstitution medInstitution);
    List<MedWorker> findAllByMedInstitutionAndRole(MedInstitution medInstitution, String role);
}
