package com.app.fastcab.helpers;

import android.util.Log;

import com.app.fastcab.activities.DockActivity;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.interfaces.webServiceResponseLisener;
import com.app.fastcab.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.message;

/**
 * Created on 7/17/2017.
 */

public class HomeServiceHelper<T> {
    private T result;
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;
    private WebService webService;
    private Call<T> call;

    public HomeServiceHelper(T response, webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService) {
        this.result = response;
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
    }
    public HomeServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
    }
    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                        result = response.body().getResult();

                        serviceResponseLisener.ResponseSuccess(result, tag);
                    } else {
                        UIHelper.showShortToastInCenter(context, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    Log.e(HomeServiceHelper.class.getSimpleName()+" by tag: " + tag, t.toString());
                }
            });
        }
    }

    public T getResult() {
        return result;
    }

}
