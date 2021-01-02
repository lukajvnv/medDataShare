package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;

public class CustomClientAdapterInterceptor {

    @Hook(Pointcut.CLIENT_REQUEST)
    public void logRequestDetails(RequestDetails theRequest) {


        return;
    }

}
