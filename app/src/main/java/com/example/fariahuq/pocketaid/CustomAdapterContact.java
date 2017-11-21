package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class CustomAdapterContact extends RecyclerView.Adapter<CustomAdapterContact.MyViewHolder> {

    private ArrayList<contacts> dataSet;
    private Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewName1;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewName1 = (TextView) itemView.findViewById(R.id.textViewName2);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewName1() {
            return textViewName1;
        }

    }

    public CustomAdapterContact(ViewGroup container)
    {
        this.dataSet =  new MyDBHandler(container.getContext(),null,null,1).databasetocontact();
        this.context = container.getContext();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout_contact, viewGroup, false);
        view.setOnClickListener(HolderOfContact.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int listPosition) {
        Log.d("Contact", "Element " + listPosition + " set.");
        holder.getTextViewName1().setText(dataSet.get(listPosition).getTitle());
        holder.getTextViewName().setText(dataSet.get(listPosition).getDesc());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
