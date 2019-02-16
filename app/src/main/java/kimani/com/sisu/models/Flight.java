package kimani.com.sisu.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "flights_table")
public class Flight {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "flight_number")
    @SerializedName("flight_number")
    public String flight_number;


    @ColumnInfo(name = "from")
    @SerializedName("from")
    public String from;

    @ColumnInfo(name = "destination")
    @SerializedName("destination")
    public String destination;

    @ColumnInfo(name = "craft_number")
    @SerializedName("craft_number")
    public String craft_number;

    @ColumnInfo(name = "captain_id")
    @SerializedName("captain_id")
    public int captain_id;


    @ColumnInfo(name = "first_officer")
    @SerializedName("first_officer")
    public int first_officer;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Ignore
    @SerializedName("captain")
    public User captain;

    @Ignore
    @SerializedName("officer")
    public User officer;

}
