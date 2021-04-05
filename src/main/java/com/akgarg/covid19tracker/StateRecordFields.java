package com.akgarg.covid19tracker;

@SuppressWarnings("all")
public class StateRecordFields {
    private String stateName;
    private int activeCases;
    private int confirmedCases;
    private int recoveredCases;
    private int totalDeceased;

    String getStateName() {
        return stateName;
    }

    void setStateName(String stateName) {
        this.stateName = stateName;
    }

    int getActiveCases() {
        return activeCases;
    }

    void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    int getConfirmedCases() {
        return confirmedCases;
    }

    void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    int getRecoveredCases() {
        return recoveredCases;
    }

    void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    int getTotalDeceased() {
        return totalDeceased;
    }

    void setTotalDeceased(int totalDeceased) {
        this.totalDeceased = totalDeceased;
    }

    @Override
    public String toString() {
        return "StateRecordFields{" +
                "state='" + stateName + '\'' +
                ", active=" + activeCases +
                ", confirmed=" + confirmedCases +
                ", recovered=" + recoveredCases +
                ", deaths=" + totalDeceased +
                '}';
    }
}
