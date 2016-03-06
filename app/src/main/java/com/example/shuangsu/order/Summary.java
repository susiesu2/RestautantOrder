package com.example.shuangsu.order;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Summary extends AppCompatActivity {
    //int tableNo;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        Resources res = getResources();
        String[] food = res.getStringArray(R.array.food);
        String[] price = res.getStringArray(R.array.price);
        Bundle b = this.getIntent().getExtras();
        String[] qty = b.getStringArray("qty");
        //tableNo = b.getInt("tableNo");
        Double sum = 0.0;
        int length = res.getInteger(R.integer.length);
        for (int i = 0; i < length; i++){
            if (Integer.parseInt(qty[i]) == 0) continue;

            sum += Double.parseDouble(price[i]) * Integer.parseInt(qty[i]);

            TableRow tr = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(lp);

            TextView tvLeft = new TextView(this);
            tvLeft.setLayoutParams(lp);
            tvLeft.setBackgroundColor(Color.LTGRAY);
            tvLeft.setTextColor(Color.BLACK);
            tvLeft.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvLeft.setText(food[i]);

            TextView tvCenter = new TextView(this);
            tvCenter.setLayoutParams(lp);
            tvCenter.setBackgroundColor(Color.LTGRAY);
            tvCenter.setTextColor(Color.BLACK);
            tvCenter.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvCenter.setText(price[i]);

            TextView tvRight = new TextView(this);
            tvRight.setLayoutParams(lp);
            tvRight.setBackgroundColor(Color.LTGRAY);
            tvRight.setTextColor(Color.BLACK);
            tvRight.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvRight.setText(qty[i]);

            tr.addView(tvLeft);
            tr.addView(tvCenter);
            tr.addView(tvRight);

            tl.addView(tr, lp);
        }
        TextView sub = (TextView) findViewById(R.id.subtotal);
        TextView tax = (TextView) findViewById(R.id.tax);
        TextView total = (TextView) findViewById(R.id.total);
        sub.append(" $" + sum);
        double taxValue = Math.round(sum * 0.0875 * 100.0) / 100.0;
        tax.append(" $" + taxValue);
        amount = sum + taxValue;
        total.append(" $" + amount);
        TextView table = (TextView) findViewById(R.id.table);
        /*
        if (tableNo != -1){
            table.setText(R.string.table + tableNo);
        } else {
            table.setText(R.string.prompt);
        }
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        //int hour = timePicker.getHour();
        //int min = timePicker.getMinute();
        */
    }

    public void back(View view) {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
    /*
    public void next(View view){
        Intent i = new Intent(getApplicationContext(), Payment.class);
        Bundle b = new Bundle();
        //b.putInt("tableNo", tableNo);
        b.putDouble("total", amount);
        i.putExtras(b);
        startActivity(i);
    }
    */
}
