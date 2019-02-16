package kimani.com.sisu.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.R;
import kimani.com.sisu.adapters.QuestionsAdapter;
import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedAssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedFlightAssessment;
import kimani.com.sisu.models.Assess.Question;
import kimani.com.sisu.models.ComposedFlight;
import kimani.com.sisu.views.AssessView;

public class QuestionsFragment extends Fragment implements QuestionsAdapter.QuestionsInterface {

    @BindView(R.id.questions_recycler) RecyclerView questions_recycler;
    private QuestionsAdapter questionsAdapter;
    private AssessView assessView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup order_view = (ViewGroup) inflater.inflate(
                R.layout.questions_fragment, container, false
        );

        ButterKnife.bind(this,order_view);
        return order_view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questions_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionsAdapter = new QuestionsAdapter(getActivity(),new ArrayList<ComposedAssessmentQuestion>(),this);
        questions_recycler.setAdapter(questionsAdapter);

        assessView = ViewModelProviders.of(this).get(AssessView.class);
        assessView.getFlightQuestions(getArguments().getInt("flight",0)).observe(this, new Observer<ComposedFlightAssessment>() {
            @Override
            public void onChanged(@Nullable ComposedFlightAssessment composedFlightAssessment) {
                if(composedFlightAssessment!=null)
                    questionsAdapter.updateData(filteredQuestions(getArguments().getInt("category",0),composedFlightAssessment));

            }
        });
    }


    public List<ComposedAssessmentQuestion> filteredQuestions(int category, ComposedFlightAssessment composedFlightAssessment){

        List<ComposedAssessmentQuestion> composedAssessmentQuestions = new ArrayList<>();
        for(ComposedAssessmentQuestion c:composedFlightAssessment.questions){

            if(c.getQuestion()!=null && c.getQuestion().category_id==category){
                composedAssessmentQuestions.add(c);
            }

        }

        return composedAssessmentQuestions;
    }


    @Override
    public void saveAnswer(AssessmentQuestion assessmentQuestion) {
        assessView.saveAnswer(assessmentQuestion);
    }
}
