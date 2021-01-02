
package rs.ac.uns.ftn.fhir.fhir_server.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.Enumerations;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patient")
public class PatientEntity  {

    @Id
    private ObjectId id;

    private Date dateOfBirth;
    private Enumerations.AdministrativeGender gender;
    private Collection<rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier> identifiers  = new LinkedHashSet<>();
    private Collection<rs.ac.uns.ftn.fhir.fhir_server.entity.Telecom> telecoms = new LinkedHashSet<>();
    private Collection<Name> names = new LinkedHashSet<>();
    private Collection<Address> addresses = new LinkedHashSet<>();

}
