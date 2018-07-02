package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.utils.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView saveAddress, user_profile_name, user_profile_mobile, user_profile_email;
    private ImageView user_profile_photo;
    private String user_name;
    private String user_email;
    private String user_phone;
    private String user_address;
    private String user_image;
    private String imageUrl = "http://littlejoy.co.in/admin_parilok/api/";
    private String url = "http://192.168.1.11/admin_parilok/users/profile_get.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getId();
        signupRequest();

        saveAddress.setText(user_address);
        user_profile_name.setText(user_name);
        user_profile_mobile.setText(user_phone);
        user_profile_email.setText(user_email);
        Glide.with(this).load(user_image).into(user_profile_photo);
    }

    private void getId() {
        saveAddress = (TextView) findViewById(R.id.saveAddress);
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);
        user_profile_mobile = (TextView) findViewById(R.id.user_profile_mobile);
        user_profile_email = (TextView) findViewById(R.id.user_profile_email);
        user_profile_photo = (ImageView) findViewById(R.id.user_profile_photo);
    }

    private void signupRequest() {
        MyProgressDialog.showPDialog(this);

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
                            if (jsonObject.getString("status").equals("STATUS_SUCCESS")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.d("jsonObject", jsonObject + "");
                                Log.d("jsonArray", jsonArray + "");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject profile = jsonArray.getJSONObject(i);
                                    user_name = profile.getString("name");
                                    user_email = profile.getString("email");
                                    user_phone = profile.getString("phone");
                                    user_address = profile.getString("address");
                                    user_image = imageUrl + profile.getString("image");

                                }
                                MyProgressDialog.hidePDialog();
                            } else {
                                MyProgressDialog.hidePDialog();
                                Toast.makeText(ProfileActivity.this, "No data Found", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "6" + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    public void myWallet(View view) {
        startActivity(new Intent(this, WalletActivity.class));

    }

    public void allOrder(View view) {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    public void editAddress(View view) {
//        startActivity(new Intent(this, ProductBookingDetailActivity.class));
    }

    public void profileEdit(View view) {
        startActivity(new Intent(this, ProfileEditActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void logout(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
