package rs.ac.uns.ftn.medDataShare.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDto {

    private String id;

    @NotBlank
    @Size(min=4, max = 30)
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String role;
    private boolean enabled;
    private Date activeSince;
}
