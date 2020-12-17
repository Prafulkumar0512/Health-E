package com.example.loginpge.Alarmmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.loginpge.R;
import com.example.loginpge.Todopge;

public class AlarmReciever1 extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Vibrator vibrate= (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrate.vibrate(500);
        int notificationid=intent.getIntExtra("notificationid",0);
        String title=intent.getStringExtra("Title");
        Intent i = new Intent(getApplicationContext(), com.example.loginpge.Alarmmanager.Ringtone.class);
        getApplicationContext().startService(i);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i);
        } else {
            context.startService(i);
        }*/


        Intent mainintent=new Intent(getApplicationContext(), Todopge.class);
        PendingIntent pintent1=PendingIntent.getActivity(getApplicationContext(),0,mainintent,0);
        NotificationManager notificationManager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),"Health-e");
        builder.setSmallIcon(R.drawable.log1)
                .setContentTitle(title)
                .setContentText("It's time for your " + title)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pintent1)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setOnlyAlertOnce(true);


        Intent dismissIntent = new Intent(getApplicationContext(), com.example.loginpge.Alarmmanager.Ringtone.class);
        dismissIntent.setAction(com.example.loginpge.Alarmmanager.Ringtone.ACTION_DISMISS);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                123, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action
                (android.R.drawable.ic_lock_idle_alarm, "DISMISS", pendingIntent);
        builder.addAction(action);

        notificationManager.notify(123,builder.build());
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent mainintent=new Intent(getApplicationContext(), Todopge.class);
        PendingIntent pintent1=PendingIntent.getActivity(getApplicationContext(),0,mainintent,0);
        super.onTaskRemoved(rootIntent);
    }
}
