package rs.ac.uns.ftn.medDataShare.dao;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import rs.ac.uns.ftn.medDataShare.component.ClinicalTrialContext;
import rs.ac.uns.ftn.medDataShare.contract.ClinicalTrialContract;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrialAccessRequest;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ClinicalTrialCRUD {

    private Context ctx;
    private String entityName;
    private Genson gensonSerializer;

    private final static Logger LOG = Logger.getLogger(ClinicalTrialAccessRequestCRUD.class.getName());

    public ClinicalTrialCRUD(Context context, String entityName, Genson genson){
        this.ctx = context;
        this.entityName = entityName;
        this.gensonSerializer = genson;
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
        CompositeKey compositeKey = ctx.getStub().createCompositeKey(entityName, key);
        String dbKey = compositeKey.toString();

        ClinicalTrial clinicalTrial = ClinicalTrial.createInstance(
                key,
                accessType,
                patientId,
                doctorId,
                medInstitutionId,
                time,
                clinicalTrialType,
                offlineDataUrl,
                hashData,
                Boolean.toString(relevantParameters)
        );

        String entityJsonString = gensonSerializer.serialize(clinicalTrial);
        ctx.getStub().putStringState(dbKey, entityJsonString);
//        ctx.getStub().putState(dbKey, ClinicalTrial.serialize(clinicalTrial));

        return clinicalTrial;
    }

    public ClinicalTrial defineClinicalTrialAccess(
            String key,
            String accessType
    ) {
        ClinicalTrial clinicalTrial = getClinicalTrial(key);
        clinicalTrial.setAccessType(accessType);

        String dbKey = ctx.getStub().createCompositeKey(entityName, key).toString();

        String entityJsonString = gensonSerializer.serialize(clinicalTrial);
        ctx.getStub().putStringState(dbKey, entityJsonString);
//        ctx.getStub().putState(dbKey, ClinicalTrial.serialize(clinicalTrial));

        return clinicalTrial;
    }

    public ClinicalTrial getClinicalTrial(String key) {
        String dbKey = ctx.getStub().createCompositeKey(entityName, key).toString();

//        String entityStringValue = ctx.getStub().getStringState(dbKey);
//        LOG.info("getStringState():" + entityStringValue);
//        ClinicalTrial clinicalTrial = gensonSerializer.deserialize(entityStringValue, ClinicalTrial.class);
////        ClinicalTrial clinicalTrial = gensonSerializer.deserialize(ctx.getStub().getState(dbKey), ClinicalTrial.class);
//        LOG.info(clinicalTrial.toString());

        byte[] value = ctx.getStub().getState(dbKey);
        ClinicalTrial clinicalTrial = ClinicalTrial.deserialize(value);
        return clinicalTrial;
    }

    public boolean clinicalTrialExist(final String key) {
        ChaincodeStub stub = ctx.getStub();
        String dbKey = stub.createCompositeKey(entityName, key).toString();
        byte[] value = stub.getState(dbKey);

        return value.length > 0;
    }
}
