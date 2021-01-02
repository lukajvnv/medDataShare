package rs.ac.uns.ftn.medDataShare.dto;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class ClinicalTrialsPreviewResponse {

    @Property()
    private int total;

    @Property()
    private List<ClinicalTrialDto> clinicalTrialDtoList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ClinicalTrialDto> getClinicalTrialDtoList() {
        return clinicalTrialDtoList;
    }

    public void setClinicalTrialDtoList(List<ClinicalTrialDto> clinicalTrialDtoList) {
        this.clinicalTrialDtoList = clinicalTrialDtoList;
    }

    public ClinicalTrialsPreviewResponse(int total, List<ClinicalTrialDto> clinicalTrialDtoList) {
        this.total = total;
        this.clinicalTrialDtoList = clinicalTrialDtoList;
    }
}
