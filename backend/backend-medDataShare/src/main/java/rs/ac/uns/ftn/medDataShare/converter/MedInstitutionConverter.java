package rs.ac.uns.ftn.medDataShare.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.chaincode.Config;
import rs.ac.uns.ftn.medDataShare.converter.declaration.ConverterInterface;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedInstitutionDto;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.repository.MedInstitutionRepository;
import rs.ac.uns.ftn.medDataShare.util.StringUtil;

@Component
public class MedInstitutionConverter implements ConverterInterface<MedInstitution, MedInstitutionDto, MedInstitutionDto> {

    @Autowired
    private MedInstitutionRepository medInstitutionRepository;

    @Override
    public MedInstitutionDto convertToDto(MedInstitution medInstitution) {
        return new MedInstitutionDto(
                medInstitution.getId(),
                medInstitution.getMembershipOrganizationId(),
                medInstitution.getName(),
                medInstitution.getAddress()
        );
    }

    @Override
    public MedInstitution convertFromDto(MedInstitutionDto medInstitutionDto, boolean update) {
        if(update){
            return medInstitutionRepository.getOne(medInstitutionDto.getId());
        }
        String membershipOrganizationId = StringUtil.generateMembershipOrganizationId(Config.ORG_COUNT);
        return new MedInstitution(
                medInstitutionDto.getId(),
                medInstitutionDto.getName(),
                medInstitutionDto.getAddress(),
                membershipOrganizationId
        );
    }
}
