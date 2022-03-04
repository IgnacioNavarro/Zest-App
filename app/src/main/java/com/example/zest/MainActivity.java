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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;
    Button createNotification;
    private NotificationManager mNotifyManager;
    private DatabaseReference rootDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        //hooks
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotification = findViewById(R.id.notify);

        //leer database
        rootDatabase = FirebaseDatabase.getInstance("https://zest-a7919-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("QuotesES");
        rootDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String data = dataSnapshot.child("001").getValue().toString();
                    createNotification.setOnClickListener(v -> sendNotification(data));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





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
    private NotificationCompat.Builder getNotificationBuilder(String quote){

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Motivación diaria de Zest")
                .setContentText(quote)
                .setSmallIcon(R.drawable.zest);
        return notifyBuilder;
    }

    //enviar notificacion a traves del manager con la notificacion creada
    public void sendNotification(String quote){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(quote);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public int randomNumber(int min,int max){
        Random rand = new Random();

        // nextInt as provided by Random is exclusive of the top value so you need to add 1

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}