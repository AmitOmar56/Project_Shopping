package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.TimeSlotAdapter;
import com.shopping.lifeplay.model.TimeSlots;
import com.shopping.lifeplay.utils.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.shopping.lifeplay.utils.utils.dpToPx;
import static java.util.Calendar.getInstance;

public class TimeSlotActivity extends AppCompatActivity {

    TimeSlotAdapter adapter;
    Button next;
    CalendarView calendarView;
    RecyclerView recyclerView;
    List<TimeSlots> timeSlotsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        next = findViewById(R.id.next_detail);
        calendarView = findViewById(R.id.my_calendar);
        recyclerView = findViewById(R.id.time_slot_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(3, dpToPx(this, 1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TimeSlotAdapter(this, timeSlotsList);

        recyclerView.setAdapter(adapter);
        //send dummy data
        sendDummyData();
        // setting minimum and  maximum values on calendar
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        long milliseconds = date.getTime()-10000;

        c.set(Calendar.MONTH, +5);
        calendarView.setMinDate(milliseconds);
        calendarView.setMaxDate(c.getTimeInMillis());


    }

    public void sendDummyData() {
        TimeSlots timeSlots = new TimeSlots("09:30 AM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("10:00 AM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("10:30 AM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("11:00 AM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("11:30 AM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("12:00 PM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("12:30 PM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("01:00 PM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("02:30 PM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("03:30 PM");
        timeSlotsList.add(timeSlots);
        timeSlots = new TimeSlots("05:00 PM");
        timeSlotsList.add(timeSlots);

    }

    public void getAddressDetails(View view) {
        Intent intent = new Intent(TimeSlotActivity.this, CheckOutSalonATHomeActivity.class);
        intent.putExtra("option_id","salon");

        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
