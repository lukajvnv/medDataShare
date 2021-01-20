package rs.ac.uns.ftn.medDataShare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedDataShareBackendApplication {

	public static void main(String[] args) {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
		SpringApplication.run(MedDataShareBackendApplication.class, args);
	}

}
