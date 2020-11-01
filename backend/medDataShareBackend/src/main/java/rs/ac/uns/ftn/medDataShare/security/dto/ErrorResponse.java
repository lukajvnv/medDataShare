package rs.ac.uns.ftn.medDataShare.security.dto;

/*
 * Pomocna klasa za vracanje greske prilikom pokusaja logovanja
 * neverifikovanog korisnika
 *
 * */

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String messageStatus;
    private HttpStatus status;

}

