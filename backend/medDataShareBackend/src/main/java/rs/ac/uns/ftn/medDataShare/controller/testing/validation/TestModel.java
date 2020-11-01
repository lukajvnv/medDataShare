package rs.ac.uns.ftn.medDataShare.controller.testing.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Validated
public class TestModel {

    private String firstName;
    private String lastName;

    public TestModel(@Size(min = 5, max = 200) @NotNull String firstName,
                    @Size(min = 5, max = 200) @NotNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
