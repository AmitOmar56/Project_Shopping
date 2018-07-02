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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.SalonAtHomeAdapter;
import com.shopping.lifeplay.model.SallonAtHome;
import com.shopping.lifeplay.utils.BagdeDrawable;
import com.shopping.lifeplay.utils.MyProgressDialog;
import com.shopping.lifeplay.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import static com.shopping.lifeplay.activity.HomeActivity.itemofsaloncart;
import static com.shopping.lifeplay.utils.utils.dpToPx;

public class SallonAtHomeActivity extends AppCompatActivity implements SalonAtHomeAdapter.Product_OnItemClicked {

    private SalonAtHomeAdapter adapter;
    private SallonAtHome sallonAtHome;
    private List<SallonAtHome> sallonAtHomeList;
    private String service_cat_name;
    private int service_cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sallon_at_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getId();

        /*****************(Start) code For Card View*****************/

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sallonAthomeRecyclerView);
        sallonAtHomeList = new ArrayList<>();
        adapter = new SalonAtHomeAdapter(this, sallonAtHomeList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        signupRequest();
        // prepareAlbums();
        /*****************(End) code For Card View Vertical*****************/
    }

    private void getId() {

    }

    @Override
    public void Product_onItemClick(int position) {
        SallonAtHome sallonAtHome = sallonAtHomeList.get(position);
        Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show();
        // made a change here
        Intent intent = (new Intent(this, Subactivity_NewOffer.class));
        intent.putExtra("id", sallonAtHome.getS_id() + "");
//        Toast.makeText(this, sallonAtHome.getS_id() + "", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    private void signupRequest() {
        MyProgressDialog.showPDialog(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.GET, "http://littlejoy.co.in/admin_parilok/users/service_cat.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("jsonObject", jsonObject + "");
                            Log.d("jsonArray", jsonArray + "");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject profile = jsonArray.getJSONObject(i);
                                service_cat_name = profile.getString("service_cat_name");
                                service_cat_id = Integer.parseInt(profile.getString("service_cat_id"));
                                Log.d("cat_Name", service_cat_name);
                                Log.d("cat_Id", service_cat_name + "");

                                sallonAtHome = new SallonAtHome("https://www.idospa.my/wp-content/uploads/2017/10/mobile-icon-15.png", service_cat_name, service_cat_id);
                                sallonAtHomeList.add(sallonAtHome);
                                adapter.notifyDataSetChanged();

                            }
                            MyProgressDialog.hidePDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SallonAtHomeActivity.this, "Network Error", Toast.LENGTH_LONG).show();

                        MyProgressDialog.hidePDialog();
                    }
                }
        );
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shooping, menu);
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

            Intent intent = new Intent(SallonAtHomeActivity.this, SalonCartActivity.class);
            startActivity(intent);

        } else {
            finish();
        }
//        finish();
        return super.onOptionsItemSelected(item);
    }
}
