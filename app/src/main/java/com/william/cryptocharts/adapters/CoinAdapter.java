package com.william.cryptocharts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.william.cryptocharts.CryptoCharts;
import com.william.cryptocharts.R;
import com.william.cryptocharts.database.AppDatabase;
import com.william.cryptocharts.database.Coin;
import com.william.cryptocharts.database.PriceResponse;
import com.william.cryptocharts.services.CoinPriceAPIService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William on 2018-06-02.
 */

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    private CoinPriceAPIService coinPriceApi;
    private List<Coin> coinList;
    private Context mContext;
    private int numOfFavouriteCoins = 0;

    public CoinAdapter(List<Coin> coinList, Context mContext){
        this.coinList = coinList;
        this.mContext = mContext;
    }

    @Override
    public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.coin_recycler_item, parent, false);
        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CoinAdapter.CoinViewHolder holder, int position) {
        final String coinName = coinList.get(holder.getAdapterPosition()).getCoinName();
        final String coinPrice = coinList.get(holder.getAdapterPosition()).getCoinPrice();
        Boolean isFavourite = coinList.get(holder.getAdapterPosition()).getFavourite();

        holder.coinName.setText(coinName);
        if(isFavourite == null || !isFavourite){
            holder.favouriteBtn.setSelected(false);
            holder.favouriteBtn.setImageResource(R.drawable.not_favourite_star);
        } else {
            holder.favouriteBtn.setSelected(true);
            holder.favouriteBtn.setImageResource(R.drawable.is_favourite_star);
        }

        if (coinPrice == null){
            updateCoinPrice(holder, coinName);
        } else {
            holder.coinPrice.setText(String.format("$%s",coinPrice));
        }

        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isSelected()){
                        removeFromFavourites(holder, coinName);
                } else {
                    addToFavourites(holder);
                }
            }
        });

    }

    private CoinPriceAPIService getCoinPriceAPIService(){
        coinPriceApi = ((CryptoCharts)mContext).getRetrofit(CoinPriceAPIService.baseUrl)
                .create(CoinPriceAPIService.class);
        return coinPriceApi;
    }

    private void updateCoinPrice(final CoinViewHolder holder, final String coinName){
        Call<PriceResponse> call = getCoinPriceAPIService().getPriceOfCoin(coinName, PriceResponse.Currency);
        call.enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                String retreivedCoinPrice = response.body().getCoinPrice();
                coinList.get(holder.getAdapterPosition()).setCoinPrice(retreivedCoinPrice);
                Log.v("PRICE", response.body().toString());

                if(response.body().getCoinPrice() != null){
                    holder.coinPrice.setText(String.format("$%s", retreivedCoinPrice));
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                Log.e("PRICE_API_ERROR","CoinPriceAPI Response Error: " + t.getMessage());
            }
        });
    }

    private void addToFavourites(CoinViewHolder holder){
        int pos = holder.getAdapterPosition();
        Coin favCoin = coinList.get(pos);
        favCoin.setFavourite(true);
        coinList.remove(pos);
        notifyItemRemoved(pos);

        numOfFavouriteCoins++;
        coinList.add(0 , favCoin);
        notifyItemInserted(0);
        notifyDataSetChanged();

        AppDatabase.getAppDatabase(mContext).coinDao().replace(coinList.get(0));
    }

    private void removeFromFavourites(CoinViewHolder holder, String coinName){
        int pos = holder.getAdapterPosition();
        Coin unFavCoin = coinList.get(pos);
        unFavCoin.setFavourite(false);
        coinList.remove(pos);
        notifyItemRemoved(pos);

        numOfFavouriteCoins--;
        coinList.add(numOfFavouriteCoins, unFavCoin);
        notifyItemInserted(numOfFavouriteCoins);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return coinList.size();
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.coin_name)
        TextView coinName;
        @BindView(R.id.coin_price)
        TextView coinPrice;
        @BindView(R.id.favourite_btn)
        ImageButton favouriteBtn;

        public CoinViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
