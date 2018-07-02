package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.utils.MyProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import static com.shopping.lifeplay.adapter.CartAdapter.item;

public class CheckOutSalonATHomeActivity extends AppCompatActivity {

    private EditText user_nameAdd, user_phoneAdd, user_emailAdd, editText_city, editText_locality, editText_flatno, editText_pincode, editText_state, editText_landmark;
    private String addressApi = "http://192.168.1.11/admin_parilok/users/address.php";
    private String id_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_salon_athome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDatafromIntent();
        getId();

    }

    public void getDatafromIntent() {
        Log.d("getIntent", getIntent() + "");
        if (getIntent() != null) {
            id_option = getIntent().getStringExtra("option_id");
        }
    }

    private void getId() {
        user_nameAdd = (EditText) findViewById(R.id.user_nameAdd);
        user_phoneAdd = (EditText) findViewById(R.id.user_phoneAdd);
        user_emailAdd = (EditText) findViewById(R.id.user_emailAdd);
        editText_city = (EditText) findViewById(R.id.editText_city);
        editText_locality = (EditText) findViewById(R.id.editText_locality);
        editText_flatno = (EditText) findViewById(R.id.editText_flatno);
        editText_pincode = (EditText) findViewById(R.id.editText_pincode);
        editText_state = (EditText) findViewById(R.id.editText_state);
        editText_landmark = (EditText) findViewById(R.id.editText_landmark);

    }


    private void addressApicall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, addressApi,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;

                            Toast.makeText(CheckOutSalonATHomeActivity.this, jsonObject.getString("data"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(CheckOutSalonATHomeActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", "6");
                params.put("pincode", "6");
                params.put("cart_qty", item + "");

                return params;
            }
        };
        postRequest.setRetryPolicy(new

                DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    public void goToPaymentMode(View view) {
        if (id_option.equals("deals")) {
            startActivity(new Intent(this, PaymentModeforDealsActivity.class));
        } else
            startActivity(new Intent(this, PaymentModeActivity.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
