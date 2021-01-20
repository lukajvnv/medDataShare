package rs.ac.uns.ftn.medDataShare.chaincode.dto;

import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ChaincodeClinicalTrial {

    private String key;
    private String accessType;
    private String patientId;
    private String doctorId;
    private String medInstitutionId;
    private String time;
    private String clinicalTrialType;
    private String offlineDataUrl;
    private String hashData;
    private String relevantParameters;
    private String entityName;

    public String getKey() {
        return key;
    }

    public ChaincodeClinicalTrial setKey(String key) {
        this.key = key;
        return this;
    }

    public String getAccessType() {
        return accessType;
    }

    public ChaincodeClinicalTrial setAccessType(String accessType) {
        this.accessType = accessType;
        return this;
    }

    public String getPatientId() {
        return patientId;
    }

    public ChaincodeClinicalTrial setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public ChaincodeClinicalTrial setDoctorId(String doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public String getMedInstitutionId() {
        return medInstitutionId;
    }

    public ChaincodeClinicalTrial setMedInstitutionId(String medInstitutionId) {
        this.medInstitutionId = medInstitutionId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ChaincodeClinicalTrial setTime(String time) {
        this.time = time;
        return this;
    }

    public String getClinicalTrialType() {
        return clinicalTrialType;
    }

    public ChaincodeClinicalTrial setClinicalTrialType(String clinicalTrialType) {
        this.clinicalTrialType = clinicalTrialType;
        return this;
    }

    public String getOfflineDataUrl() {
        return offlineDataUrl;
    }

    public ChaincodeClinicalTrial setOfflineDataUrl(String offlineDataUrl) {
        this.offlineDataUrl = offlineDataUrl;
        return this;
    }

    public String getHashData() {
        return hashData;
    }

    public ChaincodeClinicalTrial setHashData(String hashData) {
        this.hashData = hashData;
        return this;
    }

    public String getRelevantParameters() {
        return relevantParameters;
    }

    public ChaincodeClinicalTrial setRelevantParameters(String relevantParameters) {
        this.relevantParameters = relevantParameters;
        return this;
    }

    public static byte[] serialize(Object object){
        String jsonStr = new JSONObject(object).toString();
        return jsonStr.getBytes(UTF_8);
    }

    public static ChaincodeClinicalTrial deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        return parseClinicalTrial(json);
    }

    public static ChaincodeClinicalTrial parseClinicalTrial(JSONObject jsonObject){
        String key = jsonObject.getString("key");
        String accessType = jsonObject.getString("accessType");
        String patientId = jsonObject.getString("patientId");
        String doctorId = jsonObject.getString("doctorId");
        String medInstitutionId = jsonObject.getString("medInstitutionId");
        String time = jsonObject.getString("time");
        String clinicalTrialType = jsonObject.getString("clinicalTrialType");
        String offlineDataUrl = jsonObject.getString("offlineDataUrl");
        String hashData = jsonObject.getString("hashData");
        String relevantParameters = jsonObject.getString("relevantParameters");

        return createInstance(
                key,
                accessType,
                patientId,
                doctorId,
                medInstitutionId,
                time,
                clinicalTrialType,
                offlineDataUrl,
                hashData,
                relevantParameters
        );
    }

    @Override
    public String toString() {
        return "ClinicalTrial{" +
                "key='" + key + '\'' +
                ", accessType='" + accessType + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", medInstitutionId='" + medInstitutionId + '\'' +
                ", time='" + time + '\'' +
                ", clinicalTrialType='" + clinicalTrialType + '\'' +
                ", offlineDataUrl='" + offlineDataUrl + '\'' +
                ", hashData='" + hashData + '\'' +
                ", relevantParameters=" + relevantParameters +
                '}';
    }

    public static ChaincodeClinicalTrial createInstance(
            String key,
            String accessType,
            String patientId,
            String doctorId,
            String medInstitutionId,
            String time,
            String clinicalTrialType,
            String offlineDataUrl,
            String hashData,
            String relevantParameters
            ){
        return new ChaincodeClinicalTrial()
                .setKey(key)
                .setAccessType(accessType)
                .setPatientId(patientId)
                .setDoctorId(doctorId)
                .setMedInstitutionId(medInstitutionId)
                .setTime(time)
                .setClinicalTrialType(clinicalTrialType)
                .setOfflineDataUrl(offlineDataUrl)
                .setHashData(hashData)
                .setRelevantParameters(relevantParameters)
                .setEntityName(ChaincodeClinicalTrial.class.getSimpleName())
                ;
    }

    public String getEntityName() {
        return entityName;
    }

    public ChaincodeClinicalTrial setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }


}
