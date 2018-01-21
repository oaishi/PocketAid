package com.example.fariahuq.pocketaid;

import android.app.PendingIntent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class NotificationWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window);
        String value = getIntent().getExtras().getString("photo");
        TextView text = (TextView)findViewById(R.id.message);
        if(value==null)
            value="sad";
        if(text!=null)
            text.setText(value);
    }

    public void onclick(View view)
    {
        finish();
    }
}