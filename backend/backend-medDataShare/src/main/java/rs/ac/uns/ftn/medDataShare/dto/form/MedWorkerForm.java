package rs.ac.uns.ftn.medDataShare.dto.form;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class MedWorkerForm {

    @NotBlank
    @Size(min=4, max = 30)
    private String email;

    @NotBlank
    @Size(min = 4, max = 40)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String medInstitution;

    private String occupation;
    private String role;
    private boolean enabled;
    private Date activeSince;
}
