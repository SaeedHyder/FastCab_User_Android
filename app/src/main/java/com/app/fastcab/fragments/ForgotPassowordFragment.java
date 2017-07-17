package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.DialogHelper;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 6/20/2017.
 */

public class ForgotPassowordFragment extends BaseFragment {
    @BindView(R.id.edtphone)
    AnyEditTextView edtEmail;
    @BindView(R.id.btn_submit)
    Button SubmitButton;

    public static ForgotPassowordFragment newInstance() {
        return new ForgotPassowordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.forgot_password_heading));
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validated()) {
            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                setupForgotPassword();
            }

        }
    }

    private void setupForgotPassword() {
        loadingStarted();
        Call<ResponseWrapper> call = webService.ForgotPassword(edtEmail.getText().toString());
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.initForgotPasswordDialog(R.layout.dialog_reset_password, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());
                            dialogHelper.hideDialog();
                        }
                    }, getResources().getString(R.string.reset), response.body().getResult() + "");
                    dialogHelper.setCancelable(false);
                    dialogHelper.showDialog();
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                Log.e(ForgotPassowordFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    private boolean validated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.valid_email));
            return false;
        } else {
            return true;
        }
    }
}
