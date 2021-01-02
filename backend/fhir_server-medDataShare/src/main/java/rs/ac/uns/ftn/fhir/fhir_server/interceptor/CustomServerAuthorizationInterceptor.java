package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Observation;

import java.util.List;

public class CustomServerAuthorizationInterceptor extends AuthorizationInterceptor {

    @Override
    public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {

        // Process this header
        String authHeader = theRequestDetails.getHeader(Constants.HEADER_AUTHORIZATION);

        RuleBuilder builder = new RuleBuilder();
        builder
                .allowAll()
//                .allow().metadata().andThen()
//                .allow().read().allResources().withAnyId().andThen()
//                .allow().write().resourcesOfType(Observation.class)
//                .inCompartment("Patient", new IdType("Patient/123"))
        ;

//        AuthenticationException ex = new AuthenticationException("Missing or invalid Authorization header value");
//        ex.addAuthenticateHeaderForRealm("myRealm");
//        throw ex;

        return builder.build();
    }
}
