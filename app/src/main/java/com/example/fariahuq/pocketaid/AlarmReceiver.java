package com.example.fariahuq.pocketaid;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import  com.example.fariahuq.pocketaid.model.Alarm;
import  com.example.fariahuq.pocketaid.model.AlarmMsg;

/**
 * @author appsrox.com
 *
 */
public class AlarmReceiver extends BroadcastReceiver {

	private NotificationManager nm;


//	private static final String TAG = "AlarmReceiver";

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

	@Override
	public void onReceive(Context context, Intent intent) {
		long alarmMsgId = intent.getLongExtra(AlarmMsg.COL_ID, -1);
		long alarmId = intent.getLongExtra(AlarmMsg.COL_ALARMID, -1);
		String image = intent.getStringExtra("photo");

		/* Close dialogs and window shade */
		Intent closeDialogs = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		context.sendBroadcast(closeDialogs);

		nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		AlarmMsg alarmMsg = new AlarmMsg(alarmMsgId);
		alarmMsg.setStatus(AlarmMsg.EXPIRED);
		alarmMsg.persist(RemindMe.db);

		Alarm alarm = new Alarm(alarmId);
		alarm.load(RemindMe.db);

		Intent notify = new Intent(context, NotificationWindow.class);
		notify.putExtra("photo",image);
		//notify.putExtra("id", alarmId);
		PendingIntent pi = PendingIntent.getActivity(context, (int)alarmId, notify, 0);

		//@formatter:off
		Notification status = new NotificationCompat.Builder(context)
				.setContentTitle(alarm.getName())
				.setContentText("this is a message")
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pi)
				.setAutoCancel(true)
				.setOngoing(true)
				.setDefaults(Notification.DEFAULT_LIGHTS)
				.build();
		//@formatter:on

		// Send the notification using the alarm id to easily identify the
		// correct notification.
		nm.notify((int)alarmMsgId, status);

   //TODO: getting null channel issue
	}

}
