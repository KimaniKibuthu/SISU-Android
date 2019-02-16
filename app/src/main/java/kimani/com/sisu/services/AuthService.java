package kimani.com.sisu.services;

import kimani.com.sisu.models.Authorization;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login")
    @FormUrlEncoded
    Call<Authorization> tryLogin(@Field("username") String title, @Field("password") String body);

}
