package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class PaymentMethodFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_selectCOD)
    ImageView ivSelectCOD;
    @BindView(R.id.ll_cashOnDelivery)
    LinearLayout llCashOnDelivery;
    @BindView(R.id.iv_selectCreditCard)
    ImageView ivSelectCreditCard;
    @BindView(R.id.ll_creditCard)
    LinearLayout llCreditCard;

    public static PaymentMethodFragment newInstance() {
        return new PaymentMethodFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_methods, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(),HomeMapFragment.class.getSimpleName());
            }
        });
        titleBar.setSubHeading(getResources().getString(R.string.payment_method));
    }


    @OnClick({R.id.ll_cashOnDelivery, R.id.ll_creditCard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_cashOnDelivery:
                ivSelectCOD.setVisibility(View.VISIBLE);
                ivSelectCreditCard.setVisibility(View.GONE);
                break;
            case R.id.ll_creditCard:
                ivSelectCOD.setVisibility(View.GONE);
                ivSelectCreditCard.setVisibility(View.VISIBLE);
                getDockActivity().replaceDockableFragment(CreditCardDetailFragment.newInstance(),CreditCardDetailFragment.class.getSimpleName());
                break;
        }
    }
}
