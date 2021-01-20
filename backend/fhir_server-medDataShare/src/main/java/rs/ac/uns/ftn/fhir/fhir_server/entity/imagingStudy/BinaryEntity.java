
package rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "binary")
public class BinaryEntity {

    @Id
    private ObjectId id;
    private String fileName;
    private String extension;
}
