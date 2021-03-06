package model;

//import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Abanoub Wagdy on 8/31/2015.
 */

/**
 * This class holds Inventory Unit attributes
 * If there are inventory units ,they are displayed in details
 */
public class Inventory_Unit__r implements Serializable {

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
    public String id;
//    @JsonProperty("Name")
    @SerializedName("Name")
    public String name;
//    @JsonProperty("Building_Number__c")
    @SerializedName("Building_Number__c")
    public String building_Number__c;
//    @JsonProperty("Area_in_sq_m__c")
    @SerializedName("Area_in_sq_m__c")
    public String area_in_sq_m__c;
//    @JsonProperty("Unit_ID__c")
    @SerializedName("Unit_ID__c")
    public String unit_ID__c;
//    @JsonProperty("Zone__c")
    @SerializedName("Zone__c")
    public String zone__c;
//    @JsonProperty("Product_Type__c")
    @SerializedName("Product_Type__c")
    public String product_Type__c;

    public Product_Type__r product_Type__r;

    public String getBuilding_Number__c() {
        return building_Number__c;
    }

    public void setBuilding_Number__c(String building_Number__c) {
        this.building_Number__c = building_Number__c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea_in_sq_m__c() {
        return area_in_sq_m__c;
    }

    public void setArea_in_sq_m__c(String area_in_sq_m__c) {
        this.area_in_sq_m__c = area_in_sq_m__c;
    }

    public String getUnit_ID__c() {
        return unit_ID__c;
    }

    public void setUnit_ID__c(String unit_ID__c) {
        this.unit_ID__c = unit_ID__c;
    }

    public String getZone__c() {
        return zone__c;
    }

    public void setZone__c(String zone__c) {
        this.zone__c = zone__c;
    }

    public String getProduct_Type__c() {
        return product_Type__c;
    }

    public void setProduct_Type__c(String product_Type__c) {
        this.product_Type__c = product_Type__c;
    }

    public Product_Type__r getProduct_Type__r() {
        return product_Type__r;
    }

    public void setProduct_Type__r(Product_Type__r product_Type__r) {
        this.product_Type__r = product_Type__r;
    }
}
