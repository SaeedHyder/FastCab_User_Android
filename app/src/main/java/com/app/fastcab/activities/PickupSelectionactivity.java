package com.app.fastcab.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.fastcab.R;
import com.app.fastcab.entities.LocationEnt;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.interfaces.OnReceivePlaceListener;
import com.app.fastcab.ui.adapters.AutoCompleteListAdapter;
import com.app.fastcab.ui.viewbinders.abstracts.AutocompleteBinder;
import com.app.fastcab.ui.views.TitleBar;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickupSelectionactivity extends DockActivity implements
        View.OnFocusChangeListener,
        OnReceivePlaceListener,
        AdapterView.OnItemClickListener {

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
    RelativeLayout llDestination;
    @BindView(R.id.recent_places)
    ListView recentPlaces;
    @BindView(R.id.header_main)
    TitleBar headerMain;
    @BindView(R.id.layout_map_location)
    RelativeLayout maplayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private AutoCompleteListAdapter madapter;
    private GoogleApiClient mGoogleApiClient;
    private LocationEnt origin;
    private LocationEnt destination;
    private String currentFocus = "";

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pickup_location);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            Bundle b = getIntent().getBundleExtra("route");
            if (b != null) {
                origin = new Gson().fromJson(b.getString("origin"), LocationEnt.class);
                destination = new Gson().fromJson(b.getString("destination"), LocationEnt.class);
            }
        }
        bindData();
        setAutocomplete();
        setListeners();
        settitlebar();
    }

    private void bindData() {
        if (origin != null && origin.getAddress() != null) {
            edtPickup.setText(origin.getAddress());
        }
        if (destination != null && destination.getAddress() != null) {
            edtDestination.setText(destination.getAddress());
        }

    }

    private void settitlebar() {
        headerMain.hideButtons();
        headerMain.setSubHeading("Home");
        headerMain.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        headerMain.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmptyResult(true);
            }
        });
    }

    private void sendResult() {
        Intent i = new Intent();
        Bundle args = new Bundle();
        args.putString("origin", new Gson().toJson(origin));
        args.putString("destination", new Gson().toJson(destination));
        i.putExtra("route", args);
        setResult(RESULT_OK, i);
        finish();
    }
    private void sendEmptyResult(Boolean setEmpty) {

        finish();
    }

    private void sendResult(Boolean setFromMap) {
        Intent i = new Intent();
        Bundle args = new Bundle();
        args.putString("origin", new Gson().toJson(origin));
        args.putString("destination", new Gson().toJson(destination));
        args.putBoolean("setonMap", setFromMap);
        i.putExtra("route", args);
        setResult(RESULT_OK, i);
        finish();
    }

    private void setListeners() {
        edtPickup.clearFocus();
        // edtDestination.clearFocus();
        edtDestination.setTag("des");
        edtPickup.setTag("org");
        edtPickup.setOnFocusChangeListener(this);
        edtDestination.setOnFocusChangeListener(this);
        if (edtDestination.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        maplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(true);
            }
        });
        edtPickup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentFocus = "origin";
                madapter.getFilter().filter(s.toString());
            }
        });
        edtDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentFocus = "destination";
                madapter.getFilter().filter(s.toString());
            }
        });
        
        recentPlaces.setOnItemClickListener(this);
    }

    private void setAutocomplete() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).addApi(Places.GEO_DATA_API)
                //.addApi(AppIndex.API)
                .build();
        madapter = new AutoCompleteListAdapter(this, mGoogleApiClient, null, null, new AutocompleteBinder(), this);
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

        sendEmptyResult(true);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (v.getTag().equals("des")) {
                if (edtPickup.getText().toString().equals("")) {
                    edtPickup.setText(origin.getAddress());
                }
                maplayout.setVisibility(View.VISIBLE);
                inputLayoutDestination.setBackgroundColor(getResources().getColor(R.color.edit_text_color));
            } else {
                maplayout.setVisibility(View.GONE);
                inputLayoutPickup.setBackgroundColor(getResources().getColor(R.color.edit_text_color));
            }

        } else {
            if (v.getTag().equals("des"))
                inputLayoutDestination.setBackgroundColor(getResources().getColor(R.color.transparent));
            else
                inputLayoutPickup.setBackgroundColor(getResources().getColor(R.color.transparent));

        }

    }

    @Override
    public void OnPlaceReceive() {
        madapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(recentPlaces);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getPlace(position);
    }

    private void getPlace(int position) {
        UIHelper.hideSoftKeyboard(getApplicationContext(), getWindow().getDecorView());
        final AutocompletePrediction item = madapter.getItem(position);
        if (item != null) {
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult =
                    Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(@NonNull PlaceBuffer places) {
                    if (!places.getStatus().isSuccess()) {
                        places.release();
                        return;
                    }
                    if (places.getCount() > 0) {
                        // Got place details
                        final Place place = places.get(0);
                        if (currentFocus.equals("origin")) {
                            origin = new LocationEnt(place.getAddress().toString(), place.getLatLng());
                            if (destination == null) {
                                if (edtDestination.requestFocus()) {
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                }
                                edtPickup.setText(origin.getAddress());
                                // edtDestination.requestFocus();
                            } else {
                                edtPickup.setText(origin.getAddress());
                                sendResult();
                            }
                        } else {
                            destination = new LocationEnt(place.getAddress().toString(), place.getLatLng());
                            if (origin == null) {
                                if (edtPickup.requestFocus()) {
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                                }
                                edtDestination.setText(destination.getAddress());

                            } else
                                edtDestination.setText(destination.getAddress());
                                sendResult();
                        }
                        // Do your stuff
                    } else {
                        // No place details
                        Toast.makeText(getApplicationContext(), "Place details not found.", Toast.LENGTH_LONG).show();
                    }

                    places.release();
                }
            });

        }

    }
}
