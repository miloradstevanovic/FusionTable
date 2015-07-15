package it.ialweb.poi;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Map<String, List<Pair<Long, String>>> data;
    public static String[] mapKeysArray;

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_sensorList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupGui() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRecyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public int getItemCount() {
                return data.size();
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                ((SensorDataViewHolder) holder).sensorTitle.setText(getTitleForKey(mapKeysArray[position]));
                ((SensorDataViewHolder) holder).sensorAvgValue.setText("(avg)" + findAvgForKey(mapKeysArray[position]));
                ((SensorDataViewHolder) holder).sensorCurrentValue.setText("current: " + getLastValueForKey(mapKeysArray[position]));
                //((SensorDataViewHolder)holder).sensordataContainer.setBackground(getBackgroundForKey(mapKeysArray[position]));
            }

            private String getTitleForKey(String s) {
                String ret = "";
                switch (s) {
                    case "humidity" :
                        ret = "Humidity";
                        break;
                    case "ml" :
                        ret = "Rain";
                        break;
                    case "neve" :
                        ret = "Neve";
                        break;
                    case "temp" :
                        ret = "Temp";
                        break;
                }
                return ret;
            }

            private Drawable getBackgroundForKey(String s) {
                switch (s) {
                    case "m":
                        break;
                }
                return null;
            }

            private String getLastValueForKey(String s) {
                List<Pair<Long, String>> elements = data.get(s);
                return elements.get(elements.size() - 1).second;
            }

            private String findAvgForKey(String s) {
                List<Pair<Long, String>> elements = data.get(s);
                double sum = 0;
                for (Pair<Long, String> value : elements) {
                    double val = 0;
                    try {
                        val = Double.parseDouble(value.second);
                    } catch (Exception e) {

                    } finally {
                        sum += val;
                    }
                }
                DecimalFormat df = new DecimalFormat("#.##");
                return Double.valueOf(df.format(sum / elements.size())) + "";

            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.sensor_item, parent, false);
                return new SensorDataViewHolder(view);
            }

            class SensorDataViewHolder extends ViewHolder implements View.OnClickListener {

                TextView sensorTitle;
                TextView sensorAvgValue;
                TextView sensorCurrentValue;
                LinearLayout sensordataContainer;

                public SensorDataViewHolder(View itemView) {
                    super(itemView);
                    sensorTitle = (TextView) itemView.findViewById(R.id.txtView_sensorTitle);
                    sensorAvgValue = (TextView) itemView.findViewById(R.id.txtView_sensorAvgValue);
                    sensorCurrentValue = (TextView) itemView.findViewById(R.id.txtView_sensorCurrentValue);
                    sensordataContainer = (LinearLayout) itemView.findViewById(R.id.linearLayout_sensorContainer);
                    itemView.setOnClickListener(this);
                }

                @Override
                public void onClick(View v) {
                    String sensor = ((TextView)v.findViewById(R.id.txtView_sensorTitle)).getText().toString();
                    switch (sensor) {
                        case "Humidity" :
                            break;
                        case "Rain" :
                            break;
                        case "Neve" :
                            break;
                        case "Temp" :
                            break;
                    }
                }
            }
        });
    }

}