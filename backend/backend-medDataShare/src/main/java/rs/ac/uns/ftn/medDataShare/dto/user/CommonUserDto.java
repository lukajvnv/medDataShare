package rs.ac.uns.ftn.medDataShare.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CommonUserDto extends UserDto {

    private String gender;
    private Date birthday;
    private String address;
}
