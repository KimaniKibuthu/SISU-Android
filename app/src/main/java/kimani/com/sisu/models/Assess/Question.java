package kimani.com.sisu.models.Assess;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "questions_table")
public class Question {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "query")
    @SerializedName("query")
    public String query;

    @ColumnInfo(name = "choice_one")
    @SerializedName("choice_one")
    public String choice_one;

    @ColumnInfo(name = "mitigation_one")
    @SerializedName("mitigation_one")
    public String mitigation_one;

    @ColumnInfo(name = "choice_two")
    @SerializedName("choice_two")
    public String choice_two;

    @ColumnInfo(name = "mitigation_two")
    @SerializedName("mitigation_two")
    public String mitigation_two;

    @ColumnInfo(name = "choice_three")
    @SerializedName("choice_three")
    public String choice_three;

    @ColumnInfo(name = "mitigation_three")
    @SerializedName("mitigation_three")
    public String mitigation_three;

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    public int category_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
