package rs.ac.uns.ftn.medDataShare.dto;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.List;

@DataType()
public class ClinicalTrialsAccessRequestResponse {

    @Property()
    private int total;

    @Property()
    private List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ClinicalTrialAccessRequestDto> getClinicalTrialAccessRequestDtoList() {
        return clinicalTrialAccessRequestDtoList;
    }

    public void setClinicalTrialAccessRequestDtoList(List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList) {
        this.clinicalTrialAccessRequestDtoList = clinicalTrialAccessRequestDtoList;
    }

    public ClinicalTrialsAccessRequestResponse(int total, List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequestDtoList) {
        this.total = total;
        this.clinicalTrialAccessRequestDtoList = clinicalTrialAccessRequestDtoList;
    }
}
