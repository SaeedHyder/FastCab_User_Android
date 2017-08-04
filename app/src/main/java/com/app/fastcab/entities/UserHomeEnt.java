package com.app.fastcab.entities;

import android.location.Location;

import com.app.fastcab.helpers.HomeServiceHelper;
import com.app.fastcab.ui.views.TitleBar;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by saeedhyder on 7/26/2017.
 */

public class UserHomeEnt {

    private double latitude;
    private double longitude;
    private Location Mylocation;
    private LocationEnt origin;
    private LocationEnt destination;
    private double distance ;
    private boolean mIsTitleBarChanged ;
    private boolean isCurrentLocationMove;
    private ArrayList<SelectCarEnt> carTypeList;
    private PromoCodeEnt promoCodeEnt;
    private SelectCarEnt selectCarEnt;
    private CreateRideEnt rideEnt;
    private DriverEnt driverDetail;
    private boolean isRideinSession;
    private String curretState ;
    private EstimateFareEnt estimateFare;
    private RideDriverEnt rideDriverEnt;
    private ArrayList<DriverEnt> showFindRideArray;



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location getMylocation() {
        return Mylocation;
    }

    public void setMylocation(Location mylocation) {
        Mylocation = mylocation;
    }

    public LocationEnt getOrigin() {
        return origin;
    }

    public void setOrigin(LocationEnt origin) {
        this.origin = origin;
    }

    public LocationEnt getDestination() {
        return destination;
    }

    public void setDestination(LocationEnt destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean ismIsTitleBarChanged() {
        return mIsTitleBarChanged;
    }

    public void setmIsTitleBarChanged(boolean mIsTitleBarChanged) {
        this.mIsTitleBarChanged = mIsTitleBarChanged;
    }

    public boolean isCurrentLocationMove() {
        return isCurrentLocationMove;
    }

    public void setCurrentLocationMove(boolean currentLocationMove) {
        isCurrentLocationMove = currentLocationMove;
    }

    public ArrayList<SelectCarEnt> getCarTypeList() {
        return carTypeList;
    }

    public void setCarTypeList(ArrayList<SelectCarEnt> carTypeList) {
        this.carTypeList = carTypeList;
    }

    public PromoCodeEnt getPromoCodeEnt() {
        return promoCodeEnt;
    }

    public void setPromoCodeEnt(PromoCodeEnt promoCodeEnt) {
        this.promoCodeEnt = promoCodeEnt;
    }




    public boolean isRideinSession() {
        return isRideinSession;
    }

    public void setRideinSession(boolean rideinSession) {
        isRideinSession = rideinSession;
    }

    public String getCurretState() {
        return curretState;
    }

    public void setCurretState(String curretState) {
        this.curretState = curretState;
    }

    public SelectCarEnt getSelectCarEnt() {
        return selectCarEnt;
    }

    public void setSelectCarEnt(SelectCarEnt selectCarEnt) {
        this.selectCarEnt = selectCarEnt;
    }

    public CreateRideEnt getRideEnt() {
        return rideEnt;
    }

    public void setRideEnt(CreateRideEnt rideEnt) {
        this.rideEnt = rideEnt;
    }

    public DriverEnt getDriverDetail() {
        return driverDetail;
    }

    public void setDriverDetail(DriverEnt driverDetail) {
        this.driverDetail = driverDetail;
    }


    public EstimateFareEnt getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(EstimateFareEnt estimateFare) {
        this.estimateFare = estimateFare;
    }

    public RideDriverEnt getRideDriverEnt() {
        return rideDriverEnt;
    }

    public void setRideDriverEnt(RideDriverEnt rideDriverEnt) {
        this.rideDriverEnt = rideDriverEnt;
    }

    public ArrayList<DriverEnt> getShowFindRideArray() {
        return showFindRideArray;
    }

    public void setShowFindRideArray(ArrayList<DriverEnt> showFindRideArray) {
        this.showFindRideArray = showFindRideArray;
    }
}
