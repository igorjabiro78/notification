package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button display,al;
    private final String Channel_id = "personal notifications";
    private final int notification_id = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display =findViewById(R.id.display);
        al = findViewById(R.id.alarms);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createNotificationChannel();
                displayNotification(v);
            }
        });

      al.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),Alarm.class));
          }
      });

    }
    //notification for android version 8.0 and below
    public void displayNotification(View view){
        //notification for android version 8.0 and below


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,Channel_id);
        builder.setSmallIcon(R.drawable.ic_sms_24);
        builder.setContentTitle("Simple notification");
        builder.setContentText("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notification_id,builder.build());

    }
    //for android version 8.0 and above we must create a notification channel

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "Personal Notifications";                 //channel name
            String description = "Include all the personal notifications"; // channel descriptions or message to be disiplayed
            int importance = NotificationManager.IMPORTANCE_DEFAULT;       //notifications importance

            NotificationChannel notificationChannel = new NotificationChannel(Channel_id,name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}