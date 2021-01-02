package rs.ac.uns.ftn.medDataShare.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.repository.*;

import java.util.Date;
import java.util.List;

@Component
public class InitDataLoader implements CommandLineRunner {

    @Autowired
    private CommonUserRepository commonUserRepository;

    @Autowired
    private MedWorkerRepository medWorkerRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private MedInstitutionRepository medInstitutionRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClinicalTrialRepository clinicalTrialRepository;

    @Override
    public void run(String... args) throws Exception {
        initInstitutions();
        initUsers();
    }

    private void initInstitutions(){
        MedInstitution medInstitution1 = MedInstitution
                .builder()
                .name("Institut za plucne bolesti Vojvodine")
                .address("Institutski put 4, 21104 Sremska Kamenica")
                .build();

        MedInstitution medInstitution2 = MedInstitution
                .builder()
                .name("Klinicki centar Vojvodine")
                .address("Futoski put 4, 21102 Novi Sad")
                .build();

        medInstitutionRepository.save(medInstitution1);
        medInstitutionRepository.save(medInstitution2);
    }

    private void initUsers(){
        CommonUser commonUser1 = CommonUser
                .builder()
                .firstName("Luka")
                .lastName("Jovanovic")
                .address("Drage Spasic 7")
                .gender("Male")
                .birthday(new Date(828090000000l))  //29.03.1996 10h
                .username("user1@gmail.com")
                .email("user1@gmail.com")
                .password(userPasswordEncoder.encode("user1"))
                .enabled(true)
                .role(Constants.ROLE_COMMON_USER)
                .activeSince(new Date(1603274400000l))   //21.10.2020 12h
                .build();
        CommonUser savedCommonUser1 = commonUserRepository.save(commonUser1);

        CommonUser commonUser2 = CommonUser
                .builder()
                .firstName("Luka")
                .lastName("Markovic")
                .address("Drage Spasic 7")
                .gender("Male")
                .birthday(new Date(833356800000l)) //29.05.1966 10h
                .username("user2@gmail.com")
                .email("user2@gmail.com")
                .password(userPasswordEncoder.encode("user2"))
                .enabled(true)
                .role(Constants.ROLE_COMMON_USER)
                .activeSince(new Date(1603274400000l))   //21.10.2020 12h
                .build();
        CommonUser savedCommonUser2 = commonUserRepository.save(commonUser2);

        List<MedInstitution> medInstitutions = medInstitutionRepository.findAll();
        MedInstitution medInstitution = medInstitutions.get(0);

        MedWorker medWorker = MedWorker
                .builder()
                .firstName("Luka Doctor")
                .lastName("Jovanovic")
                .username("userd1@gmail.com")
                .email("userd1@gmail.com")
                .password(userPasswordEncoder.encode("userd1"))
                .enabled(true)
                .role(Constants.ROLE_DOCTOR)
                .occupation("pulmologist")
                .activeSince(new Date(1603188000000l)) //20.10.2020 12h
                .medInstitution(medInstitution)
                .build();

        MedWorker medWorkerAdmin = MedWorker
                .builder()
                .firstName("Luka Admin")
                .lastName("Jovanovic")
                .username("usera1@gmail.com")
                .email("usera1@gmail.com")
                .password(userPasswordEncoder.encode("usera1"))
                .enabled(true)
                .role(Constants.ROLE_MED_ADMIN)
                .activeSince(new Date(1603101600000l)) //19.10.2020 12h
                .medInstitution(medInstitution)
                .build();

        medWorkerRepository.save(medWorker);
        medWorkerRepository.save(medWorkerAdmin);

        Admin admin = Admin
                .builder()
                .firstName("Luka SuperAdmin")
                .lastName("Jovanovic")
                .username("usersa1@gmail.com")
                .email("usersa1@gmail.com")
                .password(userPasswordEncoder.encode("usersa1"))
                .enabled(true)
                .role(Constants.ROLE_SUPER_ADMIN)
                .activeSince(new Date(1602756000000l)) //15.10.2020 12h
                .build();

        adminRepository.save(admin);

        String[] patients = new String[]{commonUser1.getId(), commonUser2.getId()};
        String[] doctors = new String[]{medWorker.getId()};

        //initMedicalTrials(patients, doctors);
    }

    private void initMedicalTrials(String[] patients, String[] doctors){
        String patientId1 = patients[0];
        String patientId2 = patients[1];

        String doctor = doctors[0];

        clinicalTrialRepository.deleteAll();

        Date medicalTrialTime1 = new Date(1602756000000l); //15.10.2020 12h
        Date medicalTrialTime2 = new Date(1603101600000l); //19.10.2020 12h

        String randomText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type specimen book.";

        clinicalTrialRepository.save(ClinicalTrial
                .builder()
                .introduction(randomText)
                .conclusion(randomText)
                .time(medicalTrialTime1)
                .clinicalTrialType(ClinicalTrialType.CBC)
                .patient(patientId1)
                .doctor(doctor)
                .accessType(AccessType.IDLE)
                .build());
        clinicalTrialRepository.save(ClinicalTrial
                .builder()
                .introduction(randomText)
                .conclusion(randomText)
                .time(medicalTrialTime2)
                .clinicalTrialType(ClinicalTrialType.CT)
                .patient(patientId1)
                .doctor(doctor)
                .accessType(AccessType.ASK_FOR_ACCESS)
                .build());

        System.out.println("MedicalTrials found with findAll():");
        System.out.println("-------------------------------");
        for (ClinicalTrial clinicalTrial : clinicalTrialRepository.findAll()) {
            System.out.println(clinicalTrial);
        }
        System.out.println();

        System.out.println("MedicalTrials found with findByMedicalTrialType('MedicalTrialType.CT'):");
        System.out.println("--------------------------------");
        for (ClinicalTrial clinicalTrial : clinicalTrialRepository.findByClinicalTrialType(ClinicalTrialType.CT)) {
            System.out.println(clinicalTrial);
        }
    }
}
