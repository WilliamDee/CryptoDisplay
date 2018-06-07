package com.william.cryptocharts.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.william.cryptocharts.database.Coin;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by William on 2018-06-01.
 */

@Dao
public interface CoinDao {

    @Query("SELECT * FROM coins")
    List<Coin> getAll();

    @Query("DELETE FROM coins WHERE coin_name LIKE (:coinName)")
    void delete(String coinName);

    @Insert(onConflict = REPLACE)
    void replace(Coin coin);

    @Update(onConflict = REPLACE)
    void updateCoin(Coin coin);
}
