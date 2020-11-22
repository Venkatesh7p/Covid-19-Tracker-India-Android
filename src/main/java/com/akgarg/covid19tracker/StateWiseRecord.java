package com.akgarg.covid19tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StateWiseRecord extends AppCompatActivity {
    private static final String TAG = "errorcheck";
    private RecyclerView recyclerView;
    private StateRecordsAdapter adapter;
    private ArrayList<StateRecordFields> stateRecords;

    private static final String dataURL = "https://api.covidindiatracker.com/state_data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise_record);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllStatesRecord();
    }

    private void getAllStatesRecord() {
        stateRecords = new ArrayList<>();
        ProgressBar progressBar = findViewById(R.id.progress_bar_statewise);
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, dataURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        StateRecordFields recordFields = new StateRecordFields();
                        recordFields.setState(object.getString("state"));
                        recordFields.setActive(object.getInt("active"));
                        recordFields.setConfirmed(object.getInt("confirmed"));
                        recordFields.setRecovered(object.getInt("recovered"));
                        recordFields.setDeaths(object.getInt("deaths"));
                        stateRecords.add(recordFields);
                    }
                    adapter = new StateRecordsAdapter(stateRecords);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StateWiseRecord.this, "Error fetching data", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);
    }
}