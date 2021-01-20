package rs.ac.uns.ftn.medDataShare.chaincode.user;

import org.hyperledger.fabric.sdk.Enrollment;

import java.security.PrivateKey;

public class CAEnrollment implements Enrollment {
	private PrivateKey key;
	private String cert;

	public CAEnrollment(PrivateKey pkey, String signedPem) {
		this.key = pkey;
		this.cert = signedPem;
	}

	public PrivateKey getKey() {
		return key;
	}

	public String getCert() {
		return cert;
	}

}
