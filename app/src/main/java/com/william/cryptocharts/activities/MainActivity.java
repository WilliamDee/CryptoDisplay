package com.william.cryptocharts.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.william.cryptocharts.CryptoCharts;
import com.william.cryptocharts.R;
import com.william.cryptocharts.adapters.CoinAdapter;
import com.william.cryptocharts.database.Coin;
import com.william.cryptocharts.database.CoinListResponse;
import com.william.cryptocharts.services.CoinListAPIService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William on 2018-05-31.
 */

public class MainActivity extends AppCompatActivity {

    private CoinListAPIService coinListApi;
    private List<Coin> coinArrayList = new ArrayList<>();

    @BindView(R.id.coin_recycler_view)
    RecyclerView recyclerView;

    CoinAdapter coinAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getCoinListData();
    }

    private void getCoinListData(){
        Call<CoinListResponse> call = getCoinListAPIService().getCoinList();

        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {
                coinArrayList = new ArrayList<>(response.body().getCoins().values());
                setUpCoinRecyclerView();
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {
                Log.e("COIN_NAME_API_ERROR", "CoinListAPI Response Error: " + t.getMessage());
            }
        });
    }

    private CoinListAPIService getCoinListAPIService(){
        coinListApi = ((CryptoCharts)getApplicationContext()).getRetrofit(CoinListAPIService.baseUrl)
                .create(CoinListAPIService.class);
        return coinListApi;
    }

    private void setUpCoinRecyclerView(){
        coinAdapter = new CoinAdapter(coinArrayList, getApplicationContext());
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(coinAdapter);
            }
        });
    }
}
