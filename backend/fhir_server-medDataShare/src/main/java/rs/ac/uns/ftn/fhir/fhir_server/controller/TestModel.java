package rs.ac.uns.ftn.fhir.fhir_server.controller;

import java.util.Date;

public class TestModel {

    private String gender;

    public TestModel(){

    }

    public TestModel(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
