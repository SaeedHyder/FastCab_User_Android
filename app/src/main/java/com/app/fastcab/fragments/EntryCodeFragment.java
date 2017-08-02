package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.TokenUpdater;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.PinEntryEditText;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/20/2017.
 */

public class EntryCodeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txtverificaiton)
    AnyTextView txtverificaiton;
    @BindView(R.id.txtverify_message)
    AnyTextView txtverifyMessage;
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.txt_reset_code)
    AnyTextView txtResetCode;


    public static EntryCodeFragment newInstance() {
        return new EntryCodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_code, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {
        btnSubmit.setOnClickListener(this);
        txtResetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_reset_code:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())){
                    resendCode();
                }
                break;
            case R.id.btn_submit:
                if (validater())
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        sendEntryCodeToServer();
                    }

                break;
        }
    }

    private void resendCode() {
        loadingStarted();
        Call<ResponseWrapper<UserEnt>> call = webService.resetVerificationCode(prefHelper.getUserId());
        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putUser(response.body().getResult());
                    prefHelper.setUsrId(response.body().getResult().getId() + "");

                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getUserId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                   UIHelper.showShortToastInCenter(getDockActivity(),response.body().getMessage());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(VerifyNumFragment.class.getSimpleName(), t.toString());
            }
        });

    }

    public void sendEntryCodeToServer() {
        loadingStarted();
        Call<ResponseWrapper<UserEnt>> call = webService.VerifyCode(prefHelper.getUserId(), txtPinEntry.getText().toString());
        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putUser(response.body().getResult());
                    prefHelper.setUsrId(response.body().getResult().getId() + "");
                    prefHelper.setLoginStatus(true);
                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getUserId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeFragment.class.getSimpleName());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(VerifyNumFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    private boolean validater() {
        if (txtPinEntry.getText().toString().trim().equals("") || txtPinEntry.getText().toString().length() > 4) {
            UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.entrycode_error));
            return false;
        } else
            return true;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.verification_code));
    }
}
