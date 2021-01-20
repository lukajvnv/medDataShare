package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

import java.util.List;
import java.util.Map;

public class CustomServerAuthorizationInterceptor extends AuthorizationInterceptor {

    @Override
    public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
        // Process this header
        String authHeader = theRequestDetails.getHeader(Constants.HEADER_AUTHORIZATION);
        String cookieHeader = theRequestDetails.getHeader(Constants.HEADER_COOKIE);
        String operation = theRequestDetails.getOperation();
        Map<String, String[]> params = theRequestDetails.getParameters();
        theRequestDetails.getRequestId();
        String requestPath = theRequestDetails.getRequestPath();



        RuleBuilder builder = new RuleBuilder();
//        return builder
//                .allowAll()
////                .allow().metadata().andThen()
////                .allow().read().allResources().withAnyId().andThen()
////                .allow().write().resourcesOfType(Observation.class)
////                .inCompartment("Patient", new IdType("Patient/123"))
//        .build()
//        ;

//        AuthenticationException ex = new AuthenticationException("Missing or invalid Authorization header value");
//        ex.addAuthenticateHeaderForRealm("myRealm");
//        throw ex;

        return new RuleBuilder().allowAll().build();
//        return new RuleBuilder().denyAll().build();
//          return new RuleBuilder().deny().write().allResources().withAnyId()
//                .build();
//          return new RuleBuilder().deny().write().resourcesOfType(Patient.class).withAnyId().andThen().allowAll().build();
//          return new RuleBuilder().deny().write().resourcesOfType(Patient.class).inCompartment("Patient", new IdType("Patient/5ffd7fc5935e485a20b0acee")).andThen().allowAll().build();

    }
}
