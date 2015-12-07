package model;

/**
 * Created by Abanoub Wagdy on 6/22/2015.
 */

/**
 * This class holds navigation drawer item
 * contains icon and name for sliding menu
 */
public class NavDrawerItem {

    public int Icon;
    public String Title;

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
