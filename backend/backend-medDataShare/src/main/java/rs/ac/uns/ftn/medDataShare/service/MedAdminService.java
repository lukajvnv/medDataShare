package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.medDataShare.chaincode.client.RegisterUserHyperledger;
import rs.ac.uns.ftn.medDataShare.converter.MedWorkerConverter;
import rs.ac.uns.ftn.medDataShare.dto.form.MedWorkerForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.MedAdminDoctors;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.util.Constants;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedAdminService {

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MedWorkerConverter medWorkerConverter;

    public MedAdminDoctors getAllMedInstitutionDoctors(){
        MedWorker medAdmin = (MedWorker) userDetailsService.getLoggedUser();

        List<MedWorkerDto> doctors = medWorkerRepository
                .findAllByMedInstitutionAndRole(medAdmin.getMedInstitution(), Constants.ROLE_DOCTOR)
                .stream().map(this::convert).collect(Collectors.toList());
        return new MedAdminDoctors(medAdmin.getMedInstitution().getName(), doctors);
    }

    public MedWorkerDto addNewMedInstitutionDoctor(MedWorkerForm medWorkerForm){
        medWorkerForm.setRole(Constants.ROLE_DOCTOR);
        medWorkerForm.setPassword(userPasswordEncoder.encode(medWorkerForm.getPassword()));

        MedWorker medAdmin = (MedWorker) userDetailsService.getLoggedUser();
        medWorkerForm.setMedInstitution(medAdmin.getMedInstitution().getId());

        MedWorker medWorker = convert(medWorkerForm);
        MedWorker savedMedWorker = medWorkerRepository.save(medWorker);
        try {
            String appUserIdentityId = savedMedWorker.getEmail();
            String org = savedMedWorker.getMedInstitution().getMembershipOrganizationId();
            RegisterUserHyperledger.enrollOrgAppUser(appUserIdentityId, org, savedMedWorker.getId());
        } catch (Exception e) {
            throw new AuthException("Error while signUp in hyperledger");
        }
        return convert(savedMedWorker);
    }

    private MedWorkerDto convert(MedWorker medWorker){
        return medWorkerConverter.convertToDto(medWorker);
    }

    private MedWorker convert(MedWorkerForm medWorkerForm){
        return medWorkerConverter.convertFromDto(medWorkerForm, false);
    }
}
