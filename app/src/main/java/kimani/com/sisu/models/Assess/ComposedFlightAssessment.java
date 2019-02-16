package kimani.com.sisu.models.Assess;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class ComposedFlightAssessment {

    @Embedded
    public FlightAssessment flightAssessment;

    @Relation(parentColumn = "id", entityColumn = "flight_assessment_id", entity = AssessmentQuestion.class)
    public List<ComposedAssessmentQuestion> questions;

}
