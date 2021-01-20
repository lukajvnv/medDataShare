package rs.ac.uns.ftn.fhir.fhir_server.repository.declaration;

import org.hl7.fhir.r4.model.Binary;
import org.hl7.fhir.r4.model.IdType;

public interface IBinaryRepository {

    Binary create(Binary patient);
    Binary read(IdType theId);
}
