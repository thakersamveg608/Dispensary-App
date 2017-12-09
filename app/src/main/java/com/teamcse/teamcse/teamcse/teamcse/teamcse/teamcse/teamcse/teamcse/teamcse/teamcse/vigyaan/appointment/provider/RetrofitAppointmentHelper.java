package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.provider;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.AppointmentCallback;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.api.RequestAppointment;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.appointment.model.AppointmentDataResponse;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.Urls;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samveg on 6/10/17.
 */

public class RetrofitAppointmentHelper implements AppointmentBaseClassHelper{
    @Override
    public void appointmentData(String name, String token, String type, String issue, String date, String time, final AppointmentCallback appointmentCallback) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder().baseUrl(Urls.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        RequestAppointment requestLogin = retrofit.create(RequestAppointment.class);
        Call<AppointmentDataResponse> call= requestLogin.getJSON(token,name,issue,type,date,time);
        call.enqueue(new Callback<AppointmentDataResponse>() {
            @Override
            public void onResponse(Call<AppointmentDataResponse> call, Response<AppointmentDataResponse> response) {
                appointmentCallback.onAppointmentSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AppointmentDataResponse> call, Throwable t) {
                appointmentCallback.onAppointmentFailure(t.getMessage());
            }
        });

    }
}
