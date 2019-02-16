package kimani.com.sisu.models.Assess;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class ComposedAssessmentQuestion {

    @Embedded
    public AssessmentQuestion assessmentQuestion;

    @Relation(parentColumn = "question_id", entityColumn = "id", entity = Question.class)
    public List<Question> questionList;

    public Question getQuestion(){
        if(questionList!=null && questionList.size()>0)
            return questionList.get(0);
        return null;
    }
}
