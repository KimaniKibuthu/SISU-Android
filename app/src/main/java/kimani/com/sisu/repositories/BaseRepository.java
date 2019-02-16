package kimani.com.sisu.repositories;

import android.app.Application;
import android.preference.PreferenceManager;

public class BaseRepository {

    protected Application application;

    public String getToken(){
        return  PreferenceManager.getDefaultSharedPreferences(application).getString("ACCESS_TOKEN", null);
    }

    public Boolean isIntroduced(){
        if(PreferenceManager.getDefaultSharedPreferences(application).getString("INTRODUCED", null)==null){
            return false;
        }
        return true;
    }

    public String getAuthId(){
        return  PreferenceManager.getDefaultSharedPreferences(application).getString("AUTH_USER", null);
    }
}
