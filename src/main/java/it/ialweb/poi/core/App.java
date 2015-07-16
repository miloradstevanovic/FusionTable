package it.ialweb.poi.core;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import it.ialweb.poi.core.data.AlarmReceiver;
import it.ialweb.poi.core.data.SensorsDataContainer;

public class App extends Application {

    private final static int TIME = 2 * 60 * 1000;

    @Override
    public void onCreate() {
        SensorsDataContainer.getInstance();
        initAlarm();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        AlarmManager alarms = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        PendingIntent recurringDownload = getDownloadIntent();
        recurringDownload.cancel();
        super.onTerminate();
    }

    private void initAlarm() {
        Context c = getApplicationContext();
        Calendar updateTime = Calendar.getInstance();
        updateTime.set(Calendar.SECOND, 5);
        PendingIntent recurringDownload = getDownloadIntent();
        AlarmManager alarms = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        alarms.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), TIME, recurringDownload);
    }

    private PendingIntent getDownloadIntent() {
        Context c = getApplicationContext();
        Intent alarmIntent = new Intent(c, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(c, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return recurringDownload;
    }
}
