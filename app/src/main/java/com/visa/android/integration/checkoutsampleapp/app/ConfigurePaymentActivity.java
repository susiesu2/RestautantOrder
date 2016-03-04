package com.visa.android.integration.checkoutsampleapp.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ConfigurePaymentActivity extends Activity{

    public static final int EXO = 1;
    public static final int VXO = 2;
    public static final int CUSTOM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onButtonSelection(View view){
        Intent intent = new Intent(this, PaymentStartActivity.class);
        if(view.getId() == R.id.btn_exo) {
            intent.putExtra("buttonType", EXO);
        }else  if(view.getId() == R.id.btn_vxo) {
            intent.putExtra("buttonType", VXO);
        }else  if(view.getId() == R.id.btn_custom) {
            intent.putExtra("buttonType", CUSTOM);
        }
        startActivity(intent);

    }
}


