/*
SPDX-License-Identifier: Apache-2.0
*/
package rs.ac.uns.ftn.medDataShare.contract;

import com.google.protobuf.ByteString;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;
import rs.ac.uns.ftn.medDataShare.entity.TestClass;
import rs.ac.uns.ftn.medDataShare.component.ClinicalTrialContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Contract(name = "rs.ac.uns.ftn.testcontract",
        info = @Info(
                title = "Test contract",
                description = "",
                version = "0.0.1",
                license = @License(name = "SPDX-License-Identifier: Apache-2.0", url = ""),
                contact = @Contact(email = "luka.ra109@uns.ac.rs", name = "Luka Jovanovic", url = "https://github.com/lukajvnv")
        )
)
public class TestContract implements ContractInterface {

    private final static Logger LOG = Logger.getLogger(TestContract.class.getName());

    @Override
    public Context createContext(ChaincodeStub stub) {
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
        // No implementation required with this example
        // It could be where data migration is performed, if necessary
        LOG.info("No data migration to perform");
    }

    @Transaction
    public TestClass testInvoke(ClinicalTrialContext ctx, String issuer) {
        TestClass testClass = new TestClass();
        testClass.setPaperNumber(issuer);

        ctx.getStub().setEvent("ISSUE", "Issue event blabla".getBytes(StandardCharsets.UTF_8));

        ctx.getStub().putState("key", issuer.getBytes(StandardCharsets.UTF_8));

        // Must return a serialized paper to caller of smart contract
        return testClass;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public TestClass testQuery(ClinicalTrialContext ctx) {

        TestClass testClass = new TestClass();

        byte[] value = ctx.getStub().getState("key");
        testClass.setPaperNumber(new String(value, StandardCharsets.UTF_8));

        // trigeruje se samo na izmene u ledgeru
        ctx.getStub().setEvent("QUERY_HISTORY", "Issue event blabla".getBytes(StandardCharsets.UTF_8));

        return testClass;
    }
}
