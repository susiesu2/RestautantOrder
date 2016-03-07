package com.visa.android.integration.checkoutsampleapp.app;

import com.visa.checkout.VisaMerchantInfo;
import com.visa.checkout.VisaPaymentInfo;
import com.visa.checkout.utils.VisaEnvironmentConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  Demonstrates how payment info can be configured.
 */
public class ConfigureVisaPaymentInfo {

    /** Unique return code to identify Visa Checkout Activity's return status */
    public final static int VISA_CHECKOUT_REQUEST_CODE = 10102;

    /** Key for passing Result between activities */
    public final static String VISA_CHECKOUT_RESULT_MSG_KEY = "VisaCheckoutResult";

    /** Key for passing VisaPaymentInfo between activities */
    public final static String VISA_CHECKOUT_REFERENCE_ID_KEY = "VisaCheckoutReferenceId";

    /** REQUIRED. Endpoint configuration for Visa Checkout SDK. Can be PRODUCTION or SANDBOX. */
    public static VisaEnvironmentConfig VISA_CHECKOUT_ENVIRONMENT_CONFIG = VisaEnvironmentConfig.SANDBOX;

    /** Merchant API key obtained during enrollment */
    public final static String VISA_CHECKOUT_API_KEY = "LXFQZOKJIXXFYO20OMI313Hal2ScbAZYETLKqaXZpHFqZ8bDw";

    /** OPTIONAL. Merchant Profile name if any */
    public static String VISA_CHECKOUT_PROFILE_NAME = "defaultprofile";

    public static VisaPaymentInfo getSampleVisaPaymentInfo() {
        VisaPaymentInfo paymentInfo = new VisaPaymentInfo();
/** REQUIRED: If merchant needs to collect shipping address from the user */
        paymentInfo.setUsingShippingAddress(true);
/** (REQUIRED if total amount is set): Standard ISO format currency code.
 * Example USD, CAD, AUD
 */
        paymentInfo.setCurrency("USD");
/** (REQUIRED if currency is set): The total amount for current session in
 * BigDecimal format with a max 2 decimal places and greater than or equal to 0 */
        //paymentInfo.setTotal(new BigDecimal("12.34"));
        paymentInfo.setTotal(new BigDecimal(String.valueOf(Summary.amount)));
/** OPTIONAL (CONTINUE by default): Action merchant wants to take whether to Pay
 * or Continue. If amount is not set, it should be Continue.
 * Enum values: UserReviewAction.CONTINUE or UserReviewAction.PAY
 */
        paymentInfo.setUserReviewAction(VisaPaymentInfo.UserReviewAction.CONTINUE);
/** REQUIRED: The subtotal amount for current session in BigDecimal format with a
 * max 2 decimal places and greater than or equal to zero
 */
        paymentInfo.setSubtotal(new BigDecimal("12.34"));
/** OPTIONAL: The tax amount for current session in BigDecimal format with a
 * max 2 decimal places and greater than or equal to zero
 */
        paymentInfo.setTax(new BigDecimal("12.34"));
/** OPTIONAL: The misc amount for current session in BigDecimal format with a
 * max 2 decimal places and greater than or equal to zero
 */
        paymentInfo.setMisc(new BigDecimal("12.34"));
/** OPTIONAL: The discount amount for current session in BigDecimal format with a
 * max 2 decimal places and greater than or equal to zero */
        paymentInfo.setDiscount(new BigDecimal("12.34"));
/** OPTIONAL: The giftwrap amount for current session in BigDecimal format with a
 * max 2 decimal places and greater than or equal to zero
 */
        paymentInfo.setGiftWrap(new BigDecimal("12.34"));
/** OPTIONAL: Shipping & handling amount for current session in BigDecimal format
 * with a max 2 decimal places and greater than or equal to zero
 */
        paymentInfo.setShippingHandling(new BigDecimal("12.34"));
/** OPTIONAL: Description for current order (String value) */
        paymentInfo.setDescription("Sample Order Description");
/** OPTIONAL: Order ID for current transaction (String value) */
        paymentInfo.setOrderId("Order1234567890");
        paymentInfo.setVisaMerchantInfo(getSampleMerchantInfo());

              /** OPTIONAL: 3DS Settings */
//        ThreeDSSetup threeDSSetup = new ThreeDSSetup();
//        threeDSSetup.setThreeDSActive(true);

//        /** OPTIONAL: When True, then 3DS authentication in stream during the Checkout transaction is suppressed;
//         * When False, then the regular 3DS flow will apply, and 3DS authentication will be required in stream, and payer authentication will be required
//         */
//        threeDSSetup.setSuppressThreeDSChallenge(false);
//        paymentInfo.setThreeDSSetup(threeDSSetup);

        return paymentInfo;
    }

