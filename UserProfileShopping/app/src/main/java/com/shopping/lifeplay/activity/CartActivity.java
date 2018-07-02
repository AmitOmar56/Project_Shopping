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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.CartAdapter;
import com.shopping.lifeplay.model.Cart;
import com.shopping.lifeplay.utils.MyProgressDialog;
import com.shopping.lifeplay.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import static com.shopping.lifeplay.activity.HomeActivity.cartList;
import static com.shopping.lifeplay.activity.HomeActivity.itemofcart;
import static com.shopping.lifeplay.adapter.CartAdapter.item;
import static com.shopping.lifeplay.utils.utils.dpToPx;

public class CartActivity extends AppCompatActivity implements CartAdapter.Product_OnItemClicked, CartAdapter.Product_OnClicked, CartAdapter.Product_On_spin_Clicked {

    private RecyclerView recyclerView;
    private CartAdapter adapter = null;
    //    private List<Cart> cartList;
    private int price = 0;
    private TextView totalcartPrice;
    private String product_name;
    private String product_disc;
    private String product_price;
    private String product_cut_price;
    private String product_image;
    private int product_id;
    private String product_cat_name;
    private Cart cart;
    private String url = "http://192.168.1.10/admin_parilok/users/cart_get.php";
    private String cart_delete_url = "http://192.168.1.10/admin_parilok/users/cart_del.php";
    private int key_id;
    private String cartQtyapi = "http://192.168.1.10/admin_parilok/users/cart_qty.php";
    private int product_spin_id;
    private int product_qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signupRequest();
        totalcartPrice = (TextView) findViewById(R.id.total_cartPrice);
        for (int i = 0; i < cartList.size(); i++) {
            Cart cart = cartList.get(i);
            price = price + cart.getProduct_price();
        }
        totalcartPrice.setText("Rs." + price + "");

        /*****************(Start) code For Card View*****************/

        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerView);
//        cartList = new ArrayList<>();
        adapter = new CartAdapter(this, cartList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        adapter.setClick(this);
        adapter.setOn_Spin_Click(this);

        /*****************(End) code For Card View Vertical*****************/


    }

    @Override
    public void Product_onItemClick(int position) {
        Cart cart = cartList.get(position);
        Toast.makeText(this, cart.getProduct_price() + "", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("image", cart.getProduct_image());
        intent.putExtra("p_name", cart.getProduct_name());
        intent.putExtra("p_id", cart.getProduct_id() + "");
        intent.putExtra("p_price", cart.getProduct_price() + "");
        startActivity(intent);
        Toast.makeText(this, cart.getProduct_id() + "getProduct_id", Toast.LENGTH_LONG).show();
    }

    protected void setCartLayout() {
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        if (itemofcart > 0) {
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        } else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CartActivity.this, ShoppingActivity.class));
                }
            });
        }
    }

    protected void setCart() {

        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);
        layoutCartNoItems.setVisibility(View.VISIBLE);
        layoutCartItems.setVisibility(View.GONE);
        layoutCartPayments.setVisibility(View.GONE);

        Button bStartShopping = (Button) findViewById(R.id.bAddNew);
        bStartShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, ShoppingActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void signupRequest() {
        MyProgressDialog.showPDialog(this);
        cartList.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                                itemofcart = jsonArray.length();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject profile = jsonArray.getJSONObject(i);

                                    product_name = profile.getString("pr_name");
                                    product_cat_name = profile.getString("cat_name");
                                    product_price = profile.getString("pr_sp");
                                    product_image = profile.getString("image");
                                    product_image = "http://littlejoy.co.in/admin_parilok/api/" + product_image;
                                    product_id = Integer.parseInt(profile.getString("product_id"));
                                    product_qty = profile.getInt("cart_qty");

                                    cart = new Cart(product_cat_name, product_name, product_id, product_image, Integer.parseInt(product_price), product_qty);
                                    cartList.add(cart);

                                    adapter.notifyDataSetChanged();
                                }
                                setCartLayout();

                                MyProgressDialog.hidePDialog();
                            } else {
                                MyProgressDialog.hidePDialog();
                                Toast.makeText(CartActivity.this, "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, "Network Error", Toast.LENGTH_LONG).show();
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

    private void cartDelete() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, cart_delete_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            if (jsonObject.getString("status").equals("1")) {
                                setCartLayout();
                            } else {
                                Toast.makeText(CartActivity.this, "No data Found", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "6");
                params.put("product_id", key_id + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    private void cartqtyApiCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, cartQtyapi,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;

                            Toast.makeText(CartActivity.this, jsonObject.getString("data"), Toast.LENGTH_LONG).show();
                            if (jsonObject.getString("status").equals("1")) {

                            } else {

                            }
                            MyProgressDialog.hidePDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "6");
                params.put("product_id", product_spin_id + "");
                params.put("cart_qty", item + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new

                DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    @Override
    public void Product_onClick(int position) {


        Cart cart = cartList.get(position);
        key_id = cart.getProduct_id();
        cartDelete();
        Toast.makeText(this, "Enter", Toast.LENGTH_LONG).show();
        price = 0;
        for (int i = 0; i < cartList.size(); i++) {
//            if (cartList.get(position) == cartList.get(i)) {
//                cart.setProduct_quantity(cart.getProduct_quantity() + item);
//                Toast.makeText(this, "asd" + cart.getProduct_quantity(), Toast.LENGTH_LONG).show();
//            }
//            Toast.makeText(this, "asd" + cart.getProduct_quantity(), Toast.LENGTH_LONG).show();
            if (position != i) {
                cart = cartList.get(i);
                price = price + cart.getProduct_price();
            }
        }
        totalcartPrice.setText("Rs." + price + "");

    }

    public void cartAddressDetail(View view)
    {
        //made changes here
       // startActivity(new Intent(this,TimeSlotActivity.class));
       //
         startActivity(new Intent(this, CheckOutSalonATHomeActivity.class));
    }

    @Override
    public void Product_on_spin_Clicked(int position) {
        Cart cart = cartList.get(position);
        product_spin_id = cart.getProduct_id();
        cartqtyApiCall();
        price = 0;
        Toast.makeText(CartActivity.this, cart.getProduct_id() + cart.getProduct_name() + "clicked_on" + item + "", Toast.LENGTH_LONG).show();

        for (int i = 0; i < cartList.size(); i++) {
            if (position == i) {
                cart = cartList.get(i);
                price = price + cart.getProduct_price() * item;
            } else {
                cart = cartList.get(i);
                price = price + cart.getProduct_price();
            }
            if (cartList.get(position) == cartList.get(i)) {
                cart.setProduct_quantity(cart.getProduct_quantity() + item);
            }
            totalcartPrice.setText("Rs." + price + "");
        }
    }
}