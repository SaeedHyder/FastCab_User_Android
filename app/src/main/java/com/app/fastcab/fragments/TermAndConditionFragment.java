package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/23/2017.
 */

public class TermAndConditionFragment extends BaseFragment {
    @BindView(R.id.txt_term_condition)
    TextView txtTermCondition;

    @BindView(R.id.chk_read)
    CheckBox chkRead;
   // @BindView(R.id.chk_read1)
   // CheckBox chkRead1;
    Unbinder unbinder;


    public static TermAndConditionFragment newInstance() {
        return new TermAndConditionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_condition, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.terms_conditons));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            bindTextview();
        }
      /*  chkRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                  *//*  prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);*//*
                   // getMainActivity().refreshSideMenu();
                  //  getDockActivity().replaceDockableFragment(UserHomeFragment.newInstance(),"User Home Fragment");
                }
            }
        });*/

    }

    private void bindTextview() {

        txtTermCondition.setText(" ");
     /*   Call<ResponseWrapper<StaticPageEnt>> call = webService.getTermandAbout(prefHelper.getUserId(),"term");
        call.enqueue(new Callback<ResponseWrapper<StaticPageEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<StaticPageEnt>> call, Response<ResponseWrapper<StaticPageEnt>> response) {
                if (response.body().getResponse().equals("2000")) {
                    settitle(response.body().getResult().getBody());
                }
                else{
                    UIHelper.showShortToastInCenter(getDockActivity(),response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<StaticPageEnt>> call, Throwable t) {
                Log.e("TermAndCondition", t.toString());
              //  UIHelper.showShortToastInCenter(getDockActivity(), t.toString());
            }
        });*/

    }

    private void settitle(String response) {
        getMainActivity().titleBar.setSubHeading(getString(R.string.terms_conditons));
        getMainActivity().titleBar.invalidate();
        txtTermCondition.setText(response);
        txtTermCondition.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
