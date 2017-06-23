package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.fastcab.R.id.edtDateOfBirth;
import static com.app.fastcab.R.id.edtPassword;
import static com.app.fastcab.R.id.edtemail;

/**
 * Created by saeedhyder on 6/21/2017.
 */

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.edtUserName)
    AnyEditTextView edtUserName;
    @BindView(R.id.ll_userName)
    LinearLayout llUserName;
    @BindView(R.id.iv_MobileNumber)
    ImageView ivMobileNumber;
    @BindView(R.id.edtMobileNumber)
    AnyEditTextView edtMobileNumber;
    @BindView(R.id.ll_MobileNumber)
    LinearLayout llMobileNumber;
    @BindView(R.id.iv_Gender)
    ImageView ivGender;
    @BindView(R.id.sp_Gender)
    Spinner spGender;
    @BindView(R.id.ll_Gender)
    LinearLayout llGender;
    @BindView(R.id.iv_ll_currentAddress)
    ImageView ivLlCurrentAddress;
    @BindView(R.id.edtll_currentAddress)
    AnyEditTextView edtllCurrentAddress;
    @BindView(R.id.ll_currentAddress)
    LinearLayout llCurrentAddress;
    @BindView(R.id.iv_City)
    ImageView ivCity;
    @BindView(R.id.edtCity)
    AnyEditTextView edtCity;
    @BindView(R.id.ll_City)
    LinearLayout llCity;
    @BindView(R.id.iv_State)
    ImageView ivState;
    @BindView(R.id.edt_State)
    AnyEditTextView edtState;
    @BindView(R.id.ll_State)
    LinearLayout llState;
    @BindView(R.id.ll_city_state)
    LinearLayout llCityState;
    @BindView(R.id.iv_zipCode)
    ImageView ivZipCode;
    @BindView(R.id.edtzipCode)
    AnyEditTextView edtzipCode;
    @BindView(R.id.ll_zipCode)
    LinearLayout llZipCode;
    @BindView(R.id.ll_SignUpFields)
    LinearLayout llSignUpFields;
    @BindView(R.id.sv_signup)
    ScrollView svSignup;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        spGender();
    }

    private void spGender() {
        List<String> categories = new ArrayList<>();
        categories.add("Male");
        categories.add("Female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(dataAdapter);
        spGender.setSelection(0);
    }


    private void setListners() {

    }

    private boolean isvalidate() {

        if (edtUserName.getText() == null || (edtUserName.getText().toString().isEmpty())) {
            edtUserName.setError(getString(R.string.enter_username));
            return false;
        }
        else if (edtMobileNumber.getText().toString().isEmpty()) {
            edtMobileNumber.setError(getString(R.string.enter_phone));
            return false;
        }
        else if (edtMobileNumber.getText().toString().length() < 11) {
            edtMobileNumber.setError(getString(R.string.numberLength));
            return false;
        } else if (edtllCurrentAddress.getText() == null || (edtllCurrentAddress.getText().toString().isEmpty())) {
            edtllCurrentAddress.setError(getString(R.string.enter_currentAddress));
            return false;
        }
        else if( edtCity.getText() == null || (edtCity.getText().toString().isEmpty())) {
            edtCity.setError(getString(R.string.enter_city));
            return false;
        } else if (edtState.getText() == null || (edtState.getText().toString().isEmpty()) ) {
            edtState.setError(getString(R.string.enter_state));
            return false;
        } else if (edtzipCode.getText() == null || (edtzipCode.getText().toString().isEmpty())) {
            edtzipCode.setError(getString(R.string.enter_zipCode));
            return false;
        }else {
            return true;
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showTickButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isvalidate()){
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());}
            }
        });
        titleBar.setSubHeading(getString(R.string.Edit_Profile));

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //  case R.id.btn_submit:
            // getDockActivity().replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());
            // break;
        }
    }



}
