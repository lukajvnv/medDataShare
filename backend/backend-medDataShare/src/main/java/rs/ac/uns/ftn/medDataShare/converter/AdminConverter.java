package rs.ac.uns.ftn.medDataShare.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.converter.declaration.ConverterInterface;
import rs.ac.uns.ftn.medDataShare.dto.user.UserDto;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.repository.AdminRepository;

import java.util.Date;

@Component
public class AdminConverter implements ConverterInterface<Admin, UserDto, UserDto> {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDto convertToDto(Admin admin) {
        return UserDto
                .builder()
                .id(admin.getId())
                .email(admin.getEmail())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .role(admin.getRole())
                .enabled(admin.isEnabled())
                .activeSince(admin.getActiveSince())
                .build();
    }

    @Override
    public Admin convertFromDto(UserDto object, boolean update) {
        if(update){
            Admin admin = adminRepository.findByUsername(object.getEmail());
            admin.setFirstName(object.getFirstName());
            admin.setLastName(object.getLastName());
            return admin;
        } else {
            return Admin
                    .builder()
                    .email(object.getEmail())
                    .username(object.getEmail())
                    .firstName(object.getFirstName())
                    .lastName(object.getLastName())
                    .role(object.getRole())
                    .enabled(true)
                    .activeSince(new Date())
                    .build();
        }
    }
}
