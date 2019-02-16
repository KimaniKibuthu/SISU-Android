package kimani.com.sisu.models.Assess;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "flight_assessment_table")
public class FlightAssessment {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "flight_id")
    @SerializedName("flight_id")
    public int flight_id;

    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    public int user_id;

    @Ignore
    @SerializedName("assessment_questions")
    public List<AssessmentQuestion> assessmentQuestions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
