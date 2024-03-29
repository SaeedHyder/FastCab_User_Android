package com.app.fastcab.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.NavigationEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.ClickableSpanHelper;
import com.app.fastcab.helpers.DialogHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinders.abstracts.NavigationItemBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SideMenuFragment extends BaseFragment {

    @BindView(R.id.img_driver)
    CircleImageView imgDriver;
    @BindView(R.id.txt_drivername)
    AnyTextView txtDrivername;
    @BindView(R.id.ll_driver)
    LinearLayout llDriver;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.sideoptions)
    ListView sideoptions;
    @BindView(R.id.line_bottom)
    View lineBottom;
    @BindView(R.id.txt_driveoption)
    AnyTextView txtDriveoption;
    private ArrayList<NavigationEnt> navigationEnts;
    private ArrayListAdapter<NavigationEnt> adapter;

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

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
        adapter = new ArrayListAdapter<NavigationEnt>(getDockActivity(), new NavigationItemBinder(getDockActivity()));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);

        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BindData();
        if (prefHelper.getUser()!=null) {
            Glide.with(this).load(prefHelper.getUser().getProfileImage() + "").into(imgDriver);
            txtDrivername.setText(prefHelper.getUser().getFullName() + "");
        }
        setGooglePlayShortcut(getResources().getString(R.string.drive_with_fastcab),
                getResources().getString(R.string.fastcab), txtDriveoption);


    }

    private void setGooglePlayShortcut(String text, String spanText, AnyTextView txtview) {
        SpannableStringBuilder stringBuilder = ClickableSpanHelper.initSpan(text);
        ClickableSpanHelper.setSpan(stringBuilder, text, spanText, new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));    // you can use custom color
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);    // this remove the underline
            }

            @Override
            public void onClick(View widget) {
               openAppORPlaystore(getResources().getString(R.string.driver_app_package_name));
            }
        });

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);
    }

    private void bindview() {
        adapter.clearList();
        sideoptions.setAdapter(adapter);
        adapter.addAll(navigationEnts);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sideoptions);
        sideoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationEnts.get(position).getTitle().equals(getString(R.string.home))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.notification))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(NotificationFragment.newInstance(), NotificationFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.profile))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.your_trips))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(TripsFragment.newInstance(), TripsFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.payment))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(PaymentMethodFragment.newInstance(), PaymentMethodFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.contact))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), ContactUsFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.about_us))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(AboutUsFragment.newInstance(), AboutUsFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.invite_earn))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(InviteAndEarnFragment.newInstance(), InviteAndEarnFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.arabic_english))) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Will be Implemented in Beta Version");
                   // getMainActivity().getResideMenu().closeMenu();
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.settings))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), SettingFragment.class.getSimpleName());
                } else if (navigationEnts.get(position).getTitle().equals(getString(R.string.logoout))) {

                    final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                    logoutdialog.logout(R.layout.logout_dialog, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMainActivity().getResideMenu().closeMenu();
                            logoutdialog.hideDialog();
                            logoutUser();

                                 }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMainActivity().getResideMenu().closeMenu();
                            logoutdialog.hideDialog();
                        }
                    });
                    logoutdialog.setCancelable(false);
                    logoutdialog.showDialog();
                }
            }
        });
    }

    private void logoutUser() {

        loadingStarted();
        Call<ResponseWrapper> call = webService.LogoutUser(Integer.parseInt(prefHelper.getUserId()));

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getMainActivity().getResideMenu().closeMenu();
                    prefHelper.setLoginStatus(false);
                    prefHelper.setRideInSession(false);
                    prefHelper.removeRideSessionPreferences();
                    getDockActivity().StopDriverLocationService();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());

                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                Log.e(SideMenuFragment.class.getSimpleName(), t.toString());
            }
        });


    }

    private void BindData() {
        navigationEnts = new ArrayList<>();
        navigationEnts.add(new NavigationEnt(R.drawable.home, getResources().getString(R.string.home)));
        navigationEnts.add(new NavigationEnt(R.drawable.notifications, getResources().getString(R.string.notification)));
        navigationEnts.add(new NavigationEnt(R.drawable.profile, getResources().getString(R.string.profile)));
        navigationEnts.add(new NavigationEnt(R.drawable.yourtrips, getResources().getString(R.string.your_trips)));
        navigationEnts.add(new NavigationEnt(R.drawable.payments, getResources().getString(R.string.payment)));
        navigationEnts.add(new NavigationEnt(R.drawable.contact, getResources().getString(R.string.contact)));
        navigationEnts.add(new NavigationEnt(R.drawable.aboutus, getResources().getString(R.string.about_us)));
        navigationEnts.add(new NavigationEnt(R.drawable.invite, getResources().getString(R.string.invite_earn)));
        navigationEnts.add(new NavigationEnt(R.drawable.language, getResources().getString(R.string.arabic_english)));
        navigationEnts.add(new NavigationEnt(R.drawable.setting, getResources().getString(R.string.settings)));
        navigationEnts.add(new NavigationEnt(R.drawable.logout, getResources().getString(R.string.logoout)));
        bindview();

    }
    private void openAppORPlaystore(String packageName){
        Intent i;
        PackageManager manager = getDockActivity().getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage(packageName);
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {

//if not found in device then will come here
            // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


}
