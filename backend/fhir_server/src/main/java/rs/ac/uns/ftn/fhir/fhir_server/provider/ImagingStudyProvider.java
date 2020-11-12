package rs.ac.uns.ftn.fhir.fhir_server.provider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.gclient.ReferenceClientParam;
import ca.uhn.fhir.rest.param.*;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IImagingStudyRepository;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IPatientRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImagingStudyProvider implements IResourceProvider {

    @Autowired
    IImagingStudyRepository imagingStudyRepository;

    private static final Logger log = LoggerFactory.getLogger(ImagingStudyProvider.class);

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return ImagingStudy.class;
    }

    @Create
    public MethodOutcome createImagingStudy(@ResourceParam ImagingStudy imagingStudy) {

        log.debug("Create ImagingStudy called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();

        method.setOperationOutcome(opOutcome);

        try {
            ImagingStudy mongoImagingStudy = imagingStudyRepository.create(imagingStudy);
            log.info(mongoImagingStudy.getIdElement().getId());
            method.setId(mongoImagingStudy.getIdElement());
            method.setResource(mongoImagingStudy);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.debug("called create ImagingStudy method");

        return method;
    }

    @Update()
    public MethodOutcome updateImagingStudy(@IdParam IdType theId, @ResourceParam ImagingStudy imagingStudy) {
        MethodOutcome method = new MethodOutcome();
        method.setCreated(false);
        ImagingStudy mongoImagingStudy = imagingStudyRepository.update(imagingStudy);
        log.info(mongoImagingStudy.getIdElement().getId());
        method.setId(mongoImagingStudy.getIdElement());
        method.setResource(mongoImagingStudy);
        return method;
    }

    @Read
    public ImagingStudy readPatient(@IdParam IdType internalId) {
        ImagingStudy imagingStudy = imagingStudyRepository.read(internalId);
        return imagingStudy;
    }

    @Search
    public List<Resource> searchPatient(
            @OptionalParam(name = ImagingStudy.SP_STARTED) DateParam started,
//        @OptionalParam(name= Patient.SP_BIRTHDATE) DateRangeParam birthDate,
        @OptionalParam(name= Patient.SP_FAMILY) StringParam givenName,
            @OptionalParam(name= ImagingStudy.SP_STATUS) StringParam status,
            @OptionalParam(name = ImagingStudy.SP_SUBJECT) StringParam subject
//        @OptionalParam(name = Patient.SP_RES_ID) TokenParam resid
    ) {
        return imagingStudyRepository.search(subject, status);
    }

}
