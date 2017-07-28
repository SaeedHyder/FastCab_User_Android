package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.entities.ProgressEnt;
import com.app.fastcab.helpers.DateHelper;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;


import java.io.UnsupportedEncodingException;
import java.util.List;

import DirectionModule.DirectionFinder;
import DirectionModule.DirectionFinderListener;
import DirectionModule.Route;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class UpcomingTripsBinder extends ViewBinder<ProgressEnt> implements DirectionFinderListener {



    public UpcomingTripsBinder() {
        super(R.layout.upcoming_trips_item);

    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ProgressEnt entity, int position, int grpPosition, View view, Activity activity) {
        view.setVisibility(View.GONE);
       /* String origin = "24.839611,67.082231";
        String destination = "24.829428,67.073822";*/
        String origin = entity.getPickupLatitude() + "," + entity.getPickupLongitude();
        String destination = entity.getDestinationLatitude() + "," + entity.getDestinationLongitude();
        // imageLoader.displayImage(entity.getUpcomingImg(),viewHolder.ivUpcomingTrips);
        try {
            new DirectionFinder(this, origin, destination,view,entity).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route, View view, Object object) {
        if (view!=null && object!=null) {
            ProgressEnt entity = (ProgressEnt) object;
          /*  String origin = "24.839611,67.082231";
            String destination = "24.829428,67.073822";*/
         String origin = entity.getPickupLatitude() + "," + entity.getPickupLongitude();
        String destination = entity.getDestinationLatitude() + "," + entity.getDestinationLongitude();
            String CustomMarkerOrigin = "http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/pickup.png";
            String CustomMarkerDestination = "http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/destination.png";
            StringBuilder stringBuilder = new StringBuilder();

            for (Route routesingle : route) {
                for (int i = 0; i < routesingle.points.size(); i++) {
                    stringBuilder.append(routesingle.points.get(i) + "|");
                }
            }
            String routesList = stringBuilder.toString();
            routesList = routesList.replaceAll("[^\\d.|,]", "");
            routesList = routesList.substring(0, routesList.length() - 1);
            final UpcomingTripsBinder.ViewHolder viewHolder = (UpcomingTripsBinder.ViewHolder) view.getTag();
            viewHolder.txtRideNo.setText(entity.getId() + "");
            viewHolder.txtFare.setText("AED " + entity.getEstimateFare());
            viewHolder.txtUpcomingTimeDate.setText(DateHelper.getDesireFormatDate(entity.getDate(), "yyyy-MM-dd", "EEE,MMM d") + " at "
                    + DateHelper.getDesireFormatDate(entity.getTime(), "hh:mm:ss", "HH:mm a"));
            viewHolder.txtUpcomingType.setText(entity.getVechicleDetail().getType() + "");
            Picasso.with(view.getContext()).load(getStaticMapURL(origin, destination, routesList, CustomMarkerOrigin,
                    CustomMarkerDestination, view.getResources().getString(R.string.API_KEY)))
                    .fit().into(viewHolder.ivUpcomingTrips);
            view.setVisibility(View.VISIBLE);
        }
    }

    private String getStaticMapURL(String origin, String destination, String routelist, String customMarkerOrigin, String customMarkerDestination, String APIKEY) {
        return "https://maps.googleapis.com/maps/api/staticmap?visible=" + routelist + "&scale=2&size=300x150&maptype=roadmap" +
                "&markers=icon:" + customMarkerOrigin + "|" + origin + "&markers=icon:" + customMarkerDestination + "|" + destination +
                "&path=color:0x070707FF|weight:5|" + routelist + "&key=" + APIKEY;
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
