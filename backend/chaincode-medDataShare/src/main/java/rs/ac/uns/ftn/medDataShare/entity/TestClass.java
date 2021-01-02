package rs.ac.uns.ftn.medDataShare.entity;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class TestClass {

    @Property()
    private String paperNumber;

    public String getPaperNumber() {
        return paperNumber;
    }

    public void setPaperNumber(String paperNumber) {
        this.paperNumber = paperNumber;
    }

}
