package com.william.cryptocharts.services;

import com.william.cryptocharts.database.CoinListResponse;
import com.william.cryptocharts.database.PriceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by William on 2018-06-07.
 */

public interface CoinPriceAPIService {
    String baseUrl = "https://min-api.cryptocompare.com/";

    @GET("data/price")
    Call<PriceResponse> getPriceOfCoin(@Query("fsym") String coinName, @Query("tsyms") String currency);
}
