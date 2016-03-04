package com.example.haoweiw.restaurant;

/**
 * Created by haoweiw on 3/3/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.LineItem;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.BuyButtonText;
import com.google.android.gms.wallet.fragment.Dimension;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;



public class AndroidPay extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private SupportWalletFragment mWalletFragment;
    private SupportWalletFragment mXmlWalletFragment;

    private MaskedWallet mMaskedWallet;
    private FullWallet mFullWallet;

    public static final int MASKED_WALLET_REQUEST_CODE = 1001;
    public static final int FULL_WALLET_REQUEST_CODE = 1002;

    public static final String WALLET_FRAGMENT_ID = "wallet_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Check if WalletFragment exists
        mWalletFragment = (SupportWalletFragment) getSupportFragmentManager()
                .findFragmentByTag(WALLET_FRAGMENT_ID);

        WalletFragmentInitParams startParams;
        //Initialize the WalletFragment
        WalletFragmentInitParams.Builder startParamsBuilder =
                WalletFragmentInitParams.newBuilder()
                        .setMaskedWalletRequest(generateMaskedWalletRequest())
                        .setMaskedWalletRequestCode(MASKED_WALLET_REQUEST_CODE)
                        .setAccountName("Restaurant");
        startParams = startParamsBuilder.build();


        // it does not, add it
        if(mWalletFragment == null) {
            //Wallet Fragment style
            WalletFragmentStyle walletFragmentStyle = new WalletFragmentStyle()
                    .setBuyButtonText(BuyButtonText.BUY_WITH_GOOGLE)
                    .setBuyButtonWidth(Dimension.MATCH_PARENT);


            //wallet fragment options
            WalletFragmentOptions walletFragmentOptions = WalletFragmentOptions.newBuilder()
                    .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                    .setFragmentStyle(walletFragmentStyle)
                    .setTheme(WalletConstants.THEME_HOLO_LIGHT)
                    .setMode(WalletFragmentMode.BUY_BUTTON)
                    .build();

            //Instantiate the WalletFragment
            mWalletFragment = SupportWalletFragment.newInstance(walletFragmentOptions);

            mWalletFragment.initialize(startParams);
        }

        //Add the WalletFragment to the UI
        getSupportFragmentManager().beginTransaction()
                 .replace(R.id.wallet_button_holder, mWalletFragment, WALLET_FRAGMENT_ID)
                 .commit();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wallet.API, new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .setTheme(WalletConstants.THEME_LIGHT)
                        .build())
                .build();

        setContentView(R.layout.activity_androidpay);

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int errorCode = -1;
        if (data != null) {
            errorCode = data.getIntExtra(WalletConstants.EXTRA_ERROR_CODE, -1);
        }
        switch (requestCode) {
            case MASKED_WALLET_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (data != null ) {
                            mMaskedWallet = data
                                    .getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);
                            Toast.makeText(this, "Got Masked Wallet", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        //The user canceled the operation
                        break;
                    default:
                        handleError(errorCode);
                        break;
                }
                break;

            case FULL_WALLET_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mFullWallet = data
                                .getParcelableExtra(WalletConstants.EXTRA_FULL_WALLET);
                        //show the credit card number
                        Toast.makeText(this,
                                mFullWallet.getProxyCard().getPan().toString(),
                                Toast.LENGTH_SHORT).show();
                        // Update transaction status
                        Wallet.Payments.notifyTransactionStatus(mGoogleApiClient,
                                generateNotifyTransactionStatusRequest(
                                        mFullWallet.getGoogleTransactionId(),
                                        NotifyTransactionStatusRequest.Status.SUCCESS));
                        break;
                    default:
                        Toast.makeText(this, "An Error Occurd", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case WalletConstants.RESULT_ERROR:
                Toast.makeText(this, "An Error Occured", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static NotifyTransactionStatusRequest generateNotifyTransactionStatusRequest(String googleTransactionId, int status) {
        return NotifyTransactionStatusRequest.newBuilder()
                .setGoogleTransactionId(googleTransactionId)
                .setStatus(status)
                .build();

    }

    private MaskedWalletRequest generateMaskedWalletRequest() {

        /*PaymentMethodTokenizationParameters parameters =
                PaymentMethodTokenizationParameters.newBuilder()
                    .setPaymentMethodTokenizationType(PaymentMethodTokenizationType.PAYMENT_GATEWAY)
                    .addParameter("publicKey", "BLFzRPsZ6Znjcm+RWsSGQICm1cGqdifgfg6H/6uUR7FcWQNRdZcraLgVkmzOtHvyNEKUqIyBzM2+nh4Hlhfi5AE=")
                    .build();*/
        MaskedWalletRequest maskedWalletRequest = MaskedWalletRequest.newBuilder()
                    .setMerchantName("Restaurant")
                    .setPhoneNumberRequired(true)
                    .setShippingAddressRequired(true)
                    .setCurrencyCode("USD")
                    .setEstimatedTotalPrice("10.00")
                    .setCart(Cart.newBuilder()
                            .setCurrencyCode("USD")
                            .setTotalPrice("10.00")
                            .addLineItem(LineItem.newBuilder()
                                    .setCurrencyCode("USD")
                                    .setQuantity("1")
                                    .setUnitPrice("10.00")
                                    .setTotalPrice("10.00")
                                    .build())
                            .build())
                    //.setPaymentMethodTokenizationParameters(parameters)
                    .build();

        return maskedWalletRequest;

    }

    private FullWalletRequest generateFullWalletRequest(String googleTransactionId) {
        FullWalletRequest fullWalletRequest = FullWalletRequest.newBuilder()
                .setCart(Cart.newBuilder()
                        .setCurrencyCode("USD")
                        .setTotalPrice("10.10")
                        .addLineItem(LineItem.newBuilder()
                                .setCurrencyCode("USD")
                                .setQuantity("1")
                                .setUnitPrice("10.00")
                                .setTotalPrice("10.00")
                                .build())
                        .addLineItem(LineItem.newBuilder()
                                .setCurrencyCode("USD")
                                .setDescription("Tax")
                                .setRole(LineItem.Role.TAX)
                                .setTotalPrice(".10")
                                .build())
                        .build())
                .build();
        return fullWalletRequest;
    }

    public void requestFullWallet(View view) {
        if (mMaskedWallet == null) {
            Toast.makeText(this, "No masked wallet, can't confirm", Toast.LENGTH_SHORT).show();
            return;

        }
        if (mGoogleApiClient.isConnected()) {
            Wallet.Payments.loadFullWallet(mGoogleApiClient,
                    generateFullWalletRequest(mMaskedWallet.getGoogleTransactionId()),
                    FULL_WALLET_REQUEST_CODE);
        }

    }

    void handleError(int errorCode) {
        switch (errorCode) {
            case WalletConstants.ERROR_CODE_SPENDING_LIMIT_EXCEEDED:
            case WalletConstants.ERROR_CODE_INVALID_PARAMETERS:
            case WalletConstants.ERROR_CODE_AUTHENTICATION_FAILURE:
            case WalletConstants.ERROR_CODE_BUYER_ACCOUNT_ERROR:
            case WalletConstants.ERROR_CODE_MERCHANT_ACCOUNT_ERROR:
            case WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE:
            case WalletConstants.ERROR_CODE_UNSUPPORTED_API_VERSION:
            case WalletConstants.ERROR_CODE_UNKNOWN:
            default:
                //unrecoverbale error
                String errorMessage = getString(R.string.google_wallet_unavailable) + "\n" +
                        getString(R.string.error_code, errorCode);
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {
        // GoogleApliClient is connected, we don't need to do anything here
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // GoogleApiClient is temporarily disconnected, no action needed

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // GoogleApiClient failed to connect, we should log the error and retry

    }






}
