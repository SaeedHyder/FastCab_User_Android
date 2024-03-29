package com.app.fastcab.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.fastcab.R;
import com.app.fastcab.activities.MainActivity;
import com.app.fastcab.entities.FacebookLoginEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.CameraHelper;
import com.app.fastcab.helpers.DatePickerHelper;
import com.app.fastcab.helpers.FacebookLoginHelper;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.TokenUpdater;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.interfaces.FacebookLoginListener;
import com.app.fastcab.interfaces.ImageSetter;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;



import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/20/2017.
 */

public class SignUpFragment extends BaseFragment implements View.OnClickListener, ImageSetter, FacebookLoginListener {

    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.edtUserName)
    AnyEditTextView edtUserName;
    @BindView(R.id.ll_userName)
    LinearLayout llUserName;
    @BindView(R.id.iv_email)
    ImageView ivEmail;
    @BindView(R.id.edtemail)
    AnyEditTextView edtemail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.iv_ll_currentAddress)
    ImageView ivLlCurrentAddress;
    @BindView(R.id.edtll_currentAddress)
    AnyEditTextView edtllCurrentAddress;
    @BindView(R.id.ll_currentAddress)
    LinearLayout llCurrentAddress;
    @BindView(R.id.iv_MobileNumber)
    ImageView ivMobileNumber;
    @BindView(R.id.edtMobileNumber)
    AnyEditTextView edtMobileNumber;
    @BindView(R.id.ll_MobileNumber)
    LinearLayout llMobileNumber;
    @BindView(R.id.iv_zipCode)
    ImageView ivZipCode;
    @BindView(R.id.edtzipCode)
    AnyEditTextView edtzipCode;
    @BindView(R.id.ll_zipCode)
    LinearLayout llZipCode;
    @BindView(R.id.iv_DateOfBirth)
    ImageView ivDateOfBirth;
    @BindView(R.id.edtDateOfBirth)
    AnyTextView edtDateOfBirth;
    @BindView(R.id.ll_DateOfBirth)
    LinearLayout llDateOfBirth;
    @BindView(R.id.iv_Gender)
    ImageView ivGender;
    @BindView(R.id.sp_Gender)
    Spinner spGender;
    @BindView(R.id.ll_Gender)
    LinearLayout llGender;
    @BindView(R.id.iv_Password)
    ImageView ivPassword;
    @BindView(R.id.edtPassword)
    AnyEditTextView edtPassword;
    @BindView(R.id.ll_Password)
    LinearLayout llPassword;
    @BindView(R.id.iv_ConfirmPassword)
    ImageView ivConfirmPassword;
    @BindView(R.id.edt_ConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.ll_ConfirmPassword)
    LinearLayout llConfirmPassword;
    @BindView(R.id.btn_submuit)
    Button btnSubmuit;
    @BindView(R.id.ll_SignUpFields)
    LinearLayout llSignUpFields;
    @BindView(R.id.sv_signup)
    ScrollView svSignup;
    @BindView(R.id.ll_loginfacebook)
    RelativeLayout llLoginfacebook;
    @BindView(R.id.loginButton_fb)
    LoginButton btnfbLogin;
    Calendar calendar;
    int Year, Month, Day;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.txt_clickHere)
    AnyTextView txtClickHere;
    @BindView(R.id.txt_toAccept)
    AnyTextView txtToAccept;
    @BindView(R.id.txt_TermCond)
    AnyTextView txtTermCond;
    @BindView(R.id.ll_bottomText)
    LinearLayout llBottomText;
    File profilePic;
    String profilePath;
    MainActivity mainActivity;
    private CallbackManager callbackManager;
    private Date DateSelected;
    private List<String> genderList;
    private FacebookLoginEnt facebookLoginEnt;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDatePickerVariables();
        edtMobileNumber.setText(getDockActivity().getCountryCode());
        spGender();
        setListners();
        getMainActivity().setImageSetter(this);
        setupFacebookLogin();
        if(profilePath!=null){
            Glide.with(getDockActivity())
                    .load("file:///" + profilePath)
                    .into(CircularImageSharePop);
        }

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

    private void setListners() {
        llLoginfacebook.setOnClickListener(this);
        edtDateOfBirth.setOnClickListener(this);
        btnSubmuit.setOnClickListener(this);
        txtClickHere.setTypeface(null, Typeface.BOLD);
        txtTermCond.setTypeface(null, Typeface.BOLD);
        txtClickHere.setOnClickListener(this);
        txtTermCond.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
    }

    private boolean isvalidate() {

        if (edtUserName.getText() == null || (edtUserName.getText().toString().isEmpty())) {
            if (edtUserName.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtUserName.setError(getString(R.string.enter_username));
            return false;
        } else if (edtemail.getText() == null || (edtemail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtemail.getText().toString()).matches())) {
            if (edtemail.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtemail.setError(getString(R.string.enter_email));
            return false;
        } else if (edtllCurrentAddress.getText() == null || (edtllCurrentAddress.getText().toString().isEmpty())) {
            if (edtllCurrentAddress.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtllCurrentAddress.setError(getString(R.string.enter_currentAddress));
            return false;
        } else if (edtMobileNumber.getText().toString().isEmpty()) {
            if (edtMobileNumber.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtMobileNumber.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtMobileNumber.getText().toString().length() < 11) {
            if (edtMobileNumber.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtMobileNumber.setError(getString(R.string.numberLength));
            return false;
        } else if (edtzipCode.getText() == null || (edtzipCode.getText().toString().isEmpty())) {
            if (edtzipCode.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtzipCode.setError(getString(R.string.enter_zipCode));
            return false;
        } else if (edtDateOfBirth.getText() == null || (edtDateOfBirth.getText().toString().isEmpty())) {
            edtDateOfBirth.setError(getString(R.string.enter_dob));
            return false;
        } else if (edtPassword.getText() == null || (edtPassword.getText().toString().isEmpty())) {
            if (edtPassword.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            if (edtPassword.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtPassword.setError(getString(R.string.passwordLength));
            return false;
        } else if (edtConfirmPassword.getText() == null || (edtConfirmPassword.getText().toString().isEmpty()) || edtPassword.getText().toString().length() < 6) {
            if (edtConfirmPassword.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtConfirmPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            if (edtConfirmPassword.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtConfirmPassword.setError("confirm password does not match");
            return false;
        } else if (!prefHelper.isTermAccepted()) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Accept Term And Condition First");
            return false;
        } else if (profilePic == null) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select a Profile Picture");
            return false;
        } else if (spGender.getSelectedItemPosition() == 0) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select a Gender");
            return false;
        } else {
            return true;
        }


    }

    private void spGender() {


        genderList = new ArrayList<>();
        genderList.add("Gender");
        genderList.add("Male");
        genderList.add("Female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, genderList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        spGender.setAdapter(dataAdapter);

        spGender.setSelection(0);
    }

    private void setDatePickerVariables() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
                        Date dateSpecified = c.getTime();
                        if (dateSpecified.after(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.date_after_error));
                        } else {
                            DateSelected = dateSpecified;
                            String predate = new SimpleDateFormat("dd MMM yyyy").format(c.getTime());

                            textView.setText(predate);
                            textView.setPaintFlags(Typeface.BOLD);
                        }

                    }
                }, "PreferredDate", 1);

        datePickerHelper.showDate();
    }

    /*void ShowDateDialog(final AnyTextView txtView) {

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date todayDate = null;
                        try {
                            todayDate = sdf.parse(sdf.format(new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        txtView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, Year, Month, Day
        );
        Calendar c = Calendar.getInstance();
        c.set(new Date().getYear(), new Date().getMonth(), new Date().getDate() - 1);

        dpd.setMaxDate(c);
        dpd.show(getFragmentManager(), "Datepickerdialog");


    }*/

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.sign_up));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtDateOfBirth:
                //ShowDateDialog(edtDateOfBirth);
                initDatePicker(edtDateOfBirth);
                break;

            case R.id.btn_submuit:
                if (isvalidate()) {
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        makeUserSignup();
                    }
                }
                break;
            case R.id.txt_clickHere:
                getDockActivity().replaceDockableFragment(TermAndConditionFragment.newInstance(), "TermAndConditionFragment");
                break;

            case R.id.txt_TermCond:
                getDockActivity().replaceDockableFragment(TermAndConditionFragment.newInstance(), "TermAndConditionFragment");
                break;
            case R.id.iv_camera:
                CameraHelper.uploadPhotoDialog(getMainActivity());
                break;
            case R.id.ll_loginfacebook:
                btnfbLogin.performClick();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (DateSelected != null) {
            String predate = new SimpleDateFormat("dd MMM yyyy").format(DateSelected.getTime());
            edtDateOfBirth.setText(predate);
            edtDateOfBirth.setPaintFlags(Typeface.BOLD);
        }
    }

    private void makeUserSignup() {
        loadingStarted();

        MultipartBody.Part filePart;
        if (profilePic != null) {
            filePart = MultipartBody.Part.createFormData("profile_picture",
                    profilePic.getName(), RequestBody.create(MediaType.parse("image/*"), profilePic));
        } else {
            filePart = MultipartBody.Part.createFormData("profile_picture", "",
                    RequestBody.create(MediaType.parse("*/*"), ""));
        }
        Call<ResponseWrapper<UserEnt>> call = webService.registerUser(
                RequestBody.create(MediaType.parse("text/plain"), edtUserName.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtemail.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), WordUtils.uncapitalize(genderList.get(spGender.getSelectedItemPosition()))),
                RequestBody.create(MediaType.parse("text/plain"), edtMobileNumber.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtllCurrentAddress.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtzipCode.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtDateOfBirth.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtPassword.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtConfirmPassword.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), facebookLoginEnt != null ? facebookLoginEnt.getFacebookUID() : ""),
                RequestBody.create(MediaType.parse("text/plain"), facebookLoginEnt != null ? AppConstants.SOCIAL_MEDIA_TYPE : ""),
                filePart
        );

        call.enqueue(new Callback<ResponseWrapper<UserEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserEnt>> call, Response<ResponseWrapper<UserEnt>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    LoginManager.getInstance().logOut();
                    prefHelper.putUser(response.body().getResult());
                    prefHelper.setUsrId(response.body().getResult().getId() + "");
                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getUserId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                    getDockActivity().replaceDockableFragment(VerifyNumFragment.newInstance(), "VerifyNumFragment");
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

    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
            profilePic = new File(imagePath);
            profilePath = imagePath;
            Glide.with(getDockActivity())
                    .load("file:///" + imagePath)
                    .into(CircularImageSharePop);
            //  ImageLoader.getInstance().displayImage(
            //     "file:///" +imagePath, CircularImageSharePop);
        }
    }

    private void SetFaceBookImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
            profilePic = new File(imagePath);
            profilePath = imagePath;
            Glide.with(getDockActivity())
                    .load(imagePath)
                    .into(CircularImageSharePop);
            //  ImageLoader.getInstance().displayImage(
            //     "file:///" +imagePath, CircularImageSharePop);
        }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath, String VideoThumbail) {

    }

    @Override
    public void onSuccessfulFacebookLogin(FacebookLoginEnt loginEnt) {
        facebookLoginEnt = loginEnt;
        edtUserName.setText(checkForNullOREmpty(loginEnt.getFacebookFullName()));
        edtDateOfBirth.setText(checkForNullOREmpty(loginEnt.getFacebookBirthday()));
        edtemail.setText(checkForNullOREmpty(loginEnt.getFacebookEmail()));
        // SetFaceBookImage(loginEnt.getFacebookUProfilePicture());
        if (genderList.contains(loginEnt.getFacebookGender()))
            spGender.setSelection(genderList.indexOf(loginEnt.getFacebookGender()));

    }
}
