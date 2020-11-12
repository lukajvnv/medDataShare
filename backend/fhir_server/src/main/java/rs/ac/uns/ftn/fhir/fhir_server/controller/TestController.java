package rs.ac.uns.ftn.fhir.fhir_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.fhir.fhir_server.client.TestPatientApplication;
import rs.ac.uns.ftn.fhir.fhir_server.util.ImageUtil;
import rs.ac.uns.ftn.fhir.fhir_server.util.PdfExporter;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello(
            @RequestParam(value = "ID", required = false, defaultValue="") String requestType
    ) {

        return "Hello";
    }

    @GetMapping("/image")
    public byte[] image(
    ) {
        return ImageUtil.getBytes();
    }

    @GetMapping("/file")
    public byte[] file(
    ) {
        return PdfExporter.getBytes();
    }

    @PostMapping
    public String editUser(@RequestBody TestModel commonUserDto){
        return "post ok";
    }

    @PostMapping("/image/upload")
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
//            ImageUtil.uploadImage(file.getBytes(), "test.png", "png");
            ImageUtil.uploadImage(file.getBytes(), "test.jpg", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Image uploaded", HttpStatus.OK);
    }


}
