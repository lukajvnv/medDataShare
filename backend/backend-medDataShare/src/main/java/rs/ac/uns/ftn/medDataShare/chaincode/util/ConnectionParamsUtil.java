package rs.ac.uns.ftn.medDataShare.chaincode.util;

import rs.ac.uns.ftn.medDataShare.chaincode.Config;

import java.util.HashMap;
import java.util.Map;

public class ConnectionParamsUtil {

    public static Map<String, String> setOrgConfigParams(String org) throws Exception {
        switch (org){
            case Config.ORG1:
                return new HashMap<String, String>(){{
                    put("pemFile", Config.CA_ORG1_PEM_FILE);
                    put("caOrgUrl", Config.CA_ORG1_URL);
                    put("caOrgAdminIdentityId", Config.CA_ORG1_ADMIN_IDENTITY_ID);
                    put("orgMsp", Config.ORG1_MSP);
                    put("caAdminUsername", Config.CA_ADMIN_USERNAME);
                    put("caAdminPassword", Config.CA_ADMIN_PASSWORD);
                    put("orgAffiliation", Config.ORG1_AFFILIATION);
                    put("networkConfigPath", Config.ORG1_CONNECTION_PROFILE_PATH);
                }};
            case Config.ORG2:
                return new HashMap<String, String>(){{
                    put("pemFile", Config.CA_ORG2_PEM_FILE);
                    put("caOrgUrl", Config.CA_ORG2_URL);
                    put("caOrgAdminIdentityId", Config.CA_ORG2_ADMIN_IDENTITY_ID);
                    put("orgMsp", Config.ORG2_MSP);
                    put("caAdminUsername", Config.CA_ADMIN_USERNAME);
                    put("caAdminPassword", Config.CA_ADMIN_PASSWORD);
                    put("orgAffiliation", Config.ORG2_AFFILIATION);
                    put("networkConfigPath", Config.ORG2_CONNECTION_PROFILE_PATH);
                }};
            case Config.ORG3:
                return new HashMap<String, String>(){{
                    put("pemFile", Config.CA_ORG3_PEM_FILE);
                    put("caOrgUrl", Config.CA_ORG3_URL);
                    put("caOrgAdminIdentityId", Config.CA_ORG3_ADMIN_IDENTITY_ID);
                    put("orgMsp", Config.ORG3_MSP);
                    put("caAdminUsername", Config.CA_ADMIN_USERNAME);
                    put("caAdminPassword", Config.CA_ADMIN_PASSWORD);
                    put("orgAffiliation", Config.ORG3_AFFILIATION);
                    put("networkConfigPath", Config.ORG2_CONNECTION_PROFILE_PATH);
                }};
            default:
                throw new Exception();
        }
    }
}
