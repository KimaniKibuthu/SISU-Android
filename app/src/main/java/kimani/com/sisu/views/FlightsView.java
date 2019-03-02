package kimani.com.sisu.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import kimani.com.sisu.models.ComposedFlight;
import kimani.com.sisu.repositories.AuthRepository;
import kimani.com.sisu.repositories.FlightsRepository;
import kimani.com.sisu.utils.NetworkResponse;

public class FlightsView extends AndroidViewModel {

    private FlightsRepository flightsRepository;
    private AuthRepository authRepository;
    public MutableLiveData<NetworkResponse> monitor;
    public LiveData<List<ComposedFlight>> composed_flights;

    public FlightsView(@NonNull Application application) {
        super(application);
        flightsRepository = new FlightsRepository(application);
        authRepository = new AuthRepository(application);
        monitor = flightsRepository.monitor;
        composed_flights = flightsRepository.getComposedFlights();
    }

    public void loadFlightsOnline(){
        flightsRepository.LoadFlightsOnline();
    }

    public void logout() {
        authRepository.logout();
    }
}
