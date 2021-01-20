package rs.ac.uns.ftn.fhir.fhir_server.provider;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IImagingStudyRepository;
import rs.ac.uns.ftn.fhir.fhir_server.util.JwtProvider;

import java.util.List;

@Component
public class ImagingStudyProvider implements IResourceProvider {

    @Autowired
    IImagingStudyRepository imagingStudyRepository;

    @Autowired
    JwtProvider jwtProvider;

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
            log.info("mongoImagingStudy.getIdElement().toString(): " + mongoImagingStudy.getIdElement().toString());
            method.setId(mongoImagingStudy.getIdElement());
            method.setResource(mongoImagingStudy);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.debug("called create ImagingStudy method");

        return method;
    }

    @Update()
    public MethodOutcome updateImagingStudy(RequestDetails theRequestDetails, @IdParam IdType theId, @ResourceParam ImagingStudy imagingStudy) throws Exception {
        validateRequest(theRequestDetails, theId);

        MethodOutcome method = new MethodOutcome();
        method.setCreated(false);
        ImagingStudy mongoImagingStudy = imagingStudyRepository.update(imagingStudy);
        log.info("mongoImagingStudy.getIdElement().toString(): " + mongoImagingStudy.getIdElement().toString());
        method.setId(mongoImagingStudy.getIdElement());
        method.setResource(mongoImagingStudy);
        return method;
    }

    private void validateRequest(RequestDetails theRequestDetails, IdType theId) throws Exception {
        String patientHeader = theRequestDetails.getHeader("User-data");
        if(patientHeader == null){
            throw new Exception("User does not have rights");
        }

        ImagingStudy readingImagingStudy = imagingStudyRepository.read(theId);
        String patientImagingStudy = readingImagingStudy.getSubject().getReference();

        if(!patientHeader.equals(patientImagingStudy)){
            throw new Exception("User does not have rights");
        }
    }

    @Read
    public ImagingStudy readImagingStudy(RequestDetails requestDetails, @IdParam IdType internalId) throws Exception {
        String cookieHeader = requestDetails.getHeader(Constants.HEADER_COOKIE);
        jwtProvider.validateJwtToken(cookieHeader);

        ImagingStudy imagingStudy = imagingStudyRepository.read(internalId);
        return imagingStudy;
    }

    @Search
    public List<Resource> searchImagingStudy(
            @OptionalParam(name = ImagingStudy.SP_STARTED) DateParam started,
            @OptionalParam(name= ImagingStudy.SP_STATUS) StringParam status,
            @OptionalParam(name = ImagingStudy.SP_SUBJECT) StringParam subject
    ) {
        return imagingStudyRepository.search(subject, status);
    }

}
