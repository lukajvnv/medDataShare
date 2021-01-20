package rs.ac.uns.ftn.fhir.fhir_server.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.servlet.FhirServer;

@Component
public class FhirConfiguration {


    //*************************FHIR-CLIENT**************************

    @Bean
    public FhirContext getFhirContext() {
        return FhirContext.forR4();
    }

    @Bean
    public IGenericClient getFhirClient() {
        FhirContext context = getFhirContext();
        return context.newRestfulGenericClient("http://127.0.0.1:8183/FHIR");
    }

    //*****************************************************

    @Autowired
    ApplicationContext context;

    @Bean
    public ServletRegistrationBean ServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new FhirServer(context), "/FHIR/*");
        registration.setName("FhirServlet");
        return registration;
    }

}
