package rs.ac.uns.ftn.fhir.fhir_server.provider;

import ca.uhn.fhir.rest.annotation.*;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.repository.BinaryRepository;
import rs.ac.uns.ftn.fhir.fhir_server.util.ImageUtil;

import java.util.Date;

/**
 * This is a resource provider which stores Patient resources in memory using a HashMap. This is obviously not a production-ready solution for many reasons,
 * but it is useful to help illustrate how to build a fully-functional server.
 */
@Component
public class BinaryProvider implements IResourceProvider {

    private static final Logger log = LoggerFactory.getLogger(BinaryProvider.class);

    @Autowired
    BinaryRepository binaryRepository;

    @Override
    public Class<Binary> getResourceType() {
        return Binary.class;
    }

    @Create
    public MethodOutcome createBinary(@ResourceParam Binary binary) {

        log.debug("Create Patient Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();

        method.setOperationOutcome(opOutcome);
        boolean uploaded = false;
        try {
            System.out.println("input id: " + binary.getId());
            System.out.println("input content type: " + binary.getContentType());

            String binaryId = Long.toString(System.currentTimeMillis());

            String fileType = binary.getContentType();
            String fileName = binaryId + "." + fileType;
            uploaded = ImageUtil.uploadImage(binary.getContent(), fileName, fileType);
            binary.setIdElement(new IdType(binaryId));

            Binary binaryCreated = binaryRepository.create(binary);

            method.setId(new IdType(binaryCreated.getId()));
            method.setResource(binary);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        log.debug("called create Patient method");

        return method;
    }

    @Read(version = true)
    public Binary readBinary(@IdParam IdType theId) {
        Binary binary = binaryRepository.read(theId);
        String fileName = binary.getId();
        String extension = binary.getContentType();
        binary.setContent(ImageUtil.getFile(fileName, extension));
        return binary;
    }

}
