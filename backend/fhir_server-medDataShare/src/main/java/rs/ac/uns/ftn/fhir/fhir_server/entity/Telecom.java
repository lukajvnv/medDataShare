package rs.ac.uns.ftn.fhir.fhir_server.entity;


import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.ContactPoint;

@Getter
@Setter
public class Telecom  {

	private String value;
	ContactPoint.ContactPointUse telecomUse;
	ContactPoint.ContactPointSystem system;
}
