package com.app.fastcab.fragments;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.fastcab.R;
import com.app.fastcab.activities.PickupSelectionactivity;
import com.app.fastcab.entities.CancelReasonEnt;
import com.app.fastcab.entities.CreateRideEnt;
import com.app.fastcab.entities.DriverEnt;
import com.app.fastcab.entities.EstimateFareEnt;
import com.app.fastcab.entities.LocationEnt;
import com.app.fastcab.entities.PromoCodeEnt;
import com.app.fastcab.entities.RideDriverEnt;
import com.app.fastcab.entities.SelectCarEnt;
import com.app.fastcab.entities.UserHomeEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.helpers.BottomSheetDialogHelper;
import com.app.fastcab.helpers.DateHelper;
import com.app.fastcab.helpers.DatePickerHelper;
import com.app.fastcab.helpers.DialogHelper;
import com.app.fastcab.helpers.HomeServiceHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.interfaces.OnSettingActivateListener;
import com.app.fastcab.interfaces.webServiceResponseLisener;
import com.app.fastcab.ui.adapters.ReasonCancelListViewAdapter;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DirectionModule.DirectionFinder;
import DirectionModule.DirectionFinderListener;
import DirectionModule.Route;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.fastcab.R.drawable.abc_item_background_holo_dark;
import static com.app.fastcab.R.drawable.location;
import static com.app.fastcab.global.AppConstants.CURRENT_RATING;
import static com.app.fastcab.global.AppConstants.LAST_RATING;
import static com.app.fastcab.global.AppConstants.RATING_TYPE;
import static com.app.fastcab.global.WebServiceConstants.APPROVE_DRIVER;
import static com.app.fastcab.global.WebServiceConstants.CANCELREASON;
import static com.app.fastcab.global.WebServiceConstants.CANCEL_RIDE;
import static com.app.fastcab.global.WebServiceConstants.CREATE;
import static com.app.fastcab.global.WebServiceConstants.ESTIMATEFARE;
import static com.app.fastcab.global.WebServiceConstants.NEARBY;
import static com.app.fastcab.global.WebServiceConstants.PROMOCODE;
import static com.app.fastcab.global.WebServiceConstants.RIDE_END_TRIP;
import static com.app.fastcab.global.WebServiceConstants.RIDE_LAST_RATING;
import static com.app.fastcab.global.WebServiceConstants.RIDE_LATER;
import static com.app.fastcab.global.WebServiceConstants.RIDE_RATING_current;
import static com.app.fastcab.global.WebServiceConstants.RIDE_RATING_last;
import static com.app.fastcab.global.WebServiceConstants.RIDE_cancel;
import static com.app.fastcab.global.WebServiceConstants.RIDE_done;
import static com.app.fastcab.global.WebServiceConstants.STATUS_RIDELATER;
import static com.app.fastcab.global.WebServiceConstants.STATUS_RIDENOW;

;

/**
 * Created on 6/29/2017.
 */

