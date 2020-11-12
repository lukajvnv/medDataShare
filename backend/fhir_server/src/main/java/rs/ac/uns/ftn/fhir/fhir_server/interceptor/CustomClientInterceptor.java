package rs.ac.uns.ftn.fhir.fhir_server.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.Constants;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.util.Asserts;

import java.io.IOException;

@Interceptor
public class CustomClientInterceptor implements IClientInterceptor {

//    /**
//     * @param theUsername The username
//     * @param thePassword The password
//     */
//    public CustomClientInterceptor(String theUsername, String thePassword) {
//        this(StringUtils.defaultString(theUsername) + ":" + StringUtils.defaultString(thePassword));
//    }
//
//    /**
//     * @param theCredentialString A credential string in the format <code>username:password</code>
//     */
//    public CustomClientInterceptor(String theCredentialString) {
//        Asserts.notBlank(theCredentialString, "theCredentialString must not be null or blank");
//        Asserts.check(theCredentialString.contains(":"), "theCredentialString must be in the format 'username:password'");
//        String encoded = Base64.encodeBase64String(theCredentialString.getBytes(Constants.CHARSET_US_ASCII));
//        myHeaderValue = "Basic " + encoded;
//    }

//    @Hook(
//            value = Pointcut.CLIENT_REQUEST,
//            order = 1001
//    )
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
