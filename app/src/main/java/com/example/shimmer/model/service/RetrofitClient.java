package com.example.shimmer.model.service;

import android.content.Context;

import com.example.shimmer.view.MainActivity;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient { // AUTH must be done based on the API Authorization string format
  private static final String BASE_URL =
      "https://5e510330f2c0d300147c034c.mockapi.io/"; // 7b824ff6-prod-ingress-d9e2-637451461.eu-west-1.elb.amazonaws.com//a2fdaebcb4c4611eaacff0aca56c262b-1400181780.eu-west-1.elb.amazonaws.com  // 183.82.97.183:8084/ 192.168.1.190:8084   104.155.158.181:8084  35.193.127.64:8084
  private Context context;
  private static RetrofitClient mInstance;
  private Retrofit retrofit;
  MainActivity mainActivity;

  private RetrofitClient() {
    OkHttpClient okHttpClient =
        new OkHttpClient.Builder()
            .readTimeout(6, TimeUnit.MINUTES)
            .addInterceptor(
                new Interceptor() {
                  @Override
                  public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder =
                        original
                            .newBuilder()
                            // .addHeader("Authorization", "Bearer " +
                            // SharedPrefManager.getInstance(context).getToken())//will differ based
                            // on API
                            .method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                  }
                })
            .build();
    retrofit =
        new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
  }

  public static synchronized RetrofitClient getInstance() {
    if (mInstance == null) {
      mInstance = new RetrofitClient();
    }
    return mInstance;
  }

  public ApiInterface apiService() {
    return retrofit.create(ApiInterface.class);
  }
}
