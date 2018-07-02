package com.shopping.lifeplay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.SalonDataAdapter;
import com.shopping.lifeplay.model.SalonCart;
import com.shopping.lifeplay.model.SalonData;
import com.shopping.lifeplay.utils.BagdeDrawable;
import com.shopping.lifeplay.utils.MyProgressDialog;
import com.shopping.lifeplay.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shopping.lifeplay.activity.HomeActivity.itemofsaloncart;
import static com.shopping.lifeplay.activity.HomeActivity.salonCartList;
import static com.shopping.lifeplay.utils.utils.dpToPx;

public class SalonAtHomeData extends AppCompatActivity implements SalonDataAdapter.Product_OnItemClicked {

    private SalonDataAdapter adapter = null;
    private List<SalonData> salonDataList;
    private SalonCart salonCart;
    private int key;
    private String service_name;
    private String service_include;
    private String service_price;
    private String service_cut_price;
    private String service_time;
    private int service_id;
    private int key_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_at_home_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getdatafromIntent();

        /*****************(Start) code For Card View*****************/

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.salonDataRecuclerView);
        salonDataList = new ArrayList<>();
        adapter = new SalonDataAdapter(this, salonDataList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
//        prepareAlbums();
        signupRequest();
        /*****************(End) code For Card View Vertical*****************/

    }

    @Override
    public void Product_onItemClick(int position) {
        Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show();

        SalonData salonData = salonDataList.get(position);
        for (SalonCart salonCart : salonCartList) {

            if (salonCart.getCart_data_id() == salonData.getSalon_data_id()) {
                key = 1;
                break;
            }
        }
        if (key == 0) {
            salonCart = new SalonCart(salonData.getSalon_data_name(), salonData.getSalon_data_price(), salonData.getSalon_data_id());
            salonCartList.add(salonCart);
            itemofsaloncart++;
        }
        key = 0;
    }

    private void getdatafromIntent() {
        if (getIntent() != null) {
            key_id = Integer.parseInt(getIntent().getStringExtra("id"));

            Log.d("cat_Id", key_id + "");
        }
    }

    /**
     * Adding few albums for testing
     */

    private void signupRequest() {
        MyProgressDialog.showPDialog(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://littlejoy.co.in/admin_parilok/users/service.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            if (jsonObject.getString("status").equals("STATUS_SUCCESS")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.d("jsonObject", jsonObject + "");
                                Log.d("jsonArray", jsonArray + "");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject profile = jsonArray.getJSONObject(i);
                                    service_name = profile.getString("name");
                                    service_include = profile.getString("include");
                                    service_price = profile.getString("price");
                                    service_cut_price = profile.getString("sp");
                                    service_time = profile.getString("time");
                                    service_id = Integer.parseInt(profile.getString("service_id"));

                                    SalonData salonData = new SalonData(service_name, service_include, service_price, service_cut_price, service_time, service_id);
                                    salonDataList.add(salonData);

                                    adapter.notifyDataSetChanged();

                                }
                                MyProgressDialog.hidePDialog();
                            }
                            else {
                                MyProgressDialog.hidePDialog();
                                Toast.makeText(SalonAtHomeData.this, "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SalonAtHomeData.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("service_cat_id", key_id + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, itemofsaloncart + "");        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BagdeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BagdeDrawable) {
            badge = (BagdeDrawable) reuse;
        } else {
            badge = new BagdeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {

            Intent intent = new Intent(SalonAtHomeData.this, SalonCartActivity.class);
            startActivity(intent);

        } else {
            finish();
        }
//        finish();
        return super.onOptionsItemSelected(item);
    }

    public void gotoCart(View view) {
        startActivity(new Intent(this, SalonCartActivity.class));

    }
}
