package com.populi.chalange.rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by victor_house on 04.08.2016.
 */
//use it iF needed
public abstract class RetrofitCallback<T> implements Callback<T> {
    public RetrofitCallback() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            //get here message error from server if response bad
        } else {
            onError(call, response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onError(call, throwable.getMessage());
    }
    public abstract void onFinished(Call<T> call, T t);
    public abstract void onError(Call<T> call, String error);
}