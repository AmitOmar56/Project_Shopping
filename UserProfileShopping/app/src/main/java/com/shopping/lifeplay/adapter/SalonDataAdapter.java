package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.SalonData;

import java.util.List;

/**
 * Created by user on 12/26/2017.
 */

public class SalonDataAdapter extends RecyclerView.Adapter<SalonDataAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalonData> salonDataList;

    //declare interface
    private SalonDataAdapter.Product_OnItemClicked onClick;

    private SalonDataAdapter.Product_OnClicked onClicknew;

    public interface Product_OnClicked {
        void Product_onClick(int position);
    }

    //make interface like this
    public interface Product_OnItemClicked {
        void Product_onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView service_name, service_price, service_time, service_cut_price, service_include;
        private LinearLayout layout_item;
        private ImageView circle_pink_imageview;

        public MyViewHolder(View view) {
            super(view);
            service_name = (TextView) view.findViewById(R.id.service_name);
            service_price = (TextView) view.findViewById(R.id.service_price);
            service_time = (TextView) view.findViewById(R.id.service_time);
            layout_item = (LinearLayout) view.findViewById(R.id.layout_item);
            circle_pink_imageview = (ImageView) view.findViewById(R.id.circle_pink_imageview);
            service_include = (TextView) view.findViewById(R.id.service_include);
            service_cut_price = (TextView) view.findViewById(R.id.service_cut_price);
            service_cut_price.setPaintFlags(service_cut_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }


    public SalonDataAdapter(Context mContext, List<SalonData> salonDataList) {
        this.mContext = mContext;
        this.salonDataList = salonDataList;
    }

    @Override
    public SalonDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sallon_at_home_data_card_view, parent, false);

        return new SalonDataAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalonDataAdapter.MyViewHolder holder, final int position) {

        final SalonData salonData = salonDataList.get(position);
        holder.service_name.setText(salonData.getSalon_data_name());
        holder.service_price.setText("Rs. " + salonData.getSalon_data_price());
        holder.service_time.setText(salonData.getSalon_data_time() + " min");
        holder.service_include.setText("(" + salonData.getSalon_data_include() + ")");
        holder.service_cut_price.setText(salonData.getSalon_data_cut_price() + "");
//        Glide.with(mContext).load(sallonAtHome.getS_image()).into(holder.product_image);
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
                String backgroundImageName = String.valueOf(holder.circle_pink_imageview.getTag());
                if (backgroundImageName.equals("change")) {
                    holder.circle_pink_imageview.setTag("fill");
                    holder.circle_pink_imageview.setImageResource(R.drawable.pinkcircle_fill);
                } else {
                    holder.circle_pink_imageview.setTag("change");
                    holder.circle_pink_imageview.setImageResource(R.drawable.pinkcircle_blank);
                }

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

    public void setOnClick(SalonDataAdapter.Product_OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setClick(SalonDataAdapter.Product_OnClicked onClicknew) {
        this.onClicknew = onClicknew;
    }

    @Override
    public int getItemCount() {
        return salonDataList.size();
    }

}
