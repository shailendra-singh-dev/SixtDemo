package com.shail.sixtdemo.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class PicassoMarker implements Target {

    private Marker mMarker;

    public PicassoMarker(Marker marker) {
        mMarker = marker;
    }

    @Override
    public int hashCode() {
        return mMarker.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PicassoMarker) {
            Marker marker = ((PicassoMarker) o).mMarker;
            return mMarker.equals(marker);
        } else {
            return false;
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (null != mMarker.getTag()) {
            mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Bitmap icon = AppUtils.getBitmapFromDrawable(errorDrawable);
        if (null != mMarker.getTag()) {
            mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));
        }
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
}
