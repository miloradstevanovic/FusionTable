package it.ialweb.poi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.squareup.otto.Subscribe;

import it.ialweb.poi.R;
import it.ialweb.poi.adapters.PropertiesAdapter;
import it.ialweb.poi.core.data.SensorsDataContainer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private PropertiesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_sensorList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PropertiesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        SensorsDataContainer.getInstance().getBus().register(this);
    }

    @Override
    protected void onDestroy() {
        SensorsDataContainer.getInstance().getBus().unregister(this);
        super.onDestroy();
    }

    @Subscribe public void updateGui(SensorsDataContainer sdc) {
        Log.d(TAG, "mAdapter.notifyDataSetChanged()");
        mAdapter.notifyDataSetChanged();
    }
}