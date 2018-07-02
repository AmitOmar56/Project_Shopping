package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.user.userprofileshopping.R;


public class OffersDealCartActivity extends AppCompatActivity {

    private LinearLayout emptyCart;
    public  String option_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_deal_cart);
        getId();
        getDataFromIntent();
    }
    public void getDataFromIntent()
    {
        if (getIntent() != null)
        {
            option_id=getIntent().getStringExtra("option_id");
            Toast.makeText(this,"Option id is "+option_id,Toast.LENGTH_LONG).show();

        }
    }

    private void getId() {
        emptyCart = (LinearLayout) findViewById(R.id.emptyCart);
    }

    public void remove(View view) {
        emptyCart.setVisibility(View.VISIBLE);
    }

    public void shopNow(View view) {
        startActivity(new Intent(this, OffersDealActivity.class));
    }

    public void offerDealAddressDetail(View view)
    {
       // startActivity(new Intent(this,TimeSlotActivity.class));
        Intent i=new Intent(this,CheckOutSalonATHomeActivity.class);
        i.putExtra("option_id",option_id);
        startActivity(i);

    }
}
