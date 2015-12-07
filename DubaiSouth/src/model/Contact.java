package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Abanoub on 6/16/2015.
 */

/**
 * Holds Contact attributes for logged in user
 * Note : user has contact ,contact has account that includes all details of logged in user
 */
public class Contact implements Serializable{

    @SerializedName("url")
    public String url;

    @SerializedName("Name")
    public String Name;

    @SerializedName("Personal_Photo__c")
    public String Personal_Photo;

    public Account _account;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPersonal_Photo() {
        return Personal_Photo;
    }

    public void setPersonal_Photo(String personal_Photo) {
        Personal_Photo = personal_Photo;
    }

    public Account get_account() {
        return _account;
    }

    public void set_account(Account _account) {
        this._account = _account;
    }
}
