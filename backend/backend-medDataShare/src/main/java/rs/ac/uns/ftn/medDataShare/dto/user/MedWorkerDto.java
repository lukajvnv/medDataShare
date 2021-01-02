package rs.ac.uns.ftn.medDataShare.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedInstitutionDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MedWorkerDto extends UserDto {

    private String occupation;
    private MedInstitutionDto medInstitution;
}
