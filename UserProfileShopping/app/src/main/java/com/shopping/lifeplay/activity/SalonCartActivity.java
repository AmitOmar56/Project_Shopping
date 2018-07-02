package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.SalonCartAdapter;
import com.shopping.lifeplay.model.SalonCart;
import com.shopping.lifeplay.utils.utils;

import static com.shopping.lifeplay.activity.HomeActivity.itemofsaloncart;
import static com.shopping.lifeplay.activity.HomeActivity.salonCartList;
import static com.shopping.lifeplay.adapter.SalonCartAdapter.salon_item;
import static com.shopping.lifeplay.utils.utils.dpToPx;

public class SalonCartActivity extends AppCompatActivity implements SalonCartAdapter.Product_OnItemClicked, SalonCartAdapter.Product_On_spin_Clicked {

    private SalonCartAdapter adapter = null;
    //    private List<SalonCart> salonCartList;
    private int price = 0;
    private TextView totalcartPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        totalcartPrice = (TextView) findViewById(R.id.salon_totalcartPrice);
        for (int i = 0; i < salonCartList.size(); i++) {
            SalonCart salonCart = salonCartList.get(i);
            price = price + Integer.parseInt(salonCart.getCart_data_price());
        }
        totalcartPrice.setText("Rs." + price + "");
        setCartLayout();

        /*****************(Start) code For Card View*****************/

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.saloncartRecyclerView);
        adapter = new SalonCartAdapter(this, salonCartList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        adapter.setOn_Spin_Click(this);
        //  prepareAlbums();
        /*****************(End) code For Card View Vertical*****************/

    }

    protected void setCartLayout() {
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.salon_layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.salon_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.salon_layout_cart_empty);


        if (itemofsaloncart > 0) {
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
            Toast.makeText(this, "if" + itemofsaloncart, Toast.LENGTH_LONG).show();

        } else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SalonCartActivity.this, SallonAtHomeActivity.class));
                }
            });
        }
    }

    protected void setCart() {

        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.salon_layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.salon_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.salon_layout_cart_empty);
        layoutCartNoItems.setVisibility(View.VISIBLE);
        layoutCartItems.setVisibility(View.GONE);
        layoutCartPayments.setVisibility(View.GONE);

        Button bStartShopping = (Button) findViewById(R.id.bAddNew);
        bStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SalonCartActivity.this, SallonAtHomeActivity.class));
            }
        });
    }

    @Override
    public void Product_onItemClick(int position) {
        if (itemofsaloncart == 1) {
            setCart();
        }
        Toast.makeText(this, "Enter", Toast.LENGTH_LONG).show();
        price = 0;
        for (int i = 0; i < salonCartList.size(); i++) {
            if (position != i) {
                SalonCart salonCart = salonCartList.get(i);
                price = price + Integer.parseInt(salonCart.getCart_data_price());
            }
        }
        totalcartPrice.setText("Rs." + price + "");
    }

    public void salonCartAddressDetail(View view)
    {
        Log.d("Priyanka","Go To Time Slot");
        Toast.makeText(this,"Going To Time Slot",Toast.LENGTH_LONG).show();
       // startActivity(new Intent(this, CheckOutSalonATHomeActivity.class));
        startActivity(new Intent(this,TimeSlotActivity.class));
    }

    public void moreShopping(View view) {
        startActivity(new Intent(this, SallonAtHomeActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void Product_on_spin_Clicked(int position) {
        SalonCart salonCart = salonCartList.get(position);
        price = 0;
        Toast.makeText(this, salonCart.getCart_data_id() + salonCart.getCart_data_price() + "clicked_on" + salon_item + "", Toast.LENGTH_LONG).show();

        for (int i = 0; i < salonCartList.size(); i++) {
            if (position == i) {
                salonCart = salonCartList.get(i);
                price = price + Integer.parseInt(salonCart.getCart_data_price()) * salon_item;
            } else {
                salonCart = salonCartList.get(i);
                price = price + Integer.parseInt(salonCart.getCart_data_price());
            }

        }
        totalcartPrice.setText("Rs." + price + "");

}
}
