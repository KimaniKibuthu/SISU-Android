package kimani.com.sisu.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.AssessActivity;
import kimani.com.sisu.R;
import kimani.com.sisu.models.ComposedFlight;
import kimani.com.sisu.models.Flight;
import kimani.com.sisu.widgets.FontTextView;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightHolder> {

    private Context context;
    private List<ComposedFlight> flights;

    public FlightsAdapter(Context context, List<ComposedFlight> flights){

        this.context = context;
        this.flights = flights;
    }

    @NonNull
    @Override
    public FlightHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.flights_row, viewGroup, false);
        return new FlightHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightHolder flightHolder, int i) {

        final ComposedFlight composedFlight = flights.get(i);

        flightHolder.from.setText(composedFlight.flight.from);
        flightHolder.to.setText(composedFlight.flight.destination);

        flightHolder.flight_number.setText(composedFlight.flight.flight_number);
        flightHolder.craft.setText(composedFlight.flight.craft_number);
        if(composedFlight.getCaptain()!=null)
            flightHolder.captain.setText(composedFlight.getCaptain().name);
        if(composedFlight.getFirstOfficer()!=null)
            flightHolder.first_officer.setText(composedFlight.getFirstOfficer().name);

        flightHolder.assess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(context, AssessActivity.class);
                t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                t.putExtra("flight",composedFlight.flight.getId());
                context.startActivity(t);
            }
        });

    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public void updateData(List<ComposedFlight> all) {
        this.flights = all;
        notifyDataSetChanged();
    }


    public class FlightHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.from) FontTextView from;
        @BindView(R.id.to) FontTextView to;
        @BindView(R.id.captain) FontTextView captain;
        @BindView(R.id.first_officer) FontTextView first_officer;
        @BindView(R.id.craft) FontTextView craft;
        @BindView(R.id.flight_number) FontTextView flight_number;
        @BindView(R.id.flight_time) FontTextView flight_time;
        @BindView(R.id.assess_btn) AppCompatButton assess_btn;

        public FlightHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
