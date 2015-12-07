package model;

import com.google.gson.annotations.SerializedName;

//import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Abanoub Wagdy on 6/25/2015.
 */

/**
 * This class holds nationality attributes
 */
public class CurrentNationality implements Serializable{

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
