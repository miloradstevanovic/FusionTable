package it.ialweb.poi;


import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by TSAIM044 on 14/07/2015.
 */
public interface FusionService {

    @GET("/?key=AIzaSyCRQ2xsLkUgWwbUwx0wA7ektLfRC1wFZPs")
    void listSensorDataRecords(@Query("sql") String query, Callback<List<SensorsDataList>> cb);

}
