package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abanoub on 7/7/2015.
 */

/**
 * This class holds LegalRepresentative attributes
 * Note:Each company has number of LegalRepresentative inside it to make processes
 */
public class LegalRepresentative {

    public String url;
    @SerializedName("Id")
    public String ID;
    @SerializedName("Status__c")
    public String Status;
    @SerializedName("Role__c")
    public String Role;
    @SerializedName("Legal_Representative_End_Date__c")
    public String Legal_Representative_End_Date;
    @SerializedName("Legal_Representative_Start_Date__c")
    public String Legal_Representative_Start_Date;

    public LegalRepresentativeLookup legalRepresentativeLookup;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getLegal_Representative_End_Date() {
        return Legal_Representative_End_Date;
    }

    public void setLegal_Representative_End_Date(String legal_Representative_End_Date) {
        Legal_Representative_End_Date = legal_Representative_End_Date;
    }

    public String getLegal_Representative_Start_Date() {
        return Legal_Representative_Start_Date;
    }

    public void setLegal_Representative_Start_Date(String legal_Representative_Start_Date) {
        Legal_Representative_Start_Date = legal_Representative_Start_Date;
    }

    public LegalRepresentativeLookup getLegalRepresentativeLookup() {
        return legalRepresentativeLookup;
    }

    public void setLegalRepresentativeLookup(LegalRepresentativeLookup legalRepresentativeLookup) {
        this.legalRepresentativeLookup = legalRepresentativeLookup;
    }
}
