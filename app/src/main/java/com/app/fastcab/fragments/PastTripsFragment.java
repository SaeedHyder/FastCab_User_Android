package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.ProgressEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.PastTripsBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcab.R.id.txt_no_data;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class PastTripsFragment extends BaseFragment implements View.OnClickListener {

    @BindView(txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.PastTrips_ListView)
    ListView PastTripsListView;

    private ArrayListAdapter<ProgressEnt> adapter;

    private ArrayList<ProgressEnt> userCollection = new ArrayList<>();

    public static PastTripsFragment newInstance() {
        return new PastTripsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<ProgressEnt>(getDockActivity(), new PastTripsBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_trips, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
            getPastTrips();
        // getPastTripsData();


    }

    private void getPastTrips() {
        getDockActivity().onLoadingStarted();
        Call<ResponseWrapper<ArrayList<ProgressEnt>>> call = webService.getUserRideComplete(prefHelper.getUserId() + "");
        call.enqueue(new Callback<ResponseWrapper<ArrayList<ProgressEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<ProgressEnt>>> call, Response<ResponseWrapper<ArrayList<ProgressEnt>>> response) {
                getDockActivity().onLoadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getPastTripsData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<ProgressEnt>>> call, Throwable t) {
                getDockActivity().onLoadingFinished();
                Log.e(UpcomingTripsFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    private void getPastTripsData(ArrayList<ProgressEnt> result) {

        if(result.size()<=0)
        {
            txtNoData.setVisibility(View.VISIBLE);
            PastTripsListView.setVisibility(View.GONE);
        }
        else{
            txtNoData.setVisibility(View.GONE);
            PastTripsListView.setVisibility(View.VISIBLE);
        }

        userCollection = new ArrayList<>();
       /* userCollection.add(new PastTripsEnt("055082595","AED 15.00","13-6-17 1:36PM","AED 2.00","drawable://" + R.drawable.trip));
        userCollection.add(new PastTripsEnt("055082595","AED 15.00","13-6-17 1:36PM","AED 2.00","drawable://" + R.drawable.trip));
        userCollection.add(new PastTripsEnt("055082595","AED 15.00","13-6-17 1:36PM","AED 2.00","drawable://" + R.drawable.trip));
*/
        userCollection.addAll(result);
        bindData(userCollection);

    }

    private void bindData(ArrayList<ProgressEnt> userCollection) {

        adapter.clearList();
        if (PastTripsListView != null)
            PastTripsListView.setAdapter(adapter);
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
