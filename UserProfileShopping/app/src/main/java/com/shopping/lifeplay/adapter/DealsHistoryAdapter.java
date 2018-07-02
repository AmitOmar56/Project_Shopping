package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.DealsHistory;

import java.util.List;

public class DealsHistoryAdapter extends RecyclerView.Adapter<DealsHistoryAdapter.MyViewHolder>
{
    private Context mContext;
    private List<DealsHistory> dealsHistoryList;

    public  DealsHistoryAdapter(Context context,List<DealsHistory> dealsHistoryList)
    {
        this.mContext=context;
        this.dealsHistoryList=dealsHistoryList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView offer_expiry,offer_amount,offer_desc,offer_company,offer_status;
        private ImageView company_image;
        public  MyViewHolder(View view)
        {
            super(view);
            offer_expiry=view.findViewById(R.id.deals_expiry_value);
            offer_amount=view.findViewById(R.id.deal_price);
            offer_company=view.findViewById(R.id.deal_provider_name);
            offer_desc=view.findViewById(R.id.deal_desc);
            offer_status=view.findViewById(R.id.deal_status);
            company_image=view.findViewById(R.id.companyImage);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_offer_history, parent, false);

        return new DealsHistoryAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        final DealsHistory dealsHistory = dealsHistoryList.get(position);

        holder.offer_amount.setText(dealsHistory.getOffer_price());
        holder.offer_status.setText(dealsHistory.getOffer_status());
        holder.offer_expiry.setText(dealsHistory.getOffer_expiry());
        holder.offer_company.setText(dealsHistory.getOffer_company());
        holder.offer_desc.setText(dealsHistory.getOffer_description());
        Glide.with(mContext).load(dealsHistory.getCompany_image()).into(holder.company_image);
    }




    @Override
    public int getItemCount() {
        return dealsHistoryList.size();
    }


}
