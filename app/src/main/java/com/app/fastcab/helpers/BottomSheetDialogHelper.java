package com.app.fastcab.helpers;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.fastcab.R;
import com.app.fastcab.activities.DockActivity;
import com.app.fastcab.entities.RideDriverEnt;
import com.app.fastcab.entities.SelectCarEnt;
import com.app.fastcab.ui.adapters.SelectCarAdapter;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.CustomRatingBar;
import com.app.fastcab.ui.views.ExpandedBottomSheetBehavior;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
    ExpandedBottomSheetBehavior bottomSheetBehavior;
    // private BottomSheetDialog dialog;
    private NestedScrollView dialog;
    private DockActivity context;

    private CoordinatorLayout mainParent;
    private RecyclerView recyclerView;
    private SelectCarAdapter mAdapter;

    public BottomSheetDialogHelper(DockActivity context, CoordinatorLayout mainParent, int LayoutID) {
        this.context = context;
        this.mainParent = mainParent;
        LayoutInflater inflater = LayoutInflater.from(context);
        dialog = (NestedScrollView) inflater.inflate(LayoutID, null, false);
        mainParent.addView(dialog);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) dialog.getLayoutParams();
        params.setBehavior(new ExpandedBottomSheetBehavior());
        dialog.requestLayout();
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bottomSheetBehavior = (ExpandedBottomSheetBehavior) ExpandedBottomSheetBehavior.from(dialog);
        //  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setAllowUserDragging(false);
        bottomSheetBehavior.setPeekHeight(0);
    }

    public void initRatingDialog(View.OnClickListener onClickListener, RideDriverEnt result) {
        CircleImageView driver_image = (CircleImageView) dialog.findViewById(R.id.CircularImageSharePop);
        Glide.with(context).load(result.getDriverDetail().getProfileImage()).into(driver_image);
        AnyTextView carplate = (AnyTextView) dialog.findViewById(R.id.txtCarNo);
        carplate.setText(result.getVehicleDetail().getVehicleNumber() + "");
        AnyTextView drivername = (AnyTextView) dialog.findViewById(R.id.txtDriverName);
        drivername.setText(result.getDriverDetail().getFullName() + "");
        AnyTextView pickup = (AnyTextView) dialog.findViewById(R.id.txt_pick_text);
        pickup.setText(result.getRideDetail().getPickupAddress() + "");
        AnyTextView Destination = (AnyTextView) dialog.findViewById(R.id.txt_destination_text);
        Destination.setText(result.getRideDetail().getDestinationAddress() + "");
        AnyTextView fare = (AnyTextView) dialog.findViewById(R.id.txtFareAmount);
        Destination.setText(result.getRideDetail().getTotalAmount() + "");
        bottomSheetBehavior.setPeekHeight((int) context.getResources().getDimension(R.dimen.x150));
        Button submit = (Button) dialog.findViewById(R.id.SubmitButton);
        submit.setOnClickListener(onClickListener);
    }

    public int getRatingScore() {
        CustomRatingBar submit = (CustomRatingBar) dialog.findViewById(R.id.rbAddRating);
        return (int) submit.getScore();
    }

    public void initRideDetailBottomSheet(View.OnClickListener oncancelclicklistener, RideDriverEnt result) {
        AnyTextView pickup = (AnyTextView) dialog.findViewById(R.id.txt_pick_text);
        pickup.setText(result.getRideDetail().getPickupAddress() + "");
        AnyTextView drivername = (AnyTextView) dialog.findViewById(R.id.txt_drivername);
        drivername.setText(result.getDriverDetail().getFullName() + "");
        AnyTextView carname = (AnyTextView) dialog.findViewById(R.id.txt_car_model);
        carname.setText(result.getVehicleDetail().getVehicleName() + "");
        AnyTextView carcolor = (AnyTextView) dialog.findViewById(R.id.txt_car_color);
        carcolor.setText(result.getVehicleDetail().getVehicleColor() + "");
        AnyTextView carplate = (AnyTextView) dialog.findViewById(R.id.txt_car_number);
        carplate.setText(result.getVehicleDetail().getVehicleNumber() + "");
        ImageView driverimage = (ImageView) dialog.findViewById(R.id.img_driver);
        Glide.with(context).load(result.getDriverDetail().getProfileImage() + "").into(driverimage);
        CustomRatingBar driverrating = (CustomRatingBar) dialog.findViewById(R.id.rb_rating);
        driverrating.setScore(result.getDriverDetail().getAverageRate());
        bottomSheetBehavior.setAllowUserDragging(false);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancel_ride);
        cancelbutton.setOnClickListener(oncancelclicklistener);
    }

    public void initSelectRideBottomSheet(View.OnClickListener promoclicklistener,
                                          View.OnClickListener oncancelclicklistener, ArrayList<SelectCarEnt> carTypes) {
        // this.dialog.setContentView(layoutID);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);

        setSelectAdapter(carTypes);

        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        AnyTextView promocode = (AnyTextView) dialog.findViewById(R.id.txt_promoCode);
        promocode.setOnClickListener(promoclicklistener);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        //setButtonChanger();
        // dialog.setCanceledOnTouchOutside(false);
        // return this.dialog;
    }

    public void initSelectRideBottomSheet(View.OnClickListener promoclicklistener,
                                          View.OnClickListener oncancelclicklistener, int text, ArrayList<SelectCarEnt> carTypes) {
        // this.dialog.setContentView(layoutID);

        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);

        setSelectAdapter(carTypes);

        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        AnyTextView promocode = (AnyTextView) dialog.findViewById(R.id.txt_promoCode);
        cancelbutton.setText(context.getResources().getString(text));
        promocode.setOnClickListener(promoclicklistener);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        //setButtonChanger();
        // dialog.setCanceledOnTouchOutside(false);
        // dialog.setCancelable(false);
        // return this.dialog;
    }

    private void setSelectAdapter(ArrayList<SelectCarEnt> carTypes) {
        mAdapter = new SelectCarAdapter(carTypes, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public SelectCarEnt getSelectedType() {
        return mAdapter.getSelectedItemPosition();
    }

    public void initEstimateFareBottomSheet(View.OnClickListener requestclicklistener, String Type,
                                            String numberOfPeople, String ImageUrl, int fare) {
        //this.dialog.setContentView(layoutID);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.img_selected_ride);
        Glide.with(context).load(ImageUrl).into(imageView);
        AnyTextView textView = (AnyTextView) dialog.findViewById(R.id.txt_no_people);
        textView.setText(numberOfPeople);
        AnyTextView name = (AnyTextView) dialog.findViewById(R.id.txt_type);
        name.setText(Type);
        AnyTextView faretextView = (AnyTextView) dialog.findViewById(R.id.txt_fare_ammount);
        int fare_increse = fare + 100;
        faretextView.setText("AED "+fare+" - "+fare_increse);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_done);
        cancelbutton.setOnClickListener(requestclicklistener);
        // dialog.setCanceledOnTouchOutside(false);
        //return this.dialog;
    }

    public void initScheduleDateTimeDialog(View.OnClickListener okclicklistener,
                                           View.OnClickListener Dateclicklistener,
                                           View.OnClickListener Timeclicklistener) {
        //this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        cancelbutton.setOnClickListener(okclicklistener);
        //  dialog.setCanceledOnTouchOutside(false);
        final AnyTextView date_pick = (AnyTextView) dialog.findViewById(R.id.txt_datepicker);
        date_pick.setOnClickListener(Dateclicklistener);
        final AnyTextView time_pick = (AnyTextView) dialog.findViewById(R.id.txt_timepicker);
        time_pick.setOnClickListener(Timeclicklistener);
        // return this.dialog;
    }

    public void initSchedulesTimeDialog(View.OnClickListener okclicklistener,

                                        Date startDate,
                                        Date StartTime) {
        // this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        cancelbutton.setOnClickListener(okclicklistener);
        // dialog.setCanceledOnTouchOutside(false);
        /*final WheelView date_loop = (WheelView) dialog.findViewById(R.id.date);
        date_loop.setInitialPosition(2);
        date_loop.setIsLoopEnabled(false);
        date_loop.addOnLoopScrollListener(dateloop);
        date_loop.setTextSize(15);
        date_loop.setItems(getdateitems(startDate));
        final WheelView time_loop = (WheelView) dialog.findViewById(R.id.time_loop);
        time_loop.setInitialPosition(2);
        time_loop.setIsLoopEnabled(false);
        time_loop.addOnLoopScrollListener(dateloop);
        time_loop.setTextSize(15);
        time_loop.setItems(gettimeList(StartTime));*/
        // return this.dialog;
    }

    private List gettimeList(Date startTime) {

        return null;
    }

    private List getdateitems(Date startDate) {
        return null;
    }

  /*  private void setButtonChanger() {
        AnyTextView economyInactivetxt = (AnyTextView) dialog.findViewById(R.id.txt_economy);
        final AnyTextView economyactivetxt = (AnyTextView) dialog.findViewById(R.id.txteconomyActive);
        AnyTextView businesstxt = (AnyTextView) dialog.findViewById(R.id.txt_business);
        final AnyTextView businessActivetxt = (AnyTextView) dialog.findViewById(R.id.txt_businessActive);
        AnyTextView viptxt = (AnyTextView) dialog.findViewById(R.id.txt_vip);
        final AnyTextView vipActivetxt = (AnyTextView) dialog.findViewById(R.id.txt_vipActive);
        ImageView economyinactive = (ImageView) dialog.findViewById(R.id.iv_economyCar);
        final ImageView economyactive = (ImageView) dialog.findViewById(R.id.iv_economyCarActive);
        ImageView businessinactive = (ImageView) dialog.findViewById(R.id.iv_businessCar);
        final ImageView businessactive = (ImageView) dialog.findViewById(R.id.iv_businessCarActive);
        ImageView vipinactive = (ImageView) dialog.findViewById(R.id.iv_vipCar);
        final ImageView vipactive = (ImageView) dialog.findViewById(R.id.iv_vipCarActive);
        economyinactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                economyactive.setVisibility(View.VISIBLE);
                economyactivetxt.setVisibility(View.VISIBLE);
                vipactive.setVisibility(View.GONE);
                vipActivetxt.setVisibility(View.GONE);
                businessactive.setVisibility(View.GONE);
                businessActivetxt.setVisibility(View.GONE);
            }
        });
        economyactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        businessactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        businessinactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                economyactive.setVisibility(View.GONE);
                economyactivetxt.setVisibility(View.GONE);
                vipactive.setVisibility(View.GONE);
                vipActivetxt.setVisibility(View.GONE);
                businessactive.setVisibility(View.VISIBLE);
                businessActivetxt.setVisibility(View.VISIBLE);
            }
        });
        vipactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        vipinactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                economyactive.setVisibility(View.GONE);
                economyactivetxt.setVisibility(View.GONE);
                vipactive.setVisibility(View.VISIBLE);
                vipActivetxt.setVisibility(View.VISIBLE);
                businessactive.setVisibility(View.GONE);
                businessActivetxt.setVisibility(View.GONE);
            }
        });


    }*/

    public void showDialog() {
        // setupRideNowDialog();
// init the bottom sheet behavior


// change the state of the bottom sheet
        //  bottomSheetBehavior.setAllowUserDragging(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

// set the peek height
        bottomSheetBehavior.setPeekHeight((int) context.getResources().getDimension(R.dimen.x100));

// set hideable or not
        bottomSheetBehavior.setHideable(false);

        //dialog.show();
    }

    public void setCancelable(boolean isCancelable) {
        //dialog.setCancelable(isCancelable);
        //  dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        RemoveDialog();
        // dialog.dismiss();
    }

    private void RemoveDialog() {
        mainParent.removeView(dialog);
        // dialog.dismiss();
    }


}
