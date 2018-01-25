package com.example.fariahuq.pocketaid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NotificationWindowInshot extends Activity {

    TextView message;
    TextView tile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_window_inshot);
        message = (TextView)findViewById(R.id.message);
        tile = (TextView)findViewById(R.id.title);
        Bundle extras = getIntent().getExtras();
        message.setText(extras.getString("desc"));
        tile.setText(extras.getString("title"));
    }

    public void onclick(View view)
    {
        finish();
    }
}
