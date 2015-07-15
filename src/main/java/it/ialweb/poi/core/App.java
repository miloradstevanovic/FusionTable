package it.ialweb.poi.core;

import android.app.Application;

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
