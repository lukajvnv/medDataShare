package rs.ac.uns.ftn.medDataShare.component;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import rs.ac.uns.ftn.medDataShare.dao.ClinicalTrialAccessRequestDAO;
import rs.ac.uns.ftn.medDataShare.dao.ClinicalTrialDAO;

public class ClinicalTrialContext extends Context {

    private ClinicalTrialDAO clinicalTrialDAO;
    private ClinicalTrialAccessRequestDAO clinicalTrialAccessRequestDAO;

    public ClinicalTrialContext(ChaincodeStub stub) {
        super(stub);
        clinicalTrialDAO = new ClinicalTrialDAO(this);
        clinicalTrialAccessRequestDAO = new ClinicalTrialAccessRequestDAO(this);
    }

    public ClinicalTrialDAO getClinicalTrialDAO() {
        return clinicalTrialDAO;
    }

    public ClinicalTrialAccessRequestDAO getClinicalTrialAccessRequestDAO() {
        return clinicalTrialAccessRequestDAO;
    }
}