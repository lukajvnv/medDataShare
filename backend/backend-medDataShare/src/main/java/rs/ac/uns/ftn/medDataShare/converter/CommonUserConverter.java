package rs.ac.uns.ftn.medDataShare.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.converter.declaration.ConverterInterface;
import rs.ac.uns.ftn.medDataShare.dto.user.CommonUserDto;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.util.Constants;

import java.util.Date;

@Component
public class CommonUserConverter implements ConverterInterface<CommonUser, CommonUserDto, CommonUserDto> {

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Override
    public CommonUserDto convertToDto(CommonUser commonUser) {
        return CommonUserDto
                .builder()
                .id(commonUser.getId())
                .email(commonUser.getEmail())
                .firstName(commonUser.getFirstName())
                .lastName(commonUser.getLastName())
                .role(commonUser.getRole())
                .enabled(commonUser.isEnabled())
                .activeSince(commonUser.getActiveSince())
                .birthday(commonUser.getBirthday())
                .gender(commonUser.getGender())
                .address(commonUser.getAddress())
                .build();
    }

    @Override
    public CommonUser convertFromDto(CommonUserDto commonUserFormDto, boolean update) {
        if(update){
            CommonUser commonUser = commonUserRepository.findByUsername(commonUserFormDto.getEmail());
            commonUser.setFirstName(commonUserFormDto.getFirstName());
            commonUser.setLastName(commonUserFormDto.getLastName());
            commonUser.setGender(commonUserFormDto.getGender());
            commonUser.setAddress(commonUserFormDto.getAddress());
            commonUser.setBirthday(commonUserFormDto.getBirthday());
            return commonUser;
        } else {
            return CommonUser
                    .builder()
                    .firstName(commonUserFormDto.getFirstName())
                    .lastName(commonUserFormDto.getLastName())
                    .gender(commonUserFormDto.getGender())
                    .birthday(commonUserFormDto.getBirthday())
                    .username(commonUserFormDto.getEmail())
                    .email(commonUserFormDto.getEmail())
                    .enabled(true)
                    .role(Constants.ROLE_COMMON_USER)
                    .activeSince(new Date())
                    .build();
        }
    }
}
