package kimani.com.sisu.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedFlightAssessment;
import kimani.com.sisu.models.Assess.FlightAssessment;
import kimani.com.sisu.models.Assess.Question;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(FlightAssessment flightAssessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessmentQuestion(AssessmentQuestion assessmentQuestion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question question);

    @Query("SELECT * from flight_assessment_table WHERE flight_id =:flight")
    LiveData<ComposedFlightAssessment> getFlightQuestions(int flight);
}
