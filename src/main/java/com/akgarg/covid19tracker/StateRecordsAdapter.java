package com.akgarg.covid19tracker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class StateRecordsAdapter extends RecyclerView.Adapter<StateRecordsAdapter.StateViewHolder> {
    private static int color = 1;
    private final ArrayList<StateRecordFields> list;

    public StateRecordsAdapter(ArrayList<StateRecordFields> list) {
        this.list = list;
    }

    protected static class StateViewHolder extends RecyclerView.ViewHolder {
        Button stateRecordButton;

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            stateRecordButton = itemView.findViewById(R.id.state_record_button);
        }
    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.record_item, parent, false);
        return new StateViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        if (color == 7) {
            color = 1;
        }

        switch (color++) {
            case 1:
                holder.stateRecordButton.setBackgroundColor(Color.RED);
                break;
            case 2:
                holder.stateRecordButton.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                holder.stateRecordButton.setBackgroundColor(Color.CYAN);
                break;
            case 4:
                holder.stateRecordButton.setBackgroundColor(Color.GRAY);
                break;
            case 5:
                holder.stateRecordButton.setBackgroundColor(Color.rgb(218, 165, 32));
                break;
            case 6:
                holder.stateRecordButton.setBackgroundColor(Color.rgb(91, 89, 81));
        }

        holder.stateRecordButton.setText("\nName : " + list.get(position).getStateName() +
                "\nActive Cases : " + list.get(position).getActiveCases() +
                "\nConfirmed : " + list.get(position).getConfirmedCases() +
                "\nRecovered : " + list.get(position).getRecoveredCases() +
                "\nDeaths : " + list.get(position).getTotalDeceased() + "\n");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}