package rs.ac.uns.ftn.medDataShare.validator;

import lombok.Getter;

public class MyException extends RuntimeException{
    @Getter
    private String message;

    public MyException(final String message){
        super(message);
        this.message = message;
    }
}
