package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.hospital;

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
 * Created by samveg on 8/10/17.
 */

public class RetrofitHospitalProvider implements HospitalBaseclassHelper{


    @Override
    public void addressData(String address,String access_token,final HospitalCallback hospitalCallback) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder().baseUrl(Urls.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        RequestHospitalApi requestHospitalApi = retrofit.create(RequestHospitalApi.class);
        Call<HospitalDataResponse> call= requestHospitalApi.getJSON(address,access_token);
        call.enqueue(new Callback<HospitalDataResponse>() {
            @Override
            public void onResponse(Call<HospitalDataResponse> call, Response<HospitalDataResponse> response) {
                hospitalCallback.onRegisterSuccess(response.body());
            }

            @Override
            public void onFailure(Call<HospitalDataResponse> call, Throwable t) {
                hospitalCallback.onRegisterFailure(t.getMessage());
            }
        });

    }
}
