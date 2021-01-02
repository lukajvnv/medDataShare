package rs.ac.uns.ftn.medDataShare.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.medDataShare.fhir.CustomClientInterceptor;

@Component
public class FhirConfig {

    @Autowired
    ApplicationContext context;

    @Bean
    public FhirContext getFhirContext() {
        FhirContext context = FhirContext.forR4();
        context.getRestfulClientFactory().setConnectTimeout(60 * 1000);
        context.getRestfulClientFactory().setSocketTimeout(60 * 1000);
        return context;
    }

    @Bean
    public IGenericClient getFhirClient() {
        FhirContext context = getFhirContext();
        IGenericClient fhirClient = context.newRestfulGenericClient("http://127.0.0.1:8183/FHIR");
        fhirClient.registerInterceptor(new CustomClientInterceptor());
        return fhirClient;
    }
}
