package com.visa.android.integration.checkoutsampleapp.app;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends FragmentActivity {



    private static final String TAG = "MainActivity";



    private TextView mTextView;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity_main);



        mTextView = (TextView) findViewById(R.id.tableNumberVar);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);


        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            mTextView.setText("NFC is disabled.");
        }




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                System.exit(0);
                return true;
            default:
                return false;
        }
    }


/*

    public void onClick(View view) {
        Intent i =new Intent(this,TheMenu.class);
        startActivity(i);
    }
*/
    @Override
    protected void onNewIntent(Intent intent) {

        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        Toast.makeText(this,"NFC intent received!", Toast.LENGTH_LONG).show();

        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (parcelables != null && parcelables.length > 0) {

            readTextFromMessage((NdefMessage)parcelables[0]);


        }else{
            Toast.makeText(this, "NO NDEF message found!", Toast.LENGTH_SHORT).show();
        }

        super.onNewIntent(intent);
    }


    @Override
    protected void onResume() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};

        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);
        super.onResume();

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        mNfcAdapter.disableForegroundDispatch(this);

        super.onPause();
    }

    private void readTextFromMessage (NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length>0) {

            NdefRecord ndefRecord = ndefRecords[1];

            String tagContent = getTextFromNdefRecord(ndefRecord);

            tableNo = tagContent;

            mTextView.setText(tagContent);

        }else{
            Toast.makeText(this, "No NDEF records found!", Toast.LENGTH_LONG).show();

        }


    }

    public static String tableNo = null;




    private String getTextFromNdefRecord(NdefRecord ndefRecord) {
        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = "UTF-8";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);

        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;

    }

    public void goToMenu (View view){
        Intent intent = new Intent (this, TheMenu.class);
        startActivity(intent);
        finish();
    }

    public void goToMenu_Pickup (View view) {
        Button btn = (Button) findViewById(R.id.pickUp);
        if (tableNo != null) {
            btn.setEnabled(false);
        }else{
            Intent intent = new Intent(this, TheMenu.class);
            startActivity(intent);
            finish();
        }
     }


}
