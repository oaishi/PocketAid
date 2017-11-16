package com.example.fariahuq.pocketaid;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static java.security.AccessController.getContext;

public class Description_page extends AppCompatActivity {

    private float x1,x2;
    private int pos;
    static final int MIN_DISTANCE = 5;
    private TextView text;
    private ImageView holder;
    int []rainbow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rainbow =(this.getResources().getIntArray(R.array.array));
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("position");
        Log.i("fragment",Integer.toString(pos));
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description_page, menu);
        return true;
    }

}
