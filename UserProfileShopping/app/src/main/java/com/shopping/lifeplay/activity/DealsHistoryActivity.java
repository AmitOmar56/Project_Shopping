package com.shopping.lifeplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.DealsHistoryAdapter;
import com.shopping.lifeplay.model.DealsHistory;

import java.util.ArrayList;
import java.util.List;

public class DealsHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DealsHistoryAdapter adapter;
    List<DealsHistory> dealsHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.deals_history_recyclerView);
        dealsHistoryList = new ArrayList<>();
        adapter = new DealsHistoryAdapter(this, dealsHistoryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        showDeals_History();
    }
    public void showDeals_History()
    {
        DealsHistory dealsHistory=new DealsHistory("12-04-2018","TASCANY SALON","Full body massage+Head Massage","Pending","Rs.15000","https://images.pexels.com/photos/457701/pexels-photo-457701.jpeg?w=940&h=650&auto=compress&cs=tinysrgb");
        dealsHistoryList.add(dealsHistory);
        dealsHistory=new DealsHistory("23-04-2018","TASCANY SALON","Bridal Makeup","Pending","Rs.35000","https://images.pexels.com/photos/457701/pexels-photo-457701.jpeg?w=940&h=650&auto=compress&cs=tinysrgb");
        dealsHistoryList.add(dealsHistory);

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        return true;
    }
}
