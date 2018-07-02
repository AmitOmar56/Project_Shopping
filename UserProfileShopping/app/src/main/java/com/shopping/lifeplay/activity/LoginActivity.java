package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
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


public class LoginActivity extends AppCompatActivity {

    private EditText login_phoneid, login_password;
    private static Animation shakeAnimation;
    private RelativeLayout relativeLayout;
    private String login_url = "http://littlejoy.co.in/admin_parilok/users/loginn.php";
    private String phone, password;
    private static CheckBox show_hide_password;
    static String s_user_id;
    static String s_phone;
    private String user_id;
    private String user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getId();
        setListeners();
    }

    private void getId() {
        login_phoneid = (EditText) findViewById(R.id.login_phoneid);
        login_password = (EditText) findViewById(R.id.login_password);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
    }

    // Set Listeners
    private void setListeners() {
        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            login_password.setInputType(InputType.TYPE_CLASS_TEXT);
                            login_password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            login_password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            login_password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    private void registerApiCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, login_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            if (jsonObject.getString("status").equals("STATUS_SUCCESS")) {
                                user_id = jsonObject.getString("user_id");
                                user_phone = jsonObject.getString("phone");
                                s_user_id = user_id;
                                //   insert();
                                //  Toast.makeText(LoginActivity.this, jsonObject.getString("data"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                intent.putExtra("phone", user_phone + "");
                                startActivity(intent);
                                MyProgressDialog.hidePDialog();

                            } else {
                                Toast.makeText(LoginActivity.this, "No data Found", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(LoginActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        MyProgressDialog.hidePDialog();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("phone", phone);
                params.put("pass", password);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public void gotoregister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void goToHomeScreen(View view) {
        boolean isExecuteNext = true;
        phone = login_phoneid.getText().toString();
        password = login_password.getText().toString();

        if (phone.isEmpty()) {
            login_phoneid.setError(getResources().getString(R.string.Phone_error));
            isExecuteNext = false;
        } else if (phone.length() < 10) {
            login_phoneid.setError(getResources().getString(R.string.Phone_length_validation));
            isExecuteNext = false;
        }
        if (password.isEmpty()) {
            Log.d("password", password + "");
            login_password.setError(getResources().getString(R.string.password_error));
            isExecuteNext = false;
        }

        if (!isExecuteNext) {
            //registerLayout.startAnimation(shakeAnimation);
            relativeLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(this, view,
                    "All fields are required.");
            return;
        } else {
            MyProgressDialog.showPDialog(this);

            Log.d("data->>>>>>>>>>>>>>", phone + password);
            registerApiCall();
        }

    }

    public void insert() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", 0);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("user_id", user_id);
        //edt.putString("phone", lphone);
        edt.putBoolean("activity_executed", true);
        edt.commit();
    }
}
