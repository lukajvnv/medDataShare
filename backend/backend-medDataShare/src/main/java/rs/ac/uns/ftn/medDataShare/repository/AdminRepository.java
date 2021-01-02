package rs.ac.uns.ftn.medDataShare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

    Admin findByUsername(String username);
}
