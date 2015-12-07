package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Abanoub Wagdy on 6/16/2015.
 */

/**
 * This class holds logged in User attributes
 */
public class User implements Serializable{

    @SerializedName("url")
    public String url;

    @SerializedName("ContactId")
    public String ContactId;

    public Contact _contact;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContactId() {
        return ContactId;
    }

    public void setContactId(String contactId) {
        ContactId = contactId;
    }

    public Contact get_contact() {
        return _contact;
    }

    public void set_contact(Contact _contact) {
        this._contact = _contact;
    }
}
