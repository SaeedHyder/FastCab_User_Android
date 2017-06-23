package com.app.fastcab.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.NavigationEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.ClickableSpanHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinders.abstracts.NavigationItemBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private ArrayListAdapter<NavigationEnt>adapter;

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NavigationEnt>(getDockActivity(),new NavigationItemBinder(getDockActivity()));

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
        setPlayShortcut(getResources().getString(R.string.drive_with_fastcab),
                getResources().getString(R.string.fastcab),txtDriveoption);


    }
    private void setPlayShortcut(String text,String spanText,AnyTextView txtview) {
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
                UIHelper.showShortToastInCenter(getDockActivity(),"Clicked Event");
            }
        });

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);
    }
    private void BindData() {
        navigationEnts = new ArrayList<>();
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        navigationEnts.add(new NavigationEnt(R.drawable.ic_launcher,"Home"));
        bindview();
        
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
    private void bindview() {
        adapter.clearList();
        sideoptions .setAdapter(adapter);
        adapter.addAll(navigationEnts);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sideoptions);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    
}
