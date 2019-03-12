package kimani.com.sisu.repositories;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;


import kimani.com.sisu.models.Authorization;
import kimani.com.sisu.services.AuthService;
import kimani.com.sisu.utils.CoreUtils;
import kimani.com.sisu.utils.NetworkResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AuthorizationRepository extends BaseRepository {


    public MutableLiveData<NetworkResponse> monitor;


    public AuthorizationRepository(Application application) {
        this.application = application;
        monitor = new MutableLiveData<>();
    }

    public void attemptAuth(String username, String password) {
        monitor.setValue(new NetworkResponse(true));
        Call<Authorization> call = CoreUtils.getRetrofitClient().create(AuthService.class).tryLogin(username, password);
        call.enqueue(new Callback<Authorization>() {
            @Override
            public void onResponse(Call<Authorization> call, Response<Authorization> response) {

                if (response.isSuccessful()) {
                    Authorization auth = response.body();
                    saveToken(auth.access_token);
                    monitor.postValue(new NetworkResponse(false, "Login successful", 200));
                } else {
                    monitor.postValue(new NetworkResponse(false, "Oops the credentials were incorrect", 0));

                }

            }

            @Override
            public void onFailure(Call<Authorization> call, Throwable t) {
                try {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", ((HttpException) t).code()));
                } catch (Exception e) {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", 0));
                }
            }
        });


    }

    public void saveToken(String token){
        Log.e("SAVE_TOKEN",token);
        PreferenceManager.getDefaultSharedPreferences(application).edit().putString("ACCESS_TOKEN", token).commit();
    }



}
