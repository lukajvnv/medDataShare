package rs.ac.uns.ftn.medDataShare.chaincode.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClinicalTrialsAccessRequestResponse {

    private int total;

    private List<CCClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CCClinicalTrialAccessRequestDto> getClinicalTrialAccessRequestDtoList() {
        return clinicalTrialAccessRequestDtoList;
    }

    public void setClinicalTrialAccessRequestDtoList(List<CCClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList) {
        this.clinicalTrialAccessRequestDtoList = clinicalTrialAccessRequestDtoList;
    }

    public ClinicalTrialsAccessRequestResponse(int total, List<CCClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList) {
        this.total = total;
        this.clinicalTrialAccessRequestDtoList = clinicalTrialAccessRequestDtoList;
    }

    @Override
    public String toString() {
        return "ClinicalTrialsAccessRequestResponse{" +
                "total=" + total +
                ", clinicalTrialAccessRequestDtoList=" + clinicalTrialAccessRequestDtoList +
                '}';
    }

    public static ClinicalTrialsAccessRequestResponse deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));

        int total = json.getInt("total");
        JSONArray jsonArray = json.getJSONArray("clinicalTrialAccessRequestDtoList");
        List<CCClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList = new ArrayList<>();
        for(Object object : jsonArray){
            if(object instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) object;
                clinicalTrialAccessRequestDtoList.add(CCClinicalTrialAccessRequestDto.parseClinicalTrialAccessRequest(jsonObject));
            }
        }
        return new ClinicalTrialsAccessRequestResponse(total, clinicalTrialAccessRequestDtoList);
    }
}
