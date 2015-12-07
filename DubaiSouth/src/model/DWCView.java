package model;

/**
 * Created by Abanoub on 7/4/2015.
 */

/**
 * This class holds DWCView attributes
 * Note:this class contains generic view for drawing items on layout with ItemType and value
 * Item Type can be line , HEADER,HORIZONTAL_LIST_VIEW,LABEL,VALUE,LINE and each one has its own way for drawing
 */
public class DWCView {

    public String value;
    public ItemType type;

    public DWCView(String value, ItemType type) {
        this.value = value;
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }


    public String getValue() {

        return value;
    }
}
