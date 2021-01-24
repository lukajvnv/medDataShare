package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.util.JwtProvider;

import java.util.List;

@Component
public class CustomServerAuthorizationInterceptor extends AuthorizationInterceptor {

    @Autowired
    JwtProvider jwtProvider;

    private static final Logger log = LoggerFactory.getLogger(CustomServerAuthorizationInterceptor.class);

    @Override
    public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
        try {
            String cookieToken = theRequestDetails.getHeader(Constants.HEADER_COOKIE);
            jwtProvider.validateJwtToken(cookieToken);
            String requestUser = jwtProvider.getUserNameFromJwtToken(cookieToken);
            String id = theRequestDetails.getRequestId();
            String requestPath = theRequestDetails.getRequestPath();
            String requestType = theRequestDetails.getRequestType().toString();
            log.info("requestId: {},  requestPath: {}, requestType: {}, requestUser: {}", id, requestPath, requestType, requestUser);
        } catch (Exception e){
            AuthenticationException ex = new AuthenticationException("AuthorizationError");
            ex.addAuthenticateHeaderForRealm("myRealm");
            throw ex;
        }

        return new RuleBuilder().allowAll().build();
    }
}
