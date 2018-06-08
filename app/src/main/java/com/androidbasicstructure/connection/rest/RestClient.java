package com.androidbasicstructure.connection.rest;


import com.androidbasicstructure.connection.constant.ApiParamConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

/**
 * Created by Himangi Patel on 6/12/2017.
 */

public class RestClient {

  private static final String USER_NAME = "himangi";
  private static final String PASSWORD = "123456";

  private static final int TIME = 60;
  private static final String TAG = RestClient.class.getSimpleName();
  private static RestService restService;
  private static HttpLoggingInterceptor logging =
      new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

  private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
      .connectTimeout(TIME, TimeUnit.SECONDS)
      .readTimeout(TIME, TimeUnit.SECONDS)
      .writeTimeout(TIME, TimeUnit.SECONDS)
      .addInterceptor(logging)
      .addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
          Request original = chain.request();
          final String basic = Credentials.basic(USER_NAME, PASSWORD);
          Request.Builder requestBuilder = original.newBuilder().header("Authorization", basic);
          requestBuilder.header("Accept", "application/json");
          requestBuilder.method(original.method(), original.body());
          Request request = requestBuilder.build();
          Response response = chain.proceed(request);
          if (response.isSuccessful()) {
            return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), response.body().string()))
                .build();
          } else {
            if (response.code() == HTTP_UNAUTHORIZED) {
              //log out
            }
          }
          return response;
        }
      }).build();


  private static OkHttpClient httpClientOther = new OkHttpClient().newBuilder()
          .connectTimeout(TIME, TimeUnit.SECONDS)
          .readTimeout(TIME, TimeUnit.SECONDS)
          .writeTimeout(TIME, TimeUnit.SECONDS)
          .addInterceptor(logging)
          .build();

  public static RestService getPrimaryService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiParamConstant.BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(new ToStringConverterFactory())
        .client(httpClient)
        .build();
    return retrofit.create(RestService.class);
  }

  public static RestService getService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiParamConstant.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(new ToStringConverterFactory())
            .client(httpClientOther)
            .build();
    return retrofit.create(RestService.class);
  }


}