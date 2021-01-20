package rs.ac.uns.ftn.medDataShare.converter.declaration;

public interface UserInterface<Entity, Dto, FormDto> {

    public Dto editUser(FormDto object);
}
