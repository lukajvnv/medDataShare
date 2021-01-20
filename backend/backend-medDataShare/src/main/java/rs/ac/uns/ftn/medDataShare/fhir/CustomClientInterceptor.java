package rs.ac.uns.ftn.medDataShare.fhir;

import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.security.jwt.JwtProvider;

import java.io.IOException;

@Component
@Interceptor
public class CustomClientInterceptor implements IClientInterceptor {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {
        iHttpRequest.addHeader(Constants.HEADER_AUTHORIZATION, Constants.HEADER_AUTHORIZATION_VALPREFIX_BEARER + "myHeaderValue");
        String token = jwtProvider.generateJwtToken("username", 15);
        iHttpRequest.addHeader(Constants.HEADER_COOKIE, token);  //$NON-NLS-1$
//        System.out.println("request, token: " + token);
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) throws IOException {
//        System.out.println("response");
    }
}
