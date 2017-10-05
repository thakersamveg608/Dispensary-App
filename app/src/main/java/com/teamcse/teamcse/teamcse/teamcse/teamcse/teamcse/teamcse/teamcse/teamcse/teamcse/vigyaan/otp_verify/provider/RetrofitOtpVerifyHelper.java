package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.provider;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.Urls;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.OtpVerificationCallback;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.api.RequestOtpVerify;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.otp_verify.model.OtpData;

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

public class RetrofitOtpVerifyHelper implements OtpVerifyHelperClass{

    private Call<OtpData> call;

    @Override
    public void getOtpResponse(String otp, String mobile, String access_token, final OtpVerificationCallback otpVerificationCallback) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.BASE_URL).client(client).
                addConverterFactory(GsonConverterFactory.create()).build();
        final RequestOtpVerify requestOtpVerify = retrofit.create(RequestOtpVerify.class);

        call = requestOtpVerify.getJson(otp, mobile, access_token);
        call.enqueue(new Callback<OtpData>() {
            @Override
            public void onResponse(Call<OtpData> call, Response<OtpData> response) {

                otpVerificationCallback.onOtpVerified(response.body());

            }

            @Override
            public void onFailure(Call<OtpData> call, Throwable t) {
                t.printStackTrace();
                otpVerificationCallback.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void onDestroy() {

        if(call!=null){
            call.cancel();
        }

    }
}
