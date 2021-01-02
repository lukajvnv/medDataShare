package rs.ac.uns.ftn.fhir.fhir_server.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Identifier {
	private String system;
	private String value;
	private Integer order;
	org.hl7.fhir.r4.model.Identifier.IdentifierUse identifierUse;
}
