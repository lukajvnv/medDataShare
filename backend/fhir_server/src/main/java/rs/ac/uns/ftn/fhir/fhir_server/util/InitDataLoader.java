package rs.ac.uns.ftn.fhir.fhir_server.util;


import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.converter.ImagingStudyConverter;
import rs.ac.uns.ftn.fhir.fhir_server.entity.PatientEntity;

import java.util.ArrayList;
import java.util.Date;

@Component
public class InitDataLoader implements CommandLineRunner {

    @Autowired
    MongoOperations mongo;

    @Autowired
    ImagingStudyConverter imagingStudyConverter;

    @Override
    public void run(String... args) throws Exception {
        initPatients();
        initPdf();
//        initClinicalTrial();
    }

    private void initPatients(){
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setDateOfBirth(new Date());
//        mongo.save(patientEntity);
    }

    private void initPdf(){
        try {
//            PdfExporter.export();
//            PdfExporter.encrypt();
//            byte[] bytes = PdfExporter.getBytes();
//            PdfExporter.deleteFile();
        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initClinicalTrial(){
        ImagingStudy imagingStudy = new ImagingStudy();
        Reference referenceLocation = new Reference("fjdlkfdljfdlksjfdslj");
        referenceLocation.setDisplay("display name");
        imagingStudy.setLocation(referenceLocation);
        imagingStudy.setDescription("bla bla");
        imagingStudy.setStatus(ImagingStudy.ImagingStudyStatus.UNKNOWN);
        Reference referenceSubject = new Reference("patient1");
        referenceSubject.setDisplay("display name");
        imagingStudy.setSubject(referenceSubject);
        imagingStudy.setStarted( new Date());
        imagingStudy.addIdentifier()
                .setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
                .setValue("id1");
        imagingStudy.addIdentifier()
                .setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
                .setUse(Identifier.IdentifierUse.OFFICIAL)
                .setValue("id2");
        Meta meta = new Meta().addProfile("profile").setSource("jfdljdkf");
        imagingStudy.setMeta(meta);
        Coding codingModality = new Coding();
        codingModality.setDisplay("CT trial");
        codingModality.setCode("CT");
        codingModality.setSystem("system");
        imagingStudy.setModality(new ArrayList<>(){{add(codingModality);}});
        ImagingStudy.ImagingStudySeriesComponent component = new ImagingStudy.ImagingStudySeriesComponent();
        component.setNumber(2);
        Coding codingSeriesBody = new Coding();
        codingSeriesBody.setDisplay("picture type");
        codingSeriesBody.setCode("BINARY_ID");
        codingSeriesBody.setSystem("system");
        component.setBodySite(codingSeriesBody);
        imagingStudy.setSeries(new ArrayList<>(){{add(component);}});
        XhtmlNode xhtmlNode = new XhtmlNode(NodeType.Text, "desc");
        xhtmlNode.setContent("bjaljbalkj");
        Narrative narrative = new Narrative();
        narrative.setDiv(xhtmlNode);
        narrative.setStatus(Narrative.NarrativeStatus.ADDITIONAL);
        imagingStudy.setText(narrative);
        Coding procedureCode = new Coding();
        procedureCode.setDisplay("picture type");
        procedureCode.setCode("BINARY_ID");
        procedureCode.setSystem("system");
        CodeableConcept c = new CodeableConcept(procedureCode);
        c.setText("babababa");
        imagingStudy.setProcedureCode(new ArrayList<>(){{add(c);}});

        mongo.save(imagingStudyConverter.convertFHIRToDBEntity(imagingStudy));
    }

}
