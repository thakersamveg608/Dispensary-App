package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.view;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.model.OtpData;

/**
 * Created by samveg on 28/9/17.
 */

public interface OtpView {

    void showProgressBar(boolean show);
    void showMessage(String message);
    void OtpStatus(OtpData otpData);

}
