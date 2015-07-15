package it.ialweb.poi.core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.GregorianCalendar;

public class SensorsDataContainer {

    private static SensorsDataContainer _instance;
    private Context _context;

    public static SensorsDataContainer getInstance(Context context) {
        if (_instance == null) _instance = new SensorsDataContainer(context);
        return _instance;
    }

    private SensorsDataContainer(Context context) {
        _context = context;
        setAlarm();
    }

    private void setAlarm() {
        Long time = new GregorianCalendar().getTimeInMillis() + 2*60*1000;
        Intent intentAlarm = new Intent(_context, AlarmReceiver.class);

        // Get the Alarm Service.
        AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC,
                time, PendingIntent.getBroadcast(_context, 1,
                        intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    private class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO
            setAlarm();
        }
    }
}
