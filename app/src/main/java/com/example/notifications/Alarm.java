package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Alarm extends AppCompatActivity {

    private Button set, select, cancel;
    private TextView selectedTime;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        selectedTime = findViewById(R.id.selectedTime);
        set = findViewById(R.id.set);
        select = findViewById(R.id.select);
        cancel = findViewById(R.id.cancel);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+4:00"));

        String localTime = date.format(currentLocalTime);

        selectedTime.setText(localTime);

        createNotificationChannel();

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             setAlarm();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showTimePicker();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             cancleAlarm();
            }
        });

    }

    private void cancleAlarm() {
        Intent intent = new Intent(this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if(alarmManager==null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this,"Alarm canceled",Toast.LENGTH_SHORT).show();

    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

//        alarmManager.setExact(); this is used when you want to set at exat time
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);

        Toast.makeText(this,"Alarm Set successfully",Toast.LENGTH_SHORT).show();




    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarms";                 //channel name
            String description = "this is what is displayed "; // channel descriptions or message to be disiplayed
            int importance = NotificationManager.IMPORTANCE_HIGH;       //notifications importance

            NotificationChannel notificationChannel = new NotificationChannel("personal notifications", name, importance);
            notificationChannel.setDescription(description);

//            NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void showTimePicker(){

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select alarm time")
                .build();

        picker.show(getSupportFragmentManager(),"personal notifications");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getHour() > 12){
                    selectedTime.setText(String.format("%02d",(picker.getHour()-12)+" : "+String.format("%02d",picker.getMinute())+" PM"));
                }
                else{

                    selectedTime.setText(picker.getHour()+" : "+picker.getMinute()+" AM");
                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

            }
        });

    }

}