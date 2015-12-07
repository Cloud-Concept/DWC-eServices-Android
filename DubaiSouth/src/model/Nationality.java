package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Abanoub Wagdy on 6/25/2015.
 */

/**
 * This class holds nationality attributes
 */
public class Nationality implements Serializable{

    public model.attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(model.attributes attributes) {
        this.attributes = attributes;
    }

    @SerializedName("attributes")
    attributes attributes;
    @SerializedName("Id")
    public  String Id;
    @SerializedName("Name")
    public String Name;



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

