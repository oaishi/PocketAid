package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Aid> dataSet;
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
    }

    public CustomAdapter(ViewGroup container)
    {
        this.rainbow =(container.getContext()).getResources().getIntArray(R.array.array);
        this.dataSet =  new MyDBHandler(container.getContext(),null,null,1).databasetostringaid();
        this.context = container.getContext();
        path = container.getContext().getDir("imageDir",Context.MODE_PRIVATE) + "/";
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cards_layout, viewGroup, false);
        view.setOnClickListener(HolderOfAidList.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int listPosition) {
        holder.getTextViewName().setText(dataSet.get(listPosition).getTitle());
        int i = dataSet.get(listPosition).getFavourite();
        if(i==1)
            holder.getIconholder().setBackgroundResource(R.drawable.ic_fav);
        else
            holder.getIconholder().setBackgroundResource(R.drawable.ic_unfav);
        if(!(dataSet.get(listPosition).getImage().equals("null"))) {
            try {
                String path = "/data/user/0/com.example.fariahuq.pocketaid/app_imageDir" + "/" + dataSet.get(listPosition).getImage();
                File file = new File(path);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
                holder.getImageViewIcon().setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                holder.getImageViewIcon().setBackgroundResource(R.drawable.hi);
                e.printStackTrace();
            }
        }
        else
            holder.getImageViewIcon().setBackgroundResource(R.drawable.hi);
        i = listPosition%(rainbow.length);
        holder.getCardView().setBackgroundColor(rainbow[i]);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
