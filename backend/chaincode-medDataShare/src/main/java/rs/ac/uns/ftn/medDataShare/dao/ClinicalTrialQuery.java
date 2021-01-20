package rs.ac.uns.ftn.medDataShare.dao;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.json.JSONArray;
import org.json.JSONObject;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.enumeration.AccessType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClinicalTrialQuery {

    private Context ctx;
    private String entityName;

    private final static Logger LOG = Logger.getLogger(ClinicalTrialQuery.class.getName());


    public ClinicalTrialQuery(Context context, String entityName){
        this.ctx = context;
        this.entityName = entityName;
    }

    public List<ClinicalTrialDto> getClinicalTrialsPreview(String clinicalTrialType, String medInstitution, String from, String until, String sortingOrder, String relevantParameters
    ) {
        List<ClinicalTrialDto> clinicalTrials = new ArrayList<>();
        JSONObject queryJsonObject = createQuerySelector(clinicalTrialType, medInstitution, from, until, sortingOrder, relevantParameters);
        LOG.info("query: " + queryJsonObject.toString());
        QueryResultsIterator<KeyValue> resultsIterator = this.ctx.getStub().getQueryResult(queryJsonObject.toString());
//        this.ctx.getStub().getQueryResultWithPagination("query", 4, "bookmark")
        for(KeyValue keyValue : resultsIterator){
            String key = keyValue.getKey();
            String value = keyValue.getStringValue();
            JSONObject jsonObject = new JSONObject(value);
            byte[] bytes = keyValue.getValue();
            LOG.info("keyValue class: " + keyValue.getClass().toString() + ", type: " + keyValue.getClass().getTypeName());
            ClinicalTrialDto clinicalTrialDto = ClinicalTrialDto.parseClinicalTrialDto(jsonObject);

            clinicalTrials.add(clinicalTrialDto);
        }
        return clinicalTrials;
    }

    public JSONObject createQuerySelector(String clinicalTrialType, String medInstitution, String from, String until, String sortingOrder, String relevantParameters
    ){
        JSONObject jsonObjectTimeRange = new JSONObject();
        jsonObjectTimeRange.putOnce("$gt", from);
        jsonObjectTimeRange.putOnce("$lt", until);

        JSONArray jsonArraySortAttributes = new JSONArray();
        JSONObject jsonObjectSortTimeAttr = new JSONObject();
        jsonObjectSortTimeAttr.putOnce("time", sortingOrder);
        jsonArraySortAttributes.put(jsonObjectSortTimeAttr);

        JSONObject jsonObjectSelector = new JSONObject();
        jsonObjectSelector.putOnce("time", jsonObjectTimeRange);
        if(!clinicalTrialType.isEmpty()){
            jsonObjectSelector.putOnce("clinicalTrialType", clinicalTrialType);
        }
        if(!medInstitution.isEmpty()){
            jsonObjectSelector.putOnce("medInstitutionId", medInstitution);
        }
        if(!relevantParameters.isEmpty()){
            jsonObjectSelector.putOnce("relevantParameters", "true");
        }
        jsonObjectSelector.putOnce("entityName", entityName);

        JSONObject undefinedAccessTypeExclusion = new JSONObject();
        undefinedAccessTypeExclusion.putOnce("$ne", AccessType.IDLE);
        jsonObjectSelector.putOnce("accessType", undefinedAccessTypeExclusion);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("selector", jsonObjectSelector);
        jsonObject.putOnce("sort", jsonArraySortAttributes);

        return jsonObject;
    }

    public ClinicalTrial getClinicalTrialByOfflineDataUrl(String offlineDataUrl) {
        JSONObject queryJsonObject = new JSONObject();
        JSONObject jsonObjectSelector = new JSONObject();
        jsonObjectSelector.putOnce("offlineDataUrl", offlineDataUrl);
        jsonObjectSelector.putOnce("entityName", entityName);
        queryJsonObject.putOnce("selector", jsonObjectSelector);
        LOG.info("query: " + queryJsonObject.toString());

        QueryResultsIterator<KeyValue> resultsIterator = this.ctx.getStub().getQueryResult(queryJsonObject.toString());

        List<ClinicalTrial> clinicalTrials = new ArrayList<>();
        for(KeyValue keyValue : resultsIterator){
            String value = keyValue.getStringValue();
            JSONObject jsonObject = new JSONObject(value);
            ClinicalTrial clinicalTrialDto = ClinicalTrial.parseClinicalTrial(jsonObject);
            clinicalTrials.add(clinicalTrialDto);
        }

        if(clinicalTrials.size() == 1){
            return clinicalTrials.get(0);
        } else {
            return null;
        }
    }

}
