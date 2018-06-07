package com.william.cryptocharts.database;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by William on 2018-06-06.
 */

public class CoinListResponse {
    @SerializedName("Data")
    private Map<String, Coin>  coins;

    public Map<String, Coin> getCoins() {
        return coins;
    }
}
