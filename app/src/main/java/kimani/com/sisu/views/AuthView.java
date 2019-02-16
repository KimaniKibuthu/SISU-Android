package kimani.com.sisu.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import kimani.com.sisu.repositories.AuthorizationRepository;
import kimani.com.sisu.utils.NetworkResponse;


public class AuthView extends AndroidViewModel {

    public AuthorizationRepository authorizationRepository;
    public MutableLiveData<NetworkResponse> monitor;


    public AuthView(@NonNull Application application) {
        super(application);
        authorizationRepository = new AuthorizationRepository(application);
        monitor = authorizationRepository.monitor;

    }


    public void attemptAuth(String username, String password) {
        authorizationRepository.attemptAuth(username,password);
    }

    public String getToken(){
        return authorizationRepository.getToken();
    }

}
