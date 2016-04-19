package com.hypertrack.departuretime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Button getAgenciesBtn;
    AgencyInfoClass AgencyInfoClassObject;
    List<AgencyListItem> rowItems = new ArrayList<>();
    ListView agencyListView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAgenciesBtn = (Button)findViewById(R.id.getAgenciesBtn);
        AgencyInfoClassObject = new AgencyInfoClass();

        getAgenciesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Constants.BASE_URL + "/GetAgencies.aspx?";
                String params = "token=" + Constants.API_TOKEN;

                NetworkUtility networkUtility = new NetworkUtility(getApplicationContext());
                if (networkUtility.isNetworkAvailable()){
                    getAgencyList(URL, params);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Network Not Available !", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void getAgencyList(String URL, String params) {
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL + params;
        final String[] data = new String[1];

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pb.setVisibility(View.INVISIBLE);

                        Log.d("Response : ", response);
                        Document responseDomObject = pareXML(response);

                        JSONArray agencyListResponse = AgencyInfoClassObject.getAgencyList(responseDomObject);
                        populateAgencyListView( agencyListResponse );

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.INVISIBLE);

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

    private void populateAgencyListView(JSONArray agencyListResponse) {
        rowItems = new ArrayList<>();

        for (int i = 0; i < agencyListResponse.length(); i++) {
            JSONObject tempObj = null;
            try {
                tempObj = (JSONObject) agencyListResponse.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String name = (String)tempObj.get("Name");
                String hasDirection = (String) tempObj.get("HasDirection");
                String mode = (String) tempObj.get("Mode");
                AgencyListItem item = new AgencyListItem(name, hasDirection, mode);
                rowItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if( rowItems.size() == 0 ){
            Toast.makeText(this, "No Results Found !", Toast.LENGTH_SHORT).show();
        }

        agencyListView = (ListView) findViewById(R.id.agencyListView);
        AgencyListItemAdapter adapter = new AgencyListItemAdapter(getApplicationContext(), rowItems);
        agencyListView.setAdapter(adapter);

        agencyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String name = rowItems.get(position).getName();
                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), AgencyRoutesActivity.class);
                intent.putExtra("AGENCY_NAME", name);
                startActivity(intent);
            }
        });
    }


    public static Document pareXML(String s){

        DocumentBuilderFactory factory;
        DocumentBuilder builder = null;
        InputStream is;
        Document dom = null;

        String xml = s;
        is = null;

        try {

            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

            is = new ByteArrayInputStream(xml.getBytes("UTF-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        try {
            dom = builder.parse(is);
            Log.d("DOM IS : ", String.valueOf(dom));
            return dom;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dom;
    }

}
