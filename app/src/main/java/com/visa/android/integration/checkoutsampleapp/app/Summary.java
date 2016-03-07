package com.visa.android.integration.checkoutsampleapp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Summary extends Activity {
    //String tableNo ;
    public static double amount;

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
        //tableNo = b.getString("tableNo");
        //tableNo = "3";
        Double sum = 0.0;
        int length = res.getInteger(R.integer.length);
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(qty[i]) == 0) continue;

            sum += Double.parseDouble(price[i]) * Integer.parseInt(qty[i]);

            TableRow tr = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tr.setLayoutParams(lp);

            TextView tvLeft = new TextView(this);
            tvLeft.setLayoutParams(lp);
            //tvLeft.setBackgroundColor(Color.LTGRAY);
            tvLeft.setTextColor(Color.WHITE);
            tvLeft.setGravity(Gravity.CENTER);
            tvLeft.setText(food[i]);

            TextView tvCenter = new TextView(this);
            tvCenter.setLayoutParams(lp);
            //tvCenter.setBackgroundColor(Color.LTGRAY);
            tvCenter.setTextColor(Color.WHITE);
            tvCenter.setGravity(Gravity.CENTER);
            tvCenter.setText(price[i]);

            TextView tvRight = new TextView(this);
            tvRight.setLayoutParams(lp);
            //tvRight.setBackgroundColor(Color.LTGRAY);
            tvRight.setTextColor(Color.WHITE);
            tvRight.setGravity(Gravity.CENTER);
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

        if ( MainActivity.tableNo != null){
            table.setText("You are at table " + MainActivity.tableNo);
        } else {
            table.setText(R.string.prompt);
            /*
            TimePicker timePicker = new TimePicker(this);
            TimePicker.LayoutParams tlp= new TimePicker.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timePicker.setLayoutParams(tlp);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
            linearLayout.addView(linearLayout);
            */
        }

        //int hour = timePicker.getHour();
        //int min = timePicker.getMinute();

    }
    public static final int VXO = 2;
    public void onButtonSelection(View view){
        Intent intent = new Intent(this, PaymentStartActivity.class);
        if(view.getId() == R.id.btn_vxo) {
            intent.putExtra("buttonType", VXO);
        }
        startActivity(intent);

    }

    public void back(View view) {
        Intent i = new Intent(this, TheMenu.class);
        startActivity(i);
    }

    public void goToPay(View view) {
        Intent i =new Intent(this,ConfigurePaymentActivity.class);
        startActivity(i);
    }
}
