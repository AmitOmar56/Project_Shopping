package com.shopping.lifeplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.shopping.lifeplay.utils.MyProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import static com.shopping.lifeplay.utils.utils.isValidEmail;

public class ForgotPasswordActivity extends AppCompatActivity {

    private LinearLayout successrellay;
    private LinearLayout forgotlayout;
    private TextView userEmailId;
    private String email;
    private TextView success_email;
    private static Animation shakeAnimation;
    private String forgot_api = "http://littlejoy.co.in/admin_parilok/users/forgot_password.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getId();
    }

    private void getId() {
        successrellay = (LinearLayout) findViewById(R.id.successrellay);
        forgotlayout = (LinearLayout) findViewById(R.id.forgotlayout);
        userEmailId = (TextView) findViewById(R.id.userEmailId);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        success_email = (TextView) findViewById(R.id.success_email);

    }

    public void forgot(View view) {
        email = userEmailId.getText().toString();

        if (email.isEmpty()) {
            successrellay.startAnimation(shakeAnimation);
            userEmailId.setError(getResources().getString(R.string.enter_email));
        } else if (!isValidEmail(email)) {
            successrellay.startAnimation(shakeAnimation);
            userEmailId.setError(getResources().getString(R.string.enter_valid_email));
        } else {
            MyProgressDialog.showPDialog(this);
            forgotApiCall();
        }
    }

    private void forgotApiCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, forgot_api,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            if (jsonObject.getString("status").equals("STATUS_SUCCESS")) {
                                successrellay.setVisibility(View.GONE);
                                forgotlayout.setVisibility(View.VISIBLE);
                                success_email.setText(email);
                                MyProgressDialog.hidePDialog();

                            } else {
                                forgotlayout.setVisibility(View.GONE);
                                userEmailId.setError(getResources().getString(R.string.enter_email));
                                Toast.makeText(ForgotPasswordActivity.this, "No data Found", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(ForgotPasswordActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

//                params.put("phone", phone);
//                params.put("pass", password);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

}
