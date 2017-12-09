package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment;


import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model.AppointmentDataResponse;

/**
 * Created by samveg on 6/10/17.
 */

public interface AppointmentCallback {

    void onAppointmentSuccess(AppointmentDataResponse appointmentDataResponse);
    void onAppointmentFailure(String error);

}
