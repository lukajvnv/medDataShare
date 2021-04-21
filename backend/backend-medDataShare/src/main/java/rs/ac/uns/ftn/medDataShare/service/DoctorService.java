package rs.ac.uns.ftn.medDataShare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.medDataShare.converter.MedWorkerConverter;
import rs.ac.uns.ftn.medDataShare.converter.declaration.UserInterface;
import rs.ac.uns.ftn.medDataShare.dto.form.ClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.form.MedWorkerForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.user.CommonUserDto;
import rs.ac.uns.ftn.medDataShare.dto.user.MedWorkerDto;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.CommonUserRepository;
import rs.ac.uns.ftn.medDataShare.repository.MedWorkerRepository;
import rs.ac.uns.ftn.medDataShare.security.service.UserDetailsServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService implements UserInterface<MedWorker, MedWorkerDto, MedWorkerForm> {

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MedWorkerConverter medWorkerConverter;

    @Autowired
    private FhirService fhirService;

    @Autowired
    private HyperledgerService hyperledgerService;

    @Autowired
    private SymmetricCryptography symmetricCryptography;

    @Override
    public MedWorkerDto editUser(MedWorkerForm medWorkerDto) {
        MedWorker medWorker = convert(medWorkerDto, true);
        return convert(medWorkerRepository.save(medWorker));
    }

    public ClinicalTrialDto addClinicalTrial(ClinicalTrialForm clinicalTrialForm) throws Exception {
        User user = userDetailsService.getLoggedUser();
        String doctor = user.getId();
        String doctorName = user.getFirstName() + " " + user.getLastName();
        clinicalTrialForm.setDoctor(symmetricCryptography.putInfoInDb(doctor));
        clinicalTrialForm.setDoctorName(doctorName);
        clinicalTrialForm.setPatient(symmetricCryptography.putInfoInDb(clinicalTrialForm.getPatient()));

        String contentType = clinicalTrialForm.getFile().getContentType();
        byte[] fileContent = clinicalTrialForm.getFile().getBytes();
        Date clinicalTrialTime = new Date();
        ClinicalTrialDto clinicalTrialDto = fhirService.addClinicalTrial(clinicalTrialForm, contentType, fileContent, clinicalTrialTime);
        hyperledgerService.addClinicalTrial(user, clinicalTrialDto);
        return clinicalTrialDto;
    }

    public List<CommonUserDto> getPatients(){
        return commonUserRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    private MedWorker convert(MedWorkerForm medWorkerForm, boolean update){
        return medWorkerConverter.convertFromDto(medWorkerForm, update);
    }

    private MedWorkerDto convert(MedWorker medWorker){
        return medWorkerConverter.convertToDto(medWorker);
    }

    private CommonUserDto convert(CommonUser commonUser){
        return CommonUserDto
                .builder()
                .id(commonUser.getId())
                .firstName(commonUser.getFirstName())
                .lastName(commonUser.getLastName())
                .birthday(commonUser.getBirthday())
                .build();
    }
}
