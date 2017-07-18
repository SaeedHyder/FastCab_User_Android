package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.app.fastcab.R;
import com.app.fastcab.entities.RideFeedbackEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.RideFeedbackBinder;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.CustomRatingBar;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


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


    private ArrayListAdapter<RideFeedbackEnt> adapter;

    private ArrayList<RideFeedbackEnt> userCollection = new ArrayList<>();

    public static RideFeedbackFragment newInstance() {
        return new RideFeedbackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<RideFeedbackEnt>(getDockActivity(), new RideFeedbackBinder(getDockActivity()));
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
        getFeedbackData();
    }

    private void getFeedbackData() {
        userCollection = new ArrayList<>();
        userCollection.add(new RideFeedbackEnt("Driving"));
        userCollection.add(new RideFeedbackEnt("Navigation"));
        userCollection.add(new RideFeedbackEnt("Comfort"));
        userCollection.add(new RideFeedbackEnt("Car Quality"));


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
