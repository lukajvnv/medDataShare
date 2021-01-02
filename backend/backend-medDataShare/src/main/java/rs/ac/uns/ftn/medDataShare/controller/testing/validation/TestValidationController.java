package rs.ac.uns.ftn.medDataShare.controller.testing.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.medDataShare.util.ValidationUtil;
import rs.ac.uns.ftn.medDataShare.validator.AuthException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@RestController
@RequestMapping("/testValidation")
@Validated
public class TestValidationController {

    @PostMapping
    public String resetPassword(@Valid @RequestBody TestValidationModel1 testValidationModel1, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new AuthException(errorMsg);
        }

        return "Success";
    }

    @PostMapping("/user")
    public String user(@Valid @RequestBody TestValidationModel2 testValidationModel2, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new AuthException(errorMsg);
        }

        return "Success";
    }

    @PostMapping("/reservation")
    public String user(@Valid @RequestBody Reservation reservation, BindingResult result){
        if(result.hasErrors()){
            String errorMsg = ValidationUtil.formatValidationErrorMessages(result.getAllErrors());
            throw new AuthException(errorMsg);
        }

        Reservation r = new Reservation(LocalDate.of(2020, 10, 31), LocalDate.of(2020, 10, 3), 5);

        return "Success";
    }

    @GetMapping
    public String newTestModel() {
        TestModel testModel = new TestModel("Luka", null);
        return "Okej";
    }

    @GetMapping("/pathVariable/{id}")
    public String testPathVariable(@PathVariable @Min(1) @Max(7) Integer id) {
        return "okej";
    }

    @GetMapping("/pathParam")
    public String testPathParam(@NotBlank @Size(max = 10, message = "Error brale") String username) {
        return "okej";
    }
}
