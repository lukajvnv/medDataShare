package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizedList;
import ca.uhn.fhir.rest.server.interceptor.auth.SearchNarrowingInterceptor;

public class CustomPatientSearchNarrowingInterceptor extends SearchNarrowingInterceptor {

    /**
     * This method must be overridden to provide the list of compartments
     * and/or resources that the current user should have access to
     */
    @Override
    protected AuthorizedList buildAuthorizedList(RequestDetails theRequestDetails) {
        return new AuthorizedList();
    }
}
