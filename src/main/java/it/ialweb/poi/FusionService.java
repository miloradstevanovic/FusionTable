package it.ialweb.poi;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FusionService {

    @GET("/query?key=AIzaSyCRQ2xsLkUgWwbUwx0wA7ektLfRC1wFZPs")
    void listSensorDataRecords(@Query(value = "sql", encodeValue = false) String query, Callback<SensorsDataList> cb);

}
