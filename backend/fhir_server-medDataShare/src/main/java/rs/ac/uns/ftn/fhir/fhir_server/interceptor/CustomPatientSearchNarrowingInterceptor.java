package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizedList;
import ca.uhn.fhir.rest.server.interceptor.auth.SearchNarrowingInterceptor;

public class CustomPatientSearchNarrowingInterceptor extends SearchNarrowingInterceptor {

    /**
     * This method must be overridden to provide the list of compartments
     * and/or resources that the current user should have access to
     */
    @Override
    protected AuthorizedList buildAuthorizedList(RequestDetails theRequestDetails) {
        // Process authorization header - The following is a fake
        // implementation. Obviously we'd want something more real
        // for a production scenario.
        //
        // In this basic example we have two hardcoded bearer tokens,
        // one which is for a user that has access to one patient, and
        // another that has full access.
        String authHeader = theRequestDetails.getHeader("Authorization");
        if ("Bearer dfw98h38r".equals(authHeader)) {

            // This user will have access to two compartments
            return new AuthorizedList()
                    .addCompartment("Patient/123")
                    .addCompartment("Patient/456");

        } else if ("Bearer 39ff939jgg".equals(authHeader)) {

            // This user has access to everything
            return new AuthorizedList();

        } else {
            throw new AuthenticationException("Unknown bearer token");
        }
    }
}
