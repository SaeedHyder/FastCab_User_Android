package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.PastTripsEnt;
import com.app.fastcab.entities.UpcomingTripsEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.UpcomingTripsBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class UpcomingTripsFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.UpcomingRides_ListView)
    ListView UpcomingRidesListView;
    
    private ArrayListAdapter<UpcomingTripsEnt> adapter;

    private ArrayList<UpcomingTripsEnt> userCollection = new ArrayList<>();

    public static UpcomingTripsFragment newInstance() {
        return new UpcomingTripsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<UpcomingTripsEnt>(getDockActivity(), new UpcomingTripsBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_trips, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getUpcomingTripsData();
    }

    private void getUpcomingTripsData() {

        /* if(result.size()<0)
        {
            txtNoData.setVisibility(View.VISIBLE);
            UpcomingRidesListView.setVisibility(View.GONE);
        }
        else{
            txtNoData.setVisibility(View.GONE);
            UpcomingRidesListView.setVisibility(View.GONE);
        }*/

        userCollection = new ArrayList<>();
        userCollection.add(new UpcomingTripsEnt("055082595", "AED 15.00", "Wed, June 15 at 4:30 Am - 4:45 Am", "Business", "drawable://" + R.drawable.trip));
        userCollection.add(new UpcomingTripsEnt("055082595", "AED 15.00", "Wed, June 15 at 4:30 Am - 4:45 Am", "Business", "drawable://" + R.drawable.trip));
        userCollection.add(new UpcomingTripsEnt("055082595", "AED 15.00", "Wed, June 15 at 4:30 Am - 4:45 Am", "Business", "drawable://" + R.drawable.trip));

        bindData(userCollection);
    }

    private void bindData(ArrayList<UpcomingTripsEnt> userCollection) {

        adapter.clearList();
        if (UpcomingRidesListView!=null)
            UpcomingRidesListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setListners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Your_Trips));
    }

   
}
