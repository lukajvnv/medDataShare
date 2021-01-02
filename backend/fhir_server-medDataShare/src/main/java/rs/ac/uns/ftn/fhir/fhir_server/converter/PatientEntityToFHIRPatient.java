package rs.ac.uns.ftn.fhir.fhir_server.converter;

import org.hl7.fhir.r4.model.*;

import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

import rs.ac.uns.ftn.fhir.fhir_server.entity.Name;
import rs.ac.uns.ftn.fhir.fhir_server.entity.PatientEntity;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Telecom;
import rs.ac.uns.ftn.fhir.fhir_server.entity.Address;

@Component
public class PatientEntityToFHIRPatient implements Transformer<PatientEntity, Patient> {
    @Override
    public Patient transform(PatientEntity patientEntity) {
        final Patient patient = new Patient();

        Meta meta = new Meta().addProfile("https://fhir.hl7.org.uk/STU3/StructureDefinition/CareConnect-Patient-1");

        patient.setMeta(meta);

        patient.setId(patientEntity.getId().toString());

        for (rs.ac.uns.ftn.fhir.fhir_server.entity.Identifier identifier : patientEntity.getIdentifiers()) {
            patient.addIdentifier()
                    .setSystem(identifier.getSystem())
                    .setValue(identifier.getValue());
        }
        for (Name name : patientEntity.getNames() ) {
            HumanName humanName = patient.addName();

            humanName.setFamily(name.getFamilyName())
                    .addGiven(name.getGivenName());
            if (name.getNameUse() != null) {
                humanName.setUse(name.getNameUse());
            }
            if (name.getPrefix()!=null) {
                humanName.getPrefix().add(new StringType(name.getPrefix()));
            }
        }
        if (patientEntity.getDateOfBirth() != null) {
            patient.setBirthDate(patientEntity.getDateOfBirth());
        }
        if (patientEntity.getGender()!=null) {
            patient.setGender(patientEntity.getGender());
        }
        for (Telecom telecom : patientEntity.getTelecoms()) {
            ContactPoint contactPoint = new ContactPoint();
            contactPoint.setValue(telecom.getValue());
            if (telecom.getSystem() != null) contactPoint.setSystem(telecom.getSystem());
            if (telecom.getTelecomUse()!=null) {
                contactPoint.setUse(telecom.getTelecomUse());
            }
            patient.getTelecom().add(contactPoint);
        }
        for (Address addressEntity : patientEntity.getAddresses()) {
            org.hl7.fhir.r4.model.Address address = new org.hl7.fhir.r4.model.Address();
            patient.getAddress().add(address);

            for (String line : addressEntity.getLines()) {
                address.addLine(line);
            }
            if (addressEntity.getCity()!=null) address.setCity(addressEntity.getCity());
            if (addressEntity.getCounty()!=null) address.setDistrict(addressEntity.getCounty());
            if (addressEntity.getPostcode()!=null) address.setPostalCode(addressEntity.getPostcode());
            if (addressEntity.getUse()!=null) address.setUse(addressEntity.getUse());
        }


        return patient;
    }
}
