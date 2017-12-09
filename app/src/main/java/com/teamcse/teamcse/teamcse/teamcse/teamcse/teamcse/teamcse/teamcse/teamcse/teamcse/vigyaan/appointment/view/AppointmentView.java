package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.view;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model.AppointmentDataResponse;

/**
 * Created by samveg on 6/10/17.
 */

public interface AppointmentView {

    void showProgressBar(boolean show);
    void showError(String message);
    void showAppointmentStatus(AppointmentDataResponse appointmentDataResponse);

}
