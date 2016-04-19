package com.hypertrack.departuretime;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class RouteStopsActivity extends AppCompatActivity {

    TextView routeNameTextView;
    ListView routeStopsListView;
    ProgressBar pb3;
    AgencyInfoClass AgencyInfoClassObject;
    List<RouteStopItem> rowItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_stops);

        routeNameTextView = (TextView) findViewById(R.id.routeNameTextView);
        routeStopsListView = (ListView) findViewById(R.id.routeStopsListView);
        pb3 = (ProgressBar)findViewById(R.id.progressBar3);
        pb3.setVisibility(View.VISIBLE);

        AgencyInfoClassObject = new AgencyInfoClass();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String agency_name = extras.getString("AGENCY_NAME");
            String route_code = extras.getString("ROUTE_CODE");
            String route_direction_code = extras.getString("ROUTE_DIRECTION_CODE");

            routeNameTextView.setText( extras.getString("ROUTE_NAME") );

            String routeIDF;

            if ( route_direction_code.isEmpty() ){
                routeIDF = agency_name + "~" + route_code;
            }else{
                routeIDF = agency_name + "~" + route_code + "~" + route_direction_code;
            }


            String URL = Constants.BASE_URL + "/GetStopsForRoute.aspx?";
            String params = "token=" + Constants.API_TOKEN + "&routeIDF=" + Uri.encode(routeIDF);


            NetworkUtility networkUtility = new NetworkUtility(getApplicationContext());
            if (networkUtility.isNetworkAvailable()){
                getRouteStopsList(URL, params);
            }
            else{
                Toast.makeText(getApplicationContext(), "Network Not Available !", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Invalid Route Name", Toast.LENGTH_SHORT).show();
        }

    }


    private void getRouteStopsList(String URL, String params) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL + params;
        final String[] data = new String[1];

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb3.setVisibility(View.INVISIBLE);

                        Log.d("Response : ", response);
                        Document responseDomObject = MainActivity.pareXML(response);

                        JSONArray routeStopsListResponse = AgencyInfoClassObject.getRouteStopsList(responseDomObject);
                        populateRouteStopsListView(routeStopsListResponse);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb3.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), "Error in getting Response", Toast.LENGTH_SHORT).show();
                Log.d("Error Occurred : ", error.toString());
            }
        });

        queue.add(stringRequest);

    }

    private void populateRouteStopsListView(JSONArray stopsListResponse) {
        rowItems = new ArrayList<>();

        for (int i = 0; i < stopsListResponse.length(); i++) {
            JSONObject tempObj = null;
            try {
                tempObj = (JSONObject) stopsListResponse.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String name = (String) tempObj.get("StopName");
                String code = (String) tempObj.get("StopCode");
                RouteStopItem item = new RouteStopItem(name, code);
                rowItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if( rowItems.size() == 0 ){
            Toast.makeText(this, "No Results Found !", Toast.LENGTH_SHORT).show();
        }

        routeStopsListView = (ListView) findViewById(R.id.routeStopsListView);
        RouteStopItemAdapter adapter = new RouteStopItemAdapter(getApplicationContext(), rowItems);
        routeStopsListView.setAdapter(adapter);

        routeStopsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String stop_code = rowItems.get(position).getCode();
                String stop_name = rowItems.get(position).getName();

                Toast.makeText(getApplicationContext(), stop_code, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getApplicationContext(), StopDeparturesActivity.class);
                intent.putExtra("STOP_CODE", stop_code);
                intent.putExtra("STOP_NAME", stop_name);

                startActivity(intent);
            }
        });
    }

}
