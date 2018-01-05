package com.example.fariahuq.pocketaid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;


public class CustomAdapterVirtual extends RecyclerView.Adapter<CustomAdapterVirtual.ViewHolder> {

    private ArrayList<Integer> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
            Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
            Spinner spinner1 = (Spinner) v.findViewById(R.id.spinner1);
            spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
            Spinner spinner2 = (Spinner) v.findViewById(R.id.spinner2);
            spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
            Spinner spinner3 = (Spinner) v.findViewById(R.id.spinner3);
            spinner3.setOnItemSelectedListener(new CustomOnItemSelectedListener());
            Spinner spinner4 = (Spinner) v.findViewById(R.id.spinner4);
            spinner4.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        }

    }

    public CustomAdapterVirtual(ArrayList<Integer> dataSet) {
        mDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_virtual, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
