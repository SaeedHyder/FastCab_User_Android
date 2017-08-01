package com.app.fastcab.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.fastcab.R;
import com.app.fastcab.entities.FacebookLoginEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.ClickableSpanHelper;
import com.app.fastcab.helpers.FacebookLoginHelper;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.TokenUpdater;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.interfaces.FacebookLoginListener;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends BaseFragment implements FacebookLoginListener {


    @BindView(R.id.edtphone)
    AnyEditTextView edtEmail;
    @BindView(R.id.edtpassword)
    AnyEditTextView edtpassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.txtResetPass)
    AnyTextView txtResetPass;
    @BindView(R.id.txtSignup)
    AnyTextView txtSignup;
    @BindView(R.id.ll_loginfacebook)
    RelativeLayout llLoginfacebook;
    @BindView(R.id.loginButton_fb)
    LoginButton btnfbLogin;
    private CallbackManager callbackManager;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        setupFacebookLogin();
    }

    private void setupFacebookLogin() {

        callbackManager = CallbackManager.Factory.create();
        btnfbLogin.setFragment(this);
        FacebookLoginHelper facebookLoginHelper = new FacebookLoginHelper(getDockActivity(), this, this);
        btnfbLogin.registerCallback(callbackManager, facebookLoginHelper);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setSignupSpan(getResources().getString(R.string.new_user_create_account), getString(R.string.create_account), txtSignup);
        return view;

    }

    private void setSignupSpan(String text, String spanText, AnyTextView txtview) {
        SpannableStringBuilder stringBuilder = ClickableSpanHelper.initSpan(text);
        ClickableSpanHelper.setSpan(stringBuilder, text, spanText, new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));    // you can use custom color
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);    // this remove the underline
            }

            @Override
            public void onClick(View widget) {
                getDockActivity().replaceDockableFragment(SignUpFragment.newInstance(), SignUpFragment.class.getSimpleName());
            }
        });
        ClickableSpanHelper.setColor(stringBuilder, text, getResources().getString(R.string.new_user_text), "#e6ffffff");

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);
    }


    @OnClick({R.id.loginButton, R.id.txtResetPass, R.id.ll_loginfacebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                if (isvalidated()) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                        MakeUserLogin();
                }
                break;
            case R.id.txtResetPass:
                getDockActivity().replaceDockableFragment(ForgotPassowordFragment.newInstance(), "ForgotPassowordFragment");
                break;
            case R.id.ll_loginfacebook:
                btnfbLogin.performClick();
                break;
        }
    }

    private void MakeUserLogin() {
        loadingStarted();
        Call<ResponseWrapper<UserEnt>> call = webService.loginUser(edtEmail.getText().toString(), edtpassword.getText().toString());
        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.putUser(response.body().getResult());
                        prefHelper.setUsrId(response.body().getResult().getId() + "");
                    if (response.body().getResult().getIsVerified() == 1) {
                        prefHelper.setLoginStatus(true);
                        TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                                prefHelper.getUserId(),
                                AppConstants.Device_Type,
                                prefHelper.getFirebase_TOKEN());
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
                    } else {
                        getDockActivity().replaceDockableFragment(VerifyNumFragment.newInstance(), VerifyNumFragment.class.getSimpleName());

                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(LoginFragment.class.getSimpleName(), t.toString());
            }
        });

    }

    private boolean isvalidated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.valid_email));
            return false;
        } else if (edtpassword.getText().toString().isEmpty()) {
            edtpassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtpassword.getText().toString().length() < 6) {
            edtpassword.setError(getString(R.string.enter_valid_password));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onSuccessfulFacebookLogin(FacebookLoginEnt LoginEnt) {
        loadingStarted();
        Call<ResponseWrapper<UserEnt>> call = webService.loginFacebookUser(LoginEnt.getFacebookUID(),AppConstants.SOCIAL_MEDIA_TYPE);
        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putUser(response.body().getResult());
                    prefHelper.setUsrId(response.body().getResult().getId()+"");
                    prefHelper.setLoginStatus(true);
                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getUserId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
                } else {
                    LoginManager.getInstance().logOut();
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(LoginFragment.class.getSimpleName(), t.toString());
            }
        });
    }
}
