package com.app.fastcab.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.ClickableSpanHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.google.firebase.messaging.RemoteMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment  {


    @BindView(R.id.edtphone)
    AnyEditTextView edtphone;
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


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        setListeners();

    }

    private void setListeners() {
        //loginButton.setOnClickListener(this);
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
        setSignupSpan(getResources().getString(R.string.new_user_create_account),getString(R.string.create_account),txtSignup);
        return view;

    }

    private void setSignupSpan(String text,String spanText,AnyTextView txtview) {
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
                getDockActivity().replaceDockableFragment(SignUpFragment.newInstance(),SignUpFragment.class.getSimpleName());
            }
        });

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);
    }





    @OnClick({R.id.loginButton, R.id.txtResetPass, R.id.ll_loginfacebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                if (isvalidated()){
                    prefHelper.setLoginStatus(true);
                    //Intent intent=new Intent(getMainActivity(), MapsActivity.class);
                    //startActivity(intent);
                    getDockActivity().replaceDockableFragment(TripsFragment.newInstance(), "HomeFragmnet");
                }
                break;
            case R.id.txtResetPass:
               getDockActivity().replaceDockableFragment(ForgotPassowordFragment.newInstance(),"ForgotPassowordFragment");
                break;
            case R.id.ll_loginfacebook:
                UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented in Beta");
                break;
        }
    }

    private boolean isvalidated() {
        if (edtpassword.getText().toString().isEmpty()) {
            edtpassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtpassword.getText().toString().length() < 6) {
            edtpassword.setError(getString(R.string.enter_valid_password));
            return false;
        } else if (edtphone.getText().toString().isEmpty()) {
            edtphone.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtphone.getText().toString().length() < 10 || edtphone.getText().toString().length() > 16) {
            edtphone.setError(getString(R.string.enter_valid_number_error));
            return false;
        } else {
            return true;
        }
    }
}
