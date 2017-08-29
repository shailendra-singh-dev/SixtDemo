package com.shail.sixtdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */

public class Car implements Parcelable {

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

    public Car() {
        mID = "";
        mModelIdentifier = "";
        mModelName = "";
        mName = "";
        mMake = "";
        mGroup = "";
        mColor = "";
        mSeries = "";
        mFuelType = "";
        mFuelLevel = 0;
        mTransmission = "";
        mLicensePlate = "";
        mLatitude = 0;
        mLongitude = 0;
        mInnerCleanliness = "";
        mCarImageUrl = "";
    }

    protected Car(Parcel in) {
        mID = in.readString();
        mModelIdentifier = in.readString();
        mModelName = in.readString();
        mName = in.readString();
        mMake = in.readString();
        mGroup = in.readString();
        mColor = in.readString();
        mSeries = in.readString();
        mFuelType = in.readString();
        mFuelLevel = in.readInt();
        mTransmission = in.readString();
        mLicensePlate = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mInnerCleanliness = in.readString();
        mCarImageUrl = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mModelIdentifier);
        dest.writeString(mModelName);
        dest.writeString(mName);
        dest.writeString(mMake);
        dest.writeString(mGroup);
        dest.writeString(mColor);
        dest.writeString(mSeries);
        dest.writeString(mFuelType);
        dest.writeInt(mFuelLevel);
        dest.writeString(mTransmission);
        dest.writeString(mLicensePlate);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mInnerCleanliness);
        dest.writeString(mCarImageUrl);
    }
}
