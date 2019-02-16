package kimani.com.sisu.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import kimani.com.sisu.models.Assess.FlightAssessment;
import kimani.com.sisu.models.Category;
import kimani.com.sisu.models.Flight;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FlightsService {

    @GET("flights")
    Call<List<Flight>> getFlights();

    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("flight_report/{flight}")
    Call<FlightAssessment> getFlightsReport(@Path("flight") int flight);

    @POST("upload_answers")
    Call<JSONObject> uploadAnswers(@Body JSONArray jsonBody);

}
