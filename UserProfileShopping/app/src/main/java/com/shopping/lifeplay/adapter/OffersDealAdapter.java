package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.OfferDeal;

import java.util.List;

/**
 * Created by user on 1/11/2018.
 */

public class OffersDealAdapter extends RecyclerView.Adapter<OffersDealAdapter.MyViewHolder> {

    private Context mContext;
    private List<OfferDeal> offerDealList;

    //declare interface
    private OffersDealAdapter.Product_OnItemClicked onClick;

    //make interface like this
    public interface Product_OnItemClicked {
        void Product_onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView offer_deal_name, offer_deal_discription, offer_deal_price;
        private ImageView offer_deal_image;
        private CardView offer_deal_cardview;

        public MyViewHolder(View view) {
            super(view);
            offer_deal_discription = (TextView) view.findViewById(R.id.offer_deal_discription);
            offer_deal_price = (TextView) view.findViewById(R.id.offer_deal_price);
            offer_deal_name = (TextView) view.findViewById(R.id.offer_deal_name);
            offer_deal_image = (ImageView) view.findViewById(R.id.offer_deal_image);
            offer_deal_cardview = (CardView) view.findViewById(R.id.offer_deal_cardview);
        }
    }


    public OffersDealAdapter(Context mContext, List<OfferDeal> offerDealList) {
        this.mContext = mContext;
        this.offerDealList = offerDealList;
    }

    @Override
    public OffersDealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_deal_data_card_view, parent, false);

        return new OffersDealAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OffersDealAdapter.MyViewHolder holder, final int position) {

        final OfferDeal offerDeal = offerDealList.get(position);
        holder.offer_deal_discription.setText(offerDeal.getOffer_deal_discription());
        holder.offer_deal_name.setText(offerDeal.getOffer_deal_name());
        holder.offer_deal_price.setText("Rs. " + offerDeal.getOffer_deal_price() + "");
        Glide.with(mContext).load(offerDeal.getOffer_deal_image()).into(holder.offer_deal_image);
        Log.d("Image", offerDeal.getOffer_deal_image());

        holder.offer_deal_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
            }
        });
    }

    public void setOnClick(OffersDealAdapter.Product_OnItemClicked onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemCount() {
        return offerDealList.size();
    }

}


