package com.akgarg.covid19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button totalCases;
    private Button activeCases;
    private Button newCases;
    private Button recovered;
    private Button deceased;

    private TextView dailyConfirmedChange;
    private TextView dailyRecoveryChange;
    private TextView dailyDeathChange;

    private String totalCase;
    private String activeCase;
    private String newCase;
    private String recoveredCase;
    private String deceasedCase;

    private String dailyConfirmed;
    private String dailyRecovered;
    private String dailyDeath;

    private ProgressBar progressBar;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalCases = findViewById(R.id.totalCases);
        activeCases = findViewById(R.id.activeCases);
        newCases = findViewById(R.id.newCases);
        recovered = findViewById(R.id.recovered);
        deceased = findViewById(R.id.deceased);

        dailyConfirmedChange = findViewById(R.id.daily_confirmed_changes);
        dailyRecoveryChange = findViewById(R.id.daily_recovery_cases);
        dailyDeathChange = findViewById(R.id.daily_death_cases);

        requestQueue = Volley.newRequestQueue(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        fetchData(findViewById(android.R.id.content).getRootView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.state_wise_record:
                Intent intent = new Intent(this, StateWiseRecord.class);
                startActivity(intent);
                break;

            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("\nCovid-19 India Tracker\n" +
                        "Developed by Akhilesh Garg\n" +
                        "API Used: Postman COVID19-India API\n\n" +
                        "Report bugs to akgarg0472@gmail.com\n\n" +
                        "© Akhilesh Garg")
                        .setCancelable(true);
                AlertDialog alert = builder.create();
                alert.setTitle("About Covid-19 Tracker");
                alert.show();
                break;
        }
        return true;
    }

    private void fetchData(final View view) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.covidindiatracker.com/total.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    totalCase = response.getString("confirmed");
                    activeCase = response.getString("active");
                    newCase = response.getString("cChanges");
                    recoveredCase = response.getString("recovered");
                    deceasedCase = response.getString("deaths");
                    dailyConfirmed = response.getString("cChanges");
                    dailyRecovered = response.getString("rChanges");
                    dailyDeath = response.getString("dChanges");
                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                    totalCases.setText("Total Cases\n\n" + totalCase);
                    activeCases.setText("Active Cases\n\n" + activeCase);
                    newCases.setText("New Cases\n\n" + newCase);
                    recovered.setText("Recovered\n\n" + recoveredCase);
                    deceased.setText("Deceased\n\n" + deceasedCase);
                    dailyConfirmedChange.setText(dailyConfirmedChange.getText() + dailyConfirmed);
                    dailyRecoveryChange.setText(dailyRecoveryChange.getText() + dailyRecovered);
                    dailyDeathChange.setText(dailyDeathChange.getText() + dailyDeath);
                } catch (JSONException e) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    Log.e(TAG, "onResponse: Error fetching data");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Snackbar.make(view, "Error fetching data, Please check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fetchData(view);
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                }).show();
            }
        });

        requestQueue.add(objectRequest);
    }
}