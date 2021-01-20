package rs.ac.uns.ftn.medDataShare.security.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
public class SignUpDto {

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

    @NotBlank
    private String gender;

    private Date birthday;
}
