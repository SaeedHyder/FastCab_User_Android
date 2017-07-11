package com.app.fastcab.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_Term_STATUS = "isAgree";
    private static final String FILENAME = "preferences";


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public void setTermStatus( boolean isAgree ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isAgree );
    }

    public boolean isTermAccepted() {
        return getBooleanPreference(context, FILENAME, KEY_Term_STATUS);
    }
}
