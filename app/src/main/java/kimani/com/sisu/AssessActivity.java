package kimani.com.sisu;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kimani.com.sisu.adapters.QuestionsPagerAdapter;
import kimani.com.sisu.fragments.QuestionsFragment;
import kimani.com.sisu.models.Assess.ComposedAssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedFlightAssessment;
import kimani.com.sisu.models.Category;
import kimani.com.sisu.utils.NetworkResponse;
import kimani.com.sisu.views.AssessView;

public class AssessActivity extends AppCompatActivity {

    ActionBar actionBar;
    private QuestionsPagerAdapter questionsPagerAdapter;
    private int flight = 0;
    private AssessView assessView;
    private ComposedFlightAssessment assessment;


    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.sliding_tabs) TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);

        flight = getIntent().getIntExtra("flight",0);

        assessView = ViewModelProviders.of(this).get(AssessView.class);

        ButterKnife.bind(this);

        actionBar = getSupportActionBar();
        questionsPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager());
        tabs.setupWithViewPager(viewPager);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        assessView.loadCategoriesOnline();
        viewPager.setAdapter(questionsPagerAdapter);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading,please wait...");


        assessView.monitor.observe(this, new Observer<NetworkResponse>() {
            @Override
            public void onChanged(@Nullable NetworkResponse networkResponse) {
                if(networkResponse!=null){
                    if(networkResponse.is_loading){
                        dialog.show();
                    }else{
                        dialog.dismiss();
                    }
                    if(networkResponse.message!=null){
                        Snackbar.make(viewPager, networkResponse.message , Snackbar.LENGTH_LONG).show();


                    }
                }
            }
        });



        assessView.categories.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {


                questionsPagerAdapter.clear();

                for(Category c: categories){
                    QuestionsFragment f = new QuestionsFragment();
                    Bundle b = new Bundle();
                    b.putInt("category",c.getId());b.putInt("flight",flight);
                    f.setArguments(b);
                    questionsPagerAdapter.addFragment(f,c.name);
                }
                questionsPagerAdapter.refresh();
                if(categories.size()>0){
                    observeLocalData();
                }
            }
        });

    }

    public void observeLocalData(){
        assessView.getFlightQuestions(flight).observe(this, new Observer<ComposedFlightAssessment>() {
            @Override
            public void onChanged(@Nullable ComposedFlightAssessment composedFlightAssessment) {
                if(composedFlightAssessment==null){
                    assessView.loadFlightReportOnline(flight);
                }else{
                    assessment = composedFlightAssessment;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assess_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.ic_action_upload:
                uploadInformation();
                return true;
            case R.id.ic_action_refresh:
                assessView.loadFlightReportOnline(flight);
                return true;
            case R.id.ic_action_results:
                Intent go_to_results = new Intent(getApplicationContext(),ResultsActivity.class);
                go_to_results.putExtra("flight",flight);
                startActivity(go_to_results);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadInformation() {
        /*
        Strip the questions to json array
         */
        JSONArray jsonArray = new JSONArray();
        try{
            for(ComposedAssessmentQuestion c:assessment.questions){
                jsonArray.put(c.assessmentQuestion.getJsonObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assessView.uploadAnswers(jsonArray);

    }


}
