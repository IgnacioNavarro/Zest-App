package com.example.zest;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;
    Button createNotification;
    private NotificationManager mNotifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        //hooks
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotification = findViewById(R.id.notify);
        createNotification.setOnClickListener(v -> sendNotification());



    }

    //Creacion del canal
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Zest Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Zest");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    //crear nueva notificacion
    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Motivación diaria de Zest")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.zest);
        return notifyBuilder;
    }

    //enviar notificacion a traves del manager con la notificacion creada
    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public int randomNumber(int max,int min){
        Random rand = new Random();

        // nextInt as provided by Random is exclusive of the top value so you need to add 1

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}