package com.hypertrack.departuretime;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Yogesh on 17/04/16.
 */
public class AgencyInfoClass {


    public JSONArray getAgencyList(Document d){

        Node RTT = d.getChildNodes().item(0);
        NodeList agencyList = null;
        JSONArray agencyListArray = new JSONArray();

        for(int i = 0; i < RTT.getChildNodes().getLength() ; i++) {
            if (RTT.getChildNodes().item(i).getChildNodes().getLength() != 0) {
                agencyList = RTT.getChildNodes().item(i).getChildNodes();
            }
        }

        for(int i = 0; i < agencyList.getLength() ; i++){
            if ( agencyList.item(i).getChildNodes().getLength() != 0 ){

                Node agencyItem = agencyList.item(i);

                JSONObject Obj = new JSONObject();

                try {
                    Obj.put("Name", agencyItem.getAttributes().item(0).getNodeValue() );
                    Obj.put("HasDirection", agencyItem.getAttributes().item(1).getNodeValue() );
                    Obj.put("Mode", agencyItem.getAttributes().item(2).getNodeValue() );
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d("Error : ", e.toString());
                }
                agencyListArray.put(Obj);
            }
        }
        return agencyListArray;
    }


    public JSONArray getAgencyRoutesList(Document d){

        Node RTT = d.getChildNodes().item(0);
        NodeList agency = null;
        NodeList agencyRouteList = null;
        NodeList agencyRouteNodeList = null;
        JSONArray agencyRouteListArray = new JSONArray();

        Boolean RouteHasDirection = false;

        for(int i = 0; i < RTT.getChildNodes().getLength() ; i++) {
            if (RTT.getChildNodes().item(i).getChildNodes().getLength() != 0) {
                agency = RTT.getChildNodes().item(i).getChildNodes();
            }
        }

        for(int i = 0; i < agency.getLength() ; i++) {
            if (agency.item(i).getChildNodes().getLength() != 0) {
                agencyRouteNodeList = agency.item(i).getChildNodes();

                if( agency.item(i).getAttributes().item(1).getNodeValue().equals("True") ) {
                    RouteHasDirection = true;
                }
            }
        }

        for(int i = 0; i < agencyRouteNodeList.getLength() ; i++) {
            if (agencyRouteNodeList.item(i).getChildNodes().getLength() != 0) {
                agencyRouteList = agencyRouteNodeList.item(i).getChildNodes();
            }
        }


        for(int i = 0; i < agencyRouteList.getLength() ; i++){
            if ( agencyRouteList.item(i).getChildNodes().getLength() != 0 ){

                Node agencyItem = agencyRouteList.item(i);

                if( RouteHasDirection ){

                    for(int j=0; j < agencyItem.getChildNodes().item(1).getChildNodes().getLength() ; j++){

                        if( agencyItem.getChildNodes().item(1).getChildNodes().item(j).getChildNodes().getLength() != 0 ){

                            JSONObject Obj = new JSONObject();

                            try {
                                Obj.put("RouteName", agencyItem.getAttributes().item(0).getNodeValue() );
                                Obj.put("RouteCode", agencyItem.getAttributes().item(1).getNodeValue() );
                                Obj.put("RouteDirectionCode", agencyItem.getChildNodes().item(1).getChildNodes().item(j).getAttributes().item(0).getNodeValue() );
                                Obj.put("RouteDirectionName", agencyItem.getChildNodes().item(1).getChildNodes().item(j).getAttributes().item(1).getNodeValue() );

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.d("Error : ", e.toString());
                            }
                            agencyRouteListArray.put(Obj);
                        }

                    }

                }else{

                    JSONObject Obj = new JSONObject();

                    try {
                        Obj.put("RouteName", agencyItem.getAttributes().item(0).getNodeValue() );
                        Obj.put("RouteCode", agencyItem.getAttributes().item(1).getNodeValue() );
                        Obj.put("RouteDirectionCode", "" );
                        Obj.put("RouteDirectionName", "" );
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Log.d("Error : ", e.toString());
                    }

                    agencyRouteListArray.put(Obj);

                }

            }
        }
        return agencyRouteListArray;
    }


    public JSONArray getRouteStopsList(Document d){

        NodeList StopList = d.getElementsByTagName("StopList").item(0).getChildNodes();

        JSONArray routeStopListArray = new JSONArray();

        for(int i = 0; i < StopList.getLength() ; i++){
            if ( StopList.item(i).getChildNodes().getLength() != 0 ){

                Node stopItem = StopList.item(i);

                JSONObject Obj = new JSONObject();

                try {
                    Obj.put("StopName", stopItem.getAttributes().item(0).getNodeValue() );
                    Obj.put("StopCode", stopItem.getAttributes().item(1).getNodeValue() );
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d("Error : ", e.toString());
                }
                routeStopListArray.put(Obj);
            }
        }
        return routeStopListArray;
    }

    public JSONArray getStopDeparturesList(Document d){

        Node routeNodeList = d.getElementsByTagName("RouteList").item(0);
        NodeList routeList = null;
        NodeList StopList;

        JSONArray routeStopsDeparturesListArray = new JSONArray();

        for(int i = 0; i < routeNodeList.getChildNodes().getLength() ; i++) {
            if (routeNodeList.getChildNodes().item(i).getChildNodes().getLength() != 0) {
                routeList = routeNodeList.getChildNodes().item(i).getChildNodes();
            }
        }

        for(int i = 0; i < routeList.getLength() ; i++){
            if ( routeList.item(i).getChildNodes().getLength() != 0 ){

                Node routeItem = routeList.item(i);

                String routeName = routeItem.getParentNode().getAttributes().item(0).getNodeValue();
                String routeCode = routeItem.getParentNode().getAttributes().item(1).getNodeValue();

                for (int j = 0; j < ((Element) routeItem).getElementsByTagName("RouteDirection").getLength() ; j++){

                    String routeDirectionCode = ((Element) routeItem).getElementsByTagName("RouteDirection").item(j).getAttributes().item(0).getNodeValue();
                    String routeDirectionName = ((Element) routeItem).getElementsByTagName("RouteDirection").item(j).getAttributes().item(1).getNodeValue();

                    StopList = ((Element) routeItem).getElementsByTagName("RouteDirection").item(j).getChildNodes();

                    for ( int k = 0; k < StopList.getLength(); k++ ) {

                        if( StopList.item(k).getChildNodes().getLength() != 0 ){

                            Node StopNode = StopList.item(k);

                            for( int m=0; m < ((Element) StopNode).getElementsByTagName("DepartureTime").getLength(); m++ ){

                                String DepartureTime = ((Element) StopNode).getElementsByTagName("DepartureTime").item(m).getChildNodes().item(0).getNodeValue();

                                JSONObject Obj = new JSONObject();

                                try {
                                    Obj.put("RouteName", routeName );
                                    Obj.put("RouteCode", routeCode );
                                    Obj.put("RouteDirectionCode", routeDirectionCode );
                                    Obj.put("RouteDirectionName", routeDirectionName );
                                    Obj.put("DepartureTime", DepartureTime );

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.d("Error : ", e.toString());
                                }
                                routeStopsDeparturesListArray.put(Obj);
                            }
                        }
                    }
                }
            }
        }
        return routeStopsDeparturesListArray;
    }

}
