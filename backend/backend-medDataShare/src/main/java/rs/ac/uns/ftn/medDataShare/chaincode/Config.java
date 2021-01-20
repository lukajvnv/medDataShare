package rs.ac.uns.ftn.medDataShare.chaincode;

public class Config {

	public static final int ORG_COUNT = 2;

	public static final String ORG1_MSP = "Org1MSP";
	public static final String ORG1_AFFILIATION = "org1.department1";
	public static final String ORG1_ADMIN_AFFILIATION = "admin";
	public static final String ORG1 = "org1";

	public static final String ORG2_MSP = "Org2MSP";
	public static final String ORG2_AFFILIATION = "org2.department1";
	public static final String ORG2 = "org2";

	public static final String ORG3_MSP = "Org3MSP";
	public static final String ORG3_AFFILIATION = "org3.department1";
	public static final String ORG3 = "org3";

	public static final String COMMON_USER_ORG = "org3";

	public static final String CHANNEL_NAME = "mychannel";

	public static final String WALLET_DIRECTORY = "wallet";

	public static final String CA_ADMIN_USERNAME = "admin";
	public static final String CA_ADMIN_PASSWORD = "adminpw";

	public static final String CA_ORG1_ADMIN_IDENTITY_ID = "org1CaAdmin";
	public static final String CA_ORG2_ADMIN_IDENTITY_ID = "org2CaAdmin";
	public static final String CA_ORG3_ADMIN_IDENTITY_ID = "org3CaAdmin";

	public static final String CA_ORG1_PEM_FILE = "../../hyperledger-fabric/test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem";
	public static final String CA_ORG1_URL = "https://localhost:7054";

	public static final String CA_ORG2_PEM_FILE = "../../hyperledger-fabric/test-network/organizations/peerOrganizations/org2.example.com/ca/ca.org2.example.com-cert.pem";
	public static final String CA_ORG2_URL = "https://localhost:8054";

	public static final String CA_ORG3_PEM_FILE = "../../hyperledger-fabric/test-network/organizations/peerOrganizations/org3.example.com/ca/ca.org3.example.com-cert.pem";
	public static final String CA_ORG3_URL = "https://localhost:11054";

	public static final String ORG1_ADMIN_USERNAME = "org1admin";
	public static final String ORG1_ADMIN_PASSWORD = "org1adminpw";

	public static final String ORG2_ADMIN_USERNAME = "org2admin";
	public static final String ORG2_ADMIN_PASSWORD = "org2adminpw";

	public static final String ORG3_ADMIN_USERNAME = "org3admin";
	public static final String ORG3_ADMIN_PASSWORD = "org3adminpw";

	public static final String CHAINCODE_NAME = "chaincode-medDataShare";
	public static final String CHAINCODE_ENDORSEMENT = "src/main/resources/chaincodeendorsementpolicy.yaml";

	public static final String ORG1_CONNECTION_PROFILE_PATH = "../../hyperledger-fabric/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml";
	public static final String ORG2_CONNECTION_PROFILE_PATH = "../../hyperledger-fabric/test-network/organizations/peerOrganizations/org2.example.com/connection-org2.yaml";

}

