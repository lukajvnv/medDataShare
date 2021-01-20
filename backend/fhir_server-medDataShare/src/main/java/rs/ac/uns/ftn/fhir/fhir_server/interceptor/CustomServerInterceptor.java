package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.ResponseDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * https://hapifhir.io/hapi-fhir/docs/interceptors/server_pointcuts.html
 */
@Interceptor
public class CustomServerInterceptor {

    @Hook(
            value = Pointcut.SERVER_OUTGOING_RESPONSE,
            order = 10000
    )
    public boolean outgoingResponse(RequestDetails theRequestDetails, ResponseDetails theResponseObject, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse) throws AuthenticationException {


        return true;
    }

    @Hook(
            value = Pointcut.SERVER_INCOMING_REQUEST_PRE_PROCESSED,
            order = 10000
    )
    public boolean preProcessed(RequestDetails theRequestDetails, ResponseDetails theResponseObject, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse) throws AuthenticationException {


        return true;  /// AKO JE FALSE OVDE SE STOPIRA IZVRSAVANJE
    }

    @Hook(
            value = Pointcut.SERVER_HANDLE_EXCEPTION,
            order = 10000
    )
    public boolean exceptionOccurred(RequestDetails theRequestDetails, ResponseDetails theResponseObject, HttpServletRequest theServletRequest, HttpServletResponse theServletResponse) throws AuthenticationException {
        return true;
    }
}
