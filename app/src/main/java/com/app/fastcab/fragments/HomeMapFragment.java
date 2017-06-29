package com.app.fastcab.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fastcab.R;
import com.app.fastcab.activities.PickupSelectionactivity;
import com.app.fastcab.entities.LocationEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.interfaces.OnSettingActivateListener;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 6/29/2017.
 */

public class HomeMapFragment extends BaseFragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        DirectionFinderListener,
        OnSettingActivateListener {
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
    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    double latitude;
    double longitude;
    SupportMapFragment map;
    @BindView(R.id.custom_marker_view)
    RelativeLayout customMarkerView;
    @BindView(R.id.layout_pick)
    RelativeLayout layoutpick;
    @BindView(R.id.layout_destination)
    RelativeLayout layoutdestination;

    private LocationEnt origin;
    private LocationEnt destination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    public static HomeMapFragment newInstance() {
        return new HomeMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_map, container, false);
        getMainActivity().setOnSettingActivateListener(this);
       /* originMarker = new MarkerOptions().position(new LatLng(0, 0));
        destinationMarker = new MarkerOptions().position(new LatLng(0, 0));*/
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading("Home");

    }

    @Override
    public void onLocationActivateListener() {
        if (origin==null)
        getCurrentLocation();
    }

    @Override
    public void onNetworkActivateListener() {
        if (origin==null)
        getCurrentLocation();
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
        googleMap.addMarker(new MarkerOptions().position(origin.getLatlng())
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.set_pickup_location,
                        "14 min", R.color.black))));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.destination_icon);

        googleMap.addMarker(new MarkerOptions().position(destination.getLatlng()).icon(icon));

        for (Route routesingle : route) {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(15);

            for (int i = 0; i < routesingle.points.size(); i++)
                polylineOptions.add(routesingle.points.get(i));
            //moveMap(null);
            polylinePaths.add(googleMap.addPolyline(polylineOptions));

        }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (origin == null)
            getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
        googleMap = googlemap;
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapLongClickListener(this);
        googlemap.setOnMarkerClickListener(this);
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                String address = getCurrentAddress(cameraPosition.target.latitude, cameraPosition.target.longitude);
                if (address != null) {
                    origin = new LocationEnt(address,
                            cameraPosition.target);
                } else {
                    origin = new LocationEnt("Un Named Street",
                            cameraPosition.target);
                }
                //UIHelper.showShortToastInCenter(getDockActivity(), cameraPosition.target.toString());
            }
        });
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

    private String getCurrentAddress(double lat, double lng) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getMainActivity(), Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String country = addresses.get(0).getCountryName();
                return address + ", " + country;
            } else {

                return "Address Not Available";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @OnClick({R.id.txt_pick_text, R.id.btn_done_selection, R.id.txt_destination_text, R.id.ll_destination, R.id.btn_ridenow, R.id.btn_ridelater, R.id.btn_cancel_ride, R.id.txt_locationtype})
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
                break;
            case R.id.btn_ridelater:
                break;
            case R.id.btn_cancel_ride:
                break;
            case R.id.txt_locationtype:
                StartPickupActivity(10);
                break;
            case R.id.btn_done_selection:
                initRideStatus();
                break;
        }
    }

    private void getCurrentLocation() {
        if (googleMap != null)
            googleMap.clear();
        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            // origin = new LatLng(latitude, longitude);
            String Address = getCurrentAddress(latitude, longitude);

            if (Address != null) {

                origin = new LocationEnt(Address, new LatLng(latitude, longitude));
            } else {
                origin = new LocationEnt("Un Named Street", new LatLng(latitude, longitude));
            }

            movemap(origin.getLatlng());
            // moveMap(new LatLng(latitude, longitude));
        }

    }

    private void movemap(LatLng latlng) {
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(latlng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
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

    private void StartPickupActivity(int Id) {
        Intent i = new Intent(getActivity(), PickupSelectionactivity.class);
        Bundle args = new Bundle();
        args.putString("origin", new Gson().toJson(origin));
        args.putString("destination", new Gson().toJson(destination));
        i.putExtra("route", args);
        startActivityForResult(i, Id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle b = data.getBundleExtra("route");
            if (b != null) {
                origin = new Gson().fromJson(b.getString("origin"), LocationEnt.class);
                destination = new Gson().fromJson(b.getString("destination"), LocationEnt.class);
                boolean setonMap = b.getBoolean("setonMap");
                if (setonMap) {
                    initdestinationLocationSelect();
                } else
                    initRideStatus();
            }
        }
    }

    private void initdestinationLocationSelect() {
        googleMap.clear();
        customMarkerView.setVisibility(View.VISIBLE);
        imgIcon.setImageResource(R.drawable.destination_icon);
        txtLocationtype.setVisibility(View.GONE);
        llDestination.setVisibility(View.GONE);
        layoutdestination.setVisibility(View.VISIBLE);
        layoutpick.setVisibility(View.VISIBLE);
        btndoneselection.setVisibility(View.VISIBLE);
        btnRidenow.setVisibility(View.GONE);
        btnRidelater.setVisibility(View.GONE);
        if (destination == null){
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
    public void onResume() {
        super.onResume();
        if (origin == null) {
            getCurrentLocation();
            getMainActivity().statusCheck();

        }

    }


}
