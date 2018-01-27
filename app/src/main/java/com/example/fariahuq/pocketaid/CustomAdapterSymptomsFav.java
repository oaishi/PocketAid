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


public class CustomAdapterSymptomsFav extends RecyclerView.Adapter<CustomAdapterSymptomsFav.MyViewHolder> {

    private ArrayList<Symptoms> dataSet;
    private int[] rainbow;
    private String path;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;
        LinearLayout linear;
        ImageView iconholder;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.iconholder = (ImageView) itemView.findViewById(R.id.iconholder);
            this.linear = (LinearLayout) itemView.findViewById(R.id.poopy);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public ImageView getImageViewIcon(){
            return imageViewIcon;
        }

        public ImageView getIconholder(){return iconholder;}

        public LinearLayout getCardView(){return linear;}

        public void setImageViewIcon(ImageView imageViewIcon) {
            this.imageViewIcon = imageViewIcon;
        }
    }

    public CustomAdapterSymptomsFav(ViewGroup container)
    {
        this.dataSet =  new MyDBHandler(container.getContext(),null,null,1).DatabaseToStringFavSymptoms();
        Log.i("favhoyna" , "currnet size symp " + Integer.toString(dataSet.size()));
        this.context = container.getContext();
        path = container.getContext().getDir("imageDir",Context.MODE_PRIVATE) + "/";
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout_symptoms, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int listPosition) {
        Log.i("favhoyna" , "currnet size symp " + listPosition);
        holder.getTextViewName().setText(dataSet.get(listPosition).getTitle());
        int i = dataSet.get(listPosition).getFavourite();
        if(i==1)
            holder.getIconholder().setBackgroundResource(R.drawable.ic_fav);
        else
            holder.getIconholder().setBackgroundResource(R.drawable.ic_unfav);
        i = listPosition%5;
        if(i==0)
        holder.getImageViewIcon().setBackgroundResource(R.drawable.item1);
        else if(i==1)
            holder.getImageViewIcon().setBackgroundResource(R.drawable.item2);
        else if(i==2)
            holder.getImageViewIcon().setBackgroundResource(R.drawable.item3);
        else if(i==3)
            holder.getImageViewIcon().setBackgroundResource(R.drawable.item4);
        else
            holder.getImageViewIcon().setBackgroundResource(R.drawable.item5);

        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DescriptionSymptom.class);
                intent.putExtra("position",dataSet.get(listPosition).getId());
                intent.putExtra("favourite",dataSet.get(listPosition).getFavourite());
                intent.putExtra("headline",dataSet.get(listPosition).getTitle());
                intent.putExtra("tofav","tofav");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
