package com.app.fastcab.retrofit;


import com.app.fastcab.entities.CMSEnt;
import com.app.fastcab.entities.CancelReasonEnt;
import com.app.fastcab.entities.CreateRideEnt;
import com.app.fastcab.entities.DriverEnt;
import com.app.fastcab.entities.EstimateFareEnt;
import com.app.fastcab.entities.NotificationListEnt;
import com.app.fastcab.entities.ProgressEnt;
import com.app.fastcab.entities.PromoCodeEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.RideDriverEnt;
import com.app.fastcab.entities.RideEnt;
import com.app.fastcab.entities.RideFeedbackEnt;
import com.app.fastcab.entities.SelectCarEnt;
import com.app.fastcab.entities.UpdatedLocationEnt;
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
    @POST("user/facebooklogin")
    Call<ResponseWrapper<UserEnt>> loginFacebookUser(@Field("social_media_id") String FacebookUID,
                                                     @Field("social_media_platform") String Facebook
    );

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseWrapper<UserEnt>> loginUser(@Field("email") String email,
                                             @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/updateDeviceToken")
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
    @POST("user/update")
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


    @GET("getnotifications")
    Call<ResponseWrapper<ArrayList<NotificationListEnt>>> GetNotifiation(@Query("user_id") String user_id);

    @GET("cms/cancel")
    Call<ResponseWrapper<ArrayList<CancelReasonEnt>>> getCancelReasons();

    @FormUrlEncoded
    @POST("cms/contactus")
    Call<ResponseWrapper> ContactUs(@Field("user_id") String userID,
                                    @Field("text") String text);

    @FormUrlEncoded
    @POST("cms/getCms")
    Call<ResponseWrapper<CMSEnt>> getCMS(@Field("user_id") String user_id,
                                         @Field("type") String Type);

    @GET("cms/getvehicle")
    Call<ResponseWrapper<ArrayList<SelectCarEnt>>> getVehicles();

    @GET("ride/ridehistory")
    Call<ResponseWrapper<ArrayList<RideEnt>>> getUserRideHistory(@Query("user_id") String user_id);

    @GET("cms/usermessage")
    Call<ResponseWrapper<ArrayList<UserMessageEnt>>> getUserMessages();

    @GET("ride/estimatedfare")
    Call<ResponseWrapper<EstimateFareEnt>> getRideEstimate(@Query("vehicle_id") String vehicle_id,
                                                           @Query("percentage") String percentage,
                                                           @Query("distance") String distance);

    @GET("ride/promocode")
    Call<ResponseWrapper<PromoCodeEnt>> getPromoCode(@Query("user_id") String user_id,
                                                     @Query("promo_code") String promocode);

    @FormUrlEncoded
    @POST("ride/create")
    Call<ResponseWrapper<CreateRideEnt>> createNewRide(@Field("user_id") String user_id,
                                                       @Field("pickup_latitude") String pickup_latitude,
                                                       @Field("pickup_longitude") String pickup_longitude,
                                                       @Field("pickup_address") String pickup_address,
                                                       @Field("pickup_place") String pickup_place,
                                                       @Field("destination_latitude") String destination_latitude,
                                                       @Field("destination_longitude") String destination_longitude,
                                                       @Field("destination_address") String destination_address,
                                                       @Field("destination_place") String destination_place,
                                                       @Field("vehicle_id") String vehicle_id,
                                                       @Field("percentage") String percentage,
                                                       @Field("date") String date,
                                                       @Field("time") String time,
                                                       @Field("status") int status,
                                                       @Field("ride_status") String ride_status,
                                                       @Field("estimate_fare") String estimate_fare,
                                                       @Field("distance") String distance);

    @FormUrlEncoded
    @POST("ride/ridestatus")
    Call<ResponseWrapper<RideEnt>> ChangeRideStatus(@Field("user_id") String user_id,
                                                    @Field("ride_id") String ride_id,
                                                    @Field("status") int status,
                                                    @Field("cancel_id") String cancel_id
    );

    @FormUrlEncoded
    @POST("user/resetcode")
    Call<ResponseWrapper<UserEnt>> resetVerificationCode(@Field("user_id") String user_id);

    @GET("ride/driverSearch")
    Call<ResponseWrapper<ArrayList<DriverEnt>>> getNearbyDrivers(@Query("user_id") String user_id,
                                                                 @Query("ride_id") Integer ride_id,
                                                                 @Query("longitude") String longitude,
                                                                 @Query("latitude") String latitude);

    @GET("ride/approveridedetail")
    Call<ResponseWrapper<RideDriverEnt>> getApproveDriver(@Query("ride_id") String ride_id);

    @GET("ride/userinprogressride")
    Call<ResponseWrapper<ArrayList<ProgressEnt>>> getUserRideInProgress(@Query("user_id") String user_id);

    @GET("ride/usercompleteride")
    Call<ResponseWrapper<ArrayList<ProgressEnt>>> getUserRideComplete(@Query("user_id") String user_id);

    @GET("cms/getimpovetype")
    Call<ResponseWrapper<ArrayList<RideFeedbackEnt>>> getImproveType();

    @FormUrlEncoded
    @POST("cms/appfeedback")
    Call<ResponseWrapper> submitAppFeedback(@Field("user_id") String user_id,
                                            @Field("rate") Integer rate,
                                            @Field("type_id") String type_id,
                                            @Field("comment") String comment);

    @FormUrlEncoded
    @POST("driver/feedback")
    Call<ResponseWrapper> submitRideFeedback(@Field("user_id") String user_id,
                                             @Field("driver_id") String driver_id,
                                             @Field("ride_id") String ride_id,
                                             @Field("rate") String rate,
                                             @Field("type") String type);

    @GET("ride/lastridefeedback")
    Call<ResponseWrapper<RideDriverEnt>> getLastFeedback(@Query("user_id") String user_id);

    @GET("driver/getdriverlocation")
    Call<ResponseWrapper<UpdatedLocationEnt>> getUpdatedLocation(@Query("driver_id") String driver_id);

    @FormUrlEncoded
    @POST("user/userlogout")
    Call<ResponseWrapper> LogoutUser(@Field("user_id") int user_id);
}