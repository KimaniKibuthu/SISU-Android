package kimani.com.sisu.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "authorization_table")
public class Authorization {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "token_type")
    @SerializedName("token_type")
    public String token_type;

    @ColumnInfo(name = "expires_in")
    @SerializedName("expires_in")
    public String expires_in;

    @ColumnInfo(name = "access_token")
    @SerializedName("access_token")
    public String access_token;


    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    public int location_id;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
