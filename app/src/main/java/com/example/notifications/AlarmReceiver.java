package com.example.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private final String Channel_id = "personal notifications";
    static MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {

        mp = MediaPlayer.create(context,R.raw.alarm_beep);
        mp.start();

        Intent intents = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intents,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,Channel_id);
        builder.setSmallIcon(R.drawable.ic_alarm);
        builder.setContentTitle("Go to sleep");
        builder.setContentText("welcome igor");
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

    }
}
