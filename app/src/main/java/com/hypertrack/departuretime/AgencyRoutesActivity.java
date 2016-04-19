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

public class AgencyRoutesActivity extends AppCompatActivity {
    TextView agencyNameTextView;
    ListView agencyRoutesListView;
    ProgressBar pb2;
    AgencyInfoClass AgencyInfoClassObject;
    List<AgencyRouteItem> rowItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_routes);

        agencyNameTextView = (TextView) findViewById(R.id.agencyNameTextView);
        agencyRoutesListView = (ListView) findViewById(R.id.agencyRoutesListView);
        pb2 = (ProgressBar)findViewById(R.id.progressBar2);
        pb2.setVisibility(View.VISIBLE);

        AgencyInfoClassObject = new AgencyInfoClass();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String agency_name = extras.getString("AGENCY_NAME");
            agencyNameTextView.setText(agency_name);

            String URL = Constants.BASE_URL + "/GetRoutesForAgency.aspx?";
            String params = "token=" + Constants.API_TOKEN + "&agencyName=" + Uri.encode(agency_name);

            NetworkUtility networkUtility = new NetworkUtility(getApplicationContext());
            if (networkUtility.isNetworkAvailable()){
                getAgencyRoutesList(URL, params);
            }
            else{
                Toast.makeText(getApplicationContext(), "Network Not Available !", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Invalid Agency Name", Toast.LENGTH_SHORT).show();
        }

    }

    private void getAgencyRoutesList(String URL, String params) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL + params;
        final String[] data = new String[1];

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb2.setVisibility(View.INVISIBLE);

                        Log.d("Response : ", response);
                        Document responseDomObject = MainActivity.pareXML(response);

                        JSONArray agencyRoutesListResponse = AgencyInfoClassObject.getAgencyRoutesList(responseDomObject);
                        populateAgencyRoutesListView( agencyRoutesListResponse );

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb2.setVisibility(View.INVISIBLE);

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

        queue.add(stringRequest);

    }


    private void populateAgencyRoutesListView(JSONArray agencyRoutesListResponse) {
        rowItems = new ArrayList<>();

        for (int i = 0; i < agencyRoutesListResponse.length(); i++) {
            JSONObject tempObj = null;
            try {
                tempObj = (JSONObject) agencyRoutesListResponse.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String name = (String) tempObj.get("RouteName");
                String code = (String) tempObj.get("RouteCode");
                String route_direction_name = (String) tempObj.get("RouteDirectionName");
                String route_direction_code = (String) tempObj.get("RouteDirectionCode");

                AgencyRouteItem item = new AgencyRouteItem(name, code, route_direction_name, route_direction_code);
                rowItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if( rowItems.size() == 0 ){
            Toast.makeText(this, "No Results Found !", Toast.LENGTH_SHORT).show();
        }

        agencyRoutesListView = (ListView) findViewById(R.id.agencyRoutesListView);
        AgencyRouteItemAdapter adapter = new AgencyRouteItemAdapter(getApplicationContext(), rowItems);
        agencyRoutesListView.setAdapter(adapter);

        agencyRoutesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String route_name = rowItems.get(position).getName();
                String route_code = rowItems.get(position).getCode();
                String route_direction_code = rowItems.get(position).getRouteDirectionCode();

                Toast.makeText(getApplicationContext(), route_name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), RouteStopsActivity.class);
                intent.putExtra("AGENCY_NAME", agencyNameTextView.getText().toString());
                intent.putExtra("ROUTE_CODE", route_code);
                intent.putExtra("ROUTE_NAME", route_name);
                intent.putExtra("ROUTE_DIRECTION_CODE", route_direction_code);
                startActivity(intent);
            }
        });
    }


}
