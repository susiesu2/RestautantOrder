package com.visa.android.integration.checkoutsampleapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Spinner;


public class TheMenu extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_menu);

    }

    public void next(View view) {
        String[] qty = new String[5];
        int id = 0;
        Spinner spinner1 = (Spinner) findViewById(R.id.Q1);
        String q1 = spinner1.getSelectedItem().toString();
        qty[id++] = q1;
        Spinner spinner2 = (Spinner) findViewById(R.id.Q2);
        String q2 = spinner2.getSelectedItem().toString();
        qty[id++] = q2;
        Spinner spinner3 = (Spinner) findViewById(R.id.Q3);
        String q3 = spinner3.getSelectedItem().toString();
        qty[id++] = q3;
        Spinner spinner4 = (Spinner) findViewById(R.id.Q4);
        String q4 = spinner4.getSelectedItem().toString();
        qty[id++] = q4;
        Spinner spinner5 = (Spinner) findViewById(R.id.Q5);
        String q5 = spinner5.getSelectedItem().toString();
        qty[id++] = q5;
        Intent i = new Intent(getApplicationContext(), Summary.class);
        Bundle b = new Bundle();
        b.putStringArray("qty", qty);
        i.putExtras(b);
        startActivity(i);
        finish();
    }


    public void back(View view) {
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
/*
    public void goToSummary(View view) {
        Intent i =new Intent(this,Summary.class);
        startActivity(i);
    }
*/

}
