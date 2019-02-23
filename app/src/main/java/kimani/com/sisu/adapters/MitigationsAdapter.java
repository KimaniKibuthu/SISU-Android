package kimani.com.sisu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.R;
import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedAssessmentQuestion;
import kimani.com.sisu.widgets.FontTextView;

public class MitigationsAdapter extends RecyclerView.Adapter<MitigationsAdapter.MitigationHolder> {

    private Context context;
    private List<ComposedAssessmentQuestion> composedAssessmentQuestions;

    public MitigationsAdapter(Context context, List<ComposedAssessmentQuestion> composedAssessmentQuestions){

        this.context = context;
        this.composedAssessmentQuestions = composedAssessmentQuestions;
    }

    @NonNull
    @Override
    public MitigationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.mitigations_row, viewGroup, false);
        return new MitigationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MitigationHolder mitigationHolder, int i) {
        ComposedAssessmentQuestion c = composedAssessmentQuestions.get(i);
        final AssessmentQuestion a = c.assessmentQuestion;

        mitigationHolder.number.setText(""+(i+1));
        mitigationHolder.question.setText(c.getQuestion().query);
        if(a.answer==0){
        }else if(a.answer==1){
            mitigationHolder.answer.setText(c.getQuestion().choice_one);
            mitigationHolder.mitigation.setText(c.getQuestion().mitigation_one);
        }else if(a.answer==2){
            mitigationHolder.answer.setText(c.getQuestion().choice_two);
            mitigationHolder.mitigation.setText(c.getQuestion().mitigation_two);
        }else if(a.answer==3){
            mitigationHolder.answer.setText(c.getQuestion().choice_three);
            mitigationHolder.mitigation.setText(c.getQuestion().mitigation_three);
        }

    }

    public void updateData(List<ComposedAssessmentQuestion> composedAssessmentQuestions){
        this.composedAssessmentQuestions = composedAssessmentQuestions;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return composedAssessmentQuestions.size();
    }

    public class MitigationHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.number) FontTextView number;
        @BindView(R.id.question) FontTextView question;
        @BindView(R.id.answer) FontTextView answer;
        @BindView(R.id.mitigation) FontTextView mitigation;

        public MitigationHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
