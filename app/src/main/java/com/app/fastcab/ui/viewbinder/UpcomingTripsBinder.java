package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.entities.UpcomingTripsEnt;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class UpcomingTripsBinder extends ViewBinder<UpcomingTripsEnt> {

    public UpcomingTripsBinder() {
        super(R.layout.upcoming_trips_item);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UpcomingTripsEnt entity, int position, int grpPosition, View view, Activity activity) {

        final UpcomingTripsBinder.ViewHolder viewHolder = (UpcomingTripsBinder.ViewHolder) view.getTag();

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_upcomingTrips)
        ImageView ivUpcomingTrips;
        @BindView(R.id.txt_RideNo)
        AnyTextView txtRideNo;
        @BindView(R.id.txt_fare)
        AnyTextView txtFare;
        @BindView(R.id.txt_Upcoming_time_date)
        AnyTextView txtUpcomingTimeDate;
        @BindView(R.id.txt_Upcoming_type)
        AnyTextView txtUpcomingType;
        @BindView(R.id.ll_upcomingTripDetail)
        LinearLayout llUpcomingTripDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
