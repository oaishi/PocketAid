package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapterTest extends RecyclerView.Adapter<CustomAdapterTest.MyViewHolder> {

    private ArrayList<SelfTest> dataSet;
    private int[] rainbow;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        LinearLayout linear;
        ImageView iconholder;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.title);
            this.linear = (LinearLayout) itemView.findViewById(R.id.poopy);
            this.iconholder = (ImageView) itemView.findViewById(R.id.iconholder);
        }

        public ImageView getIconholder() {
            return iconholder;
        }

        public void setIconholder(ImageView iconholder) {
            this.iconholder = iconholder;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public LinearLayout getCardView(){return linear;}
    }

    public CustomAdapterTest(ViewGroup container)
    {
        this.rainbow =(container.getContext()).getResources().getIntArray(R.array.array1);
        this.dataSet =  new MyDBHandler(container.getContext(),null,null,1).DatabaseToStringTest();
        this.context = container.getContext();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout_test, viewGroup, false);
        view.setOnClickListener(HolderOfTestList.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        Log.d("Adapter", "Element " + listPosition + " set.");
        holder.getTextViewName().setText(dataSet.get(listPosition).getTitle());
        int i = dataSet.get(listPosition).getFavourite();
        if(i==1)
            holder.getIconholder().setBackgroundResource(R.drawable.ic_fav);
        else
            holder.getIconholder().setBackgroundResource(R.drawable.ic_unfav);
        /*int i = listPosition%(rainbow.length);
        holder.getCardView().setBackgroundColor(rainbow[i]);*/
        /*holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DescriptionSelfTest.class);
                intent.putExtra("position",listPosition);
                intent.putExtra("favourite", dataSet.get(listPosition).getFavourite());
                intent.putExtra("headline", dataSet.get(listPosition).getTitle());
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
