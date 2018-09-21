
package com.troila.alert;

import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.troila.customealert.CustomToast;
import com.troila.customealert.ProgressDialog;

public class RNTroilaAlertModule extends ReactContextBaseJavaModule {

    private static final String KEY_TITLE = "title";
    private static final String KEY_ICON = "icon";

    ProgressDialog progressDialog;

    private final ReactApplicationContext reactContext;

    public RNTroilaAlertModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void showToast(ReadableMap options, Callback errorCallback) {
        if(reactContext.getCurrentActivity()==null){
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        String title = "";
        String icon = "none";
        if(options.hasKey(KEY_TITLE)){
            title=options.getString(KEY_TITLE);
        }
        if(options.hasKey(KEY_ICON)){
            icon=options.getString(KEY_ICON);
        }
        CustomToast.showToast(reactContext.getCurrentActivity(),icon,title);
    }

    @ReactMethod
    public void showLoading(String title, Callback errorCallback) {
        if(reactContext.getCurrentActivity()==null){
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        if(progressIsShowing()) return;
        ProgressDialog.Builder progressBuilder = new ProgressDialog.Builder(reactContext.getCurrentActivity());
        if(!TextUtils.isEmpty(title)) progressBuilder.setTitle(title);
        progressDialog=progressBuilder.create();
        progressDialog.show();
    }

    @ReactMethod
    public void hideLoading(Callback errorCallback) {
        if(reactContext.getCurrentActivity()==null){
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        if(progressIsShowing()) progressDialog.hide();
    }

    private boolean progressIsShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    @Override
    public String getName() {
        return "RNTroilaAlert";
    }
}