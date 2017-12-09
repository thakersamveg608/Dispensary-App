package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.hospital;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.Urls;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by samveg on 8/10/17.
 */

public interface RequestHospitalApi {

    @FormUrlEncoded
    @POST(Urls.REQUEST_ADDRESS)
    Call<HospitalDataResponse> getJSON(@Field("address") String address,@Field("token") String token);


}
