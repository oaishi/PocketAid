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

    ImageView mImageView;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window);
        mCurrentPhotoPath = getIntent().getExtras().getString("photo");
        TextView text = (TextView)findViewById(R.id.message);
        mImageView = (ImageView)findViewById(R.id.NotiImage);
        if(mCurrentPhotoPath==null)
            mCurrentPhotoPath="sad";
        if(text!=null)
            text.setText(mCurrentPhotoPath);
        mImageView.setBackgroundResource(R.drawable.item4);


        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Log.i("alarm","in noti " + mCurrentPhotoPath);
		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        /* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);

    }

    public void onclick(View view)
    {
        finish();

    }
}
