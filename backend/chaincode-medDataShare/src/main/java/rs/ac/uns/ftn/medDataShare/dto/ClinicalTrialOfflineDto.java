package rs.ac.uns.ftn.medDataShare.dto;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class ClinicalTrialOfflineDto {

    @Property()
    private String offlineDataUrl;

    @Property()
    private String hashData;

    @Property()
    private boolean anonymity;

    public ClinicalTrialOfflineDto(String offlineDataUrl, String hashData, boolean anonymity) {
        this.offlineDataUrl = offlineDataUrl;
        this.hashData = hashData;
        this.anonymity = anonymity;
    }

    public boolean isAnonymity() {
        return anonymity;
    }

    public ClinicalTrialOfflineDto setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
        return this;
    }

    public ClinicalTrialOfflineDto(){

    }



    public String getOfflineDataUrl() {
        return offlineDataUrl;
    }

    public ClinicalTrialOfflineDto setOfflineDataUrl(String offlineDataUrl) {
        this.offlineDataUrl = offlineDataUrl;
        return this;
    }

    public String getHashData() {
        return hashData;
    }

    public ClinicalTrialOfflineDto setHashData(String hashData) {
        this.hashData = hashData;
        return this;
    }

    public static ClinicalTrialOfflineDto deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));
        return parseClinicalTrialOfflineDto(json);
    }

    @Override
    public String toString() {
        return "ClinicalTrialOfflineDto{" +
                "offlineDataUrl='" + offlineDataUrl + '\'' +
                ", hashData='" + hashData + '\'' +
                ", anonymity=" + anonymity +
                '}';
    }

    public static ClinicalTrialOfflineDto parseClinicalTrialOfflineDto(JSONObject jsonObject){
        String hashData = jsonObject.getString("hashData");
        String offlineDataUrl = jsonObject.getString("offlineDataUrl");
        boolean anonymity = jsonObject.getBoolean("anonymity");


        return createInstance(
                hashData,
                offlineDataUrl,
                anonymity
        );
    }

    public static ClinicalTrialOfflineDto createInstance(
            String hashData,
            String offlineDataUrl,
            boolean anonymity
    ){
        return new ClinicalTrialOfflineDto()
                .setHashData(hashData)
                .setOfflineDataUrl(offlineDataUrl)
                .setAnonymity(anonymity)
                ;
    }

}
