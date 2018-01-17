package com.example.fariahuq.pocketaid;

import android.app.PendingIntent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

public class NotificationWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window);
    }

    public void onclick(View view)
    {
        finish();
    }
}
