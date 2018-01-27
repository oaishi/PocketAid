package com.example.fariahuq.pocketaid;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
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
    Context context;

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


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp = "Showing Your Result :\n";
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params)  {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            dbHandler = new MyDBHandler(context, null, null, 1);
            ArrayList<Contact> contacts = dbHandler.DatabaseToContact();
            SmsManager smsManager = SmsManager.getDefault();
            for (int count = 0; count < contacts.size(); count++) {
                smsManager.sendTextMessage(contacts.get(count).getDesc(), null, params[0],
                        null, null);
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        this.context = context;
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
                general =  general + " " + link + " "+ address;
                Log.i("widget",general );
            }else{
                Log.i("widget",general);
            }

            AsyncTaskRunner sender = new AsyncTaskRunner();
            sender.execute(general);

        }
    }
}

