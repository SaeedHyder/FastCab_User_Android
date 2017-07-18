package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.NotificationEnt;
import com.app.fastcab.entities.NotificationListEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.NotificationitemBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/21/2017.
 */

public class NotificationFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_Notifications)
    ListView lvNotifications;

    private ArrayListAdapter<NotificationListEnt> adapter;
    private ArrayList<NotificationListEnt> userCollection;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NotificationListEnt>(getDockActivity(), new NotificationitemBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
            getNotificationFromServer();
    }

    private void getNotificationFromServer() {
        loadingStarted();
        Call<ResponseWrapper<ArrayList<NotificationListEnt>>> call = webService.GetNotifiation(prefHelper.getUserId());
        call.enqueue(new Callback<ResponseWrapper<ArrayList<NotificationListEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<NotificationListEnt>>> call, Response<ResponseWrapper<ArrayList<NotificationListEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                   setNotifications(response.body().getResult());

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<NotificationListEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    private void setNotifications(ArrayList<NotificationListEnt> result) {
        userCollection =result;/*new ArrayList<>();

        userCollection.add(new NotificationEnt(R.drawable.logo_a,"Lorem is simply dummy text"));
        userCollection.add(new NotificationEnt(R.drawable.logo_a,"Lorem is simply dummy text"));
        userCollection.add(new NotificationEnt(R.drawable.logo_a,"Lorem is simply dummy text"));*/

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotifications.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvNotifications.setVisibility(View.VISIBLE);
        }

        bindData(userCollection);

    }

    private void bindData(ArrayList<NotificationListEnt> userCollection) {
        adapter.clearList();
        lvNotifications.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResources().getString(R.string.notifications));
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.showMenuButton();
    }



}

