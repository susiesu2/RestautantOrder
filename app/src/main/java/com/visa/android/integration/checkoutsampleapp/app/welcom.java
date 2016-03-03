package com.visa.android.integration.checkoutsampleapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class welcom extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
    }
    public void onClick2(View view) {
        Intent iii =new Intent(this,menu.class);
        startActivity(iii);
    }

}
