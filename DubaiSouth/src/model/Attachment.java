package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Abanoub on 7/6/2015.
 */

/**
 * Holds attachment attributes for logged in user
 * Note:There are a lot of services needed attachment to complete the survey process for attaching ,downloading and uploading to salesforce portal
 */
public class Attachment extends SObject implements Serializable {

    //    @JsonProperty("Id")
    @SerializedName("Id")
    public String ID;

    //    @JsonProperty("Body")
    @SerializedName("Body")
    public String Body;
    //    @JsonProperty("BodyLength")
    @SerializedName("BodyLength")
    public String BodyLength;
    //    @JsonProperty("Name")
    @SerializedName("Name")
    public String Name;

//    public model.attributes attributes;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getBodyLength() {
        return BodyLength;
    }

    public void setBodyLength(String bodyLength) {
        BodyLength = bodyLength;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
