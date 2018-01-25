package com.example.fariahuq.pocketaid;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NotificationWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window);
        String value = getIntent().getExtras().getString("photo");
        TextView text = (TextView)findViewById(R.id.message);
        ImageView NotiImage = (ImageView)findViewById(R.id.NotiImage);
        if(value==null)
            value="sad";
        if(text!=null)
            text.setText(value);
        /*int targetW = NotiImage.getWidth();
        int targetH = NotiImage.getHeight();

		/* Get the size of the image */
        /*BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(value, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        /*int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        /*bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        /*Bitmap bitmap = BitmapFactory.decodeFile(value, bmOptions);
        Log.i("image" , "path  " + value);*/
        //Drawable drawable = new BitmapDrawable(getResources(),bitmap);
        //NotiImage.setImageDrawable(drawable);
        /* Associate the Bitmap to the ImageView */
        //NotiImage.setImageBitmap(bitmap);


        try {
            File file = new File(value);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
            NotiImage.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            NotiImage.setBackgroundResource(R.drawable.hi);
            e.printStackTrace();
        }

    }

    public void onclick(View view)
    {
        finish();
    }
}
