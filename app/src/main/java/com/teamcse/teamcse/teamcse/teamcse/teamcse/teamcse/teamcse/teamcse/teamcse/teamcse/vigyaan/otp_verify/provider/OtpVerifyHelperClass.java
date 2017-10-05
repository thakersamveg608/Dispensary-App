package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.provider;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.OtpVerificationCallback;

/**
 * Created by samveg on 28/9/17.
 */

public interface OtpVerifyHelperClass {

    void getOtpResponse(String otp, String mobile, String access_token, OtpVerificationCallback otpVerificationCallback);
    void onDestroy();

}
