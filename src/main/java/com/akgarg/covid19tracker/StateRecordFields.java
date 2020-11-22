package com.akgarg.covid19tracker;

public class StateRecordFields {
    private String state;
    private int active;
    private int confirmed;
    private int recovered;
    private int deaths;

    String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    int getActive() {
        return active;
    }

    void setActive(int active) {
        this.active = active;
    }

    int getConfirmed() {
        return confirmed;
    }

    void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    int getRecovered() {
        return recovered;
    }

    void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    int getDeaths() {
        return deaths;
    }

    void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
