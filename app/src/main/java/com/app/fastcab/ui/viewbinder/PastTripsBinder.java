package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.entities.PastTripsEnt;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class PastTripsBinder extends ViewBinder<PastTripsEnt> {

    ImageLoader imageLoader;

    public PastTripsBinder() {
        super(R.layout.past_trips_item);
        imageLoader=ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(PastTripsEnt entity, int position, int grpPosition, View view, Activity activity) {

        final PastTripsBinder.ViewHolder viewHolder = (PastTripsBinder.ViewHolder) view.getTag();

        viewHolder.txtRideNo.setText(entity.getRideNo());
        viewHolder.txtFare.setText(entity.getFare());
        viewHolder.txtTimeDate.setText(entity.getDate());
        viewHolder.txtTip.setText(entity.getTip());
        imageLoader.displayImage(entity.getPastTripImg(),viewHolder.ivPastTrips);



    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_pastTrips)
        ImageView ivPastTrips;
        @BindView(R.id.txt_RideNo)
        AnyTextView txtRideNo;
        @BindView(R.id.txt_fare)
        AnyTextView txtFare;
        @BindView(R.id.txt_time_date)
        AnyTextView txtTimeDate;
        @BindView(R.id.txt_tip)
        AnyTextView txtTip;
        @BindView(R.id.ll_pastTripDetail)
        LinearLayout llPastTripDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}