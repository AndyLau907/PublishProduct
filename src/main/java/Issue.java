import java.util.HashMap;
import java.util.Objects;

public class Issue {
    private String issueID;
    private String issueType;
    private String informPerson;
    private String code;
    private String enText;
    private String chText;
    private String informTo;
    private String orginPerson;
    private boolean isOffice;
    private boolean isInform;
    private boolean isNewFeature;

    public Issue(String issueID, String issueType, String informPerson, String code, String enText, String chText, boolean isOffice, boolean isInform, boolean isNewFeature, String informTo,String orginPerson) {
        this.issueID = issueID;
        this.issueType = issueType;
        this.informPerson = informPerson;
        this.code = code;
        this.enText = enText;
        this.chText = chText;
        this.isOffice = isOffice;
        this.isInform = isInform;
        this.isNewFeature = isNewFeature;
        this.informTo = informTo;
        this.orginPerson=orginPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Issue)) return false;
        Issue issue = (Issue) o;
        return issue.getTicketStr().equals(this.getTicketStr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIssueID(), getIssueType(), getInformPerson(), getCode(), getEnText(), getChText(), isOffice(), isInform(), isNewFeature());
    }

    public String getTypeStr() {
        return Static.map.get(issueType);
    }

    public String getTicketStr() {
        return getTypeStr() + "-" + issueID;
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getInformPerson() {
        return informPerson;
    }

    public void setInformPerson(String informPerson) {
        this.informPerson = informPerson;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnText() {
        return enText;
    }

    public void setEnText(String enText) {
        this.enText = enText;
    }

    public String getChText() {
        return chText;
    }

    public void setChText(String chText) {
        this.chText = chText;
    }

    public boolean isOffice() {
        return isOffice;
    }

    public void setOffice(boolean office) {
        isOffice = office;
    }

    public boolean isInform() {
        return isInform;
    }

    public void setInform(boolean inform) {
        isInform = inform;
    }

    public boolean isNewFeature() {
        return isNewFeature;
    }

    public void setNewFeature(boolean newFeature) {
        isNewFeature = newFeature;
    }

    public String getInformTo() {
        return informTo;
    }

    public void setInformTo(String informTo) {
        this.informTo = informTo;
    }

    public String getOrginPerson() {
        return orginPerson;
    }

    public void setOrginPerson(String orginPerson) {
        this.orginPerson = orginPerson;
    }
}
