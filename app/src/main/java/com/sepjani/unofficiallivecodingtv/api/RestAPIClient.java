package com.sepjani.unofficiallivecodingtv.api;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sepjani.unofficiallivecodingtv.LivecodingApplication;
import com.sepjani.unofficiallivecodingtv.activity.LoginWebActivity;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class RestAPIClient {

    public static final String SERVER_URL = "https://www.livecoding.tv:443";
    private final OkHttpClient.Builder client;
    private final ILivecodingAPI mApi;

    public RestAPIClient() {
        this.client = createBaseClient();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LivecodingApplication.getAppContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        mApi = retrofit.create(ILivecodingAPI.class);
    }

    public ILivecodingAPI getAPI() {
        return mApi;
    }

    public static OkHttpClient.Builder createBaseClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                //.authenticator(getAuthenticator())
                .addInterceptor(interceptor);
    }

    //TODO later check
    private static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LivecodingApplication.getAppContext());
                String token = prefs.getString("token", "");
                String tokenType = prefs.getString("token_type", "");
                if (token.isEmpty() || tokenType.isEmpty()) {
                    chain.proceed(original.newBuilder().build());
                }

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", tokenType + " " + token)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json");

                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                if (response != null && response.code() == 401) {
                    Intent i = new Intent(LivecodingApplication.getAppContext(), LoginWebActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    LivecodingApplication.getAppContext().startActivity(i);
                }
                return response;
            }
        };
    }

    public static Authenticator getAuthenticator() {
        return new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                System.out.println("Authenticating for response: " + response);
//                String credential = Credentials.basic("jesse", "password1");
                return response.request().newBuilder()
//                        .header("Authorization", credential)
                        .build();
            }
        };
    }
}
