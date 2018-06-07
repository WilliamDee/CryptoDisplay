package com.william.cryptocharts;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by William on 2018-05-31.
 */

public class CryptoCharts extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Retrofit getRetrofit(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
