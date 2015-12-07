/**
 * Quote.java
 * <p/>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package model;


import com.google.gson.annotations.SerializedName;

//import org.codehaus.jackson.annotate.JsonProperty;

public class Quote implements java.io.Serializable {

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
    public String Id;
    //    @JsonProperty("Name")
    @SerializedName("Name")
    public String name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}