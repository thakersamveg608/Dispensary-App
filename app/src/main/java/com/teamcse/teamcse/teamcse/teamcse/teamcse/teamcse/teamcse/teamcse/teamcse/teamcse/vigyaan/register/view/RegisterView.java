package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;

/**
 * Created by samveg on 27/9/17.
 */

public interface RegisterView {

    void showProgressBar(boolean show);
    void showError(String message);
    void showRegisterStatus(RegisterDataResponse registerDataResponse);

}
