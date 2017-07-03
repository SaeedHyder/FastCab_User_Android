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

/**
 * Created by saeedhyder on 6/23/2017.
 */

public class AboutUsFragment extends BaseFragment {

    @BindView(R.id.txt_term_condition)
    TextView txtTermCondition;

    public static TermAndConditionFragment newInstance() {
        return new TermAndConditionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.terms_conditons));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            bindTextview();
        }

    }

    private void bindTextview() {
        txtTermCondition.setText(getResources().getString(R.string.lorem_ipsum) + "\n \n \n " + getResources().getString(R.string.lorem_ipsum));
        txtTermCondition.setMovementMethod(new ScrollingMovementMethod());
    }




}
