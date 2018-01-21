package com.example.fariahuq.pocketaid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NotificationWindowInshot extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window_inshot);
    }

    public void onclick(View view)
    {
        finish();
    }
}
