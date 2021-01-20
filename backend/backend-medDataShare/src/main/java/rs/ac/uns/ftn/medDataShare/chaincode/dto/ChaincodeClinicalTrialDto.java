package rs.ac.uns.ftn.medDataShare.chaincode.dto;

import org.json.JSONObject;

public class ChaincodeClinicalTrialDto {

    private String key;
    private String accessType;
    private String patientId;
    private String doctorId;
    private String medInstitutionId;
    private String time;
    private String clinicalTrialType;
    private boolean relevantParameters;

    public String getKey() {
        return key;
    }

    public ChaincodeClinicalTrialDto setKey(String key) {
        this.key = key;
        return this;
    }

    public String getAccessType() {
        return accessType;
    }

    public ChaincodeClinicalTrialDto setAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    public String getPatientId() {
        return patientId;
    }

    public ChaincodeClinicalTrialDto setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public ChaincodeClinicalTrialDto setDoctorId(String doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public String getMedInstitutionId() {
        return medInstitutionId;
    }

    public ChaincodeClinicalTrialDto setMedInstitutionId(String medInstitutionId) {
        this.medInstitutionId = medInstitutionId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ChaincodeClinicalTrialDto setTime(String time) {
        this.time = time;
        return this;
    }

    public String getClinicalTrialType() {
        return clinicalTrialType;
    }

    public ChaincodeClinicalTrialDto setClinicalTrialType(String clinicalTrialType) {
        this.clinicalTrialType = clinicalTrialType;
        return this;
    }

    public boolean isRelevantParameters() {
        return relevantParameters;
    }

    public ChaincodeClinicalTrialDto setRelevantParameters(boolean relevantParameters) {
        this.relevantParameters = relevantParameters;
        return this;
    }

    public static ChaincodeClinicalTrialDto parseClinicalTrialDto(JSONObject jsonObject){
        String key = jsonObject.getString("key");
        String accessType = jsonObject.getString("accessType");
        String patientId = jsonObject.getString("patientId");
        String doctorId = jsonObject.getString("doctorId");
        String medInstitutionId = jsonObject.getString("medInstitutionId");
        String time = jsonObject.getString("time");
        String clinicalTrialType = jsonObject.getString("clinicalTrialType");
        boolean relevantParameters = jsonObject.getBoolean("relevantParameters");

        return createInstance(
                key,
                accessType,
                patientId,
                doctorId,
                medInstitutionId,
                time,
                clinicalTrialType,
                relevantParameters
        );
    }

    public static ChaincodeClinicalTrialDto createInstance(
            String key,
            String accessType,
            String patientId,
            String doctorId,
            String medInstitutionId,
            String time,
            String clinicalTrialType,
            boolean relevantParameters
    ){
        return new ChaincodeClinicalTrialDto()
                .setKey(key)
                .setAccessType(accessType)
                .setPatientId(patientId)
                .setDoctorId(doctorId)
                .setMedInstitutionId(medInstitutionId)
                .setTime(time)
                .setClinicalTrialType(clinicalTrialType)
                .setRelevantParameters(relevantParameters)
                ;
    }

}
