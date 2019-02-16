package kimani.com.sisu.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    public String email;

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    public String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
