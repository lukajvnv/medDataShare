package rs.ac.uns.ftn.medDataShare.fhir;

import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;

import java.io.IOException;

@Interceptor
public class CustomClientInterceptor implements IClientInterceptor {

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {
        iHttpRequest.addHeader(Constants.HEADER_AUTHORIZATION, Constants.HEADER_AUTHORIZATION_VALPREFIX_BEARER + "myHeaderValue");
        iHttpRequest.addHeader(Constants.HEADER_COOKIE, "cookie"); //$NON-NLS-1$
        System.out.println("request");
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) throws IOException {
        System.out.println("response");
    }
}
