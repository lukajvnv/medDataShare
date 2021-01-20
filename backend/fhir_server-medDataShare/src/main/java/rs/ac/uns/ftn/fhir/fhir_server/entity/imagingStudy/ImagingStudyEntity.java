package rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy;

import lombok.*;
import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "imaging_study")
public class ImagingStudyEntity {

    @Id
    private ObjectId id;

    private ImagingStudy.ImagingStudyStatus status;
    private Date started;
    private Collection<Identifier> identifiers  = new LinkedHashSet<>();
    private Reference subject;
    private Reference location;
    private Coding modality;
    private String description;
    private String text;
    private Meta meta;
    private Collection<ImagingStudySeria> series  = new LinkedHashSet<>();
}