    private static VisaMerchantInfo getSampleMerchantInfo() {
/** Sample for a VisaMerchantInfo object */
        VisaMerchantInfo visaMerchantInfo = new VisaMerchantInfo();
/** REQUIRED: Merchant API key obtained during onboarding (String) */
        visaMerchantInfo.setMerchantApiKey(VISA_CHECKOUT_API_KEY);
/** OPTIONAL (SUMMARY by default): Access level for current merchant for the
 * encrypted payment info
 * (ENUM: MerchantDataLevel.SUMMARY or MerchantDataLevel.FULL)
 */
        visaMerchantInfo.setDataLevel(VisaMerchantInfo.MerchantDataLevel.SUMMARY);
/** OPTIONAL: Logo Resource to display during the review screen (int) */
        visaMerchantInfo.setLogoResourceId(R.drawable.app_icon);
/** OPTIONAL: Name to display on the review screen for the merchant (String) */
        visaMerchantInfo.setDisplayName("Cafe & Bar");
/** OPTIONAL: External profile Id of the Merchant. Only specify if there is one. (String) */
        visaMerchantInfo.setExternalProfileId(VISA_CHECKOUT_PROFILE_NAME);
/** OPTIONAL: Merchant ID obtained during onboarding (String) */
        visaMerchantInfo.setMerchantId("212");
/** OPTIONAL (false by default): Whether the current merchant wants to accept
 * Canadian Visa Debit cards (boolean)
 */
        visaMerchantInfo.setAcceptCanadianVisaDebit(false);
/** OPTIONAL - List of shipping address regions merchant wants to accept for current
 * transaction. (List of AcceptedShippingRegions Enum)
 */
        visaMerchantInfo.setAcceptedShippingRegions(getAcceptedShippingRegions());
/** OPTIONAL - List of card brands that merchant wants to accept for current
 * transaction. (List of AcceptedCardBrands Enum)
 */
        visaMerchantInfo.setAcceptedCardBrands(getAcceptedCardBrands());
/** OPTIONAL - List of billing address regions merchant wants to accept for current
 * transaction. (List of AcceptedShippingRegions Enum)
 */
        visaMerchantInfo.setAcceptedBillingRegions(getAcceptedBillingRegions());
        return visaMerchantInfo;
    }

    private static List<VisaMerchantInfo.AcceptedCardBrands> getAcceptedCardBrands(){
/** Current AcceptedCardBrands values - AcceptedCardBrands.VISA,
 * AcceptedCardBrands.AMEX, AcceptedCardBrands.DISCOVER,
 * AcceptedCardBrands.MASTERCARD
 */
        List<VisaMerchantInfo.AcceptedCardBrands> acb =
                new ArrayList<VisaMerchantInfo.AcceptedCardBrands>();
        acb.add(VisaMerchantInfo.AcceptedCardBrands.VISA);
        acb.add(VisaMerchantInfo.AcceptedCardBrands.AMEX);
        acb.add(VisaMerchantInfo.AcceptedCardBrands.DISCOVER);
        acb.add(VisaMerchantInfo.AcceptedCardBrands.MASTERCARD);
        return acb;
    }

    private static List<VisaMerchantInfo.AcceptedShippingRegions> getAcceptedShippingRegions() {
        /** Current AcceptedShippingRegions */

        List<VisaMerchantInfo.AcceptedShippingRegions> acceptedShippingRegions = new ArrayList<VisaMerchantInfo.AcceptedShippingRegions>();
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.US);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.CA);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.AU);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.CN);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.AR);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.CL);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.MX);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.PE);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.ZA);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.NZ);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.AE);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.CO);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.BR);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.SG);
        acceptedShippingRegions.add(VisaMerchantInfo.AcceptedShippingRegions.MY);
        return acceptedShippingRegions;
    }

    private static List<VisaMerchantInfo.AcceptedBillingRegions> getAcceptedBillingRegions() {

        /** Current AcceptedBillingRegions */

        List<VisaMerchantInfo.AcceptedBillingRegions> acceptedBillingRegions = new ArrayList<VisaMerchantInfo.AcceptedBillingRegions>();
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.US);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.CA);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.AU);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.CN);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.AR);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.CL);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.MX);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.PE);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.ZA);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.NZ);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.AE);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.CO);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.BR);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.HK);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.SG);
        acceptedBillingRegions.add(VisaMerchantInfo.AcceptedBillingRegions.MY);

        return acceptedBillingRegions;
    }
}
