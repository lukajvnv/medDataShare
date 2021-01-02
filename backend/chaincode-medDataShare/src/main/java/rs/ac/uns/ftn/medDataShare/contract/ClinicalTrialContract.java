/*
SPDX-License-Identifier: Apache-2.0
*/
package rs.ac.uns.ftn.medDataShare.contract;

import com.google.protobuf.ByteString;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.CompositeKey;
import rs.ac.uns.ftn.medDataShare.component.ClinicalTrialContext;
import rs.ac.uns.ftn.medDataShare.dao.ClinicalTrialAccessRequestDAO;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialAccessRequestDto;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialDto;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialsAccessRequestResponse;
import rs.ac.uns.ftn.medDataShare.dto.ClinicalTrialsPreviewResponse;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrial;
import rs.ac.uns.ftn.medDataShare.entity.ClinicalTrialAccessRequest;
import rs.ac.uns.ftn.medDataShare.enumeration.AccessType;
import rs.ac.uns.ftn.medDataShare.enumeration.ClinicalTrialType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Define commercial paper smart contract by extending Fabric Contract class
 *
 */
@Contract(name = "rs.ac.uns.ftn.clinicalTrial",
        info = @Info(
                title = "ClinicalTrial contract",
                description = "",
                version = "0.0.1",
                license = @License(name = "SPDX-License-Identifier: Apache-2.0", url = ""),
                contact = @Contact(email = "luka.ra109@uns.ac.rs", name = "Luka Jovanovic", url = "https://github.com/lukajvnv")
        )
)
@Default
public class ClinicalTrialContract implements ContractInterface {

    private enum ClinicalTrialContractErrors {
        CLINICAL_TRIAL_NOT_FOUND,
        ASSET_ALREADY_EXISTS
    }

    private final static Logger LOG = Logger.getLogger(ClinicalTrialContract.class.getName());

    @Override
    public Context createContext(ChaincodeStub stub) {
        LOG.info("invoke createContext");
        return new ClinicalTrialContext(stub);
    }

    @Override
    public void beforeTransaction(Context ctx) {
        List<String> paramList = ctx.getStub().getParameters();
        String params = String.join(",", paramList);
        String function = ctx.getStub().getFunction();

        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOG.info(String.format("Function name: %s, params: [%s]", function, params));
    }

    private void clientIdentityInfo(final Context ctx){
        ByteString signature = ctx.getStub().getSignedProposal().getSignature();
        String clientIdentityId = ctx.getClientIdentity().getId();
        String clientIdentityMspId = ctx.getClientIdentity().getMSPID();
        byte[] c = ctx.getStub().getCreator();
        LOG.info(String.format(", clientIdentityId: %s, clientIdentityMspId: %s", clientIdentityId, clientIdentityMspId));
    }

