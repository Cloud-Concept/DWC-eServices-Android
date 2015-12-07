package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abanoub Wagdy on 8/2/2015.
 */

/**
 * This class holds Form Field attributes
 * for any generated service in DWC in second step during survey process,
 * there are some fields that are dynamically drawn according to its type
 * Each field has some attributes that values are assigned to it by reflection
 */
public class FormField {


    @SerializedName("Id")
    public String Id;
    @SerializedName("Name")
    public String name;

    public String nameNoSpace;
    @SerializedName("Email_Value__c")
    public String emailValue;
    @SerializedName("Label__c")
    public String label;
    @SuppressWarnings("Phone_Value__c")
    public String phoneValue;
    @SerializedName("Picklist_Value__c")
    public String picklistValue;
    @SerializedName("PicklistEntries__c")
    public String picklistEntries;
    @SerializedName("Text_Area_Long_Value__c")
    public String textAreaLongValue;
    @SerializedName("Text_Area_Value__c")
    public String textAreaValue;
    @SerializedName("Text_Value__c")
    public String textValue;
    @SerializedName("Type__c")
    public String type;
    @SerializedName("URL_Value__c")
    public String urlValue;
    @SerializedName("Web_Form__c")
    public String webForm;
    @SerializedName("Mobile_Label__c")
    public String mobileLabel;
    @SerializedName("Controlling_Field__c")
    public String controllingField;
    @SerializedName("APIRequired__c")
    public boolean apiRequired;
    @SerializedName("Boolean_Value__c")
    public boolean booleanValue;
    @SerializedName("Hidden__c")
    public boolean hidden;
    @SerializedName("isCalculated__c")
    public boolean isCalculated;
    @SerializedName("isParameter__c")
    public boolean isParameter;
    @SerializedName("isQuery__c")
    public boolean isQuery;
    @SerializedName("Required__c")
    public boolean required;
    @SerializedName("isMobileAvailable__c")
    public boolean isMobileAvailable;
    @SerializedName("isDependentPicklist__c")
    public boolean isDependentPicklist;
    @SerializedName("should_Be_Cloned__c")
    public boolean shouldBeCloned;
    @SerializedName("Currency_Value__c")
    public String currencyValue;
    @SerializedName("Number_Value__c")
    public String numberValue;
    @SerializedName("Order__c")
    public String order;
    @SerializedName("Mobile_Order__c")
    public String mobileOrder;
    @SerializedName("Percent_Value__c")
    public String percentValue;
    @SerializedName("Width__c")
    public String width;
    @SerializedName("DateTime_Value__c")
    public String dateTimeValue;
    @SerializedName("Date_Value__c")
    public String dateValue;

    public String getFormFieldValue() {
        return FormFieldValue;
    }

    public void setFormFieldValue(String formFieldValue) {
        FormFieldValue = formFieldValue;
    }

    String FormFieldValue;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameNoSpace() {
        return nameNoSpace;
    }

    public void setNameNoSpace(String nameNoSpace) {
        this.nameNoSpace = nameNoSpace;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPhoneValue() {
        return phoneValue;
    }

    public void setPhoneValue(String phoneValue) {
        this.phoneValue = phoneValue;
    }

    public String getPicklistValue() {
        return picklistValue;
    }

    public void setPicklistValue(String picklistValue) {
        this.picklistValue = picklistValue;
    }

    public String getPicklistEntries() {
        return picklistEntries;
    }

    public void setPicklistEntries(String picklistEntries) {
        this.picklistEntries = picklistEntries;
    }

    public String getTextAreaLongValue() {
        return textAreaLongValue;
    }

    public void setTextAreaLongValue(String textAreaLongValue) {
        this.textAreaLongValue = textAreaLongValue;
    }

    public String getTextAreaValue() {
        return textAreaValue;
    }

    public void setTextAreaValue(String textAreaValue) {
        this.textAreaValue = textAreaValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlValue() {
        return urlValue;
    }

    public void setUrlValue(String urlValue) {
        this.urlValue = urlValue;
    }

    public String getWebForm() {
        return webForm;
    }

    public void setWebForm(String webForm) {
        this.webForm = webForm;
    }

    public String getMobileLabel() {
        return mobileLabel;
    }

    public void setMobileLabel(String mobileLabel) {
        this.mobileLabel = mobileLabel;
    }

    public String getControllingField() {
        return controllingField;
    }

    public void setControllingField(String controllingField) {
        this.controllingField = controllingField;
    }

    public boolean isApiRequired() {
        return apiRequired;
    }

    public void setApiRequired(boolean apiRequired) {
        this.apiRequired = apiRequired;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setIsCalculated(boolean isCalculated) {
        this.isCalculated = isCalculated;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public void setIsParameter(boolean isParameter) {
        this.isParameter = isParameter;
    }

    public boolean isQuery() {
        return isQuery;
    }

    public void setIsQuery(boolean isQuery) {
        this.isQuery = isQuery;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isMobileAvailable() {
        return isMobileAvailable;
    }

    public void setIsMobileAvailable(boolean isMobileAvailable) {
        this.isMobileAvailable = isMobileAvailable;
    }

    public boolean isDependentPicklist() {
        return isDependentPicklist;
    }

    public void setIsDependentPicklist(boolean isDependentPicklist) {
        this.isDependentPicklist = isDependentPicklist;
    }

    public boolean isShouldBeCloned() {
        return shouldBeCloned;
    }

    public void setShouldBeCloned(boolean shouldBeCloned) {
        this.shouldBeCloned = shouldBeCloned;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(String numberValue) {
        this.numberValue = numberValue;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMobileOrder() {
        return mobileOrder;
    }

    public void setMobileOrder(String mobileOrder) {
        this.mobileOrder = mobileOrder;
    }

    public String getPercentValue() {
        return percentValue;
    }

    public void setPercentValue(String percentValue) {
        this.percentValue = percentValue;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDateTimeValue() {
        return dateTimeValue;
    }

    public void setDateTimeValue(String dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

}