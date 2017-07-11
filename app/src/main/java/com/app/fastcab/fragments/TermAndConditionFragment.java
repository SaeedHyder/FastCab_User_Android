package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/23/2017.
 */

public class TermAndConditionFragment extends BaseFragment {
    @BindView(R.id.txt_term_condition)
    TextView txtTermCondition;

    @BindView(R.id.chk_read)
    CheckBox chkRead;
    // @BindView(R.id.chk_read1)
    // CheckBox chkRead1;


    public static TermAndConditionFragment newInstance() {
        return new TermAndConditionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_condition, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void setCheckboxPadding(float scale) {
        chkRead.setPadding(chkRead.getPaddingLeft() + (int) (10.0f * scale + 0.5f),
                chkRead.getPaddingTop(),
                chkRead.getPaddingRight(),
                chkRead.getPaddingBottom());
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.terms_conditons));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindTextview();
        final float scale = this.getResources().getDisplayMetrics().density;
        setCheckboxPadding(scale);
        chkRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefHelper.setTermStatus(isChecked);
            }
        });
    }

    private void bindTextview() {
        txtTermCondition.setText(getResources().getString(R.string.lorem_ipsum) + "\n \n \n " + getResources().getString(R.string.lorem_ipsum));
        txtTermCondition.setMovementMethod(new ScrollingMovementMethod());
    }


}
