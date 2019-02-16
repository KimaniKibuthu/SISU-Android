package kimani.com.sisu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.R;
import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedAssessmentQuestion;
import kimani.com.sisu.models.Assess.Question;
import kimani.com.sisu.widgets.FontTextView;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionHolder> {

    private Context context;
    private List<ComposedAssessmentQuestion> questions;
    private QuestionsInterface questionsInterface;

    public QuestionsAdapter(Context context, List<ComposedAssessmentQuestion> questions,QuestionsInterface questionsInterface){
        this.context = context;
        this.questions = questions;
        this.questionsInterface = questionsInterface;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.questions_row, viewGroup, false);
        return new QuestionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder questionHolder, int i) {

        ComposedAssessmentQuestion c = questions.get(i);
        final AssessmentQuestion a = c.assessmentQuestion;
        questionHolder.number.setText(""+(1+i));
        questionHolder.question.setText(c.getQuestion().query);
        questionHolder.first_choice.setText(c.getQuestion().choice_one);
        questionHolder.second_choice.setText(c.getQuestion().choice_two);
        questionHolder.third_choice.setText(c.getQuestion().choice_three);

        //highlight the previous answer
        if(a.answer==0){
            questionHolder.first_choice.setChecked(false);
            questionHolder.second_choice.setChecked(false);
            questionHolder.third_choice.setChecked(false);
        }else if(a.answer==1){
            questionHolder.first_choice.setChecked(true);
            questionHolder.second_choice.setChecked(false);
            questionHolder.third_choice.setChecked(false);
        }else if(a.answer==2){
            questionHolder.first_choice.setChecked(false);
            questionHolder.second_choice.setChecked(true);
            questionHolder.third_choice.setChecked(false);
        }else if(a.answer==3){
            questionHolder.first_choice.setChecked(false);
            questionHolder.second_choice.setChecked(false);
            questionHolder.third_choice.setChecked(true);
        }


        questionHolder.first_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.answer=1;
                questionsInterface.saveAnswer(a);
            }
        });

        questionHolder.second_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.answer=2;
                questionsInterface.saveAnswer(a);
            }
        });

        questionHolder.third_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.answer=3;
                questionsInterface.saveAnswer(a);
            }
        });


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void updateData(List<ComposedAssessmentQuestion> questions) {
        this.questions = questions;
        this.notifyDataSetChanged();
    }


    public class QuestionHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.question) FontTextView question;
        @BindView(R.id.first_choice) RadioButton first_choice;
        @BindView(R.id.second_choice) RadioButton second_choice;
        @BindView(R.id.number) FontTextView number;
        @BindView(R.id.third_choice) RadioButton third_choice;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface QuestionsInterface{
        public void saveAnswer(AssessmentQuestion assessmentQuestion);
    }

}
