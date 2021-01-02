package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.medDataShare.converter.MedWorkerConverter;
import rs.ac.uns.ftn.medDataShare.dto.form.MedWorkerForm;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.repository.MedInstitutionRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;
import rs.ac.uns.ftn.medDataShare.util.Constants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedAdminService {

    @Autowired
    private MedInstitutionRepository medInstitutionRepository;

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MedWorkerConverter medWorkerConverter;

    public List<MedWorkerDto> getAllMedInstitutionDoctors(){
        MedWorker medAdmin = (MedWorker) userDetailsService.getLoggedUser();

        return medWorkerRepository
                .findAllByMedInstitutionAndRole(medAdmin.getMedInstitution(), Constants.ROLE_DOCTOR)
                .stream().map(this::convert).collect(Collectors.toList());
    }

    public MedWorkerDto addNewMedInstitutionDoctor(MedWorkerForm medWorkerForm){
        medWorkerForm.setRole(Constants.ROLE_DOCTOR);
        medWorkerForm.setPassword(userPasswordEncoder.encode(medWorkerForm.getPassword()));

        MedWorker medWorker = convert(medWorkerForm);
        return convert(medWorkerRepository.save(medWorker));
    }

    private MedWorkerDto convert(MedWorker medWorker){
        return medWorkerConverter.convertToDto(medWorker);
    }

    private MedWorker convert(MedWorkerForm medWorkerForm){
        return medWorkerConverter.convertFromDto(medWorkerForm, false);
    }
}
