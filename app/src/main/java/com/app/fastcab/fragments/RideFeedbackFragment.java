package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.CustomRatingBar;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 6/29/2017.
 */

public class RideFeedbackFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txt_driving)
    AnyTextView txtDriving;
    @BindView(R.id.drivingTick)
    ImageView drivingTick;
    @BindView(R.id.ll_Driving)
    RelativeLayout llDriving;
    @BindView(R.id.txt_navigation)
    AnyTextView txtNavigation;
    @BindView(R.id.navigationTick)
    ImageView navigationTick;
    @BindView(R.id.ll_Navigation)
    RelativeLayout llNavigation;
    @BindView(R.id.ll_barOptionsRow1)
    LinearLayout llBarOptionsRow1;
    @BindView(R.id.txt_comfort)
    AnyTextView txtComfort;
    @BindView(R.id.comfortTick)
    ImageView comfortTick;
    @BindView(R.id.ll_Comfort)
    RelativeLayout llComfort;
    @BindView(R.id.txt_CarQuality)
    AnyTextView txtCarQuality;
    @BindView(R.id.carQualityTick)
    ImageView carQualityTick;
    @BindView(R.id.ll_CarQuality)
    RelativeLayout llCarQuality;
    @BindView(R.id.ll_barOptionsRow2)
    LinearLayout llBarOptionsRow2;
    @BindView(R.id.ll_bars)
    LinearLayout llBars;
    @BindView(R.id.edtComments)
    AnyEditTextView edtComments;
    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.SubmitButton)
    Button SubmitButton;

    Boolean drivingBoolean = false;
    Boolean navigationBoolean = false;
    Boolean comfortBoolean = false;
    Boolean carQualityBoolean = false;


    public static RideFeedbackFragment newInstance() {
        return new RideFeedbackFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride_feedback, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {

        llDriving.setOnClickListener(this);
        llNavigation.setOnClickListener(this);
        llComfort.setOnClickListener(this);
        llCarQuality.setOnClickListener(this);

        edtComments.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.ll_Driving:
                if (!drivingBoolean) {
                    llDriving.setBackground(getResources().getDrawable(R.drawable.blue_rect));
                    drivingTick.setVisibility(View.VISIBLE);
                    txtDriving.setTextColor(getResources().getColor(R.color.white));
                    txtDriving.setAlpha(1);
                    drivingBoolean = true;
                } else {
                    llDriving.setBackground(getResources().getDrawable(R.drawable.black_border));
                    drivingTick.setVisibility(View.VISIBLE);
                    txtDriving.setTextColor(getResources().getColor(R.color.black));
                    drivingBoolean = false;
                }
                break;

            case R.id.ll_Navigation:
                if (!navigationBoolean) {
                    llNavigation.setBackground(getResources().getDrawable(R.drawable.blue_rect));
                    navigationTick.setVisibility(View.VISIBLE);
                    txtNavigation.setTextColor(getResources().getColor(R.color.white));
                    txtNavigation.setAlpha(1);
                    navigationBoolean = true;
                } else {
                    llNavigation.setBackground(getResources().getDrawable(R.drawable.black_border));
                    navigationTick.setVisibility(View.VISIBLE);
                    txtNavigation.setTextColor(getResources().getColor(R.color.black));
                    navigationBoolean = false;
                }
                break;

            case R.id.ll_Comfort:
                if (!comfortBoolean) {
                    llComfort.setBackground(getResources().getDrawable(R.drawable.blue_rect));
                    comfortTick.setVisibility(View.VISIBLE);
                    txtComfort.setTextColor(getResources().getColor(R.color.white));
                    txtComfort.setAlpha(1);
                    comfortBoolean = true;
                } else {
                    llComfort.setBackground(getResources().getDrawable(R.drawable.black_border));
                    comfortTick.setVisibility(View.VISIBLE);
                    txtComfort.setTextColor(getResources().getColor(R.color.black));
                    comfortBoolean = false;
                }
                break;

            case R.id.ll_CarQuality:
                if (!carQualityBoolean) {
                    llCarQuality.setBackground(getResources().getDrawable(R.drawable.blue_rect));
                    carQualityTick.setVisibility(View.VISIBLE);
                    txtCarQuality.setTextColor(getResources().getColor(R.color.white));
                    txtCarQuality.setAlpha(1);
                    carQualityBoolean = true;
                } else {
                    llCarQuality.setBackground(getResources().getDrawable(R.drawable.black_border));
                    carQualityTick.setVisibility(View.VISIBLE);
                    txtCarQuality.setTextColor(getResources().getColor(R.color.black));
                    carQualityBoolean = false;
                }
                break;
            case R.id.SubmitButton:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
                break;
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.Ride_Feedback));
    }


}
