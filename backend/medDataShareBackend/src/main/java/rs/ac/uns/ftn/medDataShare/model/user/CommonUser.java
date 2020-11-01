package rs.ac.uns.ftn.medDataShare.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommonUser extends User{

    private String gender;
    private Date birthday;
    private String address;

}

