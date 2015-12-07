package model;

import java.util.ArrayList;


/**
 * Created by Abanoub Wagdy on 6/26/2015.
 */

/**
 * This class holds Service Item attributes
 * Note : Each service in expandable item contains of text and icon and this model respresents that
 */
public class ServiceItem {

    int drawableIcon;
    String tvServiceName;

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }

    ArrayList<Object> objects;
    public ServiceItem() {

    }

    public ServiceItem(String text, int icon) {
        this.drawableIcon = icon;
        this.tvServiceName = text;
    }
    public ServiceItem(String text, int icon,ArrayList<Object> objects) {
        this.drawableIcon = icon;
        this.tvServiceName = text;
        this.objects=objects;
    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public String getTvServiceName() {
        return tvServiceName;
    }

    public void setTvServiceName(String tvServiceName) {
        this.tvServiceName = tvServiceName;
    }
}
