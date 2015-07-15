package it.ialweb.poi;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class SensorDataRecord {

    public Date date;
    public Time time;
    public double temp, humidity, ml;
    public String station, location, neve, gas, lux;

    public String getStation() {
        return station;
    }
}
