package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.OffersDealAdapter;
import com.shopping.lifeplay.model.OfferDeal;
import com.shopping.lifeplay.utils.MyProgressDialog;
import com.shopping.lifeplay.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import static com.shopping.lifeplay.utils.utils.dpToPx;

public class OffersDealDataActivity extends AppCompatActivity implements OffersDealAdapter.Product_OnItemClicked {

    private RecyclerView recyclerView;
    private OffersDealAdapter adapter = null;
    private List<OfferDeal> offerDealList;
    private String merchant_name;
    private String merchant_service_name;
    private String merchant_price;
    private String merchant_cut_price;
    private String merchant_image;
    private int merchant_id;
    private int merchant_service_id;
    private String url = "http://littlejoy.co.in/admin_parilok/users/merchant.php";
    private String imageUrl = "http://littlejoy.co.in/admin_parilok/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_deal_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*****************(Start) code For Card View*****************/

        recyclerView = (RecyclerView) findViewById(R.id.offerdataRecuclerView);
        offerDealList = new ArrayList<>();
        adapter = new OffersDealAdapter(this, offerDealList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        signupRequest();
        /*****************(End) code For Card View Vertical*****************/

    }

    @Override
    public void Product_onItemClick(int position) {
        OfferDeal offerDeal = offerDealList.get(position);
        Toast.makeText(this, offerDeal.getOffer_deal_name(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, OffersdealDiscriptionActivity.class);
        intent.putExtra("id", offerDeal.getMerchant_id() + "");
        intent.putExtra("s_id", offerDeal.getOffer_deal_id()+"");
        startActivity(intent);
    }

    private void signupRequest() {
        MyProgressDialog.showPDialog(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
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
                                    merchant_name = profile.getString("mr_name");
                                    merchant_service_name = profile.getString("ser_name");
                                    merchant_price = profile.getString("ser_sp");
                                    merchant_cut_price = profile.getString("ser_MRP");
                                    merchant_image = profile.getString("image");
                                    merchant_image = imageUrl + merchant_image;
                                    merchant_id = Integer.parseInt(profile.getString("merchant_id"));
                                    merchant_service_id = Integer.parseInt(profile.getString("merchant_ser_id"));

                                    OfferDeal offerDeal = new OfferDeal(merchant_service_name, merchant_name, merchant_id, merchant_service_id, merchant_image, merchant_price, merchant_cut_price);
                                    offerDealList.add(offerDeal);

                                    adapter.notifyDataSetChanged();

                                }
                                MyProgressDialog.hidePDialog();
                            } else {
                                MyProgressDialog.hidePDialog();
                                Toast.makeText(OffersDealDataActivity.this, "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OffersDealDataActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        );
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
