package rs.ac.uns.ftn.fhir.fhir_server.repository;

import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.Binary;
import org.hl7.fhir.r4.model.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.BinaryEntity;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IBinaryRepository;

@Repository
public class BinaryRepository implements IBinaryRepository {

    @Autowired
    MongoOperations mongo;

    @Override
    public Binary create(Binary binary) {
        String fileName = binary.getId();
        String contentType = binary.getContentType();
        BinaryEntity binaryEntity = BinaryEntity
                .builder()
                .fileName(fileName)
                .extension(contentType)
                .build();
        mongo.save(binaryEntity);
        binary.setIdElement(new IdType(binaryEntity.getId().toString()));
        return binary;
    }

    @Override
    public Binary read(IdType theId) {
        ObjectId objectId = new ObjectId(theId.getIdPart());
        Query qry = Query.query(Criteria.where("_id").is(objectId));
        BinaryEntity binaryEntity = mongo.findOne(qry, BinaryEntity.class);

        if(binaryEntity == null) return null;

        Binary binary = new Binary();
        binary.setIdElement(new IdType(binaryEntity.getFileName()));
        binary.setContentType(binaryEntity.getExtension());
        return binary;
    }
}
