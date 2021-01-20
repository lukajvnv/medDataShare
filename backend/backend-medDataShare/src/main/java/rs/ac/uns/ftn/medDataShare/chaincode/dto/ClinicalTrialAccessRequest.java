package rs.ac.uns.ftn.medDataShare.chaincode.dto;

import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClinicalTrialAccessRequest {

    private String key;
    private String patientId;
    private String requesterId;

    private String time;
    private String decision;
    private String accessAvailableFrom;
    private String accessAvailableUntil;
    private boolean anonymity;

//    private ClinicalTrial clinicalTrial;

    private String clinicalTrial;
    private String entityName;

    public String getKey() {
        return key;
    }

    public ClinicalTrialAccessRequest setKey(String key) {
        this.key = key;
        return this;
    }

    public String getPatientId() {
        return patientId;
    }

    public ClinicalTrialAccessRequest setPatientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public ClinicalTrialAccessRequest setRequesterId(String requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public ClinicalTrialAccessRequest setTime(String time) {
        this.time = time;
        return this;
    }

    public String getDecision() {
        return decision;
    }

    public ClinicalTrialAccessRequest setDecision(String decision) {
        this.decision = decision;
        return this;
    }

    public boolean isAnonymity() {
        return anonymity;
    }

    public ClinicalTrialAccessRequest setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
        return this;
    }

    public String getClinicalTrial() {
        return clinicalTrial;
    }

    public ClinicalTrialAccessRequest setClinicalTrial(String clinicalTrial) {
        this.clinicalTrial = clinicalTrial;
        return this;
    }

    public String getAccessAvailableFrom() {
        return accessAvailableFrom;
    }

    public ClinicalTrialAccessRequest setAccessAvailableFrom(String accessAvailableFrom) {
        this.accessAvailableFrom = accessAvailableFrom;
        return this;
    }

    public String getAccessAvailableUntil() {
        return accessAvailableUntil;
    }

    public ClinicalTrialAccessRequest setAccessAvailableUntil(String accessAvailableUntil) {
        this.accessAvailableUntil = accessAvailableUntil;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public ClinicalTrialAccessRequest setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public static byte[] serialize(Object object){
        String jsonStr = new JSONObject(object).toString();
        return jsonStr.getBytes(UTF_8);
    }

    public static ClinicalTrialAccessRequest deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        return parseClinicalTrialAccessRequest(json);
    }

    public static ClinicalTrialAccessRequest parseClinicalTrialAccessRequest(JSONObject jsonObject) {

        String key = jsonObject.getString("key");
        String patientId = jsonObject.getString("patientId");
        String requesterId = jsonObject.getString("requesterId");
        String time = jsonObject.getString("time");
        String decision = jsonObject.getString("decision");
        String accessAvailableFrom = jsonObject.optString("accessAvailableFrom");
        String accessAvailableUntil = jsonObject.optString("accessAvailableUntil");
        boolean anonymity = jsonObject.optBoolean("anonymity");

//        JSONObject clinicalTrialJsonObject = json.getJSONObject("clinicalTrial");
//        ClinicalTrial clinicalTrial = ClinicalTrial.parseClinicalTrial(clinicalTrialJsonObject);

        String clinicalTrialJsonObject = jsonObject.getString("clinicalTrial");

        return createInstance(
                key,
                patientId,
                time,
                requesterId,
                decision,
                accessAvailableFrom,
                accessAvailableUntil,
                anonymity,
                clinicalTrialJsonObject
        );
    }

    @Override
    public String toString() {
        return "ClinicalTrialAccessRequest{" +
                "key='" + key + '\'' +
                ", patientId='" + patientId + '\'' +
                ", requesterId='" + requesterId + '\'' +
                ", time='" + time + '\'' +
                ", decision='" + decision + '\'' +
                ", accessAvailableFrom='" + accessAvailableFrom + '\'' +
                ", accessAvailableUntil='" + accessAvailableUntil + '\'' +
                ", anonymity=" + anonymity +
                ", clinicalTrial=" + clinicalTrial +
                '}';
    }

    public static ClinicalTrialAccessRequest createInstance(
            String key,
            String patientId,
            String time,
            String requesterId,
            String decision,
            String accessAvailableFrom,
            String accessAvailableUntil,
            boolean anonymity,
            String clinicalTrial
    ){
        return new ClinicalTrialAccessRequest()
                .setKey(key)
                .setPatientId(patientId)
                .setTime(time)
                .setRequesterId(requesterId)
                .setDecision(decision)
                .setAccessAvailableFrom(accessAvailableFrom)
                .setAccessAvailableUntil(accessAvailableUntil)
                .setAnonymity(anonymity)
                .setClinicalTrial(clinicalTrial)
                .setEntityName(ClinicalTrialAccessRequest.class.getSimpleName())
                ;
    }
}
