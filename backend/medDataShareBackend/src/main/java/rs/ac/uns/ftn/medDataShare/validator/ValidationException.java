package rs.ac.uns.ftn.medDataShare.validator;

import lombok.Getter;

public class ValidationException extends RuntimeException{
    @Getter
    private String message;

    public ValidationException(final String message){
        super(message);
        this.message = message;
    }
}
