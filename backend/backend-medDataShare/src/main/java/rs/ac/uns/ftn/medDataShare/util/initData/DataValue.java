package rs.ac.uns.ftn.medDataShare.util.initData;

public class DataValue {

    private String patient;
    private String doctor;
    private String contentType;
    private String contentName;
    private String clinicalTrialType;
    private String introduction;
    private String relevantParameters;
    private String conclusion;
    private String clinicalTrialAccessType;
    private String clinicalTrialTime;
    private boolean sendAccessRequest;
    private boolean sendAccessRequestDecision;
    private String requester;
    private String sendAccessRequestTime;
    private String canAccessFrom;
    private String canAccessUntil;
    private boolean anonymity;
    private String accessDecision;

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getClinicalTrialType() {
        return clinicalTrialType;
    }

    public void setClinicalTrialType(String clinicalTrialType) {
        this.clinicalTrialType = clinicalTrialType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRelevantParameters() {
        return relevantParameters;
    }

    public void setRelevantParameters(String relevantParameters) {
        this.relevantParameters = relevantParameters;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getClinicalTrialAccessType() {
        return clinicalTrialAccessType;
    }

    public void setClinicalTrialAccessType(String clinicalTrialAccessType) {
        this.clinicalTrialAccessType = clinicalTrialAccessType;
    }

    public String getClinicalTrialTime() {
        return clinicalTrialTime;
    }

    public void setClinicalTrialTime(String clinicalTrialTime) {
        this.clinicalTrialTime = clinicalTrialTime;
    }

    public boolean isSendAccessRequest() {
        return sendAccessRequest;
    }

    public void setSendAccessRequest(boolean sendAccessRequest) {
        this.sendAccessRequest = sendAccessRequest;
    }

    public boolean isSendAccessRequestDecision() {
        return sendAccessRequestDecision;
    }

    public void setSendAccessRequestDecision(boolean sendAccessRequestDecision) {
        this.sendAccessRequestDecision = sendAccessRequestDecision;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getSendAccessRequestTime() {
        return sendAccessRequestTime;
    }

    public void setSendAccessRequestTime(String sendAccessRequestTime) {
        this.sendAccessRequestTime = sendAccessRequestTime;
    }

    public String getCanAccessFrom() {
        return canAccessFrom;
    }

    public void setCanAccessFrom(String canAccessFrom) {
        this.canAccessFrom = canAccessFrom;
    }

    public String getCanAccessUntil() {
        return canAccessUntil;
    }

    public void setCanAccessUntil(String canAccessUntil) {
        this.canAccessUntil = canAccessUntil;
    }

    public boolean isAnonymity() {
        return anonymity;
    }

    public void setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
    }

    public String getAccessDecision() {
        return accessDecision;
    }

    public void setAccessDecision(String accessDecision) {
        this.accessDecision = accessDecision;
    }

    @Override
    public String toString() {
        return "DataValue{" +
                "patient='" + patient + '\'' +
                ", doctor='" + doctor + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentName='" + contentName + '\'' +
                ", clinicalTrialType='" + clinicalTrialType + '\'' +
                ", introduction='" + introduction + '\'' +
                ", relevantParameters='" + relevantParameters + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", clinicalTrialAccessType='" + clinicalTrialAccessType + '\'' +
                ", clinicalTrialTime='" + clinicalTrialTime + '\'' +
                ", sendAccessRequest=" + sendAccessRequest +
                ", sendAccessRequestDecision=" + sendAccessRequestDecision +
                ", requester='" + requester + '\'' +
                ", sendAccessRequestTime='" + sendAccessRequestTime + '\'' +
                ", canAccessFrom='" + canAccessFrom + '\'' +
                ", canAccessUntil='" + canAccessUntil + '\'' +
                ", anonymity=" + anonymity +
                ", accessDecision='" + accessDecision + '\'' +
                '}';
    }
}
