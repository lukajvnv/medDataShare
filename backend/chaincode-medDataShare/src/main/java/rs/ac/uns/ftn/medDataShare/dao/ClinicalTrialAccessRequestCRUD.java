package rs.ac.uns.ftn.medDataShare.dao;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrialAccessRequest;
import rs.ac.uns.ftn.medDataShare.enumeration.AccessType;

import java.util.logging.Logger;

public class ClinicalTrialAccessRequestCRUD {

    private Context ctx;
    private String entityName;
    private Genson gensonSerializer;

    private final static Logger LOG = Logger.getLogger(ClinicalTrialAccessRequestCRUD.class.getName());

    public ClinicalTrialAccessRequestCRUD(Context context, String entityName, Genson genson){
        this.ctx = context;
        this.entityName = entityName;
        this.gensonSerializer = genson;
    }

    public ClinicalTrialAccessRequest addClinicalTrialAccessRequest(
            String patientId,
            String time,
            String requesterId,
            String decision,
            String accessAvailableFrom,
            String accessAvailableUntil,
            boolean anonymity,
            ClinicalTrial clinicalTrial
    ) {
        String key = ctx.getStub().getTxId();

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(entityName, key);
        String dbKey = compositeKey.toString();

        ClinicalTrialAccessRequest clinicalTrialAccessRequest = ClinicalTrialAccessRequest.createInstance(
                key,
                patientId,
                time,
                requesterId,
                decision,
                accessAvailableFrom,
                accessAvailableUntil,
                anonymity,
                clinicalTrial.getKey(),
                clinicalTrial.getClinicalTrialType()
        );

        String entityToStr = gensonSerializer.serialize(clinicalTrialAccessRequest);
        ctx.getStub().putStringState(dbKey, entityToStr);
//        ctx.getStub().putState(dbKey, ClinicalTrialAccessRequest.serialize(clinicalTrialAccessRequest));

        return clinicalTrialAccessRequest;
    }

    public ClinicalTrialAccessRequest addClinicalTrialAccessRequest(
            String patientId,
            String time,
            String requesterId,
            ClinicalTrial clinicalTrial
    ) {
        String key = ctx.getStub().getTxId();

        CompositeKey compositeKey = ctx.getStub().createCompositeKey(entityName, key);
        String dbKey = compositeKey.toString();

        ClinicalTrialAccessRequest clinicalTrialAccessRequest = ClinicalTrialAccessRequest.createInstance(
                key,
                patientId,
                time,
                requesterId,
                AccessType.IDLE,
                "",
                "",
                false,
                clinicalTrial.getKey(),
                clinicalTrial.getClinicalTrialType()
        );

        String entityToStr = gensonSerializer.serialize(clinicalTrialAccessRequest);
        ctx.getStub().putStringState(dbKey, entityToStr);

        return clinicalTrialAccessRequest;
    }

    public ClinicalTrialAccessRequest defineClinicalTrialAccessRequest(
            String key,
            String decision,
            String accessAvailableFrom,
            String accessAvailableUntil,
            boolean anonymity,
            ClinicalTrialAccessRequest clinicalTrialAccessRequest
    ) {

        clinicalTrialAccessRequest
                .setDecision(decision).setAnonymity(anonymity).setAccessAvailableFrom(accessAvailableFrom).setAccessAvailableUntil(accessAvailableUntil);

        String dbKey = ctx.getStub().createCompositeKey(entityName, key).toString();

        String entityToStr = gensonSerializer.serialize(clinicalTrialAccessRequest);
        ctx.getStub().putStringState(dbKey, entityToStr);
//        ctx.getStub().putState(dbKey, ClinicalTrialAccessRequest.serialize(clinicalTrialAccessRequest));

        return clinicalTrialAccessRequest;
    }

    public ClinicalTrialAccessRequest getClinicalTrialAccessRequest(String key) {
        String dbKey = ctx.getStub().createCompositeKey(entityName, key).toString();

        String entityStringValue = ctx.getStub().getStringState(dbKey);
        LOG.info("getStringState(): "+ entityStringValue);
//        ClinicalTrialAccessRequest clinicalTrialAccessRequest = gensonSerializer.deserialize(entityStringValue, ClinicalTrialAccessRequest.class);
        byte[] value = ctx.getStub().getState(dbKey);
        ClinicalTrialAccessRequest clinicalTrialAccessRequest = ClinicalTrialAccessRequest.deserialize(value);
        LOG.info(clinicalTrialAccessRequest.toString());

        return clinicalTrialAccessRequest;
    }

    public boolean clinicalTrialAccessRequestExist(final String key) {
        ChaincodeStub stub = ctx.getStub();
        String dbKey = stub.createCompositeKey(entityName, key).toString();
        byte[] value = stub.getState(dbKey);

        return value.length > 0;
    }
}
