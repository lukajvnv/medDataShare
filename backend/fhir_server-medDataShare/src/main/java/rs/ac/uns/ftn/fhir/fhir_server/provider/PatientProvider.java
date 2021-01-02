package rs.ac.uns.ftn.fhir.fhir_server.provider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IPatientRepository;

@Component
public class PatientProvider implements IResourceProvider {

    @Autowired
    FhirContext ctx;

    @Autowired
    IPatientRepository patientRepository;

    private static final Logger log = LoggerFactory.getLogger(PatientProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) {

        log.debug("Create Patient Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();

        method.setOperationOutcome(opOutcome);

        try {
            Patient mongoPatient = patientRepository.create(patient);
            log.info(mongoPatient.getIdElement().toString());
            method.setId(mongoPatient.getIdElement());
            method.setResource(mongoPatient);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.debug("called create Patient method");

        return method;
    }

    @Read
    public Patient readPatient(@IdParam IdType internalId) {

        Patient patient = patientRepository.read(internalId);

        return patient;
    }

    /*
    @Search
    public List<Resource> searchPatient(
        @OptionalParam(name= Patient.SP_BIRTHDATE) DateRangeParam birthDate,
        @OptionalParam(name = Patient.SP_FAMILY) StringParam familyName,
        @OptionalParam(name= Patient.SP_GENDER) StringParam gender ,
        @OptionalParam(name= Patient.SP_GIVEN) StringParam givenName ,
        @OptionalParam(name = Patient.SP_IDENTIFIER) TokenParam identifier,
        @OptionalParam(name= Patient.SP_NAME) StringParam name,
        @OptionalParam(name = Patient.SP_RES_ID) TokenParam resid

    ) {
        List<Resource> results = patientRepository.search(ctx,birthDate,familyName,gender,givenName,identifier,name);

        return results;
    }
    */

}
