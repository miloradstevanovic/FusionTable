package it.ialweb.poi;

import android.content.Intent;
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

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import it.ialweb.poi.core.SensorsDataContainer;

public class MainActivity extends AppCompatActivity {

    public static final String CATKEY = "CATKEY";

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_sensorList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SensorsDataContainer.getInstance().getBus().register(this);
    }

    @Override
    protected void onDestroy() {
        SensorsDataContainer.getInstance().getBus().unregister(this);
        super.onDestroy();
    }

    @Subscribe public void setupGui(SensorsDataContainer sdc) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRecyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public int getItemCount() {
                return SensorsDataContainer.getInstance().getProperties().length;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                String property = SensorsDataContainer.getInstance().getProperties()[position];

                SensorDataViewHolder vholder = (SensorDataViewHolder) holder;
                vholder.sensorTitle.setText(property);
                vholder.sensorAvgValue.setText("(avg)" + SensorsDataContainer.getInstance().getValue(property, SensorsDataContainer.DataValues.AVG));
                vholder.sensorCurrentValue.setText("current: " + SensorsDataContainer.getInstance().getValue(property, SensorsDataContainer.DataValues.CURRENT));
                //((SensorDataViewHolder)holder).sensordataContainer.setBackground(getBackgroundForKey(mapKeysArray[position]));
            }

            private Drawable getBackgroundForKey(String s) {
                switch (s) {
                    case "m":
                        break;
                }
                return null;
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

                @Override //bs781
                public void onClick(View v) {
                    String sensor = ((TextView) v.findViewById(R.id.txtView_sensorTitle)).getText().toString();



                    Intent startDetailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
                    startDetailActivityIntent.putExtra(CATKEY, SensorsDataContainer.getInstance().getProperties());
                    startActivity(startDetailActivityIntent);
                }
            }
        });
    }

}