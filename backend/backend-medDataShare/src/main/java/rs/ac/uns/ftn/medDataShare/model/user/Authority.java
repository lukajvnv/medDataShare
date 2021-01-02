package rs.ac.uns.ftn.medDataShare.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITY", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME" ,length = 30)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}

