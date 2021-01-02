package rs.ac.uns.ftn.medDataShare.controller.testing;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.service.FhirService;
import rs.ac.uns.ftn.medDataShare.util.ImageUtil;
import rs.ac.uns.ftn.medDataShare.util.PdfExporter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/test")
@Validated
public class TestController {

    @GetMapping
    public String hello(
            @RequestParam(value = "ID", required = false, defaultValue="") String requestType
    ) {
        return "Hello";
    }

    @GetMapping("/image")
    public byte[] image(
    ) {
        return fhirService.getImage();
    }

    @Autowired
    private FhirService fhirService;

    @GetMapping("/file")
    public byte[] file(
    ) {
        return fhirService.exportInPdf();
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("runtime");
    }

    @GetMapping("/pathVariable/{id}")
    public String testPathVariable(@PathVariable @Min(1) @Max(7) Integer id) {
        return "okej";
    }

    @GetMapping("/pathParam")
    public String testPathParam(@NotBlank @Size(max = 10, message = "Error brale") String username) {
        return "okej";
    }
}
