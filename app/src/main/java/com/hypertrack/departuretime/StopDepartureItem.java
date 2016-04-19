package com.hypertrack.departuretime;

/**
 * Created by Yogesh on 18/04/16.
 */
public class StopDepartureItem {


    private String routeName;
    private String routeCode;
    private String routeDirectionCode;
    private String routeDirectionName;
    private String departureTime;

    public StopDepartureItem(String route_name, String route_code, String route_direction_code, String route_direction_name, String departure_time) {
        this.routeName = route_name;
        this.routeCode = route_code;
        this.routeDirectionCode = route_direction_code;
        this.routeDirectionName = route_direction_name;
        this.departureTime = departure_time;
    }

    public String getRouteName() {
        return routeName;
    }
    public void setRouteName(String name) {
        this.routeName = name;
    }

    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String code) {
        this.routeCode = code;
    }

    public String getRouteDirectionCode() {
        return routeDirectionCode;
    }
    public void setRouteDirectionCode(String code) {
        this.routeDirectionCode = code;
    }

    public String getRouteDirectionName() {
        return routeDirectionName;
    }
    public void setRouteDirectionName(String name) {
        this.routeDirectionName = name;
    }

    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String time) {
        this.departureTime = time;
    }


}
