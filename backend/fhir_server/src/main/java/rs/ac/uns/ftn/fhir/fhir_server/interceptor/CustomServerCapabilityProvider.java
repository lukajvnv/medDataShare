package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.annotation.Metadata;
import org.hl7.fhir.r4.hapi.rest.server.ServerCapabilityStatementProvider;
import org.hl7.fhir.r4.model.CapabilityStatement;

public class CustomServerCapabilityProvider extends ServerCapabilityStatementProvider {

    @Metadata
    public CapabilityStatement getServerMetadata() {
        CapabilityStatement retVal = new CapabilityStatement();
        retVal.setName("CustomServerCapabilityProvider");
        // ..populate..
        return retVal;
    }
}
