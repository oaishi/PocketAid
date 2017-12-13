package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapterAlarm extends RecyclerView.Adapter<CustomAdapterAlarm.MyViewHolder> {

    private ArrayList<Aid> dataSet;
    private int[] rainbow;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        LinearLayout linear;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.linear = (LinearLayout) itemView.findViewById(R.id.poopy);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public LinearLayout getCardView(){return linear;}
    }

    public CustomAdapterAlarm(ViewGroup container)
    {
        this.rainbow =(container.getContext()).getResources().getIntArray(R.array.array1);
        this.dataSet =  new MyDBHandler(container.getContext(),null,null,1).DatabaseToStringAid();
        this.context = container.getContext();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout_alarm, viewGroup, false);
        view.setOnClickListener(HolderOfAlarm.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        Log.d("Adapter", "Element " + listPosition + " set.");
        holder.getTextViewName().setText(dataSet.get(listPosition).getTitle());
        int i = listPosition%(rainbow.length);
        holder.getCardView().setBackgroundColor(rainbow[i]);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
