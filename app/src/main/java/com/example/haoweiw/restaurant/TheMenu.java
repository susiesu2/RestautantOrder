package com.example.haoweiw.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class TheMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_menu);

        TextView t1 = (TextView) findViewById(R.id.des1);
        t1.setText(Html.fromHtml("<p>Local Thai sausages, cilantro mashed potatoes</p>"));

        TextView t2 = (TextView) findViewById(R.id.des2);
        t2.setText(Html.fromHtml("<p>Boneless buttermilk chicken, fried and served on a cornbread waffle with bbq syrup</p>"));

        TextView t3 = (TextView) findViewById(R.id.des3);
        t3.setText(Html.fromHtml("<p>Graded jumbo tiger shrimps, sourced scallops, tender squid, and New-Zealand mussels tossed" +
                "in Thai spicy and sweet dressing</p>"));

        TextView t4 = (TextView) findViewById(R.id.des4);
        t4.setText(Html.fromHtml("<p>Teriyaki-garlic flavored burger, topped with grilled pineapple and served with lightly " +
                "seasoned coleslaw</p>"));

        TextView t5 = (TextView) findViewById(R.id.des5);
        t5.setText(Html.fromHtml("<p>Steak lettuce wraps filled with thinly sliced steak, carrots and onion tossed with a " +
                "spicy chili garlic sauce</p>"));

        Spinner spinner1 = (Spinner) findViewById(R.id.Q1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Q1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = (Spinner) findViewById(R.id.Q2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Q1, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = (Spinner) findViewById(R.id.Q3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Q1, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

    }


    public void onClick(View view) {
        Intent i =new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void GotoPay(View view) {
        Intent i = new Intent(this, AndroidPay.class);
        startActivity(i);
    }


}
