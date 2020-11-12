package rs.ac.uns.ftn.fhir.fhir_server.repository;

import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import org.bson.types.ObjectId;
import org.hl7.fhir.r4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.fhir.fhir_server.converter.PatientEntityToFHIRPatient;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Name;
import rs.ac.uns.ftn.fhir.fhir_server.entity.PatientEntity;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Telecom;
import rs.ac.uns.ftn.fhir.fhir_server.repository.declaration.IPatientRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientRepository implements IPatientRepository {

    @Autowired
    MongoOperations mongo;

    @Autowired
    PatientEntityToFHIRPatient patientEntityToFHIRPatient;

    @Override
    public Patient create(Patient patient) {


        PatientEntity patientEntity = new PatientEntity();

        for (Identifier identifier : patient.getIdentifier()) {
            rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier identifierE = new rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier();
            identifierE.setSystem(identifier.getSystem());
            identifierE.setValue(identifier.getValue().replaceAll(" ",""));

            patientEntity.getIdentifiers().add(identifierE);
        }
        for (HumanName name : patient.getName()) {
            Name nameE = new Name();
            nameE.setFamilyName(name.getFamily());
            nameE.setGivenName(name.getGivenAsSingleString());
            if (name.hasPrefix()) {
                nameE.setPrefix(name.getPrefix().get(0).getValue());
            }
            if (name.hasUse()) {
                nameE.setNameUse(name.getUse());
            }
            patientEntity.getNames().add(nameE);
        }
        if (patient.hasBirthDate()) {
            patientEntity.setDateOfBirth(patient.getBirthDate());
        }
        if (patient.hasGender()) {
            patientEntity.setGender(patient.getGender());
        }
        for (ContactPoint contactPoint : patient.getTelecom()) {
            Telecom telecom = new Telecom();
            telecom.setValue(contactPoint.getValue());
            if (contactPoint.hasSystem()) {
                telecom.setSystem(contactPoint.getSystem());
            }
            if (contactPoint.hasUse()) telecom.setTelecomUse(contactPoint.getUse());

            patientEntity.getTelecoms().add(telecom);
        }
        for (Address address : patient.getAddress()) {
            rs.ac.uns.ftn.fhir.fhir_server.entity.Address addressEntity = new rs.ac.uns.ftn.fhir.fhir_server.entity.Address();

            for (StringType line : address.getLine()) {
                addressEntity.getLines().add(line.toString());
            }

            if (address.hasCity()) {
                addressEntity.setCity(address.getCity());
            }
            if (address.hasPostalCode()) {
                addressEntity.setPostcode(address.getPostalCode());
            }
            if (address.hasDistrict()) {
                addressEntity.setCounty(address.getDistrict());
            }
            if (address.hasUse()) {
                addressEntity.setUse(address.getUse());
            }

            patientEntity.getAddresses().add(addressEntity);
        }
        mongo.save(patientEntity);

        ObjectId bundleId = patientEntity.getId();

        return patientEntityToFHIRPatient.transform(patientEntity);
    }

    @Override
    public Patient read(IdType theId) {
        ObjectId objectId = new ObjectId(theId.getIdPart());
        Query qry = Query.query(Criteria.where("_id").is(objectId));

        PatientEntity patientEntity = mongo.findOne(qry, PatientEntity.class);
        if (patientEntity == null) return null;
        return patientEntityToFHIRPatient.transform(patientEntity);
    }

    @Override
    public List<Resource> search(DateRangeParam birthDate, StringParam familyName, StringParam gender, StringParam givenName, TokenParam identifier, StringParam name) {

        List<Resource> resources = new ArrayList<>();

        Criteria criteria = null;

        if (identifier != null) {
            if (criteria ==null) {
                criteria = Criteria.where("identifiers.system").is(identifier.getSystem()).and("identifiers.value").is(identifier.getValue());
            } else {
                criteria.and("identifiers.system").is(identifier.getSystem()).and("identifiers.value").is(identifier.getValue());
            }
        }
        if (familyName!=null) {
            if (criteria ==null) {
                criteria = Criteria.where("names.familyName").regex(familyName.getValue());
            } else {
                criteria.and("names.familyName").regex(familyName.getValue());
            }
        }
        if (givenName!=null) {
            if (criteria ==null) {
                criteria = Criteria.where("names.givenName").regex(givenName.getValue());
            } else {
                criteria.and("names.givenName").regex(givenName.getValue());
            }
        }
        if (name!=null) {

            String regexName = name.getValue() ;
            if (criteria ==null) {
                criteria = new Criteria().orOperator(Criteria.where("names.familyName").regex(regexName),Criteria.where("names.givenName").regex(regexName));
            } else {
                criteria.orOperator(Criteria.where("names.familyName").regex(regexName),Criteria.where("names.givenName").regex(regexName));
            }
        }


        if (criteria != null) {
            Query qry = Query.query(criteria);

            List<PatientEntity> patientResults = mongo.find(qry, PatientEntity.class);

            for (PatientEntity patientEntity : patientResults) {
                resources.add(patientEntityToFHIRPatient.transform(patientEntity));
            }
        }

        return resources;
    }



}
