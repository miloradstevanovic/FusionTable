package it.ialweb.poi;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    public static Map<String, List<Pair<Long, String>>> data;
    public static String[] mapKeysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("IoT");
        getData(100);
    }

    private void setupGui() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public int getItemCount() {
                return data.size();
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText(mapKeysArray[position]);
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                return new ViewHolder(view) {
                };
            }
        });

        findViewById(R.id.fabBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.coordinator), "abcdefg", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void getData(int limitResults) {
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

        service.listSensorDataRecords(queryFixed, new Callback<SensorsDataList>() {

            @Override
            public void failure(RetrofitError arg0) {
                System.out.print("hej");
            }

            @Override
            public void success(SensorsDataList arg0, Response arg1) {
                data = arg0.build();
                Set<String> keys = data.keySet();
                mapKeysArray = keys.toArray(new String[keys.size()]);
                setupGui();
            }

        });

    }

    public static class PlaceHolder extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            RecyclerView recyclerView = new RecyclerView(getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @Override
                public int getItemCount() {
                    return 30;
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    ((TextView) holder.itemView).setText("Item " + position);
                }

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
                    LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                    View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                    return new ViewHolder(view) {
                    };
                }
            });
            return recyclerView;
        }
    }
}