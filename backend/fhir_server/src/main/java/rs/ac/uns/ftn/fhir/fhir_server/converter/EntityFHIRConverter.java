package rs.ac.uns.ftn.fhir.fhir_server.converter;

public interface EntityFHIRConverter<DBEntity, FHIREntity>  {

    DBEntity convertFHIRToDBEntity(FHIREntity fhirEntity);
    FHIREntity convertDBToFhirEntity(DBEntity dbEntity);
}
