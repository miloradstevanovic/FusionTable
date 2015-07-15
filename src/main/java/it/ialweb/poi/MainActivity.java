package it.ialweb.poi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("IoT");
        getData();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);


		/*FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			private int[] titles = new int[] { R.string.Timeline, R.string.Users, R.string.MyProfile };

			@Override
			public int getCount() {
				return titles.length;
			}

			@Override
			public Fragment getItem(int position) {
				return new PlaceHolder();
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return getResources().getString(titles[position]);
			}
		};
		viewPager.setAdapter(adapter);

		tabLayout.setupWithViewPager(viewPager);

		findViewById(R.id.fabBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(findViewById(R.id.coordinator), "abcdefg", Snackbar.LENGTH_LONG).show();
			}
		});*/
    }

    public void getData() {
        RestAdapter retrofit = new RestAdapter.Builder().setEndpoint(
                "https://www.googleapis.com/fusiontables/v2"
        ).build();

        String query = null;

        try {
            query = URLEncoder.encode(
                    "SELECT * FROM 1vGFzsJsfHFVKhX5kjBB10eZfsXLiIz3RJY_D1sx2 ORDER BY TimestampMillis DESC LIMIT 1", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        FusionService service = retrofit.create(FusionService.class);

        service.listSensorDataRecords(query, new Callback<List<SensorsDataList>>() {

            @Override
            public void failure(RetrofitError arg0) {
                System.out.print("hej");
            }

            @Override
            public void success(List<SensorsDataList> arg0, Response arg1) {
                SensorDataRecord sdr = arg0.get(0).build();

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