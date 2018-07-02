package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.customtoast.CustomToast;
import com.shopping.lifeplay.utils.MyProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import static com.shopping.lifeplay.utils.utils.isValidEmail;


public class RegisterActivity extends AppCompatActivity {

    private static Animation shakeAnimation;
    private ScrollView scrollView;
    private EditText fullName, userEmailId, mobileNumber, user_password;
    private String register_url = "http://littlejoy.co.in/admin_parilok/users/register.php";
    private String name, email, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getId();
    }

    private void getId() {
        fullName = (EditText) findViewById(R.id.fullName);
        userEmailId = (EditText) findViewById(R.id.userEmailId);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        user_password = (EditText) findViewById(R.id.password);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }

    private void registerApiCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, register_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            if (jsonObject.getString("status").equals("STATUS_SUCCESS")) {

                                Toast.makeText(RegisterActivity.this, jsonObject.getString("data"), Toast.LENGTH_LONG).show();
                                MyProgressDialog.hidePDialog();

                            } else {
                                scrollView.startAnimation(shakeAnimation);
                                new CustomToast().Show_Toast(RegisterActivity.this, scrollView,
                                        "Already Register");
                                Toast.makeText(RegisterActivity.this, "No data Found", Toast.LENGTH_LONG).show();
                                MyProgressDialog.hidePDialog();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("pass", password);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public void alreadylogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void signUp(View view) {

        boolean isExecuteNext = true;
        name = fullName.getText().toString();
        email = userEmailId.getText().toString();
        phone = mobileNumber.getText().toString();
        password = user_password.getText().toString();

        if (name.isEmpty()) {
            Log.d("name", name + "");
            fullName.setError(getResources().getString(R.string.name_error));
            isExecuteNext = false;
        }

        if (email.isEmpty()) {
            userEmailId.setError(getResources().getString(R.string.enter_email));
            isExecuteNext = false;
        } else if (!isValidEmail(email)) {
            userEmailId.setError(getResources().getString(R.string.enter_valid_email));
            isExecuteNext = false;
        }
        if (phone.isEmpty()) {
            mobileNumber.setError(getResources().getString(R.string.Phone_error));
            isExecuteNext = false;
        } else if (phone.length() < 10) {
            mobileNumber.setError(getResources().getString(R.string.Phone_length_validation));
            isExecuteNext = false;
        }
        if (password.isEmpty()) {
            Log.d("password", password + "");
            user_password.setError(getResources().getString(R.string.password_error));
            isExecuteNext = false;
        }

        if (!isExecuteNext) {
            //registerLayout.startAnimation(shakeAnimation);
            scrollView.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(this, view,
                    "All fields are required.");
            return;
        } else {
            MyProgressDialog.showPDialog(this);

            Log.d("data->>>>>>>>>>>>>>", name + email + phone + password);
            registerApiCall();
        }


    }
}
