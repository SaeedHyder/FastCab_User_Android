package com.app.fastcab.retrofit;


import com.app.fastcab.entities.CMSEnt;
import com.app.fastcab.entities.CancelReasonEnt;
import com.app.fastcab.entities.NotificationListEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.entities.UserMessageEnt;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {
    @Multipart
    @POST("user/register")
    Call<ResponseWrapper<UserEnt>> registerUser(@Part("full_name") RequestBody userFullname,
                                                @Part("email") RequestBody useremail,
                                                @Part("gender") RequestBody usergender,
                                                @Part("phone_no") RequestBody PhoneNumber,
                                                @Part("address") RequestBody userfulladdress,
                                                @Part("zip_code") RequestBody zip_code,
                                                @Part("dob") RequestBody dob,
                                                @Part("password") RequestBody password,
                                                @Part("password_confirmation") RequestBody password_confirmation,
                                                @Part("social_media_id") RequestBody FaceBookUID,
                                                @Part("social_media_platform") RequestBody SocialName,
                                                @Part MultipartBody.Part userprofileImage

    );

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseWrapper<UserEnt>> loginFacebookUser(@Field("social_media_id") String FacebookUID,
                                                     @Field("social_media_platform") String Facebook
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseWrapper<UserEnt>> loginUser(@Field("email") String email,
                                             @Field("password") String password
    );

    @FormUrlEncoded
    @POST("notification/updatedevicetoken")
    Call<ResponseWrapper> updateToken(@Field("user_id") String userid,
                                      @Field("device_type") String deviceType,
                                      @Field("device_token") String token);

    @FormUrlEncoded
    @POST("user/forgotpassword")
    Call<ResponseWrapper> ForgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("user/sendcode")
    Call<ResponseWrapper<UserEnt>> VerifyNumber(@Field("user_id") String userID,
                                                @Field("phone_no") String phonenumber);

    @FormUrlEncoded
    @POST("user/verifycode")
    Call<ResponseWrapper<UserEnt>> VerifyCode(@Field("user_id") String userID,
                                              @Field("code") String code);

    @GET("user/getprofile")
    Call<ResponseWrapper<UserEnt>> getProfile(@Query("user_id") String userID);

    @Multipart
    @POST("user/register")
    Call<ResponseWrapper<UserEnt>> UpdateUser(@Part("user_id") RequestBody user_id,
                                              @Part("full_name") RequestBody userFullname,
                                              @Part("phone_no") RequestBody PhoneNumber,
                                              @Part("zip_code") RequestBody zip_code,
                                              @Part("gender") RequestBody usergender,
                                              @Part("address") RequestBody userfulladdress,
                                              @Part("city") RequestBody city,
                                              @Part("state") RequestBody state,
                                              @Part MultipartBody.Part userprofileImage

    );

    @FormUrlEncoded
    @POST("user/changepassword")
    Call<ResponseWrapper<UserEnt>> ChangePassword(@Field("old_password") String old_password,
                                                  @Field("password") String password,
                                                  @Field("password_confirmation") String password_confirmation,
                                                  @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("changepushnotificationstatus")
    Call<ResponseWrapper<UserEnt>> ChangeNotifiationStatus(@Field("user_id") String user_id,
                                                           @Field("push_status") Integer status);

    @FormUrlEncoded
    @POST("getnotifications")
    Call<ResponseWrapper<ArrayList<NotificationListEnt>>> GetNotifiation(@Field("user_id") String user_id);

    @GET("cms/usermessage")
    Call<ResponseWrapper<ArrayList<UserMessageEnt>>> getUserMessages();
    @GET("cms/cancel")
    Call<ResponseWrapper<ArrayList<CancelReasonEnt>>>getCancelReasons();

    @FormUrlEncoded
    @POST("cms/contactus")
    Call<ResponseWrapper> ContactUs(@Field("user_id") String userID,
                                    @Field("text") String text);

    @FormUrlEncoded
    @POST("cms/getCms")
    Call<ResponseWrapper<CMSEnt>> getCMS(@Field("user_id") String user_id,
                                         @Field("type") String Type);




}