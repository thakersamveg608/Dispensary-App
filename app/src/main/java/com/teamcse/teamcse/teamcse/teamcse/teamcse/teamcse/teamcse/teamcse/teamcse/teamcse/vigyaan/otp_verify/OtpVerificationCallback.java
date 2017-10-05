package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.model.OtpData;

/**
 * Created by samveg on 28/9/17.
 */

public interface OtpVerificationCallback {

    void onOtpVerified(OtpData otpData);
    void onFailure(String error);

}
