package com.app.fastcab.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.app.fastcab.R;
import com.app.fastcab.ui.views.AnyTextView;

import java.util.Date;
import java.util.List;

import io.blackbox_vision.wheelview.view.WheelView;

/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
    private BottomSheetDialog dialog;
    private Context context;

    public BottomSheetDialogHelper(Context context) {
        this.context = context;
        this.dialog = new BottomSheetDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public Dialog initRideDetailBottomSheet(int layoutID, View.OnClickListener oncancelclicklistener) {
        this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancel_ride);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        dialog.setCanceledOnTouchOutside(false);
        return this.dialog;
    }
    public Dialog initSelectRideBottomSheet(int layoutID, View.OnClickListener promoclicklistener,
                                            View.OnClickListener oncancelclicklistener) {
        this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        AnyTextView promocode = (AnyTextView)dialog.findViewById(R.id.txt_promoCode);
        promocode.setOnClickListener(promoclicklistener);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        setButtonChanger();
        dialog.setCanceledOnTouchOutside(false);
        return this.dialog;
    }
    public Dialog initSelectRideBottomSheet(int layoutID, View.OnClickListener promoclicklistener,
                                            View.OnClickListener oncancelclicklistener,int text) {
        this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        AnyTextView promocode = (AnyTextView)dialog.findViewById(R.id.txt_promoCode);
        cancelbutton.setText(context.getResources().getString(text));
        promocode.setOnClickListener(promoclicklistener);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        setButtonChanger();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return this.dialog;
    }
    public Dialog initEstimateFareBottomSheet(int layoutID,
                                              View.OnClickListener requestclicklistener) {
        this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_done);
        cancelbutton.setOnClickListener(requestclicklistener);
        dialog.setCanceledOnTouchOutside(false);
        return this.dialog;
    }
    public BottomSheetDialog initScheduleDateTimeDialog(int layoutID,
                                                        View.OnClickListener okclicklistener,
                                                        View.OnClickListener Dateclicklistener,
                                                        View.OnClickListener Timeclicklistener){
        this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        cancelbutton.setOnClickListener(okclicklistener);
        dialog.setCanceledOnTouchOutside(false);
        final AnyTextView date_pick = (AnyTextView) dialog.findViewById(R.id.txt_datepicker);
        date_pick.setOnClickListener(Dateclicklistener);
        final AnyTextView time_pick = (AnyTextView) dialog.findViewById(R.id.txt_timepicker);
        time_pick.setOnClickListener(Timeclicklistener);
        return this.dialog;
    }
    public BottomSheetDialog initSchedulesTimeDialog(int layoutID,
                                                     View.OnClickListener okclicklistener,
                                                     WheelView.OnLoopScrollListener dateloop,
                                                     Date startDate,
                                                     Date StartTime){
        this.dialog.setContentView(layoutID);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        cancelbutton.setOnClickListener(okclicklistener);
        dialog.setCanceledOnTouchOutside(false);
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
        return this.dialog;
    }

    private List gettimeList(Date startTime) {

        return null;
    }

    private List getdateitems(Date startDate) {
        return null;
    }

    private void setButtonChanger() {
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
                vipactive.setVisibility(View.GONE);
                businessactive.setVisibility(View.GONE);
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
                vipactive.setVisibility(View.GONE);
                businessactive.setVisibility(View.VISIBLE);
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
                vipactive.setVisibility(View.VISIBLE);
                businessactive.setVisibility(View.GONE);
            }
        });


    }

    public void showDialog(){

        dialog.show();
    }
    public void setCancelable(boolean isCancelable){
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }
    public void hideDialog(){
        dialog.dismiss();
    }



}
