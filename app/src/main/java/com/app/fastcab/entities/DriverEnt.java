package com.app.fastcab.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 7/19/2017.
 */

public class DriverEnt {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("push_status")
    @Expose
    private Integer pushStatus;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("work_history")
    @Expose
    private String workHistory;
    @SerializedName("brief_introduction")
    @Expose
    private String briefIntroduction;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("social_media_id")
    @Expose
    private String socialMediaId;
    @SerializedName("social_media_platform")
    @Expose
    private String socialMediaPlatform;
    @SerializedName("avaibility_status")
    @Expose
    private Integer avaibilityStatus;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("promo_code")
    @Expose
    private String promoCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("average_rate")
    @Expose
    private String averageRate;
    @SerializedName("total_ride")
    @Expose
    private String totalRide;
    @SerializedName("total_distance")
    @Expose
    private String totalDistance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(String workHistory) {
        this.workHistory = workHistory;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(String socialMediaId) {
        this.socialMediaId = socialMediaId;
    }

    public String getSocialMediaPlatform() {
        return socialMediaPlatform;
    }

    public void setSocialMediaPlatform(String socialMediaPlatform) {
        this.socialMediaPlatform = socialMediaPlatform;
    }

    public Integer getAvaibilityStatus() {
        return avaibilityStatus;
    }

    public void setAvaibilityStatus(Integer avaibilityStatus) {
        this.avaibilityStatus = avaibilityStatus;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(String averageRate) {
        this.averageRate = averageRate;
    }

    public String getTotalRide() {
        return totalRide;
    }

    public void setTotalRide(String totalRide) {
        this.totalRide = totalRide;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }
}
