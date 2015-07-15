package it.ialweb.poi;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import it.ialweb.poi.core.data.SensorsDataList;

/**
 * Created by TSAIM044 on 14/07/2015.
 */
public class SensorDataRecordTest {
    @Test
    public void testParsing() throws Exception {
        String jsonToParse = "{\n" +
                "  \"kind\": \"fusiontables#sqlresponse\",\n" +
                "  \"columns\": [\n" +
                "    \"Date\",\n" +
                "    \"Time\",\n" +
                "    \"Station\",\n" +
                "    \"Location\",\n" +
                "    \"Temp\",\n" +
                "    \"Humidity\",\n" +
                "    \"Neve\",\n" +
                "    \"GAS\",\n" +
                "    \"LUX\",\n" +
                "    \"ml\",\n" +
                "    \"TimestampMillis\"\n" +
                "  ],\n" +
                "  \"rows\": [\n" +
                "    [\n" +
                "      \"2007-01-01\",\n" +
                "      \"01:00:53+CET\",\n" +
                "      \"ID11\",\n" +
                "      \"45.95315,12.682864\",\n" +
                "      \"NaN\",\n" +
                "      \"1000\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\"\n" +
                "    ],\n" +
                "    [\n" +
                "      \"2007-01-01\",\n" +
                "      \"01:00:54+CET\",\n" +
                "      \"ID11\",\n" +
                "      \"45.95315,12.682864\",\n" +
                "      27.31,\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\",\n" +
                "      \"NaN\"\n" +
                "    ]\n" +
                "  ]\n" +
                "}";

        Gson parser = new Gson();

        SensorsDataList list = parser.fromJson(jsonToParse, SensorsDataList.class);
        SensorDataRecord currentValues = list.build();

        Assert.assertEquals(currentValues.temp, 27.31, 0.01);
        Assert.assertEquals(currentValues.humidity, 1000, 0.01);
        A.main(null);
    }
}

class A {

    public static void main(String[] args) {
        String a = "abcdefg";
        Assert.assertEquals(getInverse(a), "gfedcba");
    }

    public static String getInverse(String s) {
        if (s == null) return null;

        if (s.length() == 1) return s;

        return getInverse(s.substring(1, s.length())) + s.charAt(0);
    }
}