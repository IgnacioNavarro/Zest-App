package com.example.zest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, "primary_notification_channel")
                .setContentTitle("Motivación diaria de Zest")
                .setContentText("probando")
                .setSmallIcon(R.drawable.zest);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, notifyBuilder.build());
    }
}
