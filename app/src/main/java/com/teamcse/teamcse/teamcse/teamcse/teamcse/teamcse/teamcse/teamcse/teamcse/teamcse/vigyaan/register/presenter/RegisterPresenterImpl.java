package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.presenter;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.RegisterCallback;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.provider.RegisterBaseClassHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.view.RegisterView;

/**
 * Created by samveg on 28/9/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter{

    private RegisterBaseClassHelper registerBaseClassHelper;
    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterBaseClassHelper registerBaseClassHelper, RegisterView registerView) {
        this.registerBaseClassHelper = registerBaseClassHelper;
        this.registerView = registerView;
    }

    @Override
    public void getRegisterData(String name, String mobile, String password, String bloodGroup , String userName,String eMail) {

        registerView.showProgressBar(true);
        registerBaseClassHelper.registerData(name, mobile, password, bloodGroup,userName,eMail, new RegisterCallback() {
            @Override
            public void onRegisterSuccess(RegisterDataResponse registerResponse) {
                if(registerResponse.isSuccess()){
                    registerView.showProgressBar(false);
                    registerView.showRegisterStatus(registerResponse);
                }
                else{
                    registerView.showProgressBar(false);
                    registerView.showError(registerResponse.getMessage());
                }

            }

            @Override
            public void onRegisterFailure(String error) {

            }
        });

    }
}
