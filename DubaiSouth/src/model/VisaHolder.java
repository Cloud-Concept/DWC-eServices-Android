package model;

import com.google.gson.annotations.SerializedName;

//import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abanoub Wagdy on 6/25/2015.
 */

/**
 * This class holds visa additional attributes like name ,billing city ,etc....
 */
public class VisaHolder implements Serializable{

//    public model.attributes getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(model.attributes attributes) {
//        this.attributes = attributes;
//    }
//
//    @JsonProperty("attributes")
//    @SerializedName("attributes")
//    attributes attributes;

//    @JsonProperty("Id")
    @SerializedName("Id")
    String ID;
//    @JsonProperty("Name")
    @SerializedName("Name")
    String Name;
//    @JsonProperty("BillingCity")
    @SerializedName("BillingCity")
    String BillingCity;
//    @JsonProperty("Arabic_Account_Name__c")
    @SerializedName("Arabic_Account_Name__c")
    String Arabic_Account_Name__c;

    public String getArabic_Account_Name__c() {
        return Arabic_Account_Name__c;
    }

    public void setArabic_Account_Name__c(String arabic_Account_Name__c) {
        Arabic_Account_Name__c = arabic_Account_Name__c;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public void setBillingCity(String billingCity) {
        BillingCity = billingCity;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
