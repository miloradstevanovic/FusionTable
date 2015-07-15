package it.ialweb.poi.core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.util.Log;

import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.otto.Bus;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.ialweb.poi.SensorsDataList;
import it.ialweb.poi.core.network.NetworkManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SensorsDataContainer {

    private final static String TAG = "SensorsDataContainer";

    private final static int TIME = 2 * 60 * 1000;

    private static SensorsDataContainer _instance;

    private Map<String, Pair<Double, Double>> _avgValuesMap;
    private Map<String, List<Pair<Long, String>>> _data;
    private Map<String, BaseSeries<DataPoint>> _series;
    private Context _context;
    private Bus _eventBus;

    public static SensorsDataContainer getInstance() {
        return _instance;
    }

    public static SensorsDataContainer getInstance(Context context) {
        if (_instance == null) _instance = new SensorsDataContainer(context);
        return _instance;
    }

    private SensorsDataContainer(Context context) {
        _context = context;
        _eventBus = new Bus();

        _data = new HashMap<>();
        _avgValuesMap = new HashMap<>();
        _series = new HashMap<>();

        Log.d(TAG, "SensorsDataContainer created");
        initAlarm();
        refreshData();
    }

    private void initAlarm() {
        Calendar updateTime = Calendar.getInstance();
        updateTime.set(Calendar.SECOND, 5);
        Intent alarmIntent = new Intent(_context, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(_context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
        alarms.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), TIME, recurringDownload);
    }

    private void createGraphSeries(SensorsDataList sensorsDataList) {
        Log.i(TAG, "createGraphSeries");

        _series.clear();
        _avgValuesMap.clear();
        _data = sensorsDataList.build();
        for (Map.Entry<String, List<Pair<Long, String>>> valuesPerProp : _data.entrySet()) {

            String property = valuesPerProp.getKey();
            List<Pair<Long, String>> values = valuesPerProp.getValue();

            int count = 0;
            double avg = 0, current = 0;
            DataPoint[] dpoints = new DataPoint[values.size()];

            for (Pair<Long, String> pair : values) {
                try {
                    current = Long.parseLong(pair.second);
                    avg += current;
                    dpoints[count++] = new DataPoint(pair.first, current);
                } catch (NumberFormatException nFEx) { /* yolo */ }
            }

            avg /= count;
            _avgValuesMap.put(property, new Pair<Double, Double>(current, avg));
            _series.put(property, new LineGraphSeries<DataPoint>(dpoints));
        }

        _eventBus.post(this);
    }

    public Bus getBus() {
        return _eventBus;
    }

    public void refreshData() {
        Log.d(TAG, "refreshData called");
        NetworkManager.INSTANCE.getData(150, new Callback<SensorsDataList>() {
            @Override
            public void success(SensorsDataList sensorsDataList, Response response) {
                createGraphSeries(sensorsDataList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(TAG, "refreshData error: " + error);
            }
        });
    }

    public BaseSeries<DataPoint> getSerie(String property) {
        return _series.get(property);
    }

    public String[] getProperties() {
        return _avgValuesMap.keySet().toArray(new String[_avgValuesMap.size()]);
    }

    public double getValue(String property, int dataValue) {

        Pair<Double, Double> pair = _avgValuesMap.get(property);
        if (pair == null) return -1;

        double value = -1;
        switch (dataValue) {
            case DataValues.CURRENT:
                value = pair.first;
                break;

            case DataValues.AVG:
                value = pair.second;
                break;
        }

        return value;
    }

    public static class DataValues {
        public final static int CURRENT = 10;
        public final static int AVG = 11;
    }
}
