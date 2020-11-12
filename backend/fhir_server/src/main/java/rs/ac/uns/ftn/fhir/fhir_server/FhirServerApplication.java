package rs.ac.uns.ftn.fhir.fhir_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://nhsconnect.github.io/CareConnectAPI/build_patient_server.html
 * https://github.com/nhsconnect/careconnect-examples/tree/master/ccri-nosql-fhirStarter
 */

@SpringBootApplication
public class FhirServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FhirServerApplication.class, args);
	}

}
