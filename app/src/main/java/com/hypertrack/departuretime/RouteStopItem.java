package com.hypertrack.departuretime;

/**
 * Created by Yogesh on 18/04/16.
 */
public class RouteStopItem {


    private String stopName;
    private String stopCode;

    public RouteStopItem(String stop_name, String stop_code) {
        this.stopName = stop_name;
        this.stopCode = stop_code;
    }

    public String getName() {
        return stopName;
    }
    public void setName(String name) {
        this.stopName = name;
    }

    public String getCode() {
        return stopCode;
    }
    public void setCode(String code) {
        this.stopCode = code;
    }


}
