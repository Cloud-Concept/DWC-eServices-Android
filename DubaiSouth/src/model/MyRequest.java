package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abanoub Wagdy on 7/7/2015.
 */

/**
 * This class holds my request attributes
 * Note : this model indicates any request information for logged in user like NOC ,Card ,Registration Services ...etc
 */
public class MyRequest {

    @SerializedName("url")
    public String url;
    @SerializedName("Id")
    public String ID;
    @SerializedName("CaseNumber")
    public String CaseNumber;
    @SerializedName("Status")
    public String Status;
    @SerializedName("Type")
    public String Type;
    @SerializedName("Web_Form__c")
    public String Web_Form;
    @SerializedName("CreatedDate")
    public String CreatedDate;
    @SerializedName("Sub_Type__c")
    public String Sub_Type;
    @SerializedName("Sub_Type_Formula__c")
    public String Sub_Type_Formula;

    public EmployeeRef getEmployee_Ref() {
        return Employee_Ref;
    }

    public void setEmployee_Ref(EmployeeRef employee_Ref) {
        Employee_Ref = employee_Ref;
    }

    @SerializedName("Employee_Ref__r")
    public EmployeeRef Employee_Ref;

    public RecordType recordType;

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

    public String getCaseNumber() {
        return CaseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        CaseNumber = caseNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getWeb_Form() {
        return Web_Form;
    }

    public void setWeb_Form(String web_Form) {
        Web_Form = web_Form;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getSub_Type() {
        return Sub_Type;
    }

    public void setSub_Type(String sub_Type) {
        Sub_Type = sub_Type;
    }

    public String getSub_Type_Formula() {
        return Sub_Type_Formula;
    }

    public void setSub_Type_Formula(String sub_Type_Formula) {
        Sub_Type_Formula = sub_Type_Formula;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }
}
