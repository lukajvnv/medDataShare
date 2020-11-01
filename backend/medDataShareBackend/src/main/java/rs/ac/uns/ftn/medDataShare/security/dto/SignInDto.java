package rs.ac.uns.ftn.medDataShare.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @NotBlank
    @Size(min=4, max = 30)
    private String username;

    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
}
