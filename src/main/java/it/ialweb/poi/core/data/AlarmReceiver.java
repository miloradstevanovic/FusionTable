package it.ialweb.poi.core.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "AlarmReceiver onReceive");
        SensorsDataContainer sdc = SensorsDataContainer.getInstance();
        if (sdc != null) sdc.refreshData();
    }
}
