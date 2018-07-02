package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.shopping.lifeplay.adapter.HistoryAdapter;
import com.shopping.lifeplay.model.History;
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

import static com.shopping.lifeplay.utils.utils.dpToPx;


public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.Product_OnItemClicked {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter = null;
    private List<History> historyList;
    private String product_name;
    private String product_desc;
    private String product_image;
    private int product_id;
    private String product_price;
    private String product_quantity;
    private String product_status;
    private String product_created;
    private String product_deliver;
    private String user_name;
    private String user_phone;
    private String user_address;
    private History history;
    private String imagePath = "http://littlejoy.co.in/admin_parilok/api/";
    private String history_url = "http://192.168.1.10/admin_parilok/users/history.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*****************(Start) code For Card View*****************/

        recyclerView = (RecyclerView) findViewById(R.id.history_recyclerView);
        historyList = new ArrayList<>();
        adapter = new HistoryAdapter(this, historyList);

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

        Intent intent = new Intent(this, HistoryDetailActivity.class);

        intent.putExtra("image", product_image);
        intent.putExtra("p_name", product_name);
        intent.putExtra("p_desc", product_desc);
        intent.putExtra("p_price", product_price);
        intent.putExtra("p_created", product_created);
        intent.putExtra("p_deliver", product_deliver);
        intent.putExtra("p_status", product_status);
        intent.putExtra("u_name", user_name);
        intent.putExtra("u_phone", user_phone);
        intent.putExtra("p_address", user_address);
        intent.putExtra("p_quantity", product_quantity);

        startActivity(intent);
    }

    private void signupRequest() {
        MyProgressDialog.showPDialog(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, history_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            if (jsonObject.getString("status").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.d("jsonObject", jsonObject + "");
                                Log.d("jsonArray", jsonArray + "");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject profile = jsonArray.getJSONObject(i);
                                    product_name = profile.getString("pr_name");
                                    product_desc = profile.getString("cat_name");
                                    product_image = imagePath + profile.getString("image");
                                    product_price = profile.getString("pr_sp");
                                    product_id = Integer.parseInt(profile.getString("product_id"));
                                    product_created = profile.getString("created");
                                    product_deliver = profile.getString("deliver");
                                    user_name = profile.getString("name");
                                    user_phone = profile.getString("phone");
                                    user_address = profile.getString("address");

                                    product_quantity = "Qty:" + profile.getString("quantity");
                                    if (profile.getInt("status") == 1) {
                                        product_status = "pending";
                                    } else {
                                        product_status = "Delivered ";
                                    }

                                    history = new History(product_desc, product_name, product_id, product_image, product_price, 1, product_status, product_quantity, product_created, product_deliver, user_name, user_phone, user_address);
                                    historyList.add(history);

                                    adapter.notifyDataSetChanged();

                                }
                                MyProgressDialog.hidePDialog();
                            } else {
                                MyProgressDialog.hidePDialog();
                                Toast.makeText(HistoryActivity.this, "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "6");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
