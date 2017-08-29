package com.shail.sixtdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public RecyclerViewAdapter() {
        setHasStableIds(true);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer, View> mMapView;

        public ViewHolder(View view) {
            super(view);
            mMapView = new HashMap<>();
            mMapView.put(0, view);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                mMapView.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (mMapView.containsKey(id))
                return mMapView.get(id);
            else
                initViewById(id);

            return mMapView.get(id);
        }
    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(int position, ViewHolder viewHolder);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(viewGroup.getContext(), viewGroup, viewType));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        bindView(position, viewHolder);
    }
}
