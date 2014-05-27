package com.project.assignmenttodo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notifiers  {
	
	
	public void createNotification(Context context,int no_of_subtasks)
	{
		 // Prepare intent which is triggered if the
	    // notification is selected
	    Intent intent = new Intent(context, SubtaskToday.class);
	    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
	 // Build notification
	    // Actions are just fake
	    Notification noti = new Notification.Builder(context)
	        .setContentTitle("Taskel - Today's Subtasks")
	        .setContentText( no_of_subtasks+" subtasks have due date today")
	        .setContentIntent(pIntent)
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setAutoCancel(true)
	        .build();
	    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.notify(0, noti);

	}

}
