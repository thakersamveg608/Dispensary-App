package com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.api;

import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.helper.Urls;
import com.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.teamcse.vigyaan.register.model.RegisterDataResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by samveg on 28/9/17.
 */

public interface RequestRegister {

    @FormUrlEncoded
    @POST(Urls.REQUEST_REGISTER)
    Call<RegisterDataResponse> getJSON(@Field("name") String name, @Field("mobile_number") String mobile, @Field("password") String password , @Field("blood") String bloodGroup ,@Field("username") String userName,@Field("email") String eMail);

}
