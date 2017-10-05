package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;

/**
 * Created by samveg on 28/9/17.
 */

public interface RegisterCallback {

    void onRegisterSuccess(RegisterDataResponse registerResponse);
    void onRegisterFailure(String error);
}
