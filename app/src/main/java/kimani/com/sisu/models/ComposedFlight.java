package kimani.com.sisu.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ComposedFlight {
    @Embedded
    public Flight flight;

    @Relation(parentColumn = "captain_id", entityColumn = "id", entity = User.class)
    public List<User> captains;

    @Relation(parentColumn = "first_officer", entityColumn = "id", entity = User.class)
    public List<User> firstOfficers;

    public User getCaptain(){
        if(captains!=null && captains.size()>0)
            return captains.get(0);
        return null;
    }

    public User getFirstOfficer(){
        if(firstOfficers!=null && firstOfficers.size()>0)
            return firstOfficers.get(0);
        return null;
    }

    public JSONObject getJsonObject() throws JSONException {
        Gson gson=new Gson();
        return new JSONObject(gson.toJson(this));
    }
}
