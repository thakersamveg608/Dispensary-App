package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.model.LoginDataResponse;

/**
 * Created by samveg on 28/9/17.
 */

public interface LoginCallback {

    void onLoginSuccess(LoginDataResponse loginResponse);
    void onLoginFailure(String error);

}
