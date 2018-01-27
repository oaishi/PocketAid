package com.example.fariahuq.pocketaid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NotificationWindowInshot extends Activity {

    TextView message;
    TextView tile;
    ImageView imagi;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window_inshot);
        message = (TextView)findViewById(R.id.message);
        tile = (TextView)findViewById(R.id.title);
        imagi = (ImageView)findViewById(R.id.imagi);
        Bundle extras = getIntent().getExtras();
        message.setText(extras.getString("desc"));
        tile.setText(extras.getString("title"));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
                .showImageForEmptyUri(getResources().getDrawable(R.drawable.hi))
                .showImageOnFail(getResources().getDrawable(R.drawable.hi))
                .showImageOnLoading(getResources().getDrawable(R.drawable.hi)).build();

        imageLoader.getInstance().displayImage(extras.getString("image"), imagi, options);
    }

    public void onclick(View view)
    {
        finish();
    }
}
