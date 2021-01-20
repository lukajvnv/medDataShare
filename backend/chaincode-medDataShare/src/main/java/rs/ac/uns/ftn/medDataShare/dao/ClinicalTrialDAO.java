package rs.ac.uns.ftn.medDataShare.dao;


import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrial;

import java.util.List;

public class ClinicalTrialDAO {

    private ClinicalTrialCRUD clinicalTrialCRUD;
    private ClinicalTrialQuery clinicalTrialQuery;

    public ClinicalTrialDAO(Context context){
        this.clinicalTrialCRUD = new ClinicalTrialCRUD(context, ClinicalTrial.class.getSimpleName(), new Genson());
        this.clinicalTrialQuery = new ClinicalTrialQuery(context, ClinicalTrial.class.getSimpleName());
    }

    public ClinicalTrial addClinicalTrial(
            String key,
            String accessType,
            String patientId,
            String doctorId,
            String medInstitutionId,
            String time,
            String clinicalTrialType,
            String offlineDataUrl,
            String hashData,
            boolean relevantParameters
    ) {
        return clinicalTrialCRUD.addClinicalTrial(key, accessType, patientId, doctorId, medInstitutionId, time, clinicalTrialType, offlineDataUrl, hashData, relevantParameters);
    }

    public ClinicalTrial defineClinicalTrialAccess(
            String key,
            String accessType,
            String updatedDataHash
    ) {
        return clinicalTrialCRUD.defineClinicalTrialAccess(key, accessType, updatedDataHash);
    }

    public ClinicalTrial getClinicalTrial(String key) {
        return clinicalTrialCRUD.getClinicalTrial(key);
    }

    public boolean clinicalTrialExist(final String key) {
        return clinicalTrialCRUD.clinicalTrialExist(key);
    }

    public List<ClinicalTrialDto> getClinicalTrialsPreview(String clinicalTrialType,
                                                           String medInstitution, String from, String until, String sortingOrder, String relevantParameters
    ) {
        return clinicalTrialQuery.getClinicalTrialsPreview(clinicalTrialType, medInstitution, from, until, sortingOrder, relevantParameters);
    }

    public ClinicalTrial getClinicalTrialByOfflineDataUrl(String key) {
        return clinicalTrialQuery.getClinicalTrialByOfflineDataUrl(key);
    }
}
