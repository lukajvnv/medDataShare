package rs.ac.uns.ftn.medDataShare.converter.declaration;

public interface ConverterInterface<Entity, Dto, FormDto> {
    Dto convertToDto(Entity object);
    Entity convertFromDto(FormDto object, boolean update);
}
