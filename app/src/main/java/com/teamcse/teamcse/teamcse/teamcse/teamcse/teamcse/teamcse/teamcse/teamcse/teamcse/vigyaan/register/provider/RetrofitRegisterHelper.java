package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.provider;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.Urls;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.RegisterCallback;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.api.RequestRegister;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samveg on 28/9/17.
 */

public class RetrofitRegisterHelper implements RegisterBaseClassHelper{
    @Override
    public void registerData(String name, String mobile, String password, String bloodGroup,String userName,final RegisterCallback registerCallback) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        Retrofit retrofit= new Retrofit.Builder().baseUrl(Urls.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        RequestRegister requestRegister = retrofit.create(RequestRegister.class);
        Call<RegisterDataResponse> call= requestRegister.getJSON(name,mobile,password,bloodGroup,userName);
        call.enqueue(new Callback<RegisterDataResponse>() {
            @Override
            public void onResponse(Call<RegisterDataResponse> call, Response<RegisterDataResponse> response) {
                registerCallback.onRegisterSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RegisterDataResponse> call, Throwable t) {
                registerCallback.onRegisterFailure(t.getMessage());
            }
        });

    }
}
