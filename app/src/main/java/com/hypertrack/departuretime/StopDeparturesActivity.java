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

public class StopDeparturesActivity extends AppCompatActivity {

    TextView stopNameTextView;
    ListView stopDeparturesListView;
    ProgressBar pb4;
    AgencyInfoClass AgencyInfoClassObject;
    List<StopDepartureItem> rowItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_departures);

        stopNameTextView = (TextView) findViewById(R.id.stopNameTextView);
        stopDeparturesListView = (ListView) findViewById(R.id.stopDeparturesListView);
        pb4 = (ProgressBar)findViewById(R.id.progressBar4);
        pb4.setVisibility(View.VISIBLE);

        AgencyInfoClassObject = new AgencyInfoClass();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String stop_code = extras.getString("STOP_CODE");
            stopNameTextView.setText( extras.getString("STOP_NAME") );

            String URL = Constants.BASE_URL + "/GetNextDeparturesByStopCode.aspx?";
            String params = "token=" + Constants.API_TOKEN + "&stopcode=" + Uri.encode(stop_code);


            NetworkUtility networkUtility = new NetworkUtility(getApplicationContext());
            if (networkUtility.isNetworkAvailable()){
                getStopDeparturesList(URL, params);
            }
            else{
                Toast.makeText(getApplicationContext(), "Network Not Available !", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Invalid Route Name", Toast.LENGTH_SHORT).show();
        }

    }


    private void getStopDeparturesList(String URL, String params) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL + params;
        final String[] data = new String[1];

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb4.setVisibility(View.INVISIBLE);

                        Log.d("Response : ", response);
                        Document responseDomObject = MainActivity.pareXML(response);

                        JSONArray stopDeparturesListResponse = AgencyInfoClassObject.getStopDeparturesList(responseDomObject);
                        populateStopDeparturesListView(stopDeparturesListResponse);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb4.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), "Error in getting Response", Toast.LENGTH_SHORT).show();
                Log.d("Error Occurred : ", error.toString());
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                System.out.println(response);
                data[0] = super.parseNetworkResponse(response).toString();
                return super.parseNetworkResponse(response);
            }
        };

        // Adding the request to the RequestQueue.
        queue.add(stringRequest);

    }


    private void populateStopDeparturesListView(JSONArray departuresListResponse) {
        rowItems = new ArrayList<>();

        for (int i = 0; i < departuresListResponse.length(); i++) {
            JSONObject tempObj = null;
            try {
                tempObj = (JSONObject) departuresListResponse.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                String route_name = (String)tempObj.get("RouteName");
                String route_code = (String)tempObj.get("RouteCode");
                String route_direction_code = (String) tempObj.get("RouteDirectionCode");
                String route_direction_name = (String) tempObj.get("RouteDirectionName");
                String departure_time = (String) tempObj.get("DepartureTime");

                StopDepartureItem item = new StopDepartureItem(route_name, route_code , route_direction_code, route_direction_name, departure_time);

                rowItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if( rowItems.size() == 0 ){
            Toast.makeText(this, "No Results Found !", Toast.LENGTH_SHORT).show();
        }

        stopDeparturesListView = (ListView) findViewById(R.id.stopDeparturesListView);
        StopDepartureItemAdapter adapter = new StopDepartureItemAdapter(getApplicationContext(), rowItems);
        stopDeparturesListView.setAdapter(adapter);

        stopDeparturesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String departure_time = rowItems.get(position).getDepartureTime();
                Toast.makeText(getApplicationContext(), departure_time, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
