package rs.ac.uns.ftn.medDataShare.util;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationUtil {

    public static String formatValidationErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsgsBuffer = new StringBuffer("Errors while adding new institution: ");
        for (Object object : errors) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                String errorMsg = "%" + fieldError.getField() + "% " + fieldError.getDefaultMessage();
                errorMsgsBuffer.append(errorMsg);
            }
            errorMsgsBuffer.append(", ");
        }
        return errorMsgsBuffer.toString();
    }

    public static boolean passwordMatch(String password1, String password2){
        return password1.equals(password2);
    }
}
