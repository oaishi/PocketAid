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

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Implementation of App Widget functionality.
 */
public class EmergencyContact extends AppWidgetProvider {

    static String CLICK_ACTION =  "CLICKED";
    private MyDBHandler dbHandler;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context,EmergencyContact.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.emergency_contact);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.widget,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onEnabled(Context context) {
        dbHandler = new MyDBHandler(context, null, null, 1);
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    Runnable runny = new Runnable() {
        @Override
        public void run() {
            ArrayList<Contact> contacts = dbHandler.databasetocontact();
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
           Log.i("widget","hi");
            ArrayList<Contact> contacts = dbHandler.databasetocontact();
           Thread thread = new Thread(runny);
            //thread.start();
        }
    }
}

