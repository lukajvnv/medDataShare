package rs.ac.uns.ftn.fhir.fhir_server.converter;

import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.utilities.xhtml.NodeType;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.ImagingStudyEntity;
import rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.ImagingStudySeria;

import java.util.*;

@Component
public class ImagingStudyConverter implements EntityFHIRConverter<ImagingStudyEntity, ImagingStudy> {

    @Override
    public ImagingStudyEntity convertFHIRToDBEntity(ImagingStudy imagingStudy) {
        ImagingStudyEntity imagingStudyEntity = new ImagingStudyEntity();

        imagingStudyEntity.setStarted(imagingStudy.getStarted());
        imagingStudyEntity.setStatus(imagingStudy.getStatus());
        imagingStudyEntity.setDescription(imagingStudy.getDescription());

        Reference referenceLocation = imagingStudy.getLocation();
        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Reference location = new rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Reference();
        location.setDisplay(referenceLocation.getDisplay());
        location.setReference(referenceLocation.getReference());
        imagingStudyEntity.setLocation(location);

        Reference subjectReference = imagingStudy.getSubject();
        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Reference subject = new rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Reference();
        subject.setDisplay(subjectReference.getDisplay());
        subject.setReference(subjectReference.getReference());
        imagingStudyEntity.setSubject(subject);

        Meta meta = imagingStudy.getMeta();
        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Meta metaDb = new rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Meta();
        metaDb.setSource(meta.getSource());
        imagingStudyEntity.setMeta(metaDb);

        Collection<rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier> identifiers = new LinkedHashSet<>();
        for(Identifier identifier : imagingStudy.getIdentifier()){
            rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier identifierDb = new rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier();
            identifierDb.setSystem(identifier.getSystem());
            identifierDb.setValue(identifier.getValue());
            identifierDb.setIdentifierUse(identifier.getUse());
            identifiers.add(identifierDb);
        }
        imagingStudyEntity.setIdentifiers(identifiers);

        if(imagingStudy.getModality().size() == 1){
            Coding codingModality = imagingStudy.getModality().get(0);
            rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding codingDb = new rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding();
            codingDb.setCode(codingModality.getCode());
            codingDb.setSystem(codingModality.getSystem());
            codingDb.setDisplay(codingModality.getDisplay());
            imagingStudyEntity.setModality(codingDb);
        }

        if(imagingStudy.getProcedureCode().size() == 1){
            CodeableConcept procedure = imagingStudy.getProcedureCode().get(0);
            imagingStudyEntity.setText(procedure.getText());
        }

        Collection<ImagingStudySeria> series = new LinkedHashSet<>();
        if(imagingStudy.getSeries().size() == 1){
            ImagingStudy.ImagingStudySeriesComponent procedure = imagingStudy.getSeries().get(0);
            ImagingStudySeria imagingStudySeria = new ImagingStudySeria();
            imagingStudySeria.setNumber(1);
            Coding bodySite = procedure.getBodySite();
            rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding coding = new rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding();
            coding.setDisplay(bodySite.getDisplay());
            coding.setSystem(bodySite.getSystem());
            coding.setCode(bodySite.getCode());
            imagingStudySeria.setBody(coding);
            series.add(imagingStudySeria);
        }
        imagingStudyEntity.setSeries(series);

        return imagingStudyEntity;
    }

    @Override
    public ImagingStudy convertDBToFhirEntity(ImagingStudyEntity imagingStudyEntity) {
        ImagingStudy imagingStudy = new ImagingStudy();
        imagingStudy.setId(imagingStudyEntity.getId().toString());

        imagingStudy.setDescription(imagingStudyEntity.getDescription());
        imagingStudy.setStatus(imagingStudyEntity.getStatus());
        imagingStudy.setStarted(imagingStudyEntity.getStarted());

        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Reference referenceLocationDb = imagingStudyEntity.getLocation();
        Reference referenceLocation = new Reference(referenceLocationDb.getReference());
        referenceLocation.setDisplay(referenceLocationDb.getDisplay());
        imagingStudy.setLocation(referenceLocation);

        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Reference referenceSubjectDb = imagingStudyEntity.getSubject();
        Reference referenceSubject = new Reference(referenceSubjectDb.getReference());
        referenceSubject.setDisplay(referenceSubjectDb.getDisplay());
        imagingStudy.setSubject(referenceSubject);

        for(rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier identifier: imagingStudyEntity.getIdentifiers()){
            imagingStudy.addIdentifier()
                    .setSystem(identifier.getSystem())
                    .setUse(identifier.getIdentifierUse())
                    .setValue(identifier.getValue());
        }

        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Meta metaDb = imagingStudyEntity.getMeta();
        Meta meta = new Meta()
//                .addProfile(metaDb.getProfile().toString())
                .setSource(metaDb.getSource());
        imagingStudy.setMeta(meta);

        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding codingModalityDb = imagingStudyEntity.getModality();
        Coding codingModality = new Coding();
        codingModality.setDisplay(codingModalityDb.getDisplay());
        codingModality.setCode(codingModalityDb.getCode());
        codingModality.setSystem(codingModalityDb.getSystem());
        imagingStudy.setModality(new ArrayList<>(){{add(codingModality);}});



        List<ImagingStudy.ImagingStudySeriesComponent> series = new ArrayList<>();
        for(ImagingStudySeria imagingStudySeria : imagingStudyEntity.getSeries()) {
            ImagingStudy.ImagingStudySeriesComponent seriaComponent = new ImagingStudy.ImagingStudySeriesComponent();
            seriaComponent.setNumber(imagingStudySeria.getNumber());

            rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding codingSeriaDb = imagingStudySeria.getBody();
            Coding codingSeriesBody = new Coding();
            codingSeriesBody.setDisplay(codingSeriaDb.getDisplay());
            codingSeriesBody.setCode(codingSeriaDb.getCode());
            codingSeriesBody.setSystem(codingSeriaDb.getSystem());
            seriaComponent.setBodySite(codingSeriesBody);

            series.add(seriaComponent);
        }
        imagingStudy.setSeries(series);


//        XhtmlNode xhtmlNode = new XhtmlNode(NodeType.Text, "desc");
//        xhtmlNode.setContent("bjaljbalkj");
//        Narrative narrative = new Narrative();
//        narrative.setDiv(xhtmlNode);
//        narrative.setStatus(Narrative.NarrativeStatus.ADDITIONAL);
//        imagingStudy.setText(narrative);

        rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding procedureCodeDb = new rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.Coding();
        CodeableConcept c = new CodeableConcept();
        c.setText(imagingStudyEntity.getText());
        imagingStudy.setProcedureCode(new ArrayList<>(){{add(c);}});

        return imagingStudy;
    }
}
