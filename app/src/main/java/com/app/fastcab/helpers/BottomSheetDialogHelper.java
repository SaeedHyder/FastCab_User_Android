package com.app.fastcab.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.ui.views.AnyTextView;

import java.util.Date;
import java.util.List;

import io.blackbox_vision.wheelview.view.WheelView;

/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
   // private BottomSheetDialog dialog;
    private LinearLayout dialog;
    private Context context;
    BottomSheetBehavior bottomSheetBehavior;
    private CoordinatorLayout mainParent;
    public BottomSheetDialogHelper(Context context, CoordinatorLayout mainParent,int LayoutID) {
        this.context = context;
        this.mainParent = mainParent;
        LayoutInflater inflater = LayoutInflater.from(context);
        dialog = (LinearLayout) inflater.inflate(LayoutID, null, false);
        mainParent.addView(dialog);
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams) dialog.getLayoutParams();
        params.setBehavior(new BottomSheetBehavior());
        dialog.requestLayout();
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // setupRideNowDialog();
// init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(dialog);

// change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
        bottomSheetBehavior.setPeekHeight((int)context.getResources().getDimension(R.dimen.x100));

// set hideable or not
        bottomSheetBehavior.setHideable(false);
    }
    public void initRatingDialog(View.OnClickListener onClickListener){
        bottomSheetBehavior.setPeekHeight((int)context.getResources().getDimension(R.dimen.x150));
        Button submit = (Button)dialog.findViewById(R.id.SubmitButton);
        submit.setOnClickListener(onClickListener);
    }
    public void initRideDetailBottomSheet(View.OnClickListener oncancelclicklistener) {
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancel_ride);
        cancelbutton.setOnClickListener(oncancelclicklistener);
    }
    public void initSelectRideBottomSheet(View.OnClickListener promoclicklistener,
                                            View.OnClickListener oncancelclicklistener) {
       // this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        AnyTextView promocode = (AnyTextView)dialog.findViewById(R.id.txt_promoCode);
        promocode.setOnClickListener(promoclicklistener);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        setButtonChanger();
       // dialog.setCanceledOnTouchOutside(false);
       // return this.dialog;
    }
    public void initSelectRideBottomSheet( View.OnClickListener promoclicklistener,
                                            View.OnClickListener oncancelclicklistener,int text) {
       // this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        AnyTextView promocode = (AnyTextView)dialog.findViewById(R.id.txt_promoCode);
        cancelbutton.setText(context.getResources().getString(text));
        promocode.setOnClickListener(promoclicklistener);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        setButtonChanger();
       // dialog.setCanceledOnTouchOutside(false);
       // dialog.setCancelable(false);
       // return this.dialog;
    }
    public void initEstimateFareBottomSheet(View.OnClickListener requestclicklistener) {
        //this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_done);
        cancelbutton.setOnClickListener(requestclicklistener);
       // dialog.setCanceledOnTouchOutside(false);
        //return this.dialog;
    }
    public void initScheduleDateTimeDialog(View.OnClickListener okclicklistener,
                                                        View.OnClickListener Dateclicklistener,
                                                        View.OnClickListener Timeclicklistener){
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
                                                     WheelView.OnLoopScrollListener dateloop,
                                                     Date startDate,
                                                     Date StartTime){
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

    private void setButtonChanger() {
        AnyTextView economyInactivetxt=(AnyTextView)dialog.findViewById(R.id.txt_economy);
        final AnyTextView economyactivetxt=(AnyTextView)dialog.findViewById(R.id.txteconomyActive);
        AnyTextView businesstxt=(AnyTextView)dialog.findViewById(R.id.txt_business);
        final AnyTextView businessActivetxt=(AnyTextView)dialog.findViewById(R.id.txt_businessActive);
        AnyTextView viptxt=(AnyTextView)dialog.findViewById(R.id.txt_vip);
        final AnyTextView vipActivetxt=(AnyTextView)dialog.findViewById(R.id.txt_vipActive);
        ImageView economyinactive = (ImageView)dialog.findViewById(R.id.iv_economyCar);
        final ImageView economyactive = (ImageView)dialog.findViewById(R.id.iv_economyCarActive);
        ImageView businessinactive = (ImageView)dialog.findViewById(R.id.iv_businessCar);
        final ImageView businessactive = (ImageView)dialog.findViewById(R.id.iv_businessCarActive);
        ImageView vipinactive = (ImageView)dialog.findViewById(R.id.iv_vipCar);
        final ImageView vipactive = (ImageView)dialog.findViewById(R.id.iv_vipCarActive);
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


    }

    public void showDialog(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //dialog.show();
    }
    public void setCancelable(boolean isCancelable){
        //dialog.setCancelable(isCancelable);
      //  dialog.setCanceledOnTouchOutside(isCancelable);
    }
    public void hideDialog(){
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        RemoveDialog();
       // dialog.dismiss();
    }
    private void RemoveDialog(){
        mainParent.removeView(dialog);
        // dialog.dismiss();
    }




}
