package rs.ac.uns.ftn.fhir.fhir_server.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.LinkedHashSet;

@Getter
@Setter
public class Address  {

    private String city;
    private String county;
    private org.hl7.fhir.r4.model.Address.AddressUse use;
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    private String country;
    private String postcode;
    private Collection<String> lines = new LinkedHashSet<>();
}
