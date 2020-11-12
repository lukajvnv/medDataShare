package rs.ac.uns.ftn.fhir.fhir_server.repository;

import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.param.StringParam;
import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.fhir.fhir_server.converter.ImagingStudyConverter;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Name;
import rs.ac.uns.ftn.fhir.fhir_server.entity.PatientEntity;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Telecom;
import rs.ac.uns.ftn.fhir.fhir_server.entity.imagingStudy.ImagingStudyEntity;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IImagingStudyRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagingStudyRepository implements IImagingStudyRepository {

    @Autowired
    MongoOperations mongo;

    @Autowired
    ImagingStudyConverter imagingStudyConverter;

    @Override
    public ImagingStudy create(ImagingStudy imagingStudy) {
        ImagingStudyEntity imagingStudyEntity = imagingStudyConverter.convertFHIRToDBEntity(imagingStudy);

        ImagingStudyEntity savedEntity = mongo.save(imagingStudyEntity);

        return imagingStudyConverter.convertDBToFhirEntity(savedEntity);
    }

    @Override
    public ImagingStudy update(ImagingStudy imagingStudy) {
        ImagingStudyEntity imagingStudyEntity = getEntity(imagingStudy.getIdElement());
        if (imagingStudyEntity == null) return null;

        imagingStudyEntity.setStatus(imagingStudy.getStatus());
        mongo.save(imagingStudyEntity);
        return imagingStudyConverter.convertDBToFhirEntity(imagingStudyEntity);
    }

    @Override
    public ImagingStudy read(IdType theId) {
        ImagingStudyEntity imagingStudyEntity = getEntity(theId);
        if (imagingStudyEntity == null) return null;
        return imagingStudyConverter.convertDBToFhirEntity(imagingStudyEntity);
    }

    @Override
    public List<Resource> search(
            @OptionalParam(name = ImagingStudy.SP_SUBJECT) StringParam subject,
            @OptionalParam(name= ImagingStudy.SP_STATUS) StringParam status
    ) {
        List<Resource> resources = new ArrayList<>();

        Criteria criteria = new Criteria();
        criteria = Criteria.where("subject.reference").is(subject.getValue());

        if(status != null){
            criteria.and("status").is("REGISTERED");
        }

        if (criteria != null) {
            Query qry = Query.query(criteria);

            List<ImagingStudyEntity> imagingStudyResults = mongo.find(qry, ImagingStudyEntity.class);

            for (ImagingStudyEntity imagingStudyEntity : imagingStudyResults) {
                resources.add(imagingStudyConverter.convertDBToFhirEntity(imagingStudyEntity));
            }
        }
        return resources;
    }

    private ImagingStudyEntity getEntity(IdType theId){
        ObjectId objectId = new ObjectId(theId.getIdPart());
        Query qry = Query.query(Criteria.where("_id").is(objectId));
        ImagingStudyEntity imagingStudyEntity = mongo.findOne(qry, ImagingStudyEntity.class);

        return imagingStudyEntity;
    }
}
