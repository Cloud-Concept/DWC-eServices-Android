package model;

import com.google.gson.annotations.SerializedName;

//import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abanoub Wagdy on 6/25/2015.
 */

/**
 * This class holds Sponsoring Company attributes
 * Note:Each director,shareholder ...etc has its own sponsoring company related to it.
 */
public class SponsoringCompany implements Serializable {

//    public model.attributes getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(model.attributes attributes) {
//        this.attributes = attributes;
//    }
//
//    @JsonProperty("attributes")
//    attributes attributes;

//    @JsonProperty("Name")
    @SerializedName("Name")
    public String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
