package io.anadeem2.routereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Reminders extends BroadcastReceiver {
    int stopNum = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        stopNum++;
        notificationHelper.getManager().notify(stopNum, nb.build());


    }
}
