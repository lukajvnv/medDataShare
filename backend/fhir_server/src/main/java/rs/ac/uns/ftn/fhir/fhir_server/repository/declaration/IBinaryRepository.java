package rs.ac.uns.ftn.fhir.fhir_server.repository.declaration;

import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import org.hl7.fhir.r4.model.Binary;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Resource;
import rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.BinaryEntity;

import java.util.List;

public interface IBinaryRepository {

    Binary create(Binary patient);
    Binary read(IdType theId);
}
