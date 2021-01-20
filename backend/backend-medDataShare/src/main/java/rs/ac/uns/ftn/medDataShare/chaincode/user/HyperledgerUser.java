package rs.ac.uns.ftn.medDataShare.chaincode.user;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.util.Set;

public class HyperledgerUser implements User, Serializable {

	private String name;
	private Set<String> roles;
	private String account;
	private String affiliation;
	private Enrollment enrollment;
	private String mspId;

	public HyperledgerUser(String name, Set<String> roles, String account, String affiliation, Enrollment enrollment, String mspId) {
		this.name = name;
		this.roles = roles;
		this.account = account;
		this.affiliation = affiliation;
		this.enrollment = enrollment;
		this.mspId = mspId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public String getAccount() {
		return account;
	}

	@Override
	public String getAffiliation() {
		return affiliation;
	}

	@Override
	public Enrollment getEnrollment() {
		return enrollment;
	}

	@Override
	public String getMspId() {
		return mspId;
	}

}
