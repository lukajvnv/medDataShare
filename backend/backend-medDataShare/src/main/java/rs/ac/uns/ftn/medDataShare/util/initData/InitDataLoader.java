package rs.ac.uns.ftn.medDataShare.util.initData;


import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.bouncycastle.util.encoders.Hex;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.chaincode.Config;
import rs.ac.uns.ftn.medDataShare.chaincode.client.RegisterUserHyperledger;
import rs.ac.uns.ftn.medDataShare.chaincode.dto.ChaincodeClinicalTrial;
import rs.ac.uns.ftn.medDataShare.chaincode.dto.ClinicalTrialAccessRequest;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.contract.ClinicalTrialAccessSendRequestForm;
import rs.ac.uns.ftn.medDataShare.dto.form.ClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.form.EditClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.enums.AccessType;
import rs.ac.uns.ftn.medDataShare.enums.ClinicalTrialType;
import rs.ac.uns.ftn.medDataShare.model.medInstitution.MedInstitution;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.repository.*;
import rs.ac.uns.ftn.medDataShare.service.FhirService;
import rs.ac.uns.ftn.medDataShare.service.HyperledgerService;
import rs.ac.uns.ftn.medDataShare.service.SymmetricCryptography;
import rs.ac.uns.ftn.medDataShare.util.Constants;
import rs.ac.uns.ftn.medDataShare.util.ImageUtil;
import rs.ac.uns.ftn.medDataShare.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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

    @Autowired
    private HyperledgerService hyperledgerService;

    @Autowired
    private FhirService fhirService;

    @Autowired
    private LoadDataValues loadDataValues;

    @Autowired
    private IGenericClient client;

    @Autowired
    private SymmetricCryptography symmetricCryptography;

    private final static Logger LOG = Logger.getLogger(InitDataLoader.class.getName());

    @Override
    public void run(String... args) throws Exception {
//        encryption();
        initInstitutions();
        initUsers();
        initClinicalTrials();
    }

    private void encryption() {
//        KeyStoreHelper helper = new KeyStoreHelper();
//        SymmetricCryptography crypto = new SymmetricCryptography();
//        helper.loadKeyStore("src/main/resources/med_data_share.jks", "medDataShare".toCharArray());
//        SecretKey key = crypto.generateKey();
//        PrivateKey privateKey= helper.readPrivateKey("meddatashare", "medDataShare");
//        Certificate certificate = helper.readCertificate( "meddatasharecer");
//
//        System.out.println("\n===== Generisanje kljuca =====");
//        String data = "Luka 2903 Jovanovic";
//        System.out.println("Generisan kljuc: " + Hex.toHexString(key.getEncoded()));
//        byte[] encoded =  helper.encrypt(key.getEncoded(), certificate.getPublicKey());
//        System.out.println("Enkriptovan kljuc: " + Hex.toHexString(encoded));
//        byte[] decoded = helper.decrypt(encoded, privateKey);
//        SecretKey originalKey = new SecretKeySpec(decoded, 0, decoded.length, "AES");
//
//        System.out.println("Dekriptovan klju: " + Hex.toHexString(decoded));
//        System.out.println("Dekriptovan kljuc[byte]: " + decoded);

        String data = "Luka 2903 Jovanovic";
        String hexEncryptedData = symmetricCryptography.putInfoInDb(data);
        String decryptedData = symmetricCryptography.getInfoFromDb(hexEncryptedData);
        System.out.println(String.format("originalData: %s, hexEncryptedData: %s, decryptedData: %s", data, hexEncryptedData, decryptedData));
    }

    private void invokeClinicalTrial(){
        String id = "60057b2f9f310620f22f4e24";
        // Read a patient with the given ID
        ImagingStudy imagingStudy = client.read().resource(ImagingStudy.class).withId(id).execute();

        // Print the output
        System.out.println("string");
    }

    private void initInstitutions(){
        String membershipOrganization1Id = StringUtil.generateMembershipOrganizationId(Config.ORG_COUNT);
        String membershipOrganization2Id = StringUtil.generateMembershipOrganizationId(Config.ORG_COUNT);

        MedInstitution medInstitution1 = MedInstitution
                .builder()
                .name("Institut za plucne bolesti Vojvodine")
                .address("Institutski put 4, 21104 Sremska Kamenica")
                .membershipOrganizationId(membershipOrganization1Id)
                .build();
        MedInstitution medInstitution2 = MedInstitution
                .builder()
                .name("Klinicki centar Vojvodine")
                .address("Futoski put 4, 21102 Novi Sad")
                .membershipOrganizationId(membershipOrganization2Id)
                .build();

        medInstitutionRepository.save(medInstitution1);
        medInstitutionRepository.save(medInstitution2);
    }

    private void initUsers() throws Exception {
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
        LOG.info(savedCommonUser1.toString());

        CommonUser commonUser2 = CommonUser
                .builder()
                .firstName("Luka")
                .lastName("Markovic")
                .address("Drage Spasic 7")
                .gender("Male")
                .birthday(new Date(833356800000l)) //29.05.1996 10h
                .username("user2@gmail.com")
                .email("user2@gmail.com")
                .password(userPasswordEncoder.encode("user2"))
                .enabled(true)
                .role(Constants.ROLE_COMMON_USER)
                .activeSince(new Date(1603274400000l))   //21.10.2020 12h
                .build();
        CommonUser savedCommonUser2 = commonUserRepository.save(commonUser2);
        LOG.info(savedCommonUser2.toString());

        CommonUser commonUser3 = CommonUser
                .builder()
                .firstName("Petar")
                .lastName("Markovic")
                .address("Jiricekova 5c")
                .gender("Male")
                .birthday(new Date(796464000000l)) //29.03.1995 10h
                .username("user3@gmail.com")
                .email("user3@gmail.com")
                .password(userPasswordEncoder.encode("user3"))
                .enabled(true)
                .role(Constants.ROLE_COMMON_USER)
                .activeSince(new Date(1603274400000l))   //21.10.2020 12h
                .build();
        CommonUser savedCommonUser3 = commonUserRepository.save(commonUser3);
        LOG.info(savedCommonUser3.toString());

        List<MedInstitution> medInstitutions = medInstitutionRepository.findAll();
        MedInstitution medInstitution1 = medInstitutions.get(0);
        MedInstitution medInstitution2 = medInstitutions.get(1);

        MedWorker medWorker1 = MedWorker
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
                .medInstitution(medInstitution1)
                .build();
        LOG.info(medWorker1.toString());

        MedWorker medWorker2 = MedWorker
                .builder()
                .firstName("Peter Doctor")
                .lastName("Jovanovic")
                .username("userd2@gmail.com")
                .email("userd2@gmail.com")
                .password(userPasswordEncoder.encode("userd2"))
                .enabled(true)
                .role(Constants.ROLE_DOCTOR)
                .occupation("cardiologist")
                .activeSince(new Date(1603188000000l)) //20.10.2020 12h
                .medInstitution(medInstitution2)
                .build();
        LOG.info(medWorker2.toString());

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
                .medInstitution(medInstitution1)
                .build();

        medWorkerRepository.save(medWorker1);
        medWorkerRepository.save(medWorker2);
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

        try {
            RegisterUserHyperledger.enrollOrgAppUser(commonUser1.getEmail(), Config.COMMON_USER_ORG, commonUser1.getId());
            RegisterUserHyperledger.enrollOrgAppUser(commonUser2.getEmail(), Config.COMMON_USER_ORG, commonUser2.getId());
            RegisterUserHyperledger.enrollOrgAppUser(commonUser3.getEmail(), Config.COMMON_USER_ORG, commonUser3.getId());
            RegisterUserHyperledger.enrollOrgAppUser(medWorker1.getEmail(), medInstitution1.getMembershipOrganizationId(), medWorker1.getId());
            RegisterUserHyperledger.enrollOrgAppUser(medWorker2.getEmail(), medInstitution2.getMembershipOrganizationId(), medWorker2.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initClinicalTrials() throws Exception {
        for (DataValue dataValue : loadDataValues.getValues()){
            LOG.info("Init data for" + dataValue);

            String patientId = dataValue.getPatient();
            String doctorId = dataValue.getDoctor();

            User patientUser = commonUserRepository.findByUsername(patientId);
            User doctorUser = medWorkerRepository.findByUsername(doctorId);

            String patient = patientUser.getId();
            String doctor = doctorUser.getId();

            String contentType = dataValue.getContentType();
            byte[] fileContent = ImageUtil.getBytes(dataValue.getContentName());

            ClinicalTrialForm clinicalTrialForm = ClinicalTrialForm
                    .builder()
                    .clinicalTrialType(ClinicalTrialType.valueOf(dataValue.getClinicalTrialType()))
                    .introduction(dataValue.getIntroduction())
                    .relevantParameters(dataValue.getRelevantParameters())
                    .conclusion(dataValue.getConclusion())
                    .patient(patient)
                    .doctor(doctor)
                    .build();

            AccessType clinicalTrialAccessType = AccessType.valueOf(dataValue.getClinicalTrialAccessType());

            String clinicalTrialTimeStr = dataValue.getClinicalTrialTime();
            Date clinicalTrialTime = StringUtil.createDate(clinicalTrialTimeStr);

            ClinicalTrialDto clinicalTrialDto = fhirService.addClinicalTrial(clinicalTrialForm, contentType, fileContent, clinicalTrialTime);
            String offlineDataId = clinicalTrialDto.getId();

            ChaincodeClinicalTrial chaincodeClinicalTrial = hyperledgerService.addClinicalTrial(doctorUser, clinicalTrialDto);

            ClinicalTrialDto editedClinicalTrialDto = fhirService.updateClinicalTrial(patient, EditClinicalTrialForm
                    .builder()
                    .id(offlineDataId)
                    .accessType(clinicalTrialAccessType)
                    .build());

            chaincodeClinicalTrial = hyperledgerService.defineClinicalTrialAccess(patientUser, editedClinicalTrialDto, clinicalTrialDto);

            if (dataValue.isSendAccessRequest()){
                String requesterId = dataValue.getRequester();
                User requesterUser = commonUserRepository.findByUsername(requesterId);
                String requester = requesterUser.getId();
                String sendAccessRequestTimeStr = dataValue.getSendAccessRequestTime();
                Date sendAccessRequestTime = StringUtil.createDate(sendAccessRequestTimeStr);

                String clinicalTrialId = chaincodeClinicalTrial.getKey();
                ClinicalTrialAccessRequest clinicalTrialAccessRequest = hyperledgerService.sendAccessRequest(requesterUser,
                ClinicalTrialAccessSendRequestForm
                        .builder()
                        .clinicalTrial(clinicalTrialId)
                        .patient(patient)
                        .sender(requester)
                        .time(sendAccessRequestTime)
                        .build()
                );
                if (dataValue.isSendAccessRequestDecision()){
                    String canAccessFromStr = dataValue.getCanAccessFrom();
                    String canAccessUntilStr = dataValue.getCanAccessUntil();
                    Date canAccessFrom = StringUtil.createDate(canAccessFromStr);
                    Date canAccessUntil = StringUtil.createDate(canAccessUntilStr);
                    boolean anonymity = true;
                    AccessType accessDecision = AccessType.valueOf(dataValue.getAccessDecision());
                    clinicalTrialAccessRequest = hyperledgerService.trialAccessRequestDecision(patientUser,
                            ClinicalTrialAccessRequestForm
                                    .builder()
                                    .id(clinicalTrialAccessRequest.getKey())
                                    .accessDecision(accessDecision)
                                    .from(canAccessFrom)
                                    .until(canAccessUntil)
                                    .anonymity(anonymity)
                                    .build()
                    );
                }
            }
        }

        LOG.info("INIT DATA LOADER IS FINISHED");

    }
}
