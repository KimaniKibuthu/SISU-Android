package kimani.com.sisu.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.os.AsyncTask;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import kimani.com.sisu.dao.AssessmentDao;
import kimani.com.sisu.dao.CategoriesDao;
import kimani.com.sisu.dao.FlightsDao;
import kimani.com.sisu.dao.UsersDao;
import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedFlightAssessment;
import kimani.com.sisu.models.Assess.FlightAssessment;
import kimani.com.sisu.models.Category;
import kimani.com.sisu.models.ComposedFlight;
import kimani.com.sisu.models.Flight;
import kimani.com.sisu.services.FlightsService;
import kimani.com.sisu.utils.CoreUtils;
import kimani.com.sisu.utils.NetworkResponse;
import kimani.com.sisu.utils.SisuDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class FlightsRepository extends BaseRepository{

    public MutableLiveData<NetworkResponse> monitor;
    private UsersDao usersDao;
    private FlightsDao flightsDao;
    private AssessmentDao assessmentDao;
    private CategoriesDao categoriesDao;
    public LiveData<List<Category>> categories;


    public FlightsRepository(Application application){

        SisuDatabase db = SisuDatabase.getDatabase(application);
        this.application = application;

        usersDao = db.usersDao();
        flightsDao = db.flightsDao();
        monitor = new MutableLiveData<>();
        assessmentDao = db.assessmentDao();
        categoriesDao = db.categoriesDao();
        categories = categoriesDao.getAllCategories();
    }

    public void LoadFlightsOnline(){
        monitor.setValue(new NetworkResponse(true));
        Call<List<Flight>> call = CoreUtils.getAuthRetrofitClient(getToken()).create(FlightsService.class).getFlights();
        call.enqueue(new Callback<List<Flight>>() {
            @Override
            public void onResponse(Call<List<Flight>> call, Response<List<Flight>> response) {
                if(response.isSuccessful()){

                    for(Flight f:response.body()){
                        if(f.captain!=null)
                            usersDao.insert(f.captain);
                        if(f.officer!=null){
                            usersDao.insert(f.officer);
                        }

                        flightsDao.insert(f);
                    }

                    monitor.postValue(new NetworkResponse(false, "Flights data loaded successfully", 200));

                }else{
                    monitor.postValue(new NetworkResponse(false, "Oops check your internet connection", 0));

                }
            }

            @Override
            public void onFailure(Call<List<Flight>> call, Throwable t) {
                try {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", ((HttpException) t).code()));
                } catch (Exception e) {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", 0));
                }
            }
        });

    }


    public LiveData<List<ComposedFlight>> getComposedFlights() {
        return flightsDao.getAllFlights();
    }


    public void loadFlightReportOnline(int flight_id){
        monitor.setValue(new NetworkResponse(true));
        Call<FlightAssessment> call = CoreUtils.getAuthRetrofitClient(getToken()).create(FlightsService.class).getFlightsReport(flight_id);
        call.enqueue(new Callback<FlightAssessment>() {
            @Override
            public void onResponse(Call<FlightAssessment> call, Response<FlightAssessment> response) {

                if(response.isSuccessful()){

                    List<AssessmentQuestion> questions = response.body().assessmentQuestions;
                    for(AssessmentQuestion a:questions){

                        if(a.question!=null){
                            assessmentDao.insertQuestion(a.question);
                            assessmentDao.insertAssessmentQuestion(a);
                        }
                    }
                    assessmentDao.insertAssessment(response.body());
                    monitor.postValue(new NetworkResponse(false, "Assessment report synchronised", 200));

                }else{
                    monitor.postValue(new NetworkResponse(false, "Oops check your internet connection", 0));

                }

            }

            @Override
            public void onFailure(Call<FlightAssessment> call, Throwable t) {
                try {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", ((HttpException) t).code()));
                } catch (Exception e) {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", 0));
                }
            }
        });
    }

    public LiveData<ComposedFlightAssessment> getFlightQuestions(int flight) {

        return assessmentDao.getFlightQuestions(flight);
    }


    public void loadCategoriesOnline(){
        monitor.setValue(new NetworkResponse(true));
        Call<List<Category>> call = CoreUtils.getAuthRetrofitClient(getToken()).create(FlightsService.class).getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    for(Category c:response.body()){
                        categoriesDao.insert(c);
                    }
                    monitor.postValue(new NetworkResponse(false, "Categories synchronised", 200));

                }else{
                    monitor.postValue(new NetworkResponse(false, "Oops check your internet connection", 0));
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                try {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", ((HttpException) t).code()));
                } catch (Exception e) {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", 0));
                }
            }
        });

    }

    public void saveAnswer(AssessmentQuestion assessmentQuestion) {
        new SaveAnserAsync(assessmentDao).execute(assessmentQuestion);
    }

    public void uploadAnswers(JSONArray jsonArray) {
        monitor.setValue(new NetworkResponse(true));
        Call<JSONObject> call = CoreUtils.getAuthRetrofitClient(getToken()).create(FlightsService.class).uploadAnswers(jsonArray);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if(response.isSuccessful()){
                    monitor.postValue(new NetworkResponse(false, "Report submitted successful", 200));
                }else{
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", 0));
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                try {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", ((HttpException) t).code()));
                } catch (Exception e) {
                    monitor.postValue(new NetworkResponse(false, "An error was encountered", 0));
                }
            }
        });
    }

    public static class SaveAnserAsync extends AsyncTask<AssessmentQuestion,Void,Void>{

        private AssessmentDao assessmentDaoc;

        public SaveAnserAsync(AssessmentDao assessmentDaoc){
            this.assessmentDaoc = assessmentDaoc;
        }

        @Override
        protected Void doInBackground(AssessmentQuestion... assessmentQuestions) {
            this.assessmentDaoc.insertAssessmentQuestion(assessmentQuestions[0]);
            return null;
        }
    }


}
