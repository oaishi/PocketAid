package com.example.fariahuq.pocketaid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Description_page extends AppCompatActivity {

    private int position;
    private int pos;
    static final int MIN_DISTANCE = 5;
    private TextView text;
    private ImageView holder;
    int []rainbow;
    private ArrayList<AidItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rainbow =(this.getResources().getIntArray(R.array.array));
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("position");
        items = new MyDBHandler(this,null,null,1).databasetostringaiditem(pos+1);
        pos=items.size();
        position = 0;
        setContentView(R.layout.activity_description_page);
        text = (TextView)findViewById(R.id.Description);
        text.setText(Integer.toString(pos));
        findViewById(R.id.app_bar).setBackgroundColor(rainbow[pos]);
        holder = (ImageView)findViewById(R.id.imageView2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved to Favourite List", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        display(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description_page, menu);
        return true;
    }


     public void display(int pos)
     {
         text.setText(items.get(pos).getDesc());
         try {
             File directory = getApplicationContext().getDir("imageDir", Context.MODE_PRIVATE);
             File file = new File(directory.getAbsolutePath(),"6.jpg");
             Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
             holder.setImageBitmap(b);
         }
         catch (FileNotFoundException e)
         {
             holder.setBackgroundResource(R.drawable.hi);
             e.printStackTrace();
             Log.i("Image","Ki hocche");
         }
     }

}
