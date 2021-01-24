package rs.ac.uns.ftn.medDataShare.fhir;

import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.medDataShare.security.dto.UserPrinciple;
import rs.ac.uns.ftn.medDataShare.security.jwt.JwtProvider;
import rs.ac.uns.ftn.medDataShare.service.SymmetricCryptography;

import java.io.IOException;

@Component
@Interceptor
public class CustomClientInterceptor implements IClientInterceptor {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private SymmetricCryptography symmetricCryptography;

    @Override
    public void interceptRequest(IHttpRequest iHttpRequest) {
        String requestUser = "anonymousRequestUser";
        try {
            UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            requestUser = symmetricCryptography.putInfoInDb(userPrinciple.getId());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        iHttpRequest.addHeader(Constants.HEADER_AUTHORIZATION, Constants.HEADER_AUTHORIZATION_VALPREFIX_BEARER + "myHeaderValue");
        String token = jwtProvider.generateJwtToken(requestUser, 15);
        iHttpRequest.addHeader(Constants.HEADER_COOKIE, token);
    }

    @Override
    public void interceptResponse(IHttpResponse iHttpResponse) throws IOException {
    }
}
