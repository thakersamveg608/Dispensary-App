package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.api;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model.AppointmentDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.Urls;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by samveg on 6/10/17.
 */

public interface RequestAppointment {
    @FormUrlEncoded
    @POST(Urls.REQUEST_APPOINTMENT)
    Call<AppointmentDataResponse> getJSON(@Field("token") String token,@Field("name") String name, @Field("medical_issue") String issue, @Field("issue_type") String type,@Field("date") String date,@Field("time") String time);
}
