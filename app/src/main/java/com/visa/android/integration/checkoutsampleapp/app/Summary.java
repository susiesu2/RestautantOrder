package com.visa.android.integration.checkoutsampleapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

public class Summary extends Activity {
    public static double amount;
    public static String time;
    private TimePicker timePicker;
    private final double taxRate = 0.0875;

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
        Double sum = 0.00;
        int length = res.getInteger(R.integer.length);
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(qty[i]) == 0) continue;

            sum += Double.parseDouble(price[i].substring(1)) * Integer.parseInt(qty[i]);

            TableRow tr = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(lp);

            TextView tvLeft = new TextView(this);
            tvLeft.setLayoutParams(lp);
            tvLeft.setTextColor(Color.WHITE);
            tvLeft.setGravity(Gravity.CENTER);
            tvLeft.setText(food[i]);
            tvLeft.setWidth(300);
            tvLeft.setPadding(0, 0, 0, 0);


            TextView tvCenter = new TextView(this);
            tvCenter.setLayoutParams(lp);
            tvCenter.setTextColor(Color.WHITE);
            tvCenter.setGravity(Gravity.CENTER);
            tvCenter.setText(price[i]);
            //tvCenter.setWidth(80);
            tvCenter.setPadding(60, 0, 0, 0);


            TextView tvRight = new TextView(this);
            tvRight.setLayoutParams(lp);
            tvRight.setTextColor(Color.WHITE);
            tvRight.setGravity(Gravity.CENTER);
            tvRight.setText(qty[i]);
            tvRight.setWidth(10);
            tvRight.setPadding(150, 0, 0, 0);


            tr.addView(tvLeft);
            tr.addView(tvCenter);
            tr.addView(tvRight);

            tl.addView(tr, lp);
        }
        TextView sub = (TextView) findViewById(R.id.subtotal);
        TextView tax = (TextView) findViewById(R.id.tax);
        TextView total = (TextView) findViewById(R.id.total);
        sum = Math.round(sum * 100.0) / 100.0;
        sub.append(" $" + sum);
        double taxValue = Math.round((sum * taxRate) * 100.0) / 100.0;
        tax.append(" $" + taxValue);
        amount = Math.round((sum + taxValue) * 100.0) / 100.0;
        total.append(" $" + amount);

        TextView table = (TextView) findViewById(R.id.table);
        if ( MainActivity.tableNo != null){
            String message = getResources().getString(R.string.table);
            table.setText(message + " " + MainActivity.tableNo);
        } else {
            table.setText(R.string.prompt);
            timePicker = new TimePicker(this);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            linearLayout.addView(timePicker);
            //timePicker.clearFocus();
        }
    }

    public void getTime() {
        String format;
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }else if (hour == 12) {
            format = "PM";
        }else if (hour > 12){
            hour -= 12;
            format = "PM";
        }else {
            format = "AM";
        }
        time = new StringBuilder().append(hour).append(":").append(minute).append(" ").append(format).toString();
    }

    public static final int VXO = 2;
    public void onButtonSelection(View view){
        if (MainActivity.tableNo == null) {
            getTime();
        }
        Intent intent = new Intent(this, PaymentStartActivity.class);
        if(view.getId() == R.id.btn_vxo) {
            intent.putExtra("buttonType", VXO);
        }
        startActivity(intent);
        finish();

    }

    public void back(View view) {
        Intent i = new Intent(this, TheMenu.class);
        startActivity(i);
        finish();
    }
    /*
    public void goToPay(View view) {

        Intent i =new Intent(this,ConfigurePaymentActivity.class);
        startActivity(i);
        finish();
    }*/
}
