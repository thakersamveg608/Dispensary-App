package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.presenter;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.AppointmentCallback;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model.AppointmentDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.provider.AppointmentBaseClassHelper;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.view.AppointmentView;

/**
 * Created by samveg on 6/10/17.
 */

public class AppointmentPresenterImpl implements AppointmentPresenter{

    private AppointmentBaseClassHelper appointmentBaseClassHelper;
    private AppointmentView appointmentView;
    public AppointmentPresenterImpl(AppointmentView appointmentView, AppointmentBaseClassHelper appointmentBaseClassHelper) {
        this.appointmentView = appointmentView;
        this.appointmentBaseClassHelper = appointmentBaseClassHelper;
    }

    @Override
    public void getAppointmentData(String name, String token, String issue, String date, String time, String type) {

        appointmentView.showProgressBar(true);
        appointmentBaseClassHelper.appointmentData(name,token,type,issue,date,time,new AppointmentCallback() {
            @Override
            public void onAppointmentSuccess(AppointmentDataResponse appointmentDataResponse) {
                if(appointmentDataResponse.isSuccess()) {
                    appointmentView.showProgressBar(false);
                    appointmentView.showAppointmentStatus(appointmentDataResponse);
                }
                else{
                    appointmentView.showProgressBar(false);
                    appointmentView.showError(appointmentDataResponse.getMessage());
                }
            }
            @Override
            public void onAppointmentFailure(String error) {
                appointmentView.showError("Sorry!!Something went wrong.Please try again.");
                appointmentView.showProgressBar(false);
            }
        });

    }
}
