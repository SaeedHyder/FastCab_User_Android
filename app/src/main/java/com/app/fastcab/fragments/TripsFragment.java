package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.fastcab.activities.DockActivity.KEY_FRAG_FIRST;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class TripsFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_past)
    Button btnPast;
    @BindView(R.id.btn_upcoming)
    Button btnUpcoming;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    public static TripsFragment newInstance() {
        return new TripsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReplaceListViewFragment(PastTripsFragment.newInstance());
        setListners();
    }

    private void setListners() {

        btnPast.setOnClickListener(this);
        btnUpcoming.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_past:
                btnPast.setBackgroundColor(getResources().getColor(R.color.button_color));
                btnPast.setTextColor(getDockActivity().getResources().getColor(R.color.white));

                btnUpcoming.setBackgroundColor(getResources().getColor(R.color.white));
                btnUpcoming.setTextColor(getResources().getColor(R.color.black));

                ReplaceListViewFragment(PastTripsFragment.newInstance());

                break;

            case R.id.btn_upcoming:
                btnUpcoming.setBackgroundColor(getResources().getColor(R.color.button_color));
                btnUpcoming.setTextColor(getResources().getColor(R.color.white));

                btnPast.setBackgroundColor(getResources().getColor(R.color.white));
                btnPast.setTextColor(getResources().getColor(R.color.black));


                ReplaceListViewFragment(UpcomingTripsFragment.newInstance());

                break;



        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.Your_Trips));
    }

    private void ReplaceListViewFragment(BaseFragment frag) {

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.mainFrame, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();

    }


}
