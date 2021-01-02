package rs.ac.uns.ftn.medDataShare.controller.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testAuth")
public class TestAuthController {

    @GetMapping
    public String newTestModel(){
        return "Okej";
    }

    @GetMapping("/token/validate")
    private String validateToken(){
        return "Valid";
    }
}
