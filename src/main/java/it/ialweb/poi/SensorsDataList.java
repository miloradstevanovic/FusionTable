package it.ialweb.poi;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by TSAIM044 on 14/07/2015.
 */
public class SensorsDataList {
    private List<String> columns;
    private List<List<String>> rows;

    public List<List<String>> getRows() {
        return rows;
    }

    public SensorDataRecord build() {

        SensorDataRecord currentValues = new SensorDataRecord();
        String location = "45.95315,12.682864";

        for (int i = rows.size() - 1; i >= 0; i--) {
            List<String> sensorRecord = rows.get(i);
            for (int j = 4; j < sensorRecord.size(); j++) {
                String propertyValue = sensorRecord.get(j);
                if (!propertyValue.equals("NaN")) {
                    String fieldName = columns.get(j).toLowerCase();
                    try {
                        Field field = currentValues.getClass().getField(fieldName);

                        Object value = null;
                        switch (fieldName) {
                            case "temp":
                            case "humidity":
                            case "ml":
                                value = Double.parseDouble(propertyValue);
                                break;
                        }

                        field.set(currentValues, value);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        }

        currentValues.location = location;
        return currentValues;
    }

}
