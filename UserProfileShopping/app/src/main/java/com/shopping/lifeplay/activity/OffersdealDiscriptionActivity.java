package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.utils.MyProgressDialog;
import com.shopping.lifeplay.viewPager.Viewpager_Adapter;
import com.shopping.lifeplay.viewPager.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;


public class OffersdealDiscriptionActivity extends AppCompatActivity {

    /********************code for view pager************************/
    private Viewpager_Adapter viewpager_adapter;
    private ViewPager viewPager;
    private Handler handler;
    private int delay = 4000; //milliseconds
    private int page = 0;
    Runnable runnable;
    private int key_id;
     String option_id="deals";


    /********************code for view pager************************/

    private TextView p_name, p_price, service_name, service_detail, service_fullprice, discount_price, service_timing, service_days, merchant_address;
    private TextView home_text, ac_text, wifi_text, parking_text;
    private ImageView home_image, ac_image, wifi_image, parking_image;
    private String detailUrl = "http://littlejoy.co.in/admin_parilok/users/merchant_det.php";
    private String image[];
    private String service_merchant_name;
    private String service_merchant_price;
    private String service_merchant_cut_price;
    private String service_merchant_disc;
    private String service_merchant_days;
    private String service_merchant_timing_to;
    private String service_merchant_address;
    private String service_merchant_id;
    private String service_offer_name;
    private String service_merchant_timing_from;
    private String img1, img2, img3, img4, img5;
    private String imageUrl = "http://littlejoy.co.in/admin_parilok/api/";
    private int ac, wifi, parking, home;
    private int save_price;
    private int ser_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offersdeal_discription);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getdatafromIntent();
        getId();

        product_DetailApi();
    }

    private void getId() {
        p_name = (TextView) findViewById(R.id.p_name);
        p_price = (TextView) findViewById(R.id.p_price);
        service_name = (TextView) findViewById(R.id.service_name);
        service_detail = (TextView) findViewById(R.id.service_detail);
        service_fullprice = (TextView) findViewById(R.id.service_fullprice);
        discount_price = (TextView) findViewById(R.id.discount_price);
        service_timing = (TextView) findViewById(R.id.service_timing);
        service_days = (TextView) findViewById(R.id.service_days);
        merchant_address = (TextView) findViewById(R.id.merchant_address);
        home_text = (TextView) findViewById(R.id.home_text);
        ac_text = (TextView) findViewById(R.id.ac_text);
        wifi_text = (TextView) findViewById(R.id.wifi_text);
        parking_text = (TextView) findViewById(R.id.parking_text);
        home_image = (ImageView) findViewById(R.id.home_image);
        ac_image = (ImageView) findViewById(R.id.ac_image);
        wifi_image = (ImageView) findViewById(R.id.wifi_image);
        parking_image = (ImageView) findViewById(R.id.parking_image);

    }

    private void getdatafromIntent() {
        if (getIntent() != null) {
            key_id = Integer.parseInt(getIntent().getStringExtra("id"));
            ser_id = Integer.parseInt(getIntent().getStringExtra("s_id"));
            Log.d("ser_id", ser_id + "");
        }
    }

    private void product_DetailApi() {
        MyProgressDialog.showPDialog(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, detailUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Detail", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("jsonObject", jsonObject + "");
                            Log.d("jsonArray", jsonArray + "");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject profile = jsonArray.getJSONObject(i);
                                service_merchant_name = profile.getString("mr_name");
                                service_offer_name = profile.getString("ser_name");
                                service_merchant_price = profile.getString("ser_sp");
                                service_merchant_cut_price = profile.getString("ser_MRP");
                                service_merchant_disc = profile.getString("ser_include");
                                service_merchant_days = profile.getString("days");
                                service_merchant_timing_from = profile.getString("fromm");
                                service_merchant_timing_to = profile.getString("too");
                                service_merchant_address = profile.getString("mr_address");
                                service_merchant_id = profile.getString("merchant_ser_id");
                                img1 = imageUrl + profile.getString("image");
                                img2 = imageUrl + profile.getString("image1");
                                img3 = imageUrl + profile.getString("image2");
                                img4 = imageUrl + profile.getString("image3");
                                img5 = imageUrl + profile.getString("image4");
                                ac = profile.getInt("ac");
                                wifi = profile.getInt("wifi");
                                parking = profile.getInt("parking");
                                home = profile.getInt("home_service");
                            }

                            p_name.setText(service_merchant_name);
                            p_price.setText("Rs. " + service_merchant_price);
                            service_name.setText(service_offer_name);
                            service_detail.setText(service_merchant_disc);
                            service_fullprice.setText(service_merchant_cut_price);
                            save_price = Integer.parseInt(service_merchant_cut_price) - Integer.parseInt(service_merchant_price);
                            discount_price.setText("Rs. " + save_price);
                            service_timing.setText(service_merchant_timing_from + " AM " + "to" + service_merchant_timing_to + " Pm");
                            service_days.setText(service_merchant_days);
                            merchant_address.setText(service_merchant_address);

                            viewPagerCode();
                            service_offer();
                            MyProgressDialog.hidePDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OffersdealDiscriptionActivity.this, "Network Error", Toast.LENGTH_LONG).show();

                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("merchant_id", key_id + "");
                params.put("merchant_ser_id", ser_id + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    private void service_offer() {

        if (ac == 1) {
            ac_image.setImageResource(R.drawable.fan_on);
        }
        if (wifi == 1) {
            wifi_image.setImageResource(R.drawable.wifi_on);

        }
        if (parking == 1) {
            parking_image.setImageResource(R.drawable.car_on);

        }
        if (home == 1) {
            home_image.setImageResource(R.drawable.home_on);

        }
    }

    private void viewPagerCode() {
        Log.d("Image", img1 + "");

        /***************(start) code for viewPager*************************/
        String images[] = {
                img1, img2, img3, img4, img5
        };
        runnable = new Runnable() {
            public void run() {
                if (viewpager_adapter.getCount() == page) {
                    page = 0;
                } else {
                    page++;
                }
                viewPager.setCurrentItem(page, true);
                handler.postDelayed(this, delay);
            }
        };

        handler = new Handler();
        viewPager = (ViewPager) findViewById(R.id.teacherViewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager_adapter = new Viewpager_Adapter(this, images);

        viewPager.setAdapter(viewpager_adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /***************(End) code for viewPager*************************/
    }

    /********************code for view pager************************/

    @Override
    protected void onResume() {
        super.onResume();
//        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        handler.removeCallbacks(runnable);
    }

    /********************code for view pager************************/
    public void goTomoreDeal(View view) {
        Intent intent = new Intent(this, OffersDealMoreDealsActivity.class);
        intent.putExtra("id", key_id + "");
        intent.putExtra("option_id",option_id);
        startActivity(intent);
    }

    public void getNow(View view)
    {
        Intent intent=new Intent(this,OffersDealCartActivity.class);
        intent.putExtra("option_id",option_id);
        startActivity(intent);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
