package com.example.shuangsu.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void back(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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
    }

}
