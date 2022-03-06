package com.example.zest;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;
    Button createNotification;
    private NotificationManager mNotifyManager;
    private AlarmManager alarmMgr;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        //hooks
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotification = findViewById(R.id.notify);
        timePicker = findViewById(R.id.selected_time);

        //get the selected time
        Integer hour = timePicker.getCurrentHour();
        Integer minute = timePicker.getCurrentMinute();



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                createNotification.setOnClickListener(v -> {
                    //llamamos al intent de la notificacion
                    Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0, intent,0);
                    alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    // Set the alarm to start at time X
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    //se repite cada dia
                    alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            1000 *60 *60 * 24, pendingIntent);
                    Toast.makeText(getApplicationContext(),"Notificación creada correctamente", Toast.LENGTH_LONG).show();
                });
            }
        });
        


    }

    //Creacion del canal para las notis
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






}