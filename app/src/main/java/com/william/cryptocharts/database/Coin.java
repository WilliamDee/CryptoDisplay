package com.william.cryptocharts.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by William on 2018-06-01.
 */

@Entity(tableName = "coins",
        indices = {@Index(value = {"coin_name"}, unique = true)})
public final class Coin {

    @PrimaryKey
    @NonNull
    @SerializedName("Name")
    @ColumnInfo(name = "coin_name")
    private String coinName;

    @ColumnInfo(name = "coin_price")
    private String coinPrice;

    @ColumnInfo(name = "is_favourite")
    private Boolean isFavourite;

    @Ignore
    public Coin(){
    }

    public Coin(String coinName, String coinPrice){
        super();

        this.coinName = coinName;
        this.coinPrice = coinPrice;
        this.isFavourite = false;

    }

    public String getCoinName(){
        return coinName;
    }

    public void setCoinName(String coinName){
        this.coinName = coinName;
    }

    public String getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(String coinPrice){
        this.coinPrice = coinPrice;
    }

    public Boolean getFavourite(){
        return isFavourite;
    }

    public void setFavourite(Boolean favourite){
        isFavourite = favourite;
    }

}
