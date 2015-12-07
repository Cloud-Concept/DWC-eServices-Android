package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Abanoub Wagdy on 8/18/2015.
 */
public class Party extends SObject implements Serializable {

    //    @JsonProperty("Name")
    @SerializedName("Name")
    public String Name;
    //    @JsonProperty("Id")
    @SerializedName("Id")
    public String Id;
//    @JsonProperty("attributes")
//    attributes attributes;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

//    public model.attributes getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(model.attributes attributes) {
//        this.attributes = attributes;
//    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }
}
