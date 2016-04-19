package com.hypertrack.departuretime;

/**
 * Created by Yogesh on 18/04/16.
 */
public class AgencyRouteItem {

    private String routeName;
    private String code;
    private String routeDirectionName;
    private String routeDirectionCode;

    public AgencyRouteItem(String route_name, String code, String route_direction_name, String route_direction_code) {
        this.routeName = route_name;
        this.code = code;
        this.routeDirectionName = route_direction_name;
        this.routeDirectionCode = route_direction_code;
    }

    public String getName() {
        return routeName;
    }
    public void setName(String name) {
        this.routeName = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
    public void setRouteDirectionName(String routeDirectionName) {
        this.routeDirectionName = routeDirectionName;
    }
}
