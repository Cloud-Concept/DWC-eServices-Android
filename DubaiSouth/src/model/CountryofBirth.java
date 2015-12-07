package model;

import com.google.gson.annotations.SerializedName;

//import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abanoub on 6/25/2015.
 */

/**
 * This class holds country of birth attributes displayed in services survey
 */
public class CountryofBirth implements Serializable{

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