    @Override
    public void afterTransaction(Context ctx, Object result) {
        String function = ctx.getStub().getFunction();
        LOG.info(String.format("Function name: %s", function));
        if(result == null){
            LOG.info("retval is null");
        } else {
            LOG.info(String.format("object type: %s, else: ", result.getClass().getTypeName(), result.getClass().toString()));
            if(result.getClass().getTypeName().equals("java.util.ArrayList")){
                ArrayList<String> list = (ArrayList<String>) result;
                LOG.info(String.format("arraylist size: %d", list.size()));
            }
        }
        LOG.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< END <<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Transaction
    public void instantiate(ClinicalTrialContext ctx) {
        migrate(ctx);
    }

    public void migrate(ClinicalTrialContext ctx) {
        // No implementation required with this example
        // It could be where data migration is performed, if necessary
        ClinicalTrial clinicalTrial1 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key1",
                AccessType.IDLE,
                "patient1",
                "doctor1",
                "medInstitution1",
                "2020-05-01",
                ClinicalTrialType.CBC,
                "http://clinicalTrial1",
                "hash",
                true
        );
        ClinicalTrial clinicalTrial2 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key2",
                AccessType.IDLE,
                "patient1",
                "doctor1",
                "medInstitution1",
                "2021-01-01",
                ClinicalTrialType.CT,
                "http://clinicalTrial2",
                "hash",
                false
        );
        ClinicalTrial clinicalTrial3 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key3",
                AccessType.ASK_FOR_ACCESS,
                "patient2",
                "doctor1",
                "medInstitution1",
                "2020-07-01",
                ClinicalTrialType.CBC,
                "http://clinicalTrial3",
                "hash",
                true
        );
        ClinicalTrial clinicalTrial4 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key4",
                AccessType.UNCONDITIONAL,
                "patient2",
                "doctor1",
                "medInstitution1",
                "2020-08-01",
                ClinicalTrialType.RTG,
                "http://clinicalTrial4",
                "hash",
                false
        );
        ClinicalTrial clinicalTrial5 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key5",
                AccessType.ASK_FOR_ACCESS,
                "patient3",
                "doctor1",
                "medInstitution1",
                "2021-02-01",
                ClinicalTrialType.CBC,
                "clinicalTrial5",
                "hash",
                true
        );
        ClinicalTrial clinicalTrial6 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key6",
                AccessType.ASK_FOR_ACCESS,
                "patient3",
                "doctor1",
                "medInstitution2",
                "2021-01-01",
                ClinicalTrialType.CBC,
                "http://clinicalTrial6",
                "hash",
                true
        );
        ClinicalTrial clinicalTrial7 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key7",
                AccessType.ASK_FOR_ACCESS,
                "patient2",
                "doctor2",
                "medInstitution2",
                "2021-02-01",
                ClinicalTrialType.CBC,
                "http://clinicalTrial7",
                "hash",
                false
        );
        ClinicalTrial clinicalTrial8 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key8",
                AccessType.ASK_FOR_ACCESS,
                "patient1",
                "doctor1",
                "medInstitution2",
                "2021-03-01",
                ClinicalTrialType.US,
                "http://clinicalTrial8",
                "hash",
                true
        );
        ClinicalTrial clinicalTrial9 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key9",
                AccessType.ASK_FOR_ACCESS,
                "patient1",
                "doctor1",
                "medInstitution1",
                "2020-01-01",
                ClinicalTrialType.CBC,
                "http://clinicalTrial9",
                "hash",
                false
        );
        ClinicalTrial clinicalTrial10 = ctx.getClinicalTrialDAO().addClinicalTrial(
                "key10",
                AccessType.ASK_FOR_ACCESS,
                "patient1",
                "doctor1",
                "medInstitution1",
                "2021-01-01",
                ClinicalTrialType.CBC,
                "http://clinicalTrial10",
                "hash",
                true
        );
    }

    @Transaction
    public void migrateClinicalTrialAccessRequest(ClinicalTrialContext ctx) {
        ClinicalTrialAccessRequest clinicalTrialAccessRequest1 = addClinicalTrialAccessRequest(
                ctx,
                "patient1",
                "2020-03-03",
                "user5",
                AccessType.IDLE,
                "2021-01-01",
                "2021-03-03",
                true,
                "key1"
        );
//        ClinicalTrialAccessRequest clinicalTrialAccessRequest2 = addClinicalTrialAccessRequest(
//                ctx,
//                "patient1",
//                "2020-03-03",
//                "user5",
//                AccessType.FORBIDDEN,
//                "2021-04-01",
//                "2021-05-03",
//                false,
//                "key2"
//        );
    }

    @Transaction
    public ClinicalTrial addClinicalTrial(
            ClinicalTrialContext ctx,
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
        String key = ctx.getStub().getTxId();
        ClinicalTrial clinicalTrial = ctx.getClinicalTrialDAO().addClinicalTrial(
                key,
                accessType,
                patientId,
                doctorId,
                medInstitutionId,
                time,
                clinicalTrialType,
                offlineDataUrl,
                hashData,
                relevantParameters
        );

        return clinicalTrial;
    }

    @Transaction
    public ClinicalTrial defineClinicalTrialAccess(
            ClinicalTrialContext ctx,
            String key,
            String accessType
    ) {
        if (ctx.getClinicalTrialDAO().clinicalTrialExist(key)) {
            return ctx.getClinicalTrialDAO().defineClinicalTrialAccess(key, accessType);
        } else {
            String errorMessage = String.format("ClinicalTrial %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
        }
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrial getClinicalTrial(ClinicalTrialContext ctx, String key) {
        if (ctx.getClinicalTrialDAO().clinicalTrialExist(key)) {
            return ctx.getClinicalTrialDAO().getClinicalTrial(key);
        } else {
            String errorMessage = String.format("ClinicalTrial %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
        }
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean clinicalTrialExist(final ClinicalTrialContext ctx, final String key) {
        return ctx.getClinicalTrialDAO().clinicalTrialExist(key);
    }

    @Transaction
    public ClinicalTrialAccessRequest addClinicalTrialAccessRequest(
            ClinicalTrialContext ctx,
            String patientId,
            String time,
            String requesterId,
            String decision,
            String accessAvailableFrom,
            String accessAvailableUntil,
            boolean anonymity,
            String clinicalTrialId
    ) {
        ClinicalTrial clinicalTrial = getClinicalTrial(ctx, clinicalTrialId);

        ClinicalTrialAccessRequestDAO clinicalTrialAccessRequestDAO = ctx.getClinicalTrialAccessRequestDAO();
        return clinicalTrialAccessRequestDAO.addClinicalTrialAccessRequest(
                patientId,
                time,
                requesterId,
                decision,
                accessAvailableFrom,
                accessAvailableUntil,
                anonymity,
                clinicalTrial
        );
    }

    @Transaction
    public ClinicalTrialAccessRequest defineClinicalTrialAccessRequest(
            ClinicalTrialContext ctx,
            String key,
            String decision
    ) {
        ClinicalTrialAccessRequestDAO clinicalTrialAccessRequestDAO = ctx.getClinicalTrialAccessRequestDAO();
        if (clinicalTrialAccessRequestDAO.clinicalTrialAccessRequestExist(key)) {
            return ctx.getClinicalTrialAccessRequestDAO().defineClinicalTrialAccessRequest(key, decision);
        } else {
            String errorMessage = String.format("ClinicalTrialAccessRequest %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
        }
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialAccessRequest getClinicalTrialAccessRequest(ClinicalTrialContext ctx, String key) {
        ClinicalTrialAccessRequestDAO clinicalTrialAccessRequestDAO = ctx.getClinicalTrialAccessRequestDAO();
        if (clinicalTrialAccessRequestDAO.clinicalTrialAccessRequestExist(key)) {
            return clinicalTrialAccessRequestDAO.getClinicalTrialAccessRequest(key);
        }

        String errorMessage = String.format("ClinicalTrial %s does not exist", key);
        System.out.println(errorMessage);
        throw new ChaincodeException(errorMessage, ClinicalTrialContractErrors.CLINICAL_TRIAL_NOT_FOUND.toString());
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean clinicalTrialAccessRequestExist(final ClinicalTrialContext ctx, final String key) {
        return ctx.getClinicalTrialAccessRequestDAO().clinicalTrialAccessRequestExist(key);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsPreviewResponse getClinicalTrialsPreview(
            ClinicalTrialContext ctx,
            String clinicalTrialType,
            String medInstitution,
            String from,
            String until,
            String sortingOrder,
            String relevantParameters
    ) {
        List<ClinicalTrialDto> clinicalTrials = ctx.getClinicalTrialDAO().getClinicalTrialsPreview(
                clinicalTrialType,
                medInstitution,
                from,
                until,
                sortingOrder,
                relevantParameters
        );
        return new ClinicalTrialsPreviewResponse(clinicalTrials.size(), clinicalTrials);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsAccessRequestResponse getRequestedTrialAccesses(
            ClinicalTrialContext ctx,
            String requester
    ) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequests =
                ctx.getClinicalTrialAccessRequestDAO().getRequestedTrialAccesses(
                requester
        );
        return new ClinicalTrialsAccessRequestResponse(clinicalTrialAccessRequests.size(), clinicalTrialAccessRequests);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsAccessRequestResponse getTrialAccessRequestsHistory(
            ClinicalTrialContext ctx,
            String patient
    ) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequests =
                ctx.getClinicalTrialAccessRequestDAO().getTrialAccessRequestsHistory(
                        patient
                );
        return new ClinicalTrialsAccessRequestResponse(clinicalTrialAccessRequests.size(), clinicalTrialAccessRequests);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public ClinicalTrialsAccessRequestResponse getTrialAccessRequests(
            ClinicalTrialContext ctx,
            String patient
    ) {
        List<ClinicalTrialAccessRequestDto> clinicalTrialAccessRequests =
                ctx.getClinicalTrialAccessRequestDAO().getTrialAccessRequests(
                        patient
                );
        return new ClinicalTrialsAccessRequestResponse(clinicalTrialAccessRequests.size(), clinicalTrialAccessRequests);
    }
}
