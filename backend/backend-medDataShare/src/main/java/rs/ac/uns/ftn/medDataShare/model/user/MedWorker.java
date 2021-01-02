package rs.ac.uns.ftn.medDataShare.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MedWorker extends User {

    private String occupation;

    @ManyToOne
    private MedInstitution medInstitution;
}
