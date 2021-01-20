package rs.ac.uns.ftn.medDataShare.dto.medInstitution;

import lombok.*;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MedAdminDoctors {

    private String institution;
    private List<MedWorkerDto> doctors;
}
