package it.ialweb.poi;

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
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Map<String, List<Pair<Long, String>>> data;
    public static String[] mapKeysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setupGui() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView_sensorList);
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