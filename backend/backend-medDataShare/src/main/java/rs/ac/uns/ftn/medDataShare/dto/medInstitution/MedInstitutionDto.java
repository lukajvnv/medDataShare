package rs.ac.uns.ftn.medDataShare.dto.medInstitution;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class MedInstitutionDto {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
