package com.visa.android.integration.checkoutsampleapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.visa.checkout.VisaLibrary;
import com.visa.checkout.VisaPaymentSummary;

import static com.visa.android.integration.checkoutsampleapp.app.ConfigureVisaPaymentInfo.VISA_CHECKOUT_REFERENCE_ID_KEY;
import static com.visa.android.integration.checkoutsampleapp.app.ConfigureVisaPaymentInfo.VISA_CHECKOUT_RESULT_MSG_KEY;

public class PaymentSummaryActivity extends Activity {

    private String referenceCallId;
    private Button btEditPayment;
    private TextView tvPaymentSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);

        btEditPayment = (Button) findViewById(R.id.bt_edit_visa_payment);
        tvPaymentSummary = (TextView) findViewById((R.id.tv_visa_payment_summary));

        showVisaPaymentSummary();
    }

    public void showVisaPaymentSummary() {
        String paymentSummaryMessage = getIntent().getExtras().getString(VISA_CHECKOUT_RESULT_MSG_KEY);

        VisaPaymentSummary paymentSummary = getIntent().getExtras().getParcelable(VisaLibrary.PAYMENT_SUMMARY);
        if (paymentSummary != null) {
            paymentSummaryMessage +=
                        "\n\nCountry Code: " + paymentSummary.getCountryCode()
                        + "\nPostal Code : " + paymentSummary.getPostalCode()
                        + "\nLast Four Digits : " + paymentSummary.getLastFourDigits()
                        + "\nCard Brand : " + paymentSummary.getCardBrand()
                        + "\nCard Type : " + paymentSummary.getCardType();

            referenceCallId = paymentSummary.getCallId();
            if (!TextUtils.isEmpty(referenceCallId)) {
                // pass the call Id back to relaunched payment
                paymentSummaryMessage += "\nReference Call Id : " + referenceCallId;
                btEditPayment.setText(getString(R.string.bt_edit_visa_payment_text));
            }
        }
        tvPaymentSummary.setText(paymentSummaryMessage);
    }

    @Override
    public void onBackPressed() {
        backToPaymentStart(false);
    }

    public void editPayment(View v) {
        backToPaymentStart(true);
    }

    private void backToPaymentStart(boolean launchInEditPaymentMode) {
        // we are done with this activity
        finish();

        // start the payment activity again
        Intent intent = new Intent(this, PaymentStartActivity.class);
        if (launchInEditPaymentMode && !TextUtils.isEmpty(referenceCallId)) {
            intent.putExtra(VISA_CHECKOUT_REFERENCE_ID_KEY, referenceCallId);
        }
        intent.putExtra("buttonType", getIntent().getExtras().getInt("buttonType"));
        startActivity(intent);
    }
}

