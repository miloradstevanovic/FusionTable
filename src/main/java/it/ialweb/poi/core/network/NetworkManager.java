package it.ialweb.poi.core.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import it.ialweb.poi.core.data.SensorsDataList;
import retrofit.Callback;
import retrofit.RestAdapter;

public class NetworkManager {

    public final static NetworkManager INSTANCE = new NetworkManager();

    private NetworkManager() {}

    public void getData(int limitResults, Callback<SensorsDataList> callback) {
        RestAdapter retrofit = new RestAdapter.Builder().setEndpoint(
                "https://www.googleapis.com/fusiontables/v2"
        ).build();

        String query = null;

        try {
            query = URLEncoder.encode(
                    "SELECT * FROM 1vGFzsJsfHFVKhX5kjBB10eZfsXLiIz3RJY_D1sx2 ORDER BY TimestampMillis DESC LIMIT " + limitResults, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String queryFixed = query.replaceAll("\\+", "%20");
        FusionService service = retrofit.create(FusionService.class);

        service.listSensorDataRecords(queryFixed, callback);
    }
}
