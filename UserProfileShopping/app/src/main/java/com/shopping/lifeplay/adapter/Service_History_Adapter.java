package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.ServiceHistory;

import java.util.List;

/**
 * Created by Priyanka on 3/7/2018.
 */

public class Service_History_Adapter extends RecyclerView.Adapter<Service_History_Adapter.MyViewHolder>
{
    private Context mContext;
    private List<ServiceHistory> serviceHistoryList;
    //declare interface
    private Service_History_Adapter.Product_OnItemClicked onClick;

    //make interface like this
    public interface Product_OnItemClicked
    {
        void Product_onItemClick(int position);
    }
    public Service_History_Adapter(Context context,List<ServiceHistory> serviceHistoryList)
    {
        this.mContext=context;
        this.serviceHistoryList=serviceHistoryList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView service_id,service_amount,service_dt,service_dy,service_status;
        private LinearLayout service_history_LinearLayout;
       public  MyViewHolder(View view)
        {
            super(view);
            service_id=view.findViewById(R.id.service_id_value);
            service_amount=view.findViewById(R.id.service_cash_value);
            service_dt=view.findViewById(R.id.service_date);
            service_dy=view.findViewById(R.id.service_time);
            service_status=view.findViewById(R.id.service_status);
            service_history_LinearLayout=  view.findViewById(R.id.service_history_LinearLayout);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_service_history, parent, false);

        return new Service_History_Adapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        final ServiceHistory service_history = serviceHistoryList.get(position);
        holder.service_id.setText( service_history.getBooking_id());
        holder.service_amount.setText(service_history.getPayment_amount());
        holder.service_dy.setText(service_history.getBooking_day());
        holder.service_dt.setText(service_history.getBooking_date());
        holder.service_status.setText(service_history.getService_status());
        holder.service_history_LinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
            }
        });
    }

    public void setOnClick(Service_History_Adapter.Product_OnItemClicked onClick)
    {
        this.onClick = onClick;
    }





    @Override
    public int getItemCount() {
        return serviceHistoryList.size();
    }


}
