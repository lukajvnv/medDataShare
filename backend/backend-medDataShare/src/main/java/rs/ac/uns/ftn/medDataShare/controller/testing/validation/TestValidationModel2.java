package rs.ac.uns.ftn.medDataShare.controller.testing.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!"
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestValidationModel2 {
    private String email;
    private String verifyEmail;
    private String password;
    private String verifyPassword;

}
