package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.app.fastcab.R;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.RideFeedbackEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.RideFeedbackBinder;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.CustomRatingBar;
import com.app.fastcab.ui.views.TitleBar;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by saeedhyder on 6/29/2017.
 */

public class RideFeedbackFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.edtComments)
    AnyEditTextView edtComments;
    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.SubmitButton)
    Button SubmitButton;

    private RideFeedbackBinder binder;
    private ArrayListAdapter<RideFeedbackEnt> adapter;

    private ArrayList<RideFeedbackEnt> userCollection = new ArrayList<>();

    public static RideFeedbackFragment newInstance() {
        return new RideFeedbackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = new RideFeedbackBinder(getDockActivity());
        adapter = new ArrayListAdapter<RideFeedbackEnt>(getDockActivity(), binder);
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
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
            getAllImproveType();
        setListners();

    }

    private void getAllImproveType() {
        loadingStarted();
        Call<ResponseWrapper<ArrayList<RideFeedbackEnt>>> call = webService.getImproveType();
        call.enqueue(new Callback<ResponseWrapper<ArrayList<RideFeedbackEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<RideFeedbackEnt>>> call, Response<ResponseWrapper<ArrayList<RideFeedbackEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getFeedbackData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<RideFeedbackEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e(RideFeedbackFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    private void getFeedbackData(ArrayList<RideFeedbackEnt> result) {
        userCollection = new ArrayList<>();

        userCollection.addAll(result);


        bindData(userCollection);
    }

    private void bindData(ArrayList<RideFeedbackEnt> userCollection) {

        adapter.clearList();
        gridView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }


    private void setListners() {

        SubmitButton.setOnClickListener(this);

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


            case R.id.SubmitButton:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    submitRating();

                break;
        }
    }

    private void submitRating() {
        loadingStarted();
        Call<ResponseWrapper> call = webService.submitAppFeedback(prefHelper.getUserId(), rbAddRating.getScore() + "",
                StringUtils.join(binder.getSelectedReasons(),","),edtComments.getText().toString());
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                Log.e(RideFeedbackFragment.class.getSimpleName(), t.toString());
            }
        });
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.Ride_Feedback));
    }


}
