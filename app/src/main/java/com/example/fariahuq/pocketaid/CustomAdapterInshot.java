package com.example.fariahuq.pocketaid;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterInshot extends RecyclerView.Adapter<CustomAdapterInshot.ViewHolder> {

    private ArrayList<DailyInshot> mDataSet;
    private int[] rainbow;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        LinearLayout linear;
        Button zoom;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.linear = (LinearLayout) itemView.findViewById(R.id.poopy);
            this.zoom = (Button)itemView.findViewById(R.id.zoom);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public LinearLayout getCardView(){return linear;}

        public Button getZoom() {
            return zoom;
        }
    }

    public CustomAdapterInshot(ArrayList<DailyInshot> dataSet, int []rainbow) {
        mDataSet = dataSet;
        this.rainbow = rainbow;
    }

    public ArrayList<DailyInshot> getmDataSet() {
        return mDataSet;
    }

    public void setmDataSet(ArrayList<DailyInshot> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout_alarm, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewGroup.getContext(), NotificationWindowInshot.class);
                viewGroup.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d("Adapter", "Element " + position + " set.");
        viewHolder.getTextViewName().setText(mDataSet.get(position).getTitle());
        int i = position%(rainbow.length);
        viewHolder.getCardView().setBackgroundColor(rainbow[i]);
        viewHolder.getZoom().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("p!","yay");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public ArrayList<DailyInshot> getValue(){return mDataSet;}
}
