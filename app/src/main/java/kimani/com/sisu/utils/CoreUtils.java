package kimani.com.sisu.utils;

import android.util.Log;

import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoreUtils {

    private static Retrofit retrofit = null;
    private static Retrofit auth_retrofit = null;

//    public static String base_url="http://192.168.100.5/sisu/public/api/";
    public static String base_url="http://sisu.neverest.co.ke/api/";

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);


            retrofit = new Retrofit.Builder()
                    .baseUrl(CoreUtils.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getAuthRetrofitClient(String token) {
        Log.e("Auth_TOKEN",token);
        if (auth_retrofit == null) {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(CoreUtils.base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor());
            //build http interceptor with tokens
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(token);
            httpClient.addInterceptor(interceptor);
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());

            auth_retrofit=builder.build();
        }
        return auth_retrofit;
    }

    public static void destroyRetrofit() {
        retrofit = null;
        auth_retrofit=null;
    }

    public static enum SYNC {
        CUSTOMERS,
        CUSTOMERS_UP, SALES_UP, SALES, PRODUCTS
    }
}
