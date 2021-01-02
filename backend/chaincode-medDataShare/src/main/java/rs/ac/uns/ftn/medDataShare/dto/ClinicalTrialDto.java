package rs.ac.uns.ftn.medDataShare.dto;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class ClinicalTrialDto {

    @Property()
    private String key;

    @Property()
    private String accessType;

    @Property()
    private String patientId;

    @Property()
    private String doctorId;

    @Property()
    private String medInstitutionId;

    @Property()
    private String time;

    @Property()
    private String clinicalTrialType;

    @Property()
    private boolean relevantParameters;

    public String getKey() {
        return key;
    }

    public ClinicalTrialDto setKey(String key) {
        this.key = key;
        return this;
    }

    public String getAccessType() {
        return accessType;
    }

    public ClinicalTrialDto setAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    public String getPatientId() {
        return patientId;
    }

    public ClinicalTrialDto setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public ClinicalTrialDto setDoctorId(String doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public String getMedInstitutionId() {
        return medInstitutionId;
    }

    public ClinicalTrialDto setMedInstitutionId(String medInstitutionId) {
        this.medInstitutionId = medInstitutionId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ClinicalTrialDto setTime(String time) {
        this.time = time;
        return this;
    }

    public String getClinicalTrialType() {
        return clinicalTrialType;
    }

    public ClinicalTrialDto setClinicalTrialType(String clinicalTrialType) {
        this.clinicalTrialType = clinicalTrialType;
        return this;
    }

    public boolean isRelevantParameters() {
        return relevantParameters;
    }

    public ClinicalTrialDto setRelevantParameters(boolean relevantParameters) {
        this.relevantParameters = relevantParameters;
        return this;
    }

    public static ClinicalTrialDto parseClinicalTrialDto(JSONObject jsonObject){
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

    public static ClinicalTrialDto createInstance(
            String key,
            String accessType,
            String patientId,
            String doctorId,
            String medInstitutionId,
            String time,
            String clinicalTrialType,
            boolean relevantParameters
    ){
        return new ClinicalTrialDto()
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
