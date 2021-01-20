package rs.ac.uns.ftn.fhir.fhir_server.provider;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.r4.model.Binary;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.repository.BinaryRepository;
import rs.ac.uns.ftn.fhir.fhir_server.util.ImageUtil;

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
    public MethodOutcome createBinary(@ResourceParam Binary binary) throws Exception {

        log.debug("Create Patient Provider called");

        MethodOutcome method = new MethodOutcome();
        method.setCreated(true);
        OperationOutcome opOutcome = new OperationOutcome();

        method.setOperationOutcome(opOutcome);
        boolean uploaded = false;
        try {
            System.out.println("input content type: " + binary.getContentType());

            String binaryId = Long.toString(System.currentTimeMillis());

            String fileType = binary.getContentType();
            String fileName = binaryId + "." + fileType;
            if(ImageUtil.uploadImage(binary.getContent(), fileName, fileType)){
                binary.setIdElement(new IdType(binaryId));

                Binary binaryCreated = binaryRepository.create(binary);

                method.setId(new IdType(binaryCreated.getId()));
                method.setResource(binary);

                log.debug("called create Patient method");

                return method;
            } else {
                throw new Exception("UploadImage has failed");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new Exception(ex.getMessage());
        }
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
