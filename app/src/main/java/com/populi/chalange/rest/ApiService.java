package com.populi.chalange.rest;
/**
 * Created by victg on 03.06.2017.
 */

import com.populi.chalange.rest.response.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by victg on 27.01.2017.
 */

public interface ApiService {
    @GET("code_challenge/countries_v1.json")
    Call<List<Country>> countries();
    @GET("code_challenge/countries_v2.json")
    Call<List<Country>> countriesV2();
}