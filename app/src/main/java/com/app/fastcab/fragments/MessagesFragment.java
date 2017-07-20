package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.MessagesEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.entities.UserMessageEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.MessagesBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class MessagesFragment extends BaseFragment implements View.OnClickListener {

    private static String phoneNumber = "0000000000";
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_Messages)
    ListView lvMessages;


    private ArrayListAdapter<MessagesEnt> adapter;
    private ArrayList<MessagesEnt> userCollection;

    public static MessagesFragment newInstance(String number) {
        Bundle args = new Bundle();
        args.putString(phoneNumber, number);
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString(phoneNumber);

        }
        adapter = new ArrayListAdapter<MessagesEnt>(getDockActivity(), new MessagesBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
            getUserMessages();

    }

    private void getUserMessages() {
        loadingStarted();
        Call<ResponseWrapper<ArrayList<UserMessageEnt>>> call = webService.getUserMessages();
        call.enqueue(new Callback<ResponseWrapper<ArrayList<UserMessageEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<UserMessageEnt>>> call, Response<ResponseWrapper<ArrayList<UserMessageEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    setMessages(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getResponse());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<UserMessageEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e(MessagesFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    private void setMessages(ArrayList<UserMessageEnt> result) {

        userCollection = new ArrayList<>();
        for (UserMessageEnt ent : result) {
            userCollection.add(new MessagesEnt(ent.getTitle(), phoneNumber));
        }


      /*  userCollection.add(new MessagesEnt("Please Wait", "03362912002"));
        userCollection.add(new MessagesEnt("Please Wait", "03362912002"));*/


        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMessages.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMessages.setVisibility(View.VISIBLE);
        }

        bindData(userCollection);

    }

    private void bindData(ArrayList<MessagesEnt> userCollection) {
        adapter.clearList();
        lvMessages.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().popFragment();
            }
        });
        titleBar.setSubHeading(getString(R.string.Messages));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //BaseApplication.getBus().post("true");

    }
}
