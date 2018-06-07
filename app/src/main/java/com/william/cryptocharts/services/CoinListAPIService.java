package com.william.cryptocharts.services;

import com.william.cryptocharts.database.CoinListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by William on 2018-06-07.
 */

public interface CoinListAPIService {
    String baseUrl = "https://www.cryptocompare.com/";

    @GET("api/data/coinlist")
    Call<CoinListResponse> getCoinList();
}
