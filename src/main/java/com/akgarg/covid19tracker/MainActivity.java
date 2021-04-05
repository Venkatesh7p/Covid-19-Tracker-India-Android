package com.akgarg.covid19tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private Button totalCases;
    private Button activeCases;
    private Button newCases;
    private Button recovered;
    private Button deceased;

    private TextView dailyRecoveryChange;
    private TextView dailyDeathChange;
    private TextView lastUpdated;

    private String totalCase;
    private String activeCase;
    private String newCase;
    private String recoveredCase;
    private String deceasedCase;
    private String dailyRecovered;
    private String dailyDeath;
    private String lastUpdate;

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

        dailyRecoveryChange = findViewById(R.id.daily_recovery_cases);
        dailyDeathChange = findViewById(R.id.daily_death_cases);
        lastUpdated = findViewById(R.id.last_updated);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.state_wise_record:
                Intent intent = new Intent(this, StateWiseRecord.class);
                startActivity(intent);
                break;

            case R.id.about:
                final AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.about_app_title)
                        .setMessage(R.string.dialogContent)
                        .setCancelable(true)
                        .create();
                alertDialog.show();
                ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                break;
        }
        return true;
    }

    private void fetchData(final View view) {
        String apiUrl = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true";

        @SuppressLint("SetTextI18n") JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, response -> {
            try {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                totalCase = response.getString("totalCases");
                activeCase = response.getString("activeCases");
                newCase = response.getString("activeCasesNew");
                recoveredCase = response.getString("recovered");
                deceasedCase = response.getString("deaths");
                lastUpdate = response.getString("lastUpdatedAtApify");

                dailyRecovered = response.getString("recoveredNew");
                dailyDeath = response.getString("deathsNew");

                progressBar.setVisibility(ProgressBar.INVISIBLE);

                totalCases.setText("Total Cases\n\n" + totalCase);
                activeCases.setText("Active Cases\n\n" + activeCase);
                newCases.setText("New Cases\n\n" + newCase);
                recovered.setText("Recovered\n\n" + recoveredCase);
                deceased.setText("Deceased\n\n" + deceasedCase);

                lastUpdated.setText("Last updated : " + lastUpdate.substring(8, 10) + "-" + lastUpdate.substring(5, 7) + "-" + lastUpdate.substring(0, 4));
                dailyRecoveryChange.setText(dailyRecoveryChange.getText() + " " + dailyRecovered);
                dailyDeathChange.setText(dailyDeathChange.getText() + " " + dailyDeath);
            } catch (JSONException e) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                e.printStackTrace();
            }
        }, error -> {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            Snackbar.make(view, "Error fetching data, Please check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", v -> {
                fetchData(view);
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }).show();
        });

        requestQueue.add(objectRequest);
    }
}