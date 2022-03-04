package com.example.zest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ReminderBroadcast extends BroadcastReceiver{

    private DatabaseReference rootDatabase;
    @Override
    public void onReceive(Context context, Intent intent) {
        //leer database
        rootDatabase = FirebaseDatabase.getInstance("https://zest-a7919-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("QuotesES");
        //entrar database
        rootDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //leemos quote
                    String data = dataSnapshot.child(String.valueOf(randomNumber(1,4))).getValue().toString();
                    //creamos la notificacion
                    NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, "primary_notification_channel")
                            .setContentTitle("Motivación diaria de Zest")
                            .setContentText(data)
                            .setSmallIcon(R.drawable.zest);

                    //enviamos la notificacion
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(0, notifyBuilder.build());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public int randomNumber(int min,int max){
        Random rand = new Random();

        // nextInt as provided by Random is exclusive of the top value so you need to add 1

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
