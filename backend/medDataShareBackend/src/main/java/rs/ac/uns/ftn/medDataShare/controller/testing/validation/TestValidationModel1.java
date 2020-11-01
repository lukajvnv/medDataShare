package rs.ac.uns.ftn.medDataShare.controller.testing.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestValidationModel1 {

    @ContactNumberConstraint
    private String phone;
}
