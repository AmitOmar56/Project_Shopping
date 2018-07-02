package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.Service_History_Adapter;
import com.shopping.lifeplay.model.ServiceHistory;

import java.util.ArrayList;
import java.util.List;

public class ServiceHistoryActivity extends AppCompatActivity implements Service_History_Adapter.Product_OnItemClicked {

    RecyclerView recyclerView;
    Service_History_Adapter adapter;
    List<ServiceHistory> serviceHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.service_history_recyclerView);
        serviceHistoryList = new ArrayList<>();
        adapter = new Service_History_Adapter(this, serviceHistoryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        showService_History();
    }
    public void showService_History()
    {
        ServiceHistory serviceHistory=new ServiceHistory("VM12345","500.00","12.2.18","01:20AM","Pending");
        serviceHistoryList.add(serviceHistory);
        serviceHistory=new ServiceHistory("CODVUY","100.00","10.2.18","11:20AM","Delivered");
        serviceHistoryList.add(serviceHistory);

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish();
        return true;
    }

    @Override
    public void Product_onItemClick(int position)
    {

        Intent intent = new Intent(this, ServiceHistoryDetailActivity.class);
        intent.putExtra("service_id","VM12345");
        intent.putExtra("service_amount","500.00");
        intent.putExtra("service_date","12.218");
        intent.putExtra("service_day","01:20AM");
        intent.putExtra("service_status","Pending");



        startActivity(intent);
    }
}
