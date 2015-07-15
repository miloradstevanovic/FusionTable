package it.ialweb.poi.core.data;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TSAIM044 on 14/07/2015.
 */
public class SensorsDataList {
    private List<String> columns;
    private List<List<String>> rows;

    public List<List<String>> getRows() {
        return rows;
    }

    public Map<String, List<Pair<Long, String>>> build() {

        String location = "45.95315,12.682864";
        Map<String, List<Pair<Long, String>>> ret = new HashMap<>();

        int timeStampIndex = columns.indexOf("TimestampMillis");

        for (int i = rows.size() - 1; i >= 0; i--) {
            List<String> sensorRecord = rows.get(i);
            for (int j = 4; j < sensorRecord.size(); j++) {
                if (j == timeStampIndex) continue;
                String propertyValue = sensorRecord.get(j);
                if (!propertyValue.equals("NaN")) {
                    String propertyName = columns.get(j).toLowerCase();
                    if (propertyName.equals("TimestampMillis")) continue;

                    if (ret.get(propertyName) == null)
                        ret.put(propertyName, new ArrayList<Pair<Long, String>>());
                    ret.get(propertyName).add(new Pair<Long, String>(Double.valueOf(sensorRecord.get(timeStampIndex)).longValue(), propertyValue));
                }
            }
        }

        return ret;
    }

}
