package rs.ac.uns.ftn.medDataShare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;

    public interface CommonUserRepository extends JpaRepository<CommonUser, String> {

    CommonUser findByUsername(String username);
}
