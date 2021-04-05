package com.akgarg.covid19tracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StateWiseRecord extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StateRecordsAdapter adapter;
    private ArrayList<StateRecordFields> stateRecords;

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
        String apiUrl = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, response -> {
            // populating data of state wise record
            try {
                JSONArray regionData = response.getJSONArray("regionData");

                for (int i = 0; i < regionData.length(); i++) {
                    JSONObject object = regionData.getJSONObject(i);
                    StateRecordFields recordFields = new StateRecordFields();
                    recordFields.setStateName(object.getString("region"));
                    recordFields.setActiveCases(object.getInt("totalInfected"));
                    recordFields.setConfirmedCases(object.getInt("totalInfected") + object.getInt("recovered") + object.getInt("deceased"));
                    recordFields.setRecoveredCases(object.getInt("recovered"));
                    recordFields.setTotalDeceased(object.getInt("deceased"));
                    stateRecords.add(recordFields);
                }
                adapter = new StateRecordsAdapter(stateRecords);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("TAG", "onErrorResponse: error -> " + error);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        });
        requestQueue.add(objectRequest);
    }
}