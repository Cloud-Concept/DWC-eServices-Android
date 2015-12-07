package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Abanoub on 7/7/2015.
 */

/**
 * This class holds Directorship attributes
 * Note:this class contains director object and details about him like start date , end date ...etc
 */
public class Directorship implements Serializable {

    String url;
    @SerializedName("Id")
    String ID;
    @SerializedName("Roles__c")
    String Roles;
    @SerializedName("Director_Status__c")
    String Director_Status;
    @SerializedName("Directorship_End_Date__c")
    String Directorship_End_Date;
    @SerializedName("Directorship_Start_Date__c")
    String Directorship_Start_Date;
    Director _director;

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

    public String getRoles() {
        return Roles;
    }

    public void setRoles(String roles) {
        Roles = roles;
    }

    public String getDirector_Status() {
        return Director_Status;
    }

    public void setDirector_Status(String director_Status) {
        Director_Status = director_Status;
    }

    public String getDirectorship_End_Date() {
        return Directorship_End_Date;
    }

    public void setDirectorship_End_Date(String directorship_End_Date) {
        Directorship_End_Date = directorship_End_Date;
    }

    public String getDirectorship_Start_Date() {
        return Directorship_Start_Date;
    }

    public void setDirectorship_Start_Date(String directorship_Start_Date) {
        Directorship_Start_Date = directorship_Start_Date;
    }

    public Director get_director() {
        return _director;
    }

    public void set_director(Director _director) {
        this._director = _director;
    }
}
