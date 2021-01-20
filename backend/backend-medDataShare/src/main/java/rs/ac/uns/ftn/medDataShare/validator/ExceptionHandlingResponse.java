package rs.ac.uns.ftn.medDataShare.validator;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingResponse {

    @ExceptionHandler({ MyException.class })
    public ResponseEntity<Response> invalidParameters(MyException ex) {
        String message = ex.getMessage();
        String tableName = "fd";
        Response response = new Response();
        response.setMessage(message);
        response.setTable(tableName);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Response jwtException(final JwtException exception,
                                               final HttpServletRequest request) {

        Response error = new Response();
        error.setMessage(exception.getMessage());
        error.setCallerUrl(request.getRequestURI());

        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody Response otherException(final Exception exception,
                                                           final HttpServletRequest request) {

        Response error = new Response();
        error.setMessage(exception.getMessage());
        error.setCallerUrl(request.getRequestURI());

        return error;
    }

    @Data
    private static class Response {
        private String message;
        private String table;
        private String callerUrl;
    }
}
