package kimani.com.sisu.models.Assess;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "assessment_questions_table")
public class AssessmentQuestion {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "flight_assessment_id")
    @SerializedName("flight_assessment_id")
    public int flight_assessment_id;

    @ColumnInfo(name = "question_id")
    @SerializedName("question_id")
    public int question_id;

    @ColumnInfo(name = "answer")
    @SerializedName("answer")
    public int answer;

    @Ignore
    @SerializedName("question")
    public Question question;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getJsonObject() throws JSONException {
        Gson gson=new Gson();
        return new JSONObject(gson.toJson(this));
    }
}
