package rs.ac.uns.ftn.medDataShare.dao;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.json.JSONArray;
import org.json.JSONObject;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialAccessRequestDto;
import rs.ac.uns.ftn.medDataShare.enumeration.AccessType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ClinicalTrialAccessRequestQuery {

    private Context ctx;
    private String entityName;

    private final static Logger LOG = Logger.getLogger(ClinicalTrialAccessRequestQuery.class.getName());

    public ClinicalTrialAccessRequestQuery(Context context, String entityName){
        this.ctx = context;
        this.entityName = entityName;
    }

    public List<ClinicalTrialAccessRequestDto> getRequestedTrialAccesses(String requester) {
        Map<String, String> queryParameters = new HashMap<>(){{
           put("requesterId", requester);
        }};
        JSONObject queryJsonObject = createQuerySelector(queryParameters);
        LOG.info("query: " + queryJsonObject.toString());
        QueryResultsIterator<KeyValue> resultsIterator = this.ctx.getStub().getQueryResult(queryJsonObject.toString());
        return createResponseObject(resultsIterator);
    }

    public List<ClinicalTrialAccessRequestDto> getTrialAccessRequestsHistory(String patient) {
        Map<String, String> queryParameters = new HashMap<>(){{
            put("patientId", patient);
        }};
        JSONObject queryJsonObject = createQuerySelector(queryParameters);
        LOG.info("query: " + queryJsonObject.toString());
        QueryResultsIterator<KeyValue> resultsIterator = this.ctx.getStub().getQueryResult(queryJsonObject.toString());
        return createResponseObject(resultsIterator);
    }

    public List<ClinicalTrialAccessRequestDto> getTrialAccessRequests(String patient) {
        Map<String, String> queryParameters = new HashMap<>(){{
            put("patientId", patient);
            put("decision", AccessType.IDLE);
        }};
        JSONObject queryJsonObject = createQuerySelector(queryParameters);
        LOG.info("query: " + queryJsonObject.toString());
        QueryResultsIterator<KeyValue> resultsIterator = this.ctx.getStub().getQueryResult(queryJsonObject.toString());
        return createResponseObject(resultsIterator);
    }

    private List<ClinicalTrialAccessRequestDto> createResponseObject(QueryResultsIterator<KeyValue> resultsIterator) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccesses = new ArrayList<>();
        for(KeyValue keyValue : resultsIterator){
            String value = keyValue.getStringValue();
            JSONObject jsonObject = new JSONObject(value);
            ClinicalTrialAccessRequestDto clinicalTrialAccessRequestDto = ClinicalTrialAccessRequestDto.parseClinicalTrialAccessRequest(jsonObject);
            clinicalTrialAccesses.add(clinicalTrialAccessRequestDto);
        }
        return clinicalTrialAccesses;
    }

    private JSONObject createQuerySelector(Map<String, String> queryParameters) {
        JSONArray jsonArraySortAttributes = new JSONArray();
        JSONObject jsonObjectSortTimeAttr = new JSONObject();
        jsonObjectSortTimeAttr.putOnce("time", "desc");
        jsonArraySortAttributes.put(jsonObjectSortTimeAttr);

        JSONObject jsonObjectSelector = new JSONObject();
        for (Map.Entry<String, String> queryParamEntrySet : queryParameters.entrySet()){
            String key = queryParamEntrySet.getKey();
            String value = queryParamEntrySet.getValue();
            jsonObjectSelector.putOnce(key, value);
        }
        jsonObjectSelector.putOnce("entityName", entityName);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("selector", jsonObjectSelector);
        jsonObject.putOnce("sort", jsonArraySortAttributes);

        return jsonObject;
    }
}
