package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.PinEntryEditText;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.verification_code));
    }
}
