package it.ialweb.poi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.ialweb.poi.R;
import it.ialweb.poi.activities.DetailActivity;
import it.ialweb.poi.core.data.SensorsDataContainer;
import it.ialweb.poi.utils.DataParser;

public class PropertiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;

    public PropertiesAdapter(Activity activity) {
        super();
        mActivity = activity;
    }

    @Override
    public int getItemCount() {
        return SensorsDataContainer.getInstance().getProperties().length;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SensorsDataContainer sdc = SensorsDataContainer.getInstance();

        String property = sdc.getProperties()[position];

        String title = DataParser.getTitle(property);

        SensorDataViewHolder vholder = (SensorDataViewHolder) holder;
        vholder.sensorTitle.setText(title);
        vholder.sensorAvgValue.setText(DataParser.getAvgFormatted(property));
        vholder.sensorCurrentValue.setText(DataParser.getCurrentFormatted(property));
        vholder.mPropertyName = property;
        vholder.sensordataContainer.setBackground(DataParser.getBackgroundForKey(mActivity, property));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        LayoutInflater layoutInflater = mActivity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sensor_item, parent, false);
        return new SensorDataViewHolder(view);
    }

    class SensorDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String mPropertyName;
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
            Intent startDetailActivityIntent = new Intent(mActivity, DetailActivity.class);
            startDetailActivityIntent.putExtra(DetailActivity.PROPERTY_NAME_TAG, mPropertyName);
            mActivity.startActivity(startDetailActivityIntent);
        }
    }
}
