package rs.ac.uns.ftn.fhir.fhir_server.servlet;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.FhirVersionEnum;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.server.FifoMemoryPagingProvider;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.LoggingInterceptor;
import ca.uhn.fhir.util.VersionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import rs.ac.uns.ftn.fhir.fhir_server.interceptor.CustomServerAuthorizationInterceptor;
import rs.ac.uns.ftn.fhir.fhir_server.interceptor.CustomServerInterceptor;
import rs.ac.uns.ftn.fhir.fhir_server.provider.BinaryProvider;
import rs.ac.uns.ftn.fhir.fhir_server.provider.ImagingStudyProvider;
import rs.ac.uns.ftn.fhir.fhir_server.provider.PatientProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.TimeZone;


public class FhirServer extends RestfulServer {

	private static final long serialVersionUID = 1L;
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FhirServer.class);

	private ApplicationContext applicationContext;

	public FhirServer(ApplicationContext context) {
		this.applicationContext = context;
	}

	@Value("http://127.0.0.1/FHIR")
	private String serverBase;


    @Override
	public void addHeadersToResponse(HttpServletResponse theHttpResponse) {
		theHttpResponse.addHeader("X-Powered-By", "HAPI FHIR " + VersionUtil.getVersion() + " RESTful Server (INTEROPen Care Connect STU3)");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initialize() throws ServletException {
		super.initialize();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		FhirVersionEnum fhirVersion = FhirVersionEnum.R4;
		setFhirContext(new FhirContext(fhirVersion));

		if (serverBase != null && !serverBase.isEmpty()) {
            setServerAddressStrategy(new HardcodedServerAddressStrategy(serverBase));
        }

		setResourceProviders(Arrays.asList(
				applicationContext.getBean(PatientProvider.class),
				applicationContext.getBean(ImagingStudyProvider.class),
				applicationContext.getBean(BinaryProvider.class)
		));

		FifoMemoryPagingProvider pp = new FifoMemoryPagingProvider(10);
		pp.setDefaultPageSize(10);
		pp.setMaximumPageSize(100);
		setPagingProvider(pp);

		setDefaultPrettyPrint(true);
		setDefaultResponseEncoding(EncodingEnum.JSON);

		registerInterceptor(new CustomServerAuthorizationInterceptor());
		registerInterceptor(new LoggingInterceptor());
		registerInterceptor(new CustomServerInterceptor());

		//custom metadata itd... POSTOJI PREDEFINISAN
//		setServerConformanceProvider(new CustomServerCapabilityProvider());

//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		CorsInterceptor corsInterceptor = new CorsInterceptor(corsConfiguration);
	}

}
