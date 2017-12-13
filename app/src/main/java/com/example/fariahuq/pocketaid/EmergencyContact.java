package com.example.fariahuq.pocketaid;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class EmergencyContact extends AppWidgetProvider {

    static String CLICK_ACTION =  "CLICKED";
    private MyDBHandler dbHandler;
    GPSTracker gps;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,EmergencyContact.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.emergency_contact);
        views.setOnClickPendingIntent(R.id.widget,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    Runnable runny = new Runnable() {
        @Override
        public void run() {
            ArrayList<Contact> contacts = dbHandler.DatabaseToContact();
            int count;
            SmsManager smsManager = SmsManager.getDefault();
            for (count = 0; count < contacts.size(); count++) {
                smsManager.sendTextMessage(contacts.get(count).getDesc(), null, "success",
                        null, null);
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equals(CLICK_ACTION))
        {
            gps = new GPSTracker(context);


            String general = (context.getResources().getString(R.string.messagebody));
            String link = (context.getResources().getString(R.string.mapaddress));

            // check if GPS enabled
            if(gps.canGetLocation()){
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                String address = "http://maps.google.com/maps?q=loc:" + String.format("%f,%f", latitude, longitude);
                Log.i("widget",general + " " + link + " "+ address);
            }else{
                Log.i("widget",general);
            }

            dbHandler = new MyDBHandler(context, null, null, 1);
            ArrayList<Contact> contacts = dbHandler.DatabaseToContact();
            //SmsManager smsManager = SmsManager.getDefault();
            /*for (int count = 0; count < contacts.size(); count++) {
                smsManager.sendTextMessage(contacts.get(count).getDesc(), null, link,
                        null, null);
            }*/
        }
    }
}

