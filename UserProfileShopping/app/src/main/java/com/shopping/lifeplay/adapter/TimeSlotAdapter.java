package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.TimeSlots;

import java.util.List;

/**
 * Created by Priyanka on 3/7/2018.
 */

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder>
{
    private List<TimeSlots> timeList;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView time_tv;

        public MyViewHolder(View view)
        {
            super(view);
            time_tv=view.findViewById(R.id.textview_time);

        }
    }
    public TimeSlotAdapter(Context mContext, List<TimeSlots> timeList) {
        this.mContext = mContext;
        this.timeList =timeList;
    }

    @Override
    public TimeSlotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_timeslot, parent, false);

        return new TimeSlotAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        final TimeSlots timeSlots = timeList.get(position);
          holder.time_tv.setText(timeSlots.getTime());
    }



    @Override
    public int getItemCount() {
        return timeList.size();
    }
}
