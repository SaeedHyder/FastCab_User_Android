package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.app.fastcab.R;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.TokenUpdater;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcab.global.AppConstants.PUSH_ON;

/**
 * Created by saeedhyder on 6/21/2017.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txt_notification)
    AnyTextView txtNotification;
    @BindView(R.id.ll_notificationToggel)
    LinearLayout llNotificationToggel;
    @BindView(R.id.txt_changePassword)
    AnyTextView txtChangePassword;
    @BindView(R.id.ll_ChangePassword)
    LinearLayout llChangePassword;
    @BindView(R.id.toggle_notifications)
    ToggleButton toggleNotifications;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        toggleNotifications.setChecked(prefHelper.getUser().getPushStatus() == PUSH_ON);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {
        toggleNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TurnNotificationStatus(isChecked);
            }
        });
        txtChangePassword.setOnClickListener(this);
    }

    public void TurnNotificationStatus(boolean isChecked) {
        loadingStarted();
        Call<ResponseWrapper<UserEnt>> call = webService.ChangeNotifiationStatus(prefHelper.getUserId(), isChecked ? 1 : 0);
        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putUser(response.body().getResult());
                    prefHelper.setUsrId(response.body().getResult().getId() + "");

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_changePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), ChangePasswordFragment.class.getSimpleName());

                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.setting));
        titleBar.showMenuButton();
    }


}
