package com.william.cryptocharts.database;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by William on 2018-06-06.
 */

public class PriceResponse {
    public static final String Currency = "CAD";
    @NonNull
    @SerializedName(Currency)
    String coinPrice;

    public String getCoinPrice() {
        return coinPrice;
    }
}
