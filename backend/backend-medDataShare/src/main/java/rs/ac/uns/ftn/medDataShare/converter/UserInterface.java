package rs.ac.uns.ftn.medDataShare.converter;

public interface UserInterface<Entity, Dto, FormDto> {

    public Dto editUser(FormDto object);
}
