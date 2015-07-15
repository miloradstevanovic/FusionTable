package it.ialweb.poi.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import it.ialweb.poi.R;
import it.ialweb.poi.core.data.SensorsDataContainer;

public class DataParser {

    private static DecimalFormat _df = new DecimalFormat("#.##");

    static {
        _df.setRoundingMode(RoundingMode.HALF_DOWN);
    }

    public static String formatRound2Digits(double d) {
        return _df.format(d);
    }

    public static String getAvgFormatted(String property) {

        SensorsDataContainer sdc = SensorsDataContainer.getInstance();

        return String.format("Average: %s (over %d records)",
                formatRound2Digits(sdc.getValue(property, SensorsDataContainer.DataValues.AVG)),
                sdc.valuesCount(property));
    }

    public static String getCurrentFormatted(String property) {

        SensorsDataContainer sdc = SensorsDataContainer.getInstance();

        return String.format("Current: %s",
                formatRound2Digits(sdc.getValue(property, SensorsDataContainer.DataValues.CURRENT)));
    }

    public static String getTitle(String property) {

        property = property.toLowerCase();

        switch (property) {
            case "ml": return "Precipitazioni";
            case "humidity": return "Umidità";
            case "temp": return "Temperatura";
        }

        if (property.length() < 2) return property;
        return String.valueOf(property.charAt(0)).toUpperCase() + property.substring(1);
    }

    public static Drawable getBackgroundForKey(Context context, String property) {

        property = property.toLowerCase();

        Drawable d = null;
        int resourceId = -1;

        switch (property) {
            case "temp": resourceId = R.drawable.fire; break;
            case "ml": resourceId = R.drawable.rain; break;
            case "humidity": resourceId = R.drawable.humidity; break;
            case "neve": resourceId = R.drawable.snow; break;
        }

        if (resourceId != -1) {
            d = context.getDrawable(resourceId);
            d.setAlpha(60);
        }

        return d;
    }
}
