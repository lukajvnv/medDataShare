package rs.ac.uns.ftn.medDataShare.controller.testing;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/test")
@Validated
public class TestController {

    @GetMapping
    public String newTestModel() {
        throw new RuntimeException("runtime");
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
