package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.view;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.login.model.LoginDataResponse;

/**
 * Created by samveg on 28/9/17.
 */

public interface LoginView {

    void showProgressBar(boolean show);
    void showError(String message);
    void showLoginStatus(LoginDataResponse loginDataResponse);

}
