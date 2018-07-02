package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.SallonAtHome;

import java.util.List;

/**
 * Created by user on 12/26/2017.
 */

public class SalonAtHomeAdapter extends RecyclerView.Adapter<SalonAtHomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<SallonAtHome> sallonAtHomeList;

    //declare interface
    private SalonAtHomeAdapter.Product_OnItemClicked onClick;

    private SalonAtHomeAdapter.Product_OnClicked onClicknew;

    public interface Product_OnClicked {
        void Product_onClick(int position);
    }

    //make interface like this
    public interface Product_OnItemClicked {
        void Product_onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView product_name;
        private ImageView product_image;
        private RelativeLayout salonAtHomelayout_item;

        public MyViewHolder(View view) {
            super(view);
            product_name = (TextView) view.findViewById(R.id.product_name);
            product_image = (ImageView) view.findViewById(R.id.product_image);
            salonAtHomelayout_item = (RelativeLayout) view.findViewById(R.id.salonAtHomelayout_item);
        }
    }


    public SalonAtHomeAdapter(Context mContext, List<SallonAtHome> sallonAtHomeList) {
        this.mContext = mContext;
        this.sallonAtHomeList = sallonAtHomeList;
    }

    @Override
    public SalonAtHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salon_at_home_card_view, parent, false);

        return new SalonAtHomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalonAtHomeAdapter.MyViewHolder holder, final int position) {

        final SallonAtHome sallonAtHome = sallonAtHomeList.get(position);
        holder.product_name.setText(sallonAtHome.getS_name());
        Glide.with(mContext).load(sallonAtHome.getS_image()).into(holder.product_image);
        holder.salonAtHomelayout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
            }
        });
    }

//    private void removeItem(int position) {
//        productList.remove(position);
//        // notify the item removed by position
//        // to perform recycler view delete animations
//        // NOTE: don't call notifyDataSetChanged()
//        notifyItemRemoved(position);
//        removeItem(position);
//        notifyDataSetChanged();
//    }

    public void setOnClick(SalonAtHomeAdapter.Product_OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setClick(SalonAtHomeAdapter.Product_OnClicked onClicknew) {
        this.onClicknew = onClicknew;
    }

    @Override
    public int getItemCount() {
        return sallonAtHomeList.size();
    }

}

