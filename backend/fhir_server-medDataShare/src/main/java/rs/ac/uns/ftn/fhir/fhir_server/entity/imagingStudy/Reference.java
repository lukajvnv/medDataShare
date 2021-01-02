package rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy;

import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.ImagingStudy;

import java.util.Date;

@Getter
@Setter
public class Reference {

    private String reference;
    private String display;


}
