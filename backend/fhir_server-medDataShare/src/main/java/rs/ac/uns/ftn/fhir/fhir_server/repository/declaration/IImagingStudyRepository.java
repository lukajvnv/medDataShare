package rs.ac.uns.ftn.fhir.fhir_server.repository.declaration;

import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.param.StringParam;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Resource;

import java.util.List;

public interface IImagingStudyRepository {

    ImagingStudy create(ImagingStudy imagingStudy);
    ImagingStudy update(ImagingStudy imagingStudy);

    ImagingStudy read(IdType theId);

    List<Resource> search (
            @OptionalParam(name = ImagingStudy.SP_SUBJECT) StringParam subject,
            @OptionalParam(name= ImagingStudy.SP_STATUS) StringParam status
    );
}
