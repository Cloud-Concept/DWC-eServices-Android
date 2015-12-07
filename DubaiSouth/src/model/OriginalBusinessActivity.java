package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Abanoub Wagdy on 7/28/2015.
 */

/**
 * This class holds original business activity attributes
 */
public class OriginalBusinessActivity implements Serializable{

    public String url;
    @SerializedName("Id")
    public String ID;
    @SerializedName("Name")
    public String Name;
    @SerializedName("License_Type__c")
    public String LicenseType;
    @SerializedName("Business_Activity_Name__c")
    public String BusinessActivityName;
    @SerializedName("Business_Activity_Name_Arabic__c")
    public String BusinessActivityArabicName;
    @SerializedName("Business_Activity_Description__c")
    public String BusinessActivityDescription;
    @SerializedName("Status__c")
    public String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBusinessActivityDescription() {
        return BusinessActivityDescription;
    }

    public void setBusinessActivityDescription(String businessActivityDescription) {
        BusinessActivityDescription = businessActivityDescription;
    }

    public String getBusinessActivityArabicName() {
        return BusinessActivityArabicName;
    }

    public void setBusinessActivityArabicName(String businessActivityArabicName) {
        BusinessActivityArabicName = businessActivityArabicName;
    }

    public String getBusinessActivityName() {
        return BusinessActivityName;
    }

    public void setBusinessActivityName(String businessActivityName) {
        BusinessActivityName = businessActivityName;
    }

    public String getLicenseType() {
        return LicenseType;
    }

    public void setLicenseType(String licenseType) {
        LicenseType = licenseType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
