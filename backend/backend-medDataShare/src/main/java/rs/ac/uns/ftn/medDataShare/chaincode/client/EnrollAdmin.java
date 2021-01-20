package rs.ac.uns.ftn.medDataShare.chaincode.client;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import rs.ac.uns.ftn.medDataShare.chaincode.Config;
import rs.ac.uns.ftn.medDataShare.chaincode.util.ConnectionParamsUtil;
import rs.ac.uns.ftn.medDataShare.chaincode.util.WalletUtil;

import java.util.Map;
import java.util.Properties;

public class EnrollAdmin {

	public static void main(String[] args) {
		try {
			enrollOrgCaAdmin(Config.ORG1);
			enrollOrgCaAdmin(Config.ORG2);
			enrollOrgCaAdmin(Config.ORG3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Identity enrollOrgCaAdmin(String org) throws Exception {
		Map<String, String> orgConfigParams = ConnectionParamsUtil.setOrgConfigParams(org);
		Properties props = new Properties();
		props.put("pemFile", orgConfigParams.get("pemFile"));
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance(orgConfigParams.get("caOrgUrl"), props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		WalletUtil walletUtil = new WalletUtil();

		String identityId = orgConfigParams.get("caOrgAdminIdentityId");
		if (walletUtil.getIdentity(identityId) != null) {
			System.out.printf("An identity for the admin user: %s already exists in the wallet%n", identityId);
			return null;
		}

		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
		enrollmentRequestTLS.addHost("localhost");
		enrollmentRequestTLS.setProfile("tls");
		Enrollment enrollment = caClient.enroll(orgConfigParams.get("caAdminUsername"), orgConfigParams.get("caAdminPassword"), enrollmentRequestTLS);
		Identity user = Identities.newX509Identity(orgConfigParams.get("orgMsp"), enrollment);

		walletUtil.putIdentity(identityId, user);
		System.out.printf("Successfully enrolled user %s and imported it into the wallet%n", identityId);
		return user;
	}
}
