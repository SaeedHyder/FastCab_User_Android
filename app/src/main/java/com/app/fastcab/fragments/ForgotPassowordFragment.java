package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.DialogHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 6/20/2017.
 */

public class ForgotPassowordFragment extends BaseFragment {
    public static ForgotPassowordFragment newInstance() {
        return new ForgotPassowordFragment();
    }
    @BindView(R.id.edtphone)
    AnyEditTextView edtEmail;
    @BindView(R.id.btn_submit)
    Button SubmitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.forgot_password_heading));
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validated()) {
            final DialogHelper dialogHelper =new DialogHelper(getDockActivity());
            dialogHelper.initForgotPasswordDialog(R.layout.dialog_reset_password, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(),LoginFragment.class.getSimpleName());
                    dialogHelper.hideDialog();
                }
            },getResources().getString(R.string.reset),getResources().getString(R.string.reset_password_message));
            dialogHelper.setCancelable(true);
            dialogHelper.showDialog();
        }
    }

    private boolean validated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.valid_email));
            return false;
        }  else {
            return true;
        }
    }
}
