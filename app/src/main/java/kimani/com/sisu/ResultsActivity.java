package kimani.com.sisu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.adapters.MitigationsAdapter;
import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedAssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedFlightAssessment;
import kimani.com.sisu.views.AssessView;

public class ResultsActivity extends AppCompatActivity {

    ActionBar actionBar;

    private AssessView assessView;
    private int counts[] = {0,0,0,0};
    private List<ComposedAssessmentQuestion> accepted = new ArrayList<>();
    private List<ComposedAssessmentQuestion> risky = new ArrayList<>();
    private List<ComposedAssessmentQuestion> danger = new ArrayList<>();
    private int total = 0;
    private MitigationsAdapter mitigationsAdapter;
    @BindView(R.id.chart1) PieChart chart;
    @BindView(R.id.mitigations_recycler) RecyclerView mitigations_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ButterKnife.bind(this);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mitigations_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mitigationsAdapter = new MitigationsAdapter(getApplicationContext(),new ArrayList<ComposedAssessmentQuestion>());
        mitigations_recycler.setAdapter(mitigationsAdapter);

        chart.setCenterText("Analysis");
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(counts[0]==e.getY()){
                    updateList(0);
                }
                if(counts[1]*2==e.getY()){
                    updateList(1);
                }
                if(counts[2]*3==e.getY()){
                    updateList(2);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        assessView = ViewModelProviders.of(this).get(AssessView.class);

        assessView.getFlightQuestions(getIntent().getIntExtra("flight",0)).observe(this, new Observer<ComposedFlightAssessment>() {
            @Override
            public void onChanged(@Nullable ComposedFlightAssessment composedFlightAssessment) {


                if(composedFlightAssessment!=null){
                    total = 0;
                    accepted = new ArrayList<>();
                    risky = new ArrayList<>();
                    danger = new ArrayList<>();

                    for (ComposedAssessmentQuestion q:composedFlightAssessment.questions){
                        if(q.assessmentQuestion.answer==1){
                            counts[0]+=1;
                            accepted.add(q);
                        }else if(q.assessmentQuestion.answer==2){
                            counts[1]+=1;
                            risky.add(q);
                        }else if(q.assessmentQuestion.answer==3){
                            counts[2]+=1;
                            danger.add(q);
                        }else{
                            counts[3]+=1;
                        }
                        total++;
                    }

                    setData();
                    updateList(2);
                    chart.getDescription().setText("Unanswered questions: "+counts[3]);

                }

            }
        });
    }

    private void updateList(int i) {
        switch (i){
            case 0:
                mitigationsAdapter.updateData(accepted);
                break;
            case 1:
                mitigationsAdapter.updateData(risky);
                break;
            case 2:
                mitigationsAdapter.updateData(danger);
                break;
        }
    }


    public void setData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(counts[0],"Acceptable"));
        entries.add(new PieEntry(counts[1]*2,"Caution"));
        entries.add(new PieEntry(counts[2]*3,"High risk"));
        PieDataSet dataSet = new PieDataSet(entries, "Assessment Results");
        dataSet.setSliceSpace(3f);
        dataSet.setDrawIcons(false);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.parseColor("#FFD700"));
        colors.add(Color.RED);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        chart.highlightValues(null);

        chart.invalidate();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