public class HomeMapFragment extends BaseFragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DirectionFinderListener,
        OnSettingActivateListener,
        webServiceResponseLisener {

    public static String Notfication_RIDEID = "Notfication_RIDEID";
    public static String IS_FROM_NOTIFICATION = "pending_rides_detail";

    protected BroadcastReceiver broadcastReceiver;
    @BindView(R.id.txt_locationtype)
    TextView txtLocationtype;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.txt_pick_text)
    AnyTextView txtPickText;
    @BindView(R.id.txt_destination_text)
    AnyTextView txtDestinationText;
    @BindView(R.id.ll_destination)
    LinearLayout llDestination;
    @BindView(R.id.btn_ridenow)
    Button btnRidenow;
    @BindView(R.id.btn_ridelater)
    Button btnRidelater;
    @BindView(R.id.btn_cancel_ride)
    Button btnCancelRide;
    @BindView(R.id.btn_done_selection)
    Button btndoneselection;
    @BindView(R.id.custom_marker_view)
    RelativeLayout customMarkerView;
    @BindView(R.id.layout_pick)
    RelativeLayout layoutpick;
    @BindView(R.id.layout_destination)
    RelativeLayout layoutdestination;
    @BindView(R.id.container_finding_ride)
    RelativeLayout findingRide;
    @BindView(R.id.txt_Schedule_text)
    AnyTextView txtScheduleText;
    @BindView(R.id.layout_schedule)
    RelativeLayout layoutSchedule;
    @BindView(R.id.btn_location)
    CircleImageView btnLocation;
    @BindView(R.id.Main_frame)
    CoordinatorLayout Main_frame;
    View viewParent;
    @BindView(R.id.ll_source_destination)
    LinearLayout llSourceDestination;

    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    SupportMapFragment map;

    private double latitude;
    private double longitude;
    private Location Mylocation;
    private LocationEnt origin;
    private LocationEnt destination;
    private double distance = 1;

    private Date DateSelected;
    private Date TimeSelected;

    private TitleBar titleBar;
    private boolean mIsTitleBarChanged = false;

    private boolean isCurrentLocationMove;
    private ArrayList<SelectCarEnt> carTypeList;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private LocationListener listener;

    private HomeServiceHelper serviceHelper;

    private PromoCodeEnt promoCodeEnt;
    private SelectCarEnt selectCarEnt;
    private CreateRideEnt rideEnt;
    private DriverEnt driverDetail;

    private Marker pickupMarker;
    private Marker DriverMarker;
    private boolean isRideinSession;
    private RideDriverEnt rideDriverEnt;
    private String rideID;
    private boolean isFromNotification;

    private BottomSheetDialogHelper rideReaching;

    public static HomeMapFragment newInstance() {
        return new HomeMapFragment();
    }

    public static HomeMapFragment newInstance(String type, String rideID, boolean isFromNotification) {
        if (type!=null&&type.equals(AppConstants.PUSH_END_TRIP_TYPE)){
            return new HomeMapFragment();
        }else if (type!=null&&type.equals(AppConstants.PUSH_APPROVE_TYPE)){
            Bundle args = new Bundle();
            args.putString(Notfication_RIDEID, rideID);
            args.putBoolean(IS_FROM_NOTIFICATION, isFromNotification);
            HomeMapFragment fragment = new HomeMapFragment();
            fragment.setArguments(args);
            return fragment;
        }else {
            return new HomeMapFragment();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceHelper = new HomeServiceHelper(this, getDockActivity(), webService);
        if (getArguments() != null) {
            rideID = getArguments().getString(Notfication_RIDEID);
            isFromNotification = getArguments().getBoolean(IS_FROM_NOTIFICATION);
        }
        //BaseApplication.getBus().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_home_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        getMainActivity().setOnSettingActivateListener(this);
       /* originMarker = new MarkerOptions().position(new LatLng(0, 0));
        destinationMarker = new MarkerOptions().position(new LatLng(0, 0));*/
        if (viewParent != null)
            ButterKnife.bind(this, viewParent);
        return viewParent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().refreshSideMenu();


        onNotificationReceived();
        if (map == null)
            initMap();


    }

    private void initMap() {
        map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        map.getMapAsync(this);


        googleApiClient = new GoogleApiClient.Builder(getMainActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)

                .addApi(LocationServices.API)
                .build();
        txtLocationtype.setText(getResources().getString(R.string.set_pickup_location));


    }

    @SuppressWarnings("deprecation")
    @Override
    public void onMapReady(GoogleMap googlemap) {
        googleMap = googlemap;
        if (!prefHelper.getRideInSession()){
            googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    String address = getCurrentAddress(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    if (address != null) {
                        if (!prefHelper.getRideInSession()) {
                            origin = new LocationEnt(address,
                                    cameraPosition.target);
                        }
                    } else {
                        //  origin = new LocationEnt("Un Named Street",cameraPosition.target);
                    }
                    //UIHelper.showShortToastInCenter(getDockActivity(), cameraPosition.target.toString());
                }
            });
        }
        if (isFromNotification){
            serviceHelper.enqueueCall(webService.getApproveDriver(rideID + ""), APPROVE_DRIVER);
        }

        RestoreState();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (origin == null || origin.getLatlng().equals(new LatLng(0, 0)))
            getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId, String title, int ColorID) {

        View customMarkerView = ((LayoutInflater) getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.img_icon);
        TextView textView = (TextView) customMarkerView.findViewById(R.id.txt_pick_text);
        textView.setText(title);
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setBackgroundColor(getResources().getColor(R.color.white));
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String rideID = bundle.getString("rideID");
                        String Type =bundle.getString("pushtype");
                        if (Type!=null&&Type.equals(AppConstants.PUSH_END_TRIP_TYPE)){
                            serviceHelper.enqueueCall(webService.getApproveDriver(rideID + ""), RIDE_END_TRIP);
                        }else if (Type!=null&&Type.equals(AppConstants.PUSH_APPROVE_TYPE)){
                            serviceHelper.enqueueCall(webService.getApproveDriver(rideID + ""), APPROVE_DRIVER);
                        }
                        else if (Type!=null&& Type.equals(AppConstants.PUSH_START_TRIP)){
                            if (rideReaching!=null){
                                Button cancelButton =rideReaching.getDesiredButton(R.id.btn_cancel_ride);
                                if (cancelButton!=null){
                                    cancelButton.setEnabled(false);
                                }
                            }
                        }else if (Type!=null&&Type.equals(AppConstants.PUSH_NOT_FOUND)){
                           UIHelper.showLongToastInCenter(getDockActivity(),getResources().getString(R.string.driver_not_found_error));
                            prefHelper.setRideInSession(false);
                            prefHelper.removeRideSessionPreferences();
                            getMainActivity().getIntent().replaceExtras(bundle);
                            getDockActivity().popBackStackTillEntry(0);
                            getMainActivity().initFragment();
                        }
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Notification Data is Empty");
                    }
                } else if (intent.getAction().equals(AppConstants.LOCATION_RECIEVED)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String lat = bundle.getString("lat");
                        String lon = bundle.getString("lon");
                        LatLng latLng = new LatLng(Double.parseDouble(lat + ""), Double.parseDouble(lon + ""));
                        if (DriverMarker != null)
                            animateMarker(DriverMarker.getPosition(), latLng, false);


                    } else {
                        //UIHelper.showShortToastInCenter(getDockActivity(), "Notification Data is Empty");
                    }
                }
            }
        };
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        SaveCurrentState();
        super.onPause();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity()
                .getWindow().getDecorView());
    }

    @Override
    public void onResume() {
        super.onResume();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity()
                .getWindow().getDecorView());
        if (origin == null || origin.getLatlng().equals(new LatLng(0, 0))) {
            customMarkerView.setVisibility(View.GONE);
            llDestination.setVisibility(View.GONE);
            getMainActivity().statusCheck();
            //getCurrentLocation();
        }

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.LOCATION_RECIEVED));


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        this.titleBar = titleBar;

        if (mIsTitleBarChanged) {
            adjustTitleBar();
        } else {
            titleBar.hideButtons();
            titleBar.showMenuButton();
            titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleBar.setSubHeading("Home");
        }

    }

    public void adjustTitleBar() {
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.showMessageButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(MessagesFragment.newInstance(driverDetail.getPhoneNo()), MessagesFragment.class.getSimpleName());
            }
        });
        titleBar.showCallButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + driverDetail.getPhoneNo()));
                startActivity(intent);
            }
        });
        titleBar.setSubHeading(getResources().getString(R.string.your_ride));
    }

    private void sendRequest(String origin, String destination) {
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setRoute() {
        if (origin != null && destination != null) {
            LatLng pick = origin.getLatlng();
            LatLng destinat = destination.getLatlng();
            String origin_string = String.valueOf(pick.latitude) + "," + String.valueOf(pick.longitude);
            String destination_string = String.valueOf(destinat.latitude) + "," + String.valueOf(destinat.longitude);
            sendRequest(origin_string, destination_string);
        }
    }

    @Override
    public void onDirectionFinderStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

   /*    *//* googleMap.addMarker(new MarkerOptions().position(origin.getLatlng())
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.set_pickup_location,
                        "14 min", R.color.black))));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.destination_icon);
<<<<<<< HEAD
        movemap(origin.getLatlng());
        googleMap.addMarker(new MarkerOptions().position(destination.getLatlng()).icon(icon));
=======

        googleMap.addMarker(new MarkerOptions().position(destination.getLatlng()).icon(icon));*//*
>>>>>>> f89e593cd6182c6ba33fabb11b9f8726c1c4d18b*/

        for (Route routesingle : route) {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(15);

            pickupMarker = googleMap.addMarker(new MarkerOptions().position(origin.getLatlng())
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.set_pickup_location,
                            routesingle.duration.text, R.color.black))));
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.destination_icon);
            googleMap.addMarker(new MarkerOptions().position(destination.getLatlng()).icon(icon));
            moveRouteMap();
            for (int i = 0; i < routesingle.points.size(); i++)
                polylineOptions.add(routesingle.points.get(i));

            //moveMap(null);
            polylinePaths.add(googleMap.addPolyline(polylineOptions));
            distance = (double) routesingle.distance.value / 1000;

        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route, View view, Object object) {

    }

    @Override
    public void onLocationActivateListener() {
        if (origin == null || origin.getLatlng().equals(new LatLng(0, 0)))
            getCurrentLocation();
    }

    @Override
    public void onNetworkActivateListener() {
        if (origin == null) {
            // getMainActivity().statusCheck();
            //  getCurrentLocation();
        }
    }

    private void moveRouteMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin.getLatlng());
        builder.include(destination.getLatlng());
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        googleMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {

                //   CameraUpdate zout = CameraUpdateFactory.zoomBy(-3.0f);
                //  googleMap.animateCamera(zout);
                CameraUpdate zout = CameraUpdateFactory.zoomBy(-0.5f);
                googleMap.animateCamera(zout);

            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void movemap(LatLng latlng) {


      /*  CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
>>>>>>> f89e593cd6182c6ba33fabb11b9f8726c1c4d18b

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);*/

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latlng.latitude, latlng.longitude))
                .zoom(13)
                .bearing(0)
                .tilt(45)
                .build();
        if (isCurrentLocationMove) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            isCurrentLocationMove = false;
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        /*if (isCurrentLocationMove) {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    customMarkerView.setVisibility(View.VISIBLE);
                    llDestination.setVisibility(View.VISIBLE);
                    isCurrentLocationMove = false;
                }

                @Override
                public void onCancel() {

                }
            });
        }else
        {*/
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // }
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public void animateMarker(final LatLng startPosition, final LatLng toPosition,
                              final boolean hideMarker) {


        /*final Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(startPosition)
                .title(mCarParcelableListCurrentLation.get(position).mCarName)
                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));*/
        if (DriverMarker != null) {

            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();

            final long duration = 3000;
            final Interpolator interpolator = new LinearInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed
                            / duration);
                    double lng = t * toPosition.longitude + (1 - t)
                            * startPosition.longitude;
                    double lat = t * toPosition.latitude + (1 - t)
                            * startPosition.latitude;


                    double dLon = (toPosition.longitude - startPosition.longitude);
                    double y = Math.sin(dLon) * Math.cos(toPosition.latitude);
                    double x = Math.cos(startPosition.latitude) * Math.sin(toPosition.latitude) -
                            Math.sin(startPosition.latitude) * Math.cos(toPosition.latitude) * Math.cos(dLon);
                    double brng = Math.toDegrees((Math.atan2(y, x)));
                    brng = (360 - ((brng + 360) % 360));
                    DriverMarker.setPosition(new LatLng(lat, lng));
                    // carMarker.setRotation((float) brng);

                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    } else {
                        if (hideMarker) {
                            DriverMarker.setVisible(false);
                        } else {
                            DriverMarker.setVisible(true);
                        }
                    }
                }
            });
            movemap(toPosition);
        }
    }

    private void getCurrentLocation() {


        if (googleMap != null && !prefHelper.getRideInSession()) {
            googleMap.clear();
        }
        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Mylocation == null) {
            locationRequest.setInterval(1000);
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location mlocation) {
                if (mlocation != null) {
                    Mylocation = mlocation;
                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, listener);
                    if (Mylocation != null) {
                        //Getting longitude and latitude
                        longitude = Mylocation.getLongitude();
                        latitude = Mylocation.getLatitude();
                        // origin = new LatLng(latitude, longitude);
                        String Address = getCurrentAddress(latitude, longitude);
                        if (!prefHelper.getRideInSession()) {
                            customMarkerView.setVisibility(View.VISIBLE);
                            llDestination.setVisibility(View.VISIBLE);
                        }
                        if (Address != null) {

                            origin = new LocationEnt(Address, new LatLng(latitude, longitude));
                        } else {
                            origin = new LocationEnt("Un Named Street", new LatLng(latitude, longitude));
                        }
                        isCurrentLocationMove = true;
                        movemap(origin.getLatlng());
                        // moveMap(new LatLng(latitude, longitude));
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Can't get your Location Try getting using Location Button");
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, listener);

            //location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        }
    }

    private String getCurrentAddress(double lat, double lng) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getDockActivity(), Locale.ENGLISH);
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String Province = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getCountryName();
                return address + ", " + Province + ", " + country;
            } else {

                return "Address Not Available";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LatLng translateCoordinates(final double distance, final LatLng origpoint, final double angle) {
        final double distanceNorth = Math.sin(angle) * distance;
        final double distanceEast = Math.cos(angle) * distance;

        final double earthRadius = 6371000;

        final double newLat = origpoint.latitude + (distanceNorth / earthRadius) * 180 / Math.PI;
        final double newLon = origpoint.longitude + (distanceEast / (earthRadius * Math.cos(newLat * 180 / Math.PI))) * 180 / Math.PI;

        return new LatLng(newLat, newLon);
    }

    private void StartPickupActivity(int Id) {
        Intent i = new Intent(getActivity(), PickupSelectionactivity.class);
        Bundle args = new Bundle();
        args.putString("origin", new Gson().toJson(origin));
        args.putString("destination", new Gson().toJson(destination));
        i.putExtra("route", args);
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(i, Id);
    }

    @OnClick({R.id.txt_Schedule_text, R.id.txt_pick_text, R.id.btn_done_selection, R.id.txt_destination_text, R.id.btn_location, R.id.ll_destination, R.id.btn_ridenow, R.id.btn_ridelater, R.id.btn_cancel_ride, R.id.txt_locationtype})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_pick_text:
                StartPickupActivity(10);
                break;
            case R.id.txt_destination_text:
                StartPickupActivity(10);
                break;
            case R.id.ll_destination:
                StartPickupActivity(10);
                break;
            case R.id.btn_ridenow:
                movemap(origin.getLatlng());
                serviceHelper.enqueueCall(webService.getLastFeedback(prefHelper.getUserId()), RIDE_LAST_RATING);
                break;
            case R.id.btn_ridelater:
                movemap(origin.getLatlng());
                setupScheduleDialog();
                break;
            case R.id.btn_cancel_ride:
                setcanceldialog();
                break;
            case R.id.txt_locationtype:
                StartPickupActivity(10);
                break;
            case R.id.btn_done_selection:
                initRideStatus();
                break;
            case R.id.btn_location:
                if (getMainActivity().statusCheck())
                    getCurrentLocation();
                break;
            case R.id.txt_Schedule_text:
                setupScheduleDialog();
                break;
        }
    }

    private void setupRatingDialog(final RideDriverEnt result,int RatingType) {
        if (rideReaching!=null){
            rideReaching.hideDialog();
        }
        btnRidenow.setVisibility(View.GONE);
        btnRidelater.setVisibility(View.GONE);
        llSourceDestination.setVisibility(View.GONE);
        customMarkerView.setVisibility(View.GONE);
        showRatingBottomSheet(result, RatingType);


    }

    private void showRatingBottomSheet(final RideDriverEnt result, final int RatingType) {
        final BottomSheetDialogHelper ratingDialog = new BottomSheetDialogHelper(getDockActivity(), Main_frame, R.layout.bottom_submit_rating);
        ratingDialog.initRatingDialog(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Rating = ratingDialog.getRatingScore();
                Rating = Rating+0;
                if (RatingType == LAST_RATING) {
                    ratingDialog.hideDialog();
                    serviceHelper.enqueueCall(webService.submitRideFeedback(prefHelper.getUserId(),
                            result.getDriverDetail().getId() + "",
                            result.getRideDetail().getId() + "",
                            Rating + ""
                            , RATING_TYPE), RIDE_RATING_last);
                } else if (RatingType == CURRENT_RATING) {
                    ratingDialog.hideDialog();
                    serviceHelper.enqueueCall(webService.submitRideFeedback(prefHelper.getUserId(),
                            result.getDriverDetail().getId() + "",
                            result.getRideDetail().getId() + "",
                            Rating + ""
                            , RATING_TYPE), RIDE_RATING_current);
                   }

            }
        }, result);
        ratingDialog.showDialog();
        titleBar.hideButtons();
        if (RatingType == LAST_RATING) {
            titleBar.setSubHeading(getResources().getString(R.string.submit_rating_last));
        } else if (RatingType == CURRENT_RATING) {
            getDockActivity().StopDriverLocationService();
            titleBar.setSubHeading(getResources().getString(R.string.rate_title));
        }
    }

    private void setupRideNowDialog() {
        btnRidenow.setVisibility(View.GONE);
        btnRidelater.setVisibility(View.GONE);
        llSourceDestination.setVisibility(View.GONE);
        carTypeList = prefHelper.getCarTypes();
       /* carTypeList = new ArrayList<>();

        carTypeList.add(new SelectCarEnt("drawable://" + R.drawable.economy, R.drawable.circle_unactive, "Economy", R.color.button_color, R.color.gray_dark, "drawable://" + R.drawable.economy_active, R.drawable.circle_blue));
        carTypeList.add(new SelectCarEnt("drawable://" + R.drawable.business_unactive, R.drawable.circle_unactive, "Business", R.color.button_color, R.color.gray_dark, "drawable://" + R.drawable.business_active, R.drawable.circle_blue));
        carTypeList.add(new SelectCarEnt("drawable://" + R.drawable.vip_unactive, R.drawable.circle_unactive, "Vip", R.color.button_color, R.color.gray_dark, "drawable://" + R.drawable.vip_active, R.drawable.circle_blue));
*/
        final BottomSheetDialogHelper dialogHelper = new BottomSheetDialogHelper(getDockActivity(), Main_frame, R.layout.bottomsheet_selectride);
        dialogHelper.initSelectRideBottomSheet(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPromoCodeDialog();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCarEnt = dialogHelper.getSelectedType();
                String percentage = "";
                if (promoCodeEnt != null)
                    percentage = promoCodeEnt.getPercentage();
                serviceHelper.enqueueCall(webService.getRideEstimate(String.valueOf(selectCarEnt.getId()),
                        checkForNullOREmpty(percentage), distance + ""), ESTIMATEFARE);
                dialogHelper.hideDialog();

            }
        }, carTypeList);
        dialogHelper.showDialog();
        titleBar.hideButtons();
        titleBar.setSubHeading(getResources().getString(R.string.home));
        titleBar.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRidenow.setVisibility(View.VISIBLE);
                btnRidelater.setVisibility(View.VISIBLE);
                titleBar.showMenuButton();
                llSourceDestination.setVisibility(View.VISIBLE);
                dialogHelper.hideDialog();
                StartPickupActivity(10);


            }
        });
    }

    private void setupScheduleDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getDockActivity());
        dialog.setContentView(R.layout.bottom_sheet_scheduled_picker);
        Button cancelbutton = (Button) dialog.findViewById(R.id.SubmitButton);
        final AnyTextView date_pick = (AnyTextView) dialog.findViewById(R.id.txt_datepicker);
        final AnyTextView time_pick = (AnyTextView) dialog.findViewById(R.id.txt_timepicker);
        date_pick.setPaintFlags(Typeface.BOLD);
        time_pick.setPaintFlags(Typeface.BOLD);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateSelected == null) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.select_pickup_date));
                } else if (TimeSelected == null) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.select_time_pickup));
                } else {
                    dialog.dismiss();
                    setupScheduleComfirmDialog(date_pick.getText().toString() + " at " + time_pick.getText().toString());
                }
            }
        });
        // dialog.setCanceledOnTouchOutside(false);
        //dialog.setCancelable(false);

        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker(date_pick);
            }
        });

        time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker(time_pick);
            }
        });
        dialog.show();
    }

    private void setupScheduleComfirmDialog(String s) {
        layoutSchedule.setVisibility(View.VISIBLE);
        txtScheduleText.setText(s);
        btnCancelRide.setVisibility(View.GONE);
        btnRidenow.setVisibility(View.GONE);
        btnRidelater.setVisibility(View.GONE);
        llSourceDestination.setVisibility(View.VISIBLE);


        carTypeList = prefHelper.getCarTypes();

      /*  carTypeList.add(new SelectCarEnt("drawable://" + R.drawable.economy, R.drawable.circle_unactive, "Economy", R.color.button_color, R.color.gray_dark, "drawable://" + R.drawable.economy_active, R.drawable.circle_blue));
        carTypeList.add(new SelectCarEnt("drawable://" + R.drawable.business_unactive, R.drawable.circle_unactive, "Business", R.color.button_color, R.color.gray_dark, "drawable://" + R.drawable.business_active, R.drawable.circle_blue));
        carTypeList.add(new SelectCarEnt("drawable://" + R.drawable.vip_unactive, R.drawable.circle_unactive, "Vip", R.color.button_color, R.color.gray_dark, "drawable://" + R.drawable.vip_active, R.drawable.circle_blue));
*/

        final BottomSheetDialogHelper scheduleDialog = new BottomSheetDialogHelper(getDockActivity(), Main_frame, R.layout.bottomsheet_selectride);
        scheduleDialog.initSelectRideBottomSheet(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPromoCodeDialog();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectCarEnt = scheduleDialog.getSelectedType();
                String percentage = "";
                if (promoCodeEnt != null)
                    percentage = promoCodeEnt.getPercentage();

                String SelectedDate = "", SelectedTime = "";
                if (DateSelected != null && TimeSelected != null) {
                    SelectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(DateSelected.getTime());
                    SelectedTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(TimeSelected.getTime());
                }

                serviceHelper.enqueueCall(webService.createNewRide(prefHelper.getUserId(), String.valueOf(origin.getLatlng().latitude), String.valueOf(origin.getLatlng().longitude),
                        origin.getAddress(), origin.getAddress(), String.valueOf(destination.getLatlng().latitude), String.valueOf(destination.getLatlng().longitude),
                        destination.getAddress(), destination.getAddress(), selectCarEnt.getId() + "", percentage + "", SelectedDate, SelectedTime, RIDE_done, STATUS_RIDELATER, "", distance + ""), RIDE_LATER);

                scheduleDialog.hideDialog();
            }
        }, R.string.schedule_ride, carTypeList);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResources().getString(R.string.schedule_new_trip));
        titleBar.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideScheduleViews();
                DateSelected = null;
                TimeSelected = null;
                scheduleDialog.hideDialog();


            }
        });
        scheduleDialog.showDialog();
    }

    private void setcanceldialog() {
        final DialogHelper canceldialog = new DialogHelper(getDockActivity());
        canceldialog.cancelRide(R.layout.cancel_ride_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceldialog.hideDialog();
                serviceHelper.enqueueCall(webService.getCancelReasons(), CANCELREASON);

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceldialog.hideDialog();
            }
        });
        canceldialog.setCancelable(false);
        canceldialog.showDialog();
    }

    private void ShowrideReachingDialog(RideDriverEnt result) {

        //rideDriverEnt = result;

        if (result != null) {

            googleMap.clear();
            customMarkerView.setVisibility(View.GONE);
            findingRide.setVisibility(View.GONE);
            btnCancelRide.setVisibility(View.GONE);
            llDestination.setVisibility(View.GONE);
            btnLocation.setVisibility(View.GONE);
            btndoneselection.setVisibility(View.GONE);
            layoutdestination.setVisibility(View.GONE);
            layoutpick.setVisibility(View.GONE);
            btnRidenow.setVisibility(View.GONE);
            btnRidelater.setVisibility(View.GONE);
            prefHelper.setDriverId(result.getDriverDetail().getId() + "");
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);
            DriverMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(
                    result.getDriverDetail().getLatitude()),
                    Double.parseDouble(result.getDriverDetail().getLongitude()))).icon(icon));
            getDockActivity().StartDriverLocationService();
            setRoute();

            rideReaching = new BottomSheetDialogHelper(getDockActivity(), Main_frame, R.layout.bottom_dialog_ride_detail);
            rideReaching.initRideDetailBottomSheet(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setcanceldialog();
                }
            }, result,prefHelper.isStarted());

            rideReaching.showDialog();
            mIsTitleBarChanged = true;
            adjustTitleBar();
        }
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final BottomSheetDialogHelper ratingDialog = new BottomSheetDialogHelper(getDockActivity(), Main_frame, R.layout.bottom_submit_rating);
                ratingDialog.initRatingDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rideReaching.hideDialog();
                        ratingDialog.hideDialog();

                    }
                });
                ratingDialog.showDialog();

            }
        }, 10000);*/
    }

    private void initPromoCodeDialog() {
        final DialogHelper promodialog = new DialogHelper(getDockActivity());
        promodialog.promoCode(R.layout.promo_code_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promodialog.hideDialog();

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (promodialog.getEditText(R.id.txt_promoCode).equals("")) {

                } else {
                    promodialog.hideDialog();
                    serviceHelper.enqueueCall(webService.getPromoCode(prefHelper.getUserId(), promodialog.getEditText(R.id.txt_promoCode)), PROMOCODE);
                }

            }
        });
        promodialog.setCancelable(false);
        promodialog.showDialog();
    }

    private void setRequestCancelDialog(ArrayList<CancelReasonEnt> arrayList) {
        final Dialog dialog = new Dialog(getDockActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.request_for_cancellation_dialog);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        ListView listView = (ListView) dialog.findViewById(R.id.lv_listview);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancel);


     /*   for (int i = 1; i <= 8; i++) {
            arrayList.add("I'm getting late");
        }*/


        final ReasonCancelListViewAdapter adapter = new ReasonCancelListViewAdapter(getDockActivity(), arrayList, true);
        listView.setAdapter(adapter);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                serviceHelper.enqueueCall(webService.ChangeRideStatus(prefHelper.getUserId(), String.valueOf(rideEnt.getId()),
                        RIDE_cancel, adapter.getSelectedItem()), CANCEL_RIDE);
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

    private void initEstimateFareBottomSheet(final EstimateFareEnt result) {
        final BottomSheetDialogHelper estimateFareDialog = new BottomSheetDialogHelper(getDockActivity(), Main_frame, R.layout.bottom_dialog_estimate_fare);
        estimateFareDialog.initEstimateFareBottomSheet(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               estimateFareDialog.hideDialog();
                                                               String percentage = "";
                                                               if (promoCodeEnt != null)
                                                                   percentage = promoCodeEnt.getPercentage();

                                                               serviceHelper.enqueueCall(webService.createNewRide(prefHelper.getUserId(), String.valueOf(origin.getLatlng().latitude), String.valueOf(origin.getLatlng().longitude),
                                                                       origin.getAddress(), origin.getAddress(), String.valueOf(destination.getLatlng().latitude), String.valueOf(destination.getLatlng().longitude),
                                                                       destination.getAddress(), destination.getAddress(), selectCarEnt.getId() + "", percentage + "", "", "", RIDE_done, STATUS_RIDENOW, result.getEstimateFare(), distance + ""), CREATE);

                                                           }
                                                       }, selectCarEnt.getType(),
                selectCarEnt.getCapacity() + "",
                selectCarEnt.getVehicleImageTwo() + "", Integer.parseInt(result.getEstimateFare() + ""));
        estimateFareDialog.showDialog();
        titleBar.hideButtons();
        titleBar.setSubHeading(getResources().getString(R.string.home));
        titleBar.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estimateFareDialog.hideDialog();
                setupRideNowDialog();
            }
        });
    }

    private void hideRideSelectionViews() {

        googleMap.clear();
        customMarkerView.setVisibility(View.GONE);
        llDestination.setVisibility(View.GONE);
        btndoneselection.setVisibility(View.GONE);
        layoutdestination.setVisibility(View.GONE);
        layoutpick.setVisibility(View.GONE);
        btnRidenow.setVisibility(View.GONE);
        btnRidelater.setVisibility(View.GONE);

    }

    private void showFindRideViews(ArrayList<DriverEnt> result) {
        titleBar.hideButtons();
        titleBar.setSubHeading(getResources().getString(R.string.home));
        titleBar.showMenuButton();
        final Circle mCircle;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);
        double lat = origin.getLatlng().latitude;
        double lng = origin.getLatlng().longitude;

        for (DriverEnt ent : result
                ) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(ent.getLatitude()), Double.parseDouble(ent.getLongitude())))
                    .icon(icon));
        }

      /*  googleMap.addMarker(new MarkerOptions().position(translateCoordinates(100, new LatLng(lat, lng), 180)).rotation(270.0f).icon(icon));
        googleMap.addMarker(new MarkerOptions().position(translateCoordinates(130, new LatLng(lat, lng), 180)).icon(icon));
        googleMap.addMarker(new MarkerOptions().position(translateCoordinates(160, new LatLng(lat, lng), -180)).rotation(90.0f).icon(icon));
        googleMap.addMarker(new MarkerOptions().position(translateCoordinates(190, new LatLng(lat, lng), -180)).icon(icon));*/
        googleMap.addMarker(new MarkerOptions().position(origin.getLatlng())
                .icon(BitmapDescriptorFactory.fromResource(location)));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin.getLatlng(), 17));
        googleMap.animateCamera(zoom);
        mCircle = googleMap.addCircle(new CircleOptions()
                .center(origin.getLatlng())
                .radius(6000)
                .strokeColor(0xFF1BC868)
                .fillColor(0xFFBAEED1));
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setIntValues(0, 100);
        valueAnimator.setDuration(3000);
        valueAnimator.setEvaluator(new IntEvaluator());
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                mCircle.setRadius(animatedFraction * 100);
            }
        });

        valueAnimator.start();
        findingRide.setVisibility(View.VISIBLE);
        btnCancelRide.setVisibility(View.VISIBLE);


    }

    private void hideScheduleViews() {
        btnRidenow.setVisibility(View.VISIBLE);
        btnRidelater.setVisibility(View.VISIBLE);
        layoutSchedule.setVisibility(View.GONE);
        llSourceDestination.setVisibility(View.VISIBLE);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getResources().getString(R.string.home));
    }

    private void initdestinationLocationSelect() {
        googleMap.clear();
        llSourceDestination.setVisibility(View.VISIBLE);
        titleBar.showMenuButton();
        // googleMap.setMyLocationEnabled(false);
        btnLocation.setVisibility(View.GONE);
        customMarkerView.setVisibility(View.VISIBLE);
        imgIcon.setImageResource(R.drawable.destination_icon);
        txtLocationtype.setVisibility(View.GONE);
        llDestination.setVisibility(View.GONE);
        layoutdestination.setVisibility(View.VISIBLE);
        layoutpick.setVisibility(View.VISIBLE);
        btndoneselection.setVisibility(View.VISIBLE);
        btnRidenow.setVisibility(View.GONE);
        btnRidelater.setVisibility(View.GONE);
        if (destination == null) {
            destination = origin;
        }
        movemap(destination.getLatlng());
        if (destination != null) {
            txtDestinationText.setText(destination.getAddress());
        }
        if (origin != null) {
            txtPickText.setText(origin.getAddress());
        }
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                String address = getCurrentAddress(cameraPosition.target.latitude, cameraPosition.target.longitude);
                if (address != null) {
                    destination = new LocationEnt(address,
                            cameraPosition.target);
                } else {
                    destination = new LocationEnt("Un Named Street",
                            cameraPosition.target);
                }
                txtDestinationText.setText(destination.getAddress());
            }
        });
    }

    private void initRideStatus() {
        btnLocation.setVisibility(View.GONE);
        llSourceDestination.setVisibility(View.VISIBLE);
        titleBar.showMenuButton();
        //googleMap.setMyLocationEnabled(false);
        googleMap.clear();
        customMarkerView.setVisibility(View.GONE);
        llDestination.setVisibility(View.GONE);
        btndoneselection.setVisibility(View.GONE);
        layoutdestination.setVisibility(View.VISIBLE);
        layoutpick.setVisibility(View.VISIBLE);
        btnRidenow.setVisibility(View.VISIBLE);
        btnRidelater.setVisibility(View.VISIBLE);

        if (destination != null) {
            txtDestinationText.setText(destination.getAddress());
        }
        if (origin != null) {
            txtPickText.setText(origin.getAddress());
        }
        googleMap.setOnCameraChangeListener(null);
        setRoute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle b = data.getBundleExtra("route");
            if (b != null) {
                origin = new Gson().fromJson(b.getString("origin"), LocationEnt.class);
                destination = new Gson().fromJson(b.getString("destination"), LocationEnt.class);
                boolean setEmpty = b.getBoolean("backPressed");
                boolean setonMap = b.getBoolean("setonMap");

                    if (setonMap) {
                        initdestinationLocationSelect();
                    } else
                        initRideStatus();

            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) throws ClassCastException {


        switch (Tag) {
            case CANCELREASON:
                setRequestCancelDialog((ArrayList<CancelReasonEnt>) result);
                break;
            case PROMOCODE:
                promoCodeEnt = (PromoCodeEnt) result;
                break;
            case ESTIMATEFARE:
                initEstimateFareBottomSheet((EstimateFareEnt) result);
                break;
            case CREATE:
                rideEnt = (CreateRideEnt) result;
                serviceHelper.enqueueCall(webService.getNearbyDrivers(prefHelper.getUserId(), rideEnt.getId()
                        , String.valueOf(origin.getLatlng().longitude), String.valueOf(origin.getLatlng().latitude)), NEARBY);

                break;
            case NEARBY:
                hideRideSelectionViews();
                showFindRideViews((ArrayList<DriverEnt>) result);
                break;
            case APPROVE_DRIVER:
                if (result!=null) {
                    prefHelper.setRideInSession(true);
                    driverDetail = ((RideDriverEnt) result).getDriverDetail();
                    rideDriverEnt = (RideDriverEnt) result;
                    ShowrideReachingDialog((RideDriverEnt) result);
                }
                break;
            case RIDE_END_TRIP:
                prefHelper.setRideInSession(false);
                prefHelper.removeRideSessionPreferences();
                driverDetail = ((RideDriverEnt) result).getDriverDetail();
                rideDriverEnt = (RideDriverEnt) result;
                setupRatingDialog(rideDriverEnt,CURRENT_RATING);
                break;
            case CANCEL_RIDE:
                prefHelper.setRideInSession(false);
                prefHelper.removeRideSessionPreferences();
                getDockActivity().popBackStackTillEntry(0);
                getMainActivity().initFragment();

                break;
            case RIDE_LATER:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(TripsFragment.newInstance(), TripsFragment.class.getSimpleName());
                break;
            case RIDE_RATING_current:
                prefHelper.setRideInSession(false);
                prefHelper.removeRideSessionPreferences();
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(RideFeedbackFragment.newInstance(), RideFeedbackFragment.class.getSimpleName());

                break;
            case RIDE_RATING_last:
                setupRideNowDialog();
                break;
            case RIDE_LAST_RATING:
                RideDriverEnt ent = (RideDriverEnt) result;

                if (ent == null) {
                    setupRideNowDialog();
                } else if (ent.getMessage()!=null&&ent.getMessage().equals("Please first pay last ride charges")){
                    final DialogHelper lastRidePayment = new DialogHelper(getDockActivity());
                    lastRidePayment.LastRidePayment(R.layout.dialog_last_ride_payment, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lastRidePayment.hideDialog();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lastRidePayment.hideDialog();
                            getDockActivity().replaceDockableFragment(CreditCardDetailFragment.newInstance(),CreditCardDetailFragment.class.getSimpleName());
                        }
                    });
                    lastRidePayment.showDialog();
                }else {
                    setupRatingDialog(ent,LAST_RATING);
                }

                break;


        }
    }

    @Override
    public void ResponseFailure(String tag) {
        loadingFinished();
    }

    private void initTimePicker(final TextView textView) {
        if (DateSelected != null) {
            TimePickerDialog dialog = new TimePickerDialog(getDockActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Date date = new Date();
                    if (DateHelper.isSameDay(DateSelected, date) && !DateHelper.isTimeAfter(date.getHours(), date.getMinutes(), hourOfDay, minute)) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.less_time_error));
                    } else {
                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        c.set(year, month, day, hourOfDay, minute);
                        TimeSelected = c.getTime();
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, hourOfDay, minute + 15);
                        String preTime = new SimpleDateFormat("HH:mm a").format(c.getTime()) + " - " +
                                new SimpleDateFormat("HH:mm a").format(cal.getTime());
                        textView.setText(preTime);
                        textView.setPaintFlags(Typeface.BOLD);
                    }
                }
            }, DateSelected.getHours(), DateSelected.getMinutes(), false);

            dialog.show();
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getResources().getString(R.string.select_pickup_date_first));
        }
    }

    private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
                        Date dateSpecified = c.getTime();
                        if (dateSpecified.before(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.date_before_error));
                        } else {
                            DateSelected = dateSpecified;
                            String predate = new SimpleDateFormat("EEE,MMM d").format(c.getTime());

                            textView.setText(predate);
                            textView.setPaintFlags(Typeface.BOLD);
                        }

                    }
                }, "PreferredDate");

        datePickerHelper.showDate();
    }

    private void SaveCurrentState() {
        if (prefHelper.getRideInSession()) {
            UserHomeEnt home = new UserHomeEnt();
            home.setLatitude(latitude);
            home.setLongitude(longitude);
            home.setMylocation(Mylocation);
            home.setOrigin(origin);
            home.setDestination(destination);
            home.setDistance(distance);
            home.setPromoCodeEnt(promoCodeEnt);
            home.setSelectCarEnt(selectCarEnt);
            home.setRideEnt(rideEnt);
            home.setDriverDetail(driverDetail);
            home.setRideinSession(isRideinSession);
            home.setRideDriverEnt(rideDriverEnt);

            prefHelper.putUserHome(home);
        }

    }

    private void RestoreState() {
        if (prefHelper.getUserHome() != null&&prefHelper.getRideInSession()) {
            latitude = prefHelper.getUserHome().getLatitude();
            longitude = prefHelper.getUserHome().getLongitude();
            Mylocation = prefHelper.getUserHome().getMylocation();
            destination = prefHelper.getUserHome().getDestination();
            distance = prefHelper.getUserHome().getDistance();
            promoCodeEnt = prefHelper.getUserHome().getPromoCodeEnt();
            selectCarEnt = prefHelper.getUserHome().getSelectCarEnt();
            rideEnt = prefHelper.getUserHome().getRideEnt();
            driverDetail = prefHelper.getUserHome().getDriverDetail();
            isRideinSession = prefHelper.getRideInSession();
            rideDriverEnt = prefHelper.getUserHome().getRideDriverEnt();
            origin = prefHelper.getUserHome().getOrigin();
            if (prefHelper.getRideInSession()) {
                ShowrideReachingDialog(rideDriverEnt);

            }
        }
    }



}
