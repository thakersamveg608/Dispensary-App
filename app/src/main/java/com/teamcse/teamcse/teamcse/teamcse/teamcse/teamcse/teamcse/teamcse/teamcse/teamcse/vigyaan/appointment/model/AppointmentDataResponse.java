package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model;

/**
 * Created by samveg on 6/10/17.
 */

public class AppointmentDataResponse {

    private boolean success;
    private String message;
    public AppointmentDataResponse(boolean success, String message)
    {
        this.message=message;
        this.success=success;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
