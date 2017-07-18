package com.app.fastcab.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import com.app.fastcab.entities.UserEnt;
import com.app.fastcab.retrofit.GsonFactory;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_Term_STATUS = "isAgree";
    private static final String FILENAME = "preferences";
    protected static final String Firebase_TOKEN = "Firebasetoken";
    protected static final String USERNAME = "userName";
    protected static final String USERID = "userId";
    protected static final String KEY_USER = "key_user";




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
        putBooleanPreference( context, FILENAME, KEY_Term_STATUS, isAgree );
    }

    public boolean isTermAccepted() {
        return getBooleanPreference(context, FILENAME, KEY_Term_STATUS);
    }

    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }


    public void setUsrName(String _token) {
        putStringPreference(context, FILENAME, USERNAME, _token);
    }

    public String getUserName() {
        return getStringPreference(context, FILENAME, USERNAME);
    }

    public void setUsrId(String userId) {
        putStringPreference(context, FILENAME, USERID, userId);
    }

    public String getUserId() {
        return getStringPreference(context, FILENAME, USERID);
    }

    public UserEnt getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), UserEnt.class);
    }

    public void putUser(UserEnt user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

}
