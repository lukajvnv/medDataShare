package rs.ac.uns.ftn.medDataShare.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import rs.ac.uns.ftn.medDataShare.converter.ClinicalTrialConverter;
import rs.ac.uns.ftn.medDataShare.dto.form.ClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.form.EditClinicalTrialForm;
import rs.ac.uns.ftn.medDataShare.dto.medInstitution.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.util.PdfExporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FhirService {

    @Autowired
    private IGenericClient fhirClient;

    @Autowired
    private ClinicalTrialConverter clinicalTrialConverter;

    public byte[] exportInPdf() {
        return PdfExporter.clinicalTrialExportPdf("");
    }

    public byte[] exportInPdf(String clinicalTrialId) {
        ClinicalTrialDto clinicalTrialDto = getImagingStudy(clinicalTrialId);
        try {
            Binary binary = getBinary(clinicalTrialDto.getResourcePath());

            return PdfExporter.clinicalTrialExportPdf(clinicalTrialDto, binary);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getImage() {
        Binary binary = fhirClient
                .read()
                .resource(Binary.class)
                .withId("1124683")
                .execute();

        byte[] bytes = binary.getContent();

        return bytes;
    }

    public byte[] getImage(String binaryId) {
        Binary binary = fhirClient
                .read()
                .resource(Binary.class)
                .withId(binaryId)
                .execute();

        byte[] bytes = binary.getContent();

        return bytes;
    }

    public List<ClinicalTrialDto> searchImagingStudy(String patient, boolean onlyIdle){
        try {
            String imagingStudyStatus = onlyIdle ? "registered" : null;
            Bundle response = fhirClient
                    .search()
                    .forResource(ImagingStudy.class)
                    .where(ImagingStudy.SUBJECT.hasId(patient))
                    .and(ImagingStudy.STATUS.hasSystemWithAnyCode(imagingStudyStatus))
                    .returnBundle(Bundle.class)
                    .execute();
            System.out.println("Found " + response.getTotal());
            List<ClinicalTrialDto> clinicalTrials = new ArrayList<>();
            for(Bundle.BundleEntryComponent entry : response.getEntry()) {
                ImagingStudy imagingStudy = (ImagingStudy)entry.getResource();
                clinicalTrials.add(clinicalTrialConverter.convertToDto(imagingStudy));
            }
            return clinicalTrials;
        } catch (Exception e) {
            System.out.println("An error occurred trying to search:");
            e.printStackTrace();
            return null;
        }
    }

    public ClinicalTrialDto addClinicalTrial(ClinicalTrialForm clinicalTrialForm){
        try {
            Binary binary = createBinary(clinicalTrialForm);

            MethodOutcome binaryOutcome = fhirClient
                    .create()
                    .resource(binary)
                    .execute();

            IdType idBinary = (IdType) binaryOutcome.getId();
            System.out.println("Resource is available at: " + idBinary.getValue());
            Binary receivedBinary = (Binary) binaryOutcome.getResource();

            ImagingStudy imagingStudy = createImagingStudy(clinicalTrialForm, idBinary.getValue(), receivedBinary.getContentType());
            MethodOutcome imagingStudyOutcome = fhirClient
                    .create()
                    .resource(imagingStudy)
                    .prettyPrint()
                    .encodedXml()
                    .execute();

            IdType id = (IdType) imagingStudyOutcome.getId();
            System.out.println("Resource is available at: " + id.getValue());

            ImagingStudy receivedImagingStudy = (ImagingStudy) imagingStudyOutcome.getResource();
            return clinicalTrialConverter.convertToDto(receivedImagingStudy);
        } catch (DataFormatException e) {
            System.out.println("An error occurred trying to upload:");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ClinicalTrialDto();
    }

    public ClinicalTrialDto updateClinicalTrial(EditClinicalTrialForm editClinicalTrialForm){
        ImagingStudy imagingStudy = new ImagingStudy();
        ImagingStudy.ImagingStudyStatus imagingStudyStatus = clinicalTrialConverter.convertAccessType(editClinicalTrialForm.getAccessType());
        imagingStudy.setStatus(imagingStudyStatus);

        try {
            MethodOutcome outcome = fhirClient
                    .update()
                    .resource(imagingStudy)
                    .withId(editClinicalTrialForm.getId())
                    .prettyPrint()
                    .encodedXml()
                    .execute();

            IdType id = (IdType) outcome.getId();
            System.out.println("Resource is available at: " + id.getValue());

        } catch (DataFormatException e) {
            System.out.println("An error occurred trying to upload:");
            e.printStackTrace();
        }
        return new ClinicalTrialDto();
    }

    public ClinicalTrialDto getImagingStudy(String imagingStudyId){
        ImagingStudy imagingStudy = fhirClient
                .read()
                .resource(ImagingStudy.class)
                .withId(imagingStudyId).execute();

        return clinicalTrialConverter.convertToDto(imagingStudy);
    }

    private Binary createBinary(ClinicalTrialForm clinicalTrialForm) throws IOException {
        Binary binary = new Binary();
        String contentType = clinicalTrialForm.getFile().getContentType();
        contentType = contentType.substring(contentType.indexOf("/") + 1);
        binary.setContentType(contentType);
        binary.setContent(clinicalTrialForm.getFile().getBytes());
        return binary;
    }

    private Binary getBinary(String binaryId) throws IOException {
        try {
            Binary binary = fhirClient
                    .read()
                    .resource(Binary.class)
                    .withId(binaryId)
                    .execute();
            return binary;
        } catch (DataFormatException e) {
            System.out.println("An error occurred trying to upload:");
            e.printStackTrace();
        }
        return new Binary();
    }

    private ImagingStudy createImagingStudy(ClinicalTrialForm clinicalTrialForm, String binaryId, String contentType){
        ImagingStudy imagingStudy = new ImagingStudy();
        Reference referenceLocation = new Reference(clinicalTrialForm.getDoctor());
        referenceLocation.setDisplay("display institution name");
        imagingStudy.setLocation(referenceLocation);
        imagingStudy.setDescription(clinicalTrialForm.getConclusion());
        imagingStudy.setStatus(ImagingStudy.ImagingStudyStatus.REGISTERED);
        Reference referenceSubject = new Reference(clinicalTrialForm.getPatient());
        referenceSubject.setDisplay("display name: " + clinicalTrialForm.getPatient());
        imagingStudy.setSubject(referenceSubject);
        imagingStudy.setStarted(new Date());
        imagingStudy.addIdentifier()
                .setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
                .setValue("id1");
        imagingStudy.addIdentifier()
                .setSystem("http://ns.electronichealth.net.au/id/hi/ihi/1.0")
                .setUse(Identifier.IdentifierUse.OFFICIAL)
                .setValue("id2");
        Meta meta = new Meta().addProfile("profile").setSource(clinicalTrialForm.getIntroduction());
        imagingStudy.setMeta(meta);
        Coding codingModality = new Coding();
        codingModality.setDisplay("trial type desc");
        codingModality.setCode(clinicalTrialForm.getClinicalTrialType().toString());
        codingModality.setSystem("system");
        imagingStudy.setModality(new ArrayList<>(){{add(codingModality);}});
        ImagingStudy.ImagingStudySeriesComponent component = new ImagingStudy.ImagingStudySeriesComponent();
        component.setNumber(1);
        Coding codingSeriesBody = new Coding();
        codingSeriesBody.setDisplay(contentType);
        codingSeriesBody.setCode(binaryId);
        codingSeriesBody.setSystem("system");
        component.setBodySite(codingSeriesBody);
        imagingStudy.setSeries(new ArrayList<>(){{add(component);}});

        XhtmlNode xhtmlNode = new XhtmlNode(NodeType.Text, "desc");
        xhtmlNode.setContent("text content");
        Narrative narrative = new Narrative();
        narrative.setDiv(xhtmlNode);
        narrative.setStatus(Narrative.NarrativeStatus.ADDITIONAL);
        imagingStudy.setText(narrative);

        Coding procedureCode = new Coding();
        procedureCode.setDisplay("picture type");
        procedureCode.setCode("BINARY_ID");
        procedureCode.setSystem("system");
        CodeableConcept c = new CodeableConcept(procedureCode);
        c.setText(clinicalTrialForm.getRelevantParameters());
        imagingStudy.setProcedureCode(new ArrayList<>(){{add(c);}});
        return imagingStudy;
    }
}
