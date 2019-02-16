package kimani.com.sisu.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.json.JSONArray;

import java.util.List;

import kimani.com.sisu.models.Assess.AssessmentQuestion;
import kimani.com.sisu.models.Assess.ComposedFlightAssessment;
import kimani.com.sisu.models.Assess.Question;
import kimani.com.sisu.models.Category;
import kimani.com.sisu.repositories.FlightsRepository;
import kimani.com.sisu.utils.NetworkResponse;

public class AssessView extends AndroidViewModel {

    private FlightsRepository flightsRepository;
    public LiveData<List<Category>> categories;
    public MutableLiveData<NetworkResponse> monitor;

    public AssessView(@NonNull Application application) {
        super(application);
        flightsRepository = new FlightsRepository(application);
        categories = flightsRepository.categories;
        monitor = flightsRepository.monitor;
    }

    public LiveData<List<Question>> getQuestionsForFlight(int id) {
        return getQuestionsForFlight(id);
    }

    public void loadFlightReportOnline(int flight_id) {
        flightsRepository.loadFlightReportOnline(flight_id);
    }

    public LiveData<ComposedFlightAssessment> getFlightQuestions( int flight) {
        return flightsRepository.getFlightQuestions(flight);
    }

    public void loadCategoriesOnline(){
        flightsRepository.loadCategoriesOnline();
    }

    public void saveAnswer(AssessmentQuestion assessmentQuestion) {
        flightsRepository.saveAnswer(assessmentQuestion);
    }

    public void uploadAnswers(JSONArray jsonArray) {
        flightsRepository.uploadAnswers(jsonArray);
    }
}
