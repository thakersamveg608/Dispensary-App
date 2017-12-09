package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.hospital;

/**
 * Created by samveg on 8/10/17.
 */

public class HospitalPresenterImpl implements HospitalPresenter{

    private HospitalBaseclassHelper hospitalBaseclassHelper;
    private HospitalView hospitalView;

    public HospitalPresenterImpl(HospitalBaseclassHelper hospitalBaseclassHelper, HospitalView hospitalView) {

        this.hospitalBaseclassHelper = hospitalBaseclassHelper;
        this.hospitalView = hospitalView;

    }

    @Override
    public void getAdressData(String address,String access_token) {

        hospitalView.showProgressBar(true);
        hospitalBaseclassHelper.addressData(address,access_token, new HospitalCallback() {

            @Override
            public void onRegisterSuccess(HospitalDataResponse hospitalDataResponse) {


                if(hospitalDataResponse.isSuccess()){
                    hospitalView.showProgressBar(false);
                    hospitalView.showHospitalStatus(hospitalDataResponse);
                }
            }

            @Override
            public void onRegisterFailure(String error) {

            }
        });

    }
}
