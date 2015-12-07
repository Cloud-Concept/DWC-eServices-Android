package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abanoub Wagdy on 7/7/2015.
 */


/**
 * This class holds ManagementMember attributes
 * Note : It holds the manager object ,just this separation for parsing salesforce requests
 */
public class ManagementMember {

    public String url;
    @SerializedName("Id")
    public String ID;
    @SerializedName("Manager_Status__c")
    public String Manager_Status;
    @SerializedName("Manager_Start_Date__c")
    public String Manager_Start_Date;
    @SerializedName("Manager_End_Date__c")
    public String Manager_End_Date;
    @SerializedName("Role__c")
    public String Role;
    @SerializedName("Status__c")
    public String Status;
    public Manager _manager;

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

    public String getManager_Status() {
        return Manager_Status;
    }

    public void setManager_Status(String manager_Status) {
        Manager_Status = manager_Status;
    }

    public String getManager_Start_Date() {
        return Manager_Start_Date;
    }

    public void setManager_Start_Date(String manager_Start_Date) {
        Manager_Start_Date = manager_Start_Date;
    }

    public String getManager_End_Date() {
        return Manager_End_Date;
    }

    public void setManager_End_Date(String manager_End_Date) {
        Manager_End_Date = manager_End_Date;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Manager get_manager() {
        return _manager;
    }

    public void set_manager(Manager _manager) {
        this._manager = _manager;
    }
}
