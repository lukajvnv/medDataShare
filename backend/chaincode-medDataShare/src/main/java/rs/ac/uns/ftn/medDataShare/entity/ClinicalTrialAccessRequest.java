package rs.ac.uns.ftn.medDataShare.entity;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class ClinicalTrialAccessRequest {

    @Property()
    private String key;

    @Property()
    private String patientId;

    @Property()
    private String requesterId;

    public ClinicalTrialAccessRequest() {
    }

    @Property()
    private String time;

    @Property()
    private String decision;

    @Property()
    private String accessAvailableFrom;

    @Property()
    private String accessAvailableUntil;

    @Property()
    private boolean anonymity;

//    @Property()
//    private ClinicalTrial clinicalTrial;

    @Property()
    private String clinicalTrial;

    @Property()
    private String clinicalTrialType;

    @Property()
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

    public String getClinicalTrialType() {
        return clinicalTrialType;
    }

    public ClinicalTrialAccessRequest setClinicalTrialType(String clinicalTrialType) {
        this.clinicalTrialType = clinicalTrialType;
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
        String accessAvailableFrom = jsonObject.getString("accessAvailableFrom");
        String accessAvailableUntil = jsonObject.getString("accessAvailableUntil");
        boolean anonymity = jsonObject.getBoolean("anonymity");

//        JSONObject clinicalTrialJsonObject = json.getJSONObject("clinicalTrial");
//        ClinicalTrial clinicalTrial = ClinicalTrial.parseClinicalTrial(clinicalTrialJsonObject);

        String clinicalTrialJsonObject = jsonObject.getString("clinicalTrial");
        String clinicalTrialType = jsonObject.getString("clinicalTrialType");

        return createInstance(
                key,
                patientId,
                time,
                requesterId,
                decision,
                accessAvailableFrom,
                accessAvailableUntil,
                anonymity,
                clinicalTrialJsonObject,
                clinicalTrialType
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
            String clinicalTrial,
            String clinicalTrialType
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
                .setClinicalTrialType(clinicalTrialType)
                .setEntityName(ClinicalTrialAccessRequest.class.getSimpleName())
                ;
    }
}
