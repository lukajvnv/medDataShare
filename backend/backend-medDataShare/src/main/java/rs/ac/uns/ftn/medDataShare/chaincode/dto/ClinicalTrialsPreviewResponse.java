package rs.ac.uns.ftn.medDataShare.chaincode.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClinicalTrialsPreviewResponse {

    private int total;
    private List<ChaincodeClinicalTrialDto> clinicalTrialDtoList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChaincodeClinicalTrialDto> getClinicalTrialDtoList() {
        return clinicalTrialDtoList;
    }

    public void setClinicalTrialDtoList(List<ChaincodeClinicalTrialDto> clinicalTrialDtoList) {
        this.clinicalTrialDtoList = clinicalTrialDtoList;
    }

    public ClinicalTrialsPreviewResponse(int total, List<ChaincodeClinicalTrialDto> clinicalTrialDtoList) {
        this.total = total;
        this.clinicalTrialDtoList = clinicalTrialDtoList;
    }

    public static ClinicalTrialsPreviewResponse deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));

        int total = json.getInt("total");
        JSONArray jsonArray = json.getJSONArray("clinicalTrialDtoList");
        List<ChaincodeClinicalTrialDto> clinicalTrialDtoList = new ArrayList<>();
        for(Object object : jsonArray){
            if(object instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) object;
                clinicalTrialDtoList.add(ChaincodeClinicalTrialDto.parseClinicalTrialDto(jsonObject));
            }
        }
        return new ClinicalTrialsPreviewResponse(total, clinicalTrialDtoList);
    }

    @Override
    public String toString() {
        return "ClinicalTrialsPreviewResponse{" +
                "total=" + total +
                ", clinicalTrialDtoList=" + clinicalTrialDtoList +
                '}';
    }
}
