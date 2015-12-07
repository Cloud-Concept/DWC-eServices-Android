package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Abanoub on 6/14/2015.
 */

/**
 * Holds account attributes for logged in user
 *  Note : user has contact ,contact has account that includes all details of logged in user
 */
public class Account implements Serializable {

    @SerializedName("Share_Capital_in_AED__c")
    public long Share_Capital_in_AED;

    public void setShare_Capital_in_AED(long share_Capital_in_AED) {
        Share_Capital_in_AED = share_Capital_in_AED;
    }

    @SerializedName("Id")
    public String Id;

    @SerializedName("Account_Balance__c")
    public String accountBalance;

    @SerializedName("Portal_Balance__c")
    public String portalBalance;

    @SerializedName("Name")
    public String Name;

    @SerializedName("Contact.Name")
    public String name;

    @SerializedName("Arabic_Account_Name__c")
    public String arabicAccountName;

    @SerializedName("License_Number_Formula__c")
    public String licenseNumberFormula;

    @SerializedName("BillingCity")
    public String billingCity;

    @SerializedName("Company_Registration_Date__c")
    public String companyRegistrationDate;

    @SerializedName("Legal_Form__c")
    public String legalForm;

    @SerializedName("Registration_Number_Value__c")
    public String registrationNumberValue;

    @SerializedName("Phone")
    public String Phone;

    @SerializedName("Fax")
    public String Fax;

    @SerializedName("Email__c")
    public String Email;

    @SerializedName("Mobile__c")
    public String Mobile;

    @SerializedName("PRO_Email__c")
    public String proEmail;

    @SerializedName("PRO_Mobile_Number__c")
    public String ProMobileNumber;

    @SerializedName("BillingStreet")
    public String BillingStreet;

    @SerializedName("BillingPostalCode")
    public String BillingPostalCode;

    @SerializedName("BillingCountry")
    public String BillingCountry;

    @SerializedName("BillingState")
    public String BillingState;

    @SerializedName("Company_Logo__c")
    public String Company_Logo;

    EstablishmentCard establishmentCard;

    public EstablishmentCard getEstablishmentCard() {
        return establishmentCard;
    }

    public void setEstablishmentCard(EstablishmentCard establishmentCard) {
        this.establishmentCard = establishmentCard;
    }

    public String getCompany_Logo() {
        return Company_Logo;
    }

    public void setCompany_Logo(String company_Logo) {
        Company_Logo = company_Logo;
    }
    
    @SerializedName("Current_License_Number__r")
    public CurrentLicenseNumber _currentLicenseNumber;

    public String getID() {
        return Id;
    }

    public void setID(String Id) {
        this.Id = Id;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPortalBalance() {
        return portalBalance;
    }

    public void setPortalBalance(String portalBalance) {
        this.portalBalance = portalBalance;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getArabicAccountName() {
        return arabicAccountName;
    }

    public void setArabicAccountName(String arabicAccountName) {
        this.arabicAccountName = arabicAccountName;
    }

    public String getLicenseNumberFormula() {
        return licenseNumberFormula;
    }

    public void setLicenseNumberFormula(String licenseNumberFormula) {
        this.licenseNumberFormula = licenseNumberFormula;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getCompanyRegistrationDate() {
        return companyRegistrationDate;
    }

    public void setCompanyRegistrationDate(String companyRegistrationDate) {
        this.companyRegistrationDate = companyRegistrationDate;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    public String getRegistrationNumberValue() {
        return registrationNumberValue;
    }

    public void setRegistrationNumberValue(String registrationNumberValue) {
        this.registrationNumberValue = registrationNumberValue;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getProEmail() {
        return proEmail;
    }

    public void setProEmail(String proEmail) {
        this.proEmail = proEmail;
    }

    public String getProMobileNumber() {
        return ProMobileNumber;
    }

    public void setProMobileNumber(String mobileNumber) {
        ProMobileNumber = mobileNumber;
    }

    public String getBillingStreet() {
        return BillingStreet;
    }

    public void setBillingStreet(String billingStreet) {
        BillingStreet = billingStreet;
    }

    public String getBillingPostalCode() {
        return BillingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        BillingPostalCode = billingPostalCode;
    }

    public String getBillingCountry() {
        return BillingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        BillingCountry = billingCountry;
    }

    public String getBillingState() {
        return BillingState;
    }

    public void setBillingState(String billingState) {
        BillingState = billingState;
    }

    public CurrentLicenseNumber get_currentLicenseNumber() {
        return _currentLicenseNumber;
    }

    public void set_currentLicenseNumber(CurrentLicenseNumber _currentLicenseNumber) {
        this._currentLicenseNumber = _currentLicenseNumber;
    }



    public long getShare_Capital_in_AED() {

        return Share_Capital_in_AED;
    }
}