package kimani.com.sisu;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.adapters.FlightsAdapter;
import kimani.com.sisu.models.ComposedFlight;
import kimani.com.sisu.models.Flight;
import kimani.com.sisu.utils.CoreUtils;
import kimani.com.sisu.utils.NetworkResponse;
import kimani.com.sisu.views.FlightsView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout swipe_to_refresh;
    @BindView(R.id.flights_recycler) RecyclerView flights_recycler;

    private FlightsAdapter flightsAdapter;
    private FlightsView flightsView;
    public static MutableLiveData<Boolean> logout = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        logout.setValue(false);
        logout.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean!=null && aBoolean){
                    SharedPreferences.Editor editor ;
                    editor = PreferenceManager.getDefaultSharedPreferences(getApplication()).edit();
                    editor.clear();
                    editor.commit();

                    CoreUtils.destroyRetrofit();

                    Intent t = new Intent(getApplicationContext(),AuthActivity.class);
                    t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(t);
                }
            }
        });

        flightsView = ViewModelProviders.of(this).get(FlightsView.class);

        flights_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        flightsAdapter = new FlightsAdapter(getApplicationContext(), new ArrayList<ComposedFlight>());
        flights_recycler.setAdapter(flightsAdapter);

        flightsView.loadFlightsOnline();

        flightsView.composed_flights.observe(this, new Observer<List<ComposedFlight>>() {
            @Override
            public void onChanged(@Nullable List<ComposedFlight> composedFlights) {
                flightsAdapter.updateData(composedFlights);
            }
        });

        swipe_to_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flightsView.loadFlightsOnline();
            }
        });

        flightsView.monitor.observe(this, new Observer<NetworkResponse>() {
            @Override
            public void onChanged(@Nullable NetworkResponse networkResponse) {
                if(networkResponse==null){
                    return;
                }
                if(networkResponse.is_loading){
                    swipe_to_refresh.setRefreshing(true);
                }else{
                    swipe_to_refresh.setRefreshing(false);
                }
                if(networkResponse.message!=null){
                    Snackbar.make(swipe_to_refresh, networkResponse.message , Snackbar.LENGTH_LONG).show();


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_action_refresh:
                flightsView.loadFlightsOnline();
                return true;
            case R.id.ic_action_logout:
                flightsView.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
