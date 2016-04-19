package com.hypertrack.departuretime;

/**
 * Created by Yogesh on 17/04/16.
 */
public class AgencyListItem {

    private String agencyName;
    private String hasDirection;
    private String mode;

    public AgencyListItem(String agency_name, String has_direction, String mode) {
        this.agencyName = agency_name;
        this.hasDirection = has_direction;
        this.mode = mode;
    }

    public String getName() {
        return agencyName;
    }
    public void setName(String name) {
        this.agencyName = name;
    }

    public String getHasDirection() {
        return hasDirection;
    }
    public void setHasDirection(String direction) {
        this.hasDirection = direction;
    }

    public String getMode() {
        return mode;
    }
    public void setMode(String time) {
        this.mode = time;
    }

}
