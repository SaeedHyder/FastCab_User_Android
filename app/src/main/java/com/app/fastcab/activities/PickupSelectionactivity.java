package com.app.fastcab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.fastcab.R;
import com.app.fastcab.ui.adapters.AutoCompleteListAdapter;
import com.app.fastcab.ui.viewbinders.abstracts.AutocompleteBinder;
import com.app.fastcab.ui.views.TitleBar;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickupSelectionactivity extends DockActivity implements TextWatcher, View.OnFocusChangeListener {

    @BindView(R.id.iv_pickIcon)
    ImageView ivPickIcon;
    @BindView(R.id.edt_pickup)
    EditText edtPickup;
    @BindView(R.id.input_layout_pickup)
    TextInputLayout inputLayoutPickup;
    @BindView(R.id.ll_pickup)
    LinearLayout llPickup;
    @BindView(R.id.iv_destinationIcon)
    ImageView ivDestinationIcon;
    @BindView(R.id.edt_destination)
    EditText edtDestination;
    @BindView(R.id.input_layout_destination)
    TextInputLayout inputLayoutDestination;
    @BindView(R.id.ll_destination)
    LinearLayout llDestination;
    @BindView(R.id.recent_places)
    ListView recentPlaces;
    @BindView(R.id.header_main)
    TitleBar headerMain;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private AutoCompleteListAdapter madapter;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pickup_location);
        ButterKnife.bind(this);
        setAutocomplete();
        setListeners();
    }

    private void setListeners() {
        edtPickup.clearFocus();
        edtDestination.clearFocus();
        edtDestination.setTag("des");
        edtPickup.setTag("org");
        edtPickup.setOnFocusChangeListener(this);
        edtDestination.setOnFocusChangeListener(this);
        edtPickup.addTextChangedListener(this);
        edtDestination.addTextChangedListener(this);
    }

    private void setAutocomplete() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(Places.GEO_DATA_API)
                //.addApi(AppIndex.API)
                .build();
        madapter = new AutoCompleteListAdapter(this, mGoogleApiClient, null, null, new AutocompleteBinder());
        recentPlaces.setAdapter(madapter);

        //enables filtering for the contents of the given ListView
        recentPlaces.setTextFilterEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

    }

    @Override
    public void onLoadingStarted() {


    }

    @Override
    public void onLoadingFinished() {

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent();  // or // Intent i = getIntent()
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        madapter.getFilter().filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (v.getTag().equals("des"))
                inputLayoutDestination.setBackgroundColor(getResources().getColor(R.color.edit_text_color));
            else
                inputLayoutPickup.setBackgroundColor(getResources().getColor(R.color.edit_text_color));

        } else {
            if (v.getTag().equals("des"))
                inputLayoutDestination.setBackgroundColor(getResources().getColor(R.color.transparent));
            else
                inputLayoutPickup.setBackgroundColor(getResources().getColor(R.color.transparent));

        }

    }
}
