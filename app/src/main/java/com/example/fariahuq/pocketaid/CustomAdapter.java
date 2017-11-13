package com.example.fariahuq.pocketaid;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;
    private int[] rainbow;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;
        LinearLayout linear;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.linear = (LinearLayout) itemView.findViewById(R.id.poopy);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public ImageView getImageViewIcon(){
            return imageViewIcon;
        }

        public LinearLayout getCardView(){return linear;}
    }

    public CustomAdapter(ArrayList<DataModel> data,int[]rainbow) {
        this.dataSet = data;
        this.rainbow = rainbow;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout, viewGroup, false);
        view.setOnClickListener(HolderOfAidList.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
        //ContextCompat.getColor(context, R.color.your_color);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        Log.d("Adapter", "Element " + listPosition + " set.");
        holder.getTextViewName().setText(dataSet.get(listPosition).getName());
        holder.getImageViewIcon().setImageResource(dataSet.get(listPosition).getImage());
        int i = listPosition%(rainbow.length);
        holder.getCardView().setBackgroundColor(rainbow[i]);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
