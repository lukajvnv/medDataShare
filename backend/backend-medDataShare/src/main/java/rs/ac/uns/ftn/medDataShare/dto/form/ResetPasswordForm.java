package rs.ac.uns.ftn.medDataShare.dto.form;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class ResetPasswordForm {

    @NotBlank
    @Size(min = 4, max = 40)
    private String password;

    @NotBlank
    @Size(min = 4, max = 40)
    private String passwordRepeat;

}
