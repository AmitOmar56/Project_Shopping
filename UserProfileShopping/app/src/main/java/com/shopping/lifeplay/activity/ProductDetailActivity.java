package com.shopping.lifeplay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.Cart;
import com.shopping.lifeplay.model.WishList;
import com.shopping.lifeplay.utils.BagdeDrawable;
import com.shopping.lifeplay.utils.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import static com.shopping.lifeplay.activity.HomeActivity.itemofcart;
public class ProductDetailActivity extends AppCompatActivity {

    private EditText devivery_Edittext;
    private TextView delivery_button, cod_available, delivery_days;
    private ImageView image1;
    private TextView p_price;
    private TextView p_name;
    private String pincode;
    //    private String pro_name;
//    private String pro_price;
//    private String pro_image;
    private Cart cart;
    private ImageView wishlistIcon;
    private WishList wishList;
    private int pro_id;
    private int key = 0;
    //    private String s_product_disc, s_product_how_to_apply, s_product_uses;
    private TextView product_disc, product_uses, product_how_to_apply;
    private static String cartUrl = "http://192.168.1.10/admin_parilok/users/cart.php";
    private TextView addToCart;
    private String product_detail_name;
    private String product_cat_name;
    private String product_price;
    private String product_cut_price;
    private String product_detail_disc;
    private String product_detail_uses;
    private String product_detail_how_to_apply;
    private String product_detail_image;
    private String imageUrl = "http://littlejoy.co.in/admin_parilok/api/";
    private int product_detail_id;
    private String detailUrl = "http://littlejoy.co.in/admin_parilok/users/product.php";
    private String cart_status;
    private String pinCode_url = "http://192.168.1.10/admin_parilok/users/pincode.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getId();
        getdatafromIntent();
        Toast.makeText(this, pro_id + "", Toast.LENGTH_LONG).show();

        product_DetailApi();

        devivery_Edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pincode = s + "";
                if (pincode.equals("")) {
                    delivery_button.setText("");
                    cod_available.setText("");
                    delivery_days.setText("");

                }
                if (pincode.length() != 6) {
                    delivery_button.setText("");
                    cod_available.setText("");
                    delivery_days.setText("");
                } else {
                    delivery_button.setText("Check");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        delivery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delivery_button.getText().equals("Check")) {
                    pinCodeApiCall();
                }
            }
        });
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
        setBadgeCount(this, icon, itemofcart + "");        // force the ActionBar to relayout its MenuItems.
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

    private void getdatafromIntent() {
        if (getIntent() != null) {
            pro_id = Integer.parseInt(getIntent().getStringExtra("p_id"));
        }
    }

    private void getId() {
        devivery_Edittext = (EditText) findViewById(R.id.devivery_Edittext);
        delivery_button = (TextView) findViewById(R.id.delivery_button);
        cod_available = (TextView) findViewById(R.id.cod_available);
        delivery_days = (TextView) findViewById(R.id.delivery_days);
        image1 = (ImageView) findViewById(R.id.image1);
        p_name = (TextView) findViewById(R.id.p_name);
        p_price = (TextView) findViewById(R.id.p_price);
        wishlistIcon = (ImageView) findViewById(R.id.wishlistIcon);
        product_disc = (TextView) findViewById(R.id.product_disc);
        product_uses = (TextView) findViewById(R.id.product_uses);
        product_how_to_apply = (TextView) findViewById(R.id.product_how_to_apply);
        addToCart = (TextView) findViewById(R.id.addToCart);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {

            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);

        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("jsonObject", jsonObject + "");
                            Log.d("jsonArray", jsonArray + "");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject profile = jsonArray.getJSONObject(i);
                                product_detail_name = profile.getString("pr_name");
                                product_price = profile.getString("pr_price");
                                product_cut_price = profile.getString("pr_sp");
                                product_detail_disc = profile.getString("pr_desc");
                                product_detail_uses = profile.getString("uses");
                                product_detail_how_to_apply = profile.getString("how_to_apply");
                                product_detail_image = profile.getString("image");
                                product_detail_image = imageUrl + product_detail_image;
                                product_detail_id = Integer.parseInt(profile.getString("product_id"));
                                cart_status = profile.getString("user_id");

                                if (product_detail_id == pro_id) {
                                    product_disc.setText(product_detail_disc);
                                    product_uses.setText(product_detail_uses);
                                    product_how_to_apply.setText(product_detail_how_to_apply);
                                    Glide.with(ProductDetailActivity.this).load(product_detail_image).into(image1);
                                    p_name.setText(product_detail_name);
                                    p_price.setText("Rs. " + product_price);
                                    if (cart_status.equals("6")) {
                                        addToCart.setText("GO TO CART");
                                        Toast.makeText(ProductDetailActivity.this, "GO TO CART", Toast.LENGTH_LONG).show();
                                    } else {
                                        addToCart.setText("ADD TO CART");
                                    }
                                    break;
                                }

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
                        Toast.makeText(ProductDetailActivity.this, "Network Error", Toast.LENGTH_LONG).show();

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


    private void addtocartApiCall() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, cartUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetailActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "6");
                params.put("product_id", pro_id + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    private void pinCodeApiCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, pinCode_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;

                            Toast.makeText(ProductDetailActivity.this, jsonObject.getString("data"), Toast.LENGTH_LONG).show();
                            if (jsonObject.getString("status").equals("1")) {
                                cod_available.setText("Delivery Available");
                                delivery_days.setText("Delivery in 4-5 days");
                            } else {
                                cod_available.setText("COD Not Available");
                                delivery_days.setText("");
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
                        Toast.makeText(ProductDetailActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("pincode", pincode);

                return params;
            }
        };
        postRequest.setRetryPolicy(new

                DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public void addtoCart(View view) {

        /*******************Code for RecyclerView****************/
//        Toast.makeText(this, "Add to Cart", Toast.LENGTH_LONG).show();
//
//        for (Cart cart : cartList) {
//
//            if (cart.getProduct_id() == pro_id) {
//                key = 1;
//                cart.setProduct_quantity(cart.getProduct_quantity() + 1);
//                cart.setProduct_price(cart.getProduct_price() + Integer.parseInt(pro_price));
//                break;
//            }
//        }
//        if (key == 0) {
//            cart = new Cart("Face & beuty", pro_name, pro_id, pro_image, Integer.parseInt(pro_price), 1);
//            cartList.add(cart);
//            itemofcart++;
//        }
//        key = 0;
        if (addToCart.getText().equals("ADD TO CART")) {
            addToCart.setText("GO TO CART");
            addtocartApiCall();
        } else if (addToCart.getText().equals("GO TO CART")) {
            startActivity(new Intent(this, CartActivity.class));
        }
    }

    public void buynow(View view) {
//        for (Cart cart : cartList) {
//            if (cart.getProduct_id() == pro_id) {
//                key = 1;
//                startActivity(new Intent(this, CartActivity.class));
//                break;
//            }
//        }
//
//        if (key == 0) {
//            cart = new Cart("Face & beuty", pro_name, pro_id, pro_image, Integer.parseInt(pro_price), 1);
//            cartList.add(cart);
//            itemofcart++;
        addtocartApiCall();
        startActivity(new Intent(this, CartActivity.class));
//        }
//        key = 0;
    }

//    public void detailaddtoCart(View view) {
//        wishList = new WishList("Face & beuty", pro_name, 1, pro_image, pro_price);
//        wishListList.add(wishList);
//        Glide.with(this).load(R.drawable.ic_favorite_black_18dp).into(wishlistIcon);
//    }
}
