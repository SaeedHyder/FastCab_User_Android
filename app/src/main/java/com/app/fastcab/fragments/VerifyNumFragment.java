package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by saeedhyder on 6/20/2017.
 */

public class VerifyNumFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_LogoIcon)
    ImageView ivLogoIcon;
    @BindView(R.id.iv_emailIcon)
    ImageView ivEmailIcon;
    @BindView(R.id.edtphone)
    AnyEditTextView edtphone;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.txtResetPass)
    AnyTextView txtResetPass;




    public static VerifyNumFragment newInstance() {
        return new VerifyNumFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_number, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtphone.setText(getDockActivity().getCountryCode());
        setListners();



    }

    private void setListners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.verufy_number));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_submit:
                UIHelper.hideSoftKeyboard(getDockActivity(),edtphone);
                if (validated())
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        setupVerifyNumber();
                    }

                break;

        }

    }

    public void setupVerifyNumber() {
        loadingStarted();
        Call<ResponseWrapper<UserEnt>> call = webService.VerifyNumber(prefHelper.getUserId(),edtphone.getText().toString());
        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
        @Override
        public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
            loadingFinished();
            if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                prefHelper.putUser(response.body().getResult());
                prefHelper.setUsrId(response.body().getResult().getId()+"");
                getDockActivity().replaceDockableFragment(EntryCodeFragment.newInstance(), "EntryCodeFragment");
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

    private boolean validated() {
        if (edtphone.getText().toString().isEmpty()) {
            if (edtphone.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtphone.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtphone.getText().toString().length() < 11) {
            if (edtphone.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtphone.setError(getString(R.string.numberLength));
            return false;
        } else
            return true;
    }
}
