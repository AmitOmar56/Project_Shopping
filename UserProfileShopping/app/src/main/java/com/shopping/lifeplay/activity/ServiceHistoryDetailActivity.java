package com.shopping.lifeplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.user.userprofileshopping.R;

public class ServiceHistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getId();
        getdatafromIntent();
    }
    private void getdatafromIntent() {
        if (getIntent() != null)
        {


        }
    }

    private void getId()
    {

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
