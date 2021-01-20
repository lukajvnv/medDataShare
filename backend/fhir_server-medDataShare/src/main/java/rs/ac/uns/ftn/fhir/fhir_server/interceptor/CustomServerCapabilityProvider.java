package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.annotation.Metadata;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import org.hl7.fhir.r4.hapi.rest.server.ServerCapabilityStatementProvider;
import org.hl7.fhir.r4.model.CapabilityStatement;

import javax.servlet.http.HttpServletRequest;

public class CustomServerCapabilityProvider extends ServerCapabilityStatementProvider {

    @Metadata
    public CapabilityStatement getServerMetadata(HttpServletRequest theRequest, RequestDetails theRequestDetails) {
        CapabilityStatement retVal = super.getServerConformance(theRequest, theRequestDetails);
        retVal.setName("CustomServerCapabilityProvider");
        retVal.setDescription("CustomServerCapabilityProvider implementation");
        retVal.setCopyright("Luka's copyright");
        retVal.setPublisher("Luka's Publisher");
        return retVal;
    }

}
