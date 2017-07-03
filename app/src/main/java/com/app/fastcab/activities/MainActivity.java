package com.app.fastcab.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.app.fastcab.R;
import com.app.fastcab.fragments.HomeMapFragment;
import com.app.fastcab.fragments.LoginFragment;
import com.app.fastcab.fragments.SideMenuFragment;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.SideMenuChooser;
import com.app.fastcab.global.SideMenuDirection;
import com.app.fastcab.helpers.ScreenHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.interfaces.OnSettingActivateListener;
import com.app.fastcab.residemenu.ResideMenu;
import com.app.fastcab.ui.views.TitleBar;
import com.facebook.FacebookSdk;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends DockActivity implements OnClickListener {
    public final int LocationResultCode = 1;
    public final int WifiResultCode = 2;
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private MainActivity mContext;
    private boolean loading;
    private ResideMenu resideMenu;
    private OnSettingActivateListener settingActivateListener;
    private float lastTranslate = 0.0f;

    private String sideMenuType;
    private String sideMenuDirection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);
        titleBar = header_main;
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        sideMenuType = SideMenuChooser.RESIDE_MENU.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();
        settingSideMenu(sideMenuType, sideMenuDirection);

        titleBar.setMenuButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    resideMenu.openMenu(sideMenuDirection);
                }

            }
        });

        titleBar.setBackButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {

                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        if (savedInstanceState == null)
            initFragment();

    }

    public void setOnSettingActivateListener(OnSettingActivateListener ActivateListener) {
        this.settingActivateListener = ActivateListener;
    }

    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    public boolean statusCheck() {
        if (isConnected(getApplicationContext())) {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps(R.string.gps_question, Settings.ACTION_LOCATION_SOURCE_SETTINGS, LocationResultCode);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps(int StringResourceID, final String IntentType, final int requestCode) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(super.getDockActivity());
        AlertDialog alert = builder.create();
        final AlertDialog finalAlert = alert;
        builder
                .setMessage(getString(StringResourceID))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.gps_yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finalAlert.dismiss();
                        dialog.cancel();
                        startImpIntent(dialog, IntentType, requestCode);
                        dialog.dismiss();
                        // startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),LocationResultCode);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.gps_no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finalAlert.dismiss();
                        dialog.cancel();
                        finalAlert.dismiss();
                    }
                });
         alert = builder.create();
        alert.show();

    }

    private void startImpIntent(DialogInterface dialog, String IntentType, int requestCode) {
        dialog.dismiss();
        Intent i = new Intent(IntentType);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(i, requestCode);
    }


    /*  private boolean isNetworkAvailable() {
          ConnectivityManager connectivityManager
                  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
          if (activeNetworkInfo == null ){
              WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
              if (!wifi.isWifiEnabled()){
                  buildAlertMessageNoGps(R.string.wifi_question,Settings.ACTION_WIFI_SETTINGS,WifiResultCode);
              return false;
              }
          }else{
              return (activeNetworkInfo.isConnectedOrConnecting());
          }


      }*/

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else {
                //isConnected(getApplicationContext());
                return false;
            }
        } else {
            buildAlertMessageNoGps(R.string.wifi_question, Settings.ACTION_WIFI_SETTINGS, WifiResultCode);
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LocationResultCode) {
            settingActivateListener.onLocationActivateListener();
        }

        if (requestCode == WifiResultCode) {

            settingActivateListener.onNetworkActivateListener();

        }


    }

    private void settingSideMenu(String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {


            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams((int) getResources().getDimension(R.dimen.x300), (int) DrawerLayout.LayoutParams.MATCH_PARENT);


            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);
            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            sideMenuFragment = SideMenuFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();

            drawerLayout.closeDrawers();
        } else {
            resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.60f);


            setMenuItemDirection(direction);

        }
    }

    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    private void setMenuItemDirection(String direction) {

        if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            SideMenuFragment leftSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);
            getResideMenu().getLeftMenu().setVisibility(View.GONE);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            SideMenuFragment rightSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);
            getResideMenu().getRightMenu().setVisibility(View.GONE);
        }

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }


    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        if (prefHelper.isLogin()) {
            replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
        } else {
            replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
        }
    }
    public void refreshFragmentbyTag(String tag){

        BaseFragment currFrag = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        if (currFrag!=null){
            fragTransaction.detach(currFrag);
            fragTransaction.attach(currFrag);
            fragTransaction.commit();
        }

    }
    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }
                }
            }
        };

        return result;
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

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
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onClick(View view) {

    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

}
