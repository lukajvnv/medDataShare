package rs.ac.uns.ftn.fhir.fhir_server.entity;


import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.HumanName;

@Getter
@Setter
public class Name {

    private String prefix;
    private String givenName;
    private String familyName;
    private String suffix;
    private HumanName.NameUse nameUse;
}
