package rs.ac.uns.ftn.medDataShare.controller.testing.validation;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@ValidReservation
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    private LocalDate begin;

    private LocalDate end;

    @Positive
    private int room;

    @ConsistentDateParameters
    public Reservation(LocalDate begin, LocalDate end, int room) {
        this.begin = begin;
        this.end = end;
        this.room = room;
    }


}