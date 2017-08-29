package com.shail.sixtdemo.model;

import com.shail.sixtdemo.BuildConfig;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class Car {

    private String mID;
    private String mModelIdentifier;
    private String mModelName;
    private String mName;
    private String mMake;
    private String mGroup;
    private String mColor;
    private String mSeries;
    private String mFuelType;
    private int mFuelLevel;
    private String mTransmission;
    private String mLicensePlate;
    private double mLatitude;
    private double mLongitude;
    private String mInnerCleanliness;
    private String mCarImageUrl;

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getModelIdentifier() {
        return mModelIdentifier;
    }

    public void setModelIdentifier(String mModelIdentifier) {
        this.mModelIdentifier = mModelIdentifier;
    }

    public String getModelName() {
        return mModelName;
    }

    public void setModelName(String mModelName) {
        this.mModelName = mModelName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getMake() {
        return mMake;
    }

    public void setMake(String mMake) {
        this.mMake = mMake;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String mGroup) {
        this.mGroup = mGroup;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public String getSeries() {
        return mSeries;
    }

    public void setSeries(String mSeries) {
        this.mSeries = mSeries;
    }

    public String getFuelType() {
        return mFuelType;
    }

    public void setFuelType(String mFuelType) {
        this.mFuelType = mFuelType;
    }

    public int getFuelLevel() {
        return mFuelLevel;
    }

    public void setFuelLevel(int mFuelLevel) {
        this.mFuelLevel = mFuelLevel;
    }

    public String getTransmission() {
        return mTransmission;
    }

    public void setTransmission(String mTransmission) {
        this.mTransmission = mTransmission;
    }

    public String getLicensePlate() {
        return mLicensePlate;
    }

    public void setLicensePlate(String mLicensePlate) {
        this.mLicensePlate = mLicensePlate;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getInnerCleanliness() {
        return mInnerCleanliness;
    }

    public void setInnerCleanliness(String mInnerCleanliness) {
        this.mInnerCleanliness = mInnerCleanliness;
    }

    public String getCarImageUrl() {
        return mCarImageUrl;
    }

    public void setCarImageUrl(String carImageUrl) {
        mCarImageUrl = carImageUrl;
    }

    @Override
    public String toString() {
        return "[ID:" + mID + ",CarImageUrl:" + mCarImageUrl + "]";
    }

    @Override
    public boolean equals(Object object) {
        if ((object == null) || !(object instanceof Car) || (mID.isEmpty())) {
            return false;
        }
        final Car car = (Car) object;
        return mID.equalsIgnoreCase(car.getID());
    }
}
