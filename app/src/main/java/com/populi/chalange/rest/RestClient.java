package com.populi.chalange.rest;


import android.content.Context;

import com.populi.chalange.BuildConfig;
import com.populi.chalange.Utils;
import com.populi.chalange.rest.response.model.Country;


import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String BASE_URL = "http://staticfiles.popguide.me/";
    private static RestClient sRestClientSingleton;
    private Retrofit retrofit;
    private ApiService apiService;

    private RestClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        httpclient.addInterceptor(new HeaderInterceptor());
        httpclient.addNetworkInterceptor(CACHE_CONTROL);
        if (BuildConfig.DEBUG) {
            httpclient.addInterceptor(logging);
        }

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 5 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        httpclient.cache(cache);
        OkHttpClient client = httpclient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // add gson here if you want to use custom date,etc
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        setApiService(ApiService.class);
    }

    public void setApiService(Class<ApiService> service) {
        apiService = retrofit.create(service);
    }

    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .header("Channel", "mobile")
                    .header("Content-Type", "application/json")
                    .build();
            return chain.proceed(request);
        }
    }

    private static final Interceptor CACHE_CONTROL = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (Utils.isNetworkAvailable()) {
                int maxAge = 60;  // time for reading cache
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;// 4weeks
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public Retrofit retrofit() {
        return retrofit;
    }

    public static synchronized RestClient getInstance(Context context) {
        if (null == sRestClientSingleton) {
            sRestClientSingleton = new RestClient(context);
        }
        return sRestClientSingleton;
    }

    public void getCountries(Callback<List<Country>> callback) {
        apiService.countries().enqueue(callback);
    }
}