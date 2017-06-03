package com.populi.chalange.presenter;

import android.content.Context;

import com.populi.chalange.rest.RestClient;
import com.populi.chalange.rest.response.model.Country;
import com.populi.chalange.view.MvpView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by victg on 03.06.2017.
 */

public class CountryPresenter implements BasePreseter, Callback<List<Country>> {
    private MvpView view;
    private RestClient restClient;

    @Override
    public void destroy() {
        view = null;
        restClient = null;
    }

    public CountryPresenter(Context c, MvpView v) {
        this.view = v;
        this.restClient = RestClient.getInstance(c);
    }

    @Override
    public void getData() {
        view.showLoadingView();
        view.hideRetryView();
        restClient.getCountries(this);

    }

    @Override
    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
        List<Country> list = response.body();
        view.renderWithData(list);
        view.hideLoading();
    }

    @Override
    public void onFailure(Call<List<Country>> call, Throwable t) {
        view.hideLoading();
        view.showRetry();
    }
}
