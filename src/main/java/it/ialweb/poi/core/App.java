package it.ialweb.poi.core;

import android.app.Application;

import it.ialweb.poi.core.data.SensorsDataContainer;

public class App extends Application {

    @Override
    public void onCreate() {
        SensorsDataContainer.getInstance(getApplicationContext());
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
