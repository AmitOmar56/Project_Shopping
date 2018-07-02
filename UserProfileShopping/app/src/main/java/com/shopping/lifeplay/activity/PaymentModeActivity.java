package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.paymentgatway.WebViewActivity;
import com.shopping.lifeplay.utils.AvenuesParams;
import com.shopping.lifeplay.utils.ServiceUtility;


public class PaymentModeActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    String checkBox = "";
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getId();
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (null != radioButton && checkedId > -1) {
                    checkBox = radioButton.getText().toString();
                    Toast.makeText(PaymentModeActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getId() {
        radioGroup = (RadioGroup) findViewById(R.id.rd);
    }

    public void goToPaymentGateway(View view) {
        if (checkBox.equals("Cash on Delivery")) {

            Toast.makeText(PaymentModeActivity.this, "welcome", Toast.LENGTH_SHORT).show();
        }
        if ((checkBox.equals("Online Payment"))) {
            paymentPage();
            // startActivity(new Intent(this, InitialScreenActivity.class));
        } else {
            Toast.makeText(this, "Please Click ", Toast.LENGTH_LONG).show();
        }
    }

    private void paymentPage() {

        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull("AVTG76FB43BS37GTSB").toString().trim());
        intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(166015).toString().trim());
        intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(order_id).toString().trim());
        intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull("INR").toString().trim());
        intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull("1.00").toString().trim());

        intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull("http://esaymart.com/mobile/ccavResponseHandler.php").toString().trim());
        intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull("http://esaymart.com/mobile/ccavResponseHandler.php").toString().trim());
        intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull("http://esaymart.com/mobile/GetRSA.php").toString().trim());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //generating new order number for every transaction
        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        order_id = randomNum.toString();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
