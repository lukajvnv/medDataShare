package rs.ac.uns.ftn.fhir.fhir_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.servlet.FhirServer;

@Component
public class FhirConfiguration {

    @Autowired
    ApplicationContext context;

    @Bean
    public ServletRegistrationBean ServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new FhirServer(context), "/FHIR/*");
        registration.setName("FhirServlet");
        return registration;
    }

}
