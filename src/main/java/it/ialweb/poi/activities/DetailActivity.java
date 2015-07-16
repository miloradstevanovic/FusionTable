package it.ialweb.poi.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.squareup.otto.Subscribe;

import it.ialweb.poi.R;
import it.ialweb.poi.core.data.SensorsDataContainer;
import it.ialweb.poi.utils.DataParser;

public class DetailActivity extends ActionBarActivity {

    public final static String PROPERTY_NAME_TAG = "propertynametag";

    private GraphView mGraphView;
    private String mPropertyName;
    private TextView mTvAvg, mTvCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mGraphView = (GraphView) findViewById(R.id.graph);
        mTvAvg = (TextView) findViewById(R.id.tvAvg);
        mTvCurrent = (TextView) findViewById(R.id.tvCurrent);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            mPropertyName = args.getString(PROPERTY_NAME_TAG);
            setTitle(DataParser.getTitle(mPropertyName));
            updateGui(null);
        }

        SensorsDataContainer.getInstance().getBus().register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PROPERTY_NAME_TAG, mPropertyName);
        super.onSaveInstanceState(outState);
    }

    @Subscribe
    public void updateGui(SensorsDataContainer sdc) {

        if (mPropertyName == null) return;
        if (sdc == null) sdc = SensorsDataContainer.getInstance();

        mGraphView.removeAllSeries();

        BaseSeries<DataPoint> series =  sdc.getSeries(mPropertyName);
        if (series != null) {
            Viewport v = mGraphView.getViewport();
            v.setXAxisBoundsManual(true);
            v.setMaxX(series.getHighestValueX());
            v.setYAxisBoundsManual(true);
            v.setMaxY(series.getHighestValueY() + 5);
            v.setMinY(series.getLowestValueY() - 5);
            mGraphView.addSeries(series);
        }

        mTvAvg.setText(DataParser.getAvgFormatted(mPropertyName));
        mTvCurrent.setText(DataParser.getCurrentFormatted(mPropertyName));
    }

    @Override
    protected void onDestroy() {
        SensorsDataContainer.getInstance().getBus().unregister(this);
        super.onDestroy();
    }
}
