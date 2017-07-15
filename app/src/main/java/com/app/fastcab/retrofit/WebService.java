package com.app.fastcab.retrofit;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebService {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<RegistrationResultEnt>> registerUser(@Field("full_name") String userName,
                                                              @Field("phone_no") String UserPhone);





}