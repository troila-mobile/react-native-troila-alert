
package com.troila.alert;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.troila.customealert.CustomDialog;
import com.troila.customealert.CustomToast;
import com.troila.customealert.ProgressDialog;

public class RNTroilaAlertModule extends ReactContextBaseJavaModule {

    private static final String KEY_TITLE = "title";
    private static final String KEY_ICON = "icon";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_LEFT_BUTTON = "leftButton";
    private static final String KEY_LEFT_BUTTON_COLOR = "leftButtonColor";
    private static final String KEY_LEFT_BUTTON_SIZE = "leftButtonSize";
    private static final String KEY_RIGHT_BUTTON = "rightButton";
    private static final String KEY_RIGHT_BUTTON_COLOR = "rightButtonColor";
    private static final String KEY_RIGHT_BUTTON_SIZE = "rightButtonSize";
    private static final String KEY_CANCELABLE = "cancelable";
    private static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    private static final String ACTION_DISMISSED = "dismissed";

    ProgressDialog progressDialog;
    CustomDialog customDialog;

    private final ReactApplicationContext reactContext;

    public RNTroilaAlertModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void showAlert(ReadableMap options, Callback errorCallback, Callback actionCallback) {
        if(reactContext.getCurrentActivity()==null){
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        AlertListener alertListener = actionCallback!=null?new AlertListener(actionCallback):null;
        CustomDialog.Builder customBuilder = new CustomDialog.Builder(reactContext.getCurrentActivity());
        if(options.hasKey(KEY_TITLE)){
            customBuilder.setTitle(options.getString(KEY_TITLE));
        }
        if(options.hasKey(KEY_CONTENT)){
            customBuilder.setMessage(options.getString(KEY_CONTENT));
        }
        if(options.hasKey(KEY_ICON)){
            customBuilder.setIconType(options.getString(KEY_ICON));
        }
        if(options.hasKey(KEY_LEFT_BUTTON)){
            customBuilder.setPositiveButton(options.getString(KEY_LEFT_BUTTON), alertListener);
            if(options.hasKey(KEY_LEFT_BUTTON_COLOR)){
                customBuilder.setButtonLeftColor(options.getString(KEY_LEFT_BUTTON_COLOR));
            }
            if(options.hasKey(KEY_LEFT_BUTTON_SIZE)){
                customBuilder.setButtonLeftSize(options.getInt(KEY_LEFT_BUTTON_SIZE));
            }
        }
        if(options.hasKey(KEY_RIGHT_BUTTON)){
            customBuilder.setNegativeButton(options.getString(KEY_RIGHT_BUTTON), alertListener);
            if(options.hasKey(KEY_RIGHT_BUTTON_COLOR)){
                customBuilder.setButtonRightColor(options.getString(KEY_RIGHT_BUTTON_COLOR));
            }
            if(options.hasKey(KEY_RIGHT_BUTTON_SIZE)){
                customBuilder.setButtonRightSize(options.getInt(KEY_RIGHT_BUTTON_SIZE));
            }
        }
        if (options.hasKey(KEY_CANCELABLE)) {
            customBuilder.setCancelOutSide(options.getBoolean(KEY_CANCELABLE));
        }
        customDialog=customBuilder.create();
        customDialog.show();
    }

    class AlertListener implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener{

        private final Callback mCallback;
        private boolean mCallbackConsumed = false;

        public AlertListener(Callback callback){
            mCallback=callback;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (!mCallbackConsumed) {
                if (getReactApplicationContext().hasActiveCatalystInstance()) {
                    mCallback.invoke(ACTION_BUTTON_CLICKED, which);
                    customDialog.dismiss();
                    mCallbackConsumed = true;
                }
            }
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if (!mCallbackConsumed) {
                if (getReactApplicationContext().hasActiveCatalystInstance()) {
                    mCallback.invoke(ACTION_DISMISSED);
                    mCallbackConsumed = true;
                }
            }
        }
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
        if(progressIsShowing()) progressDialog.dismiss();
    }

    private boolean progressIsShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    @Override
    public String getName() {
        return "RNTroilaAlert";
    }
}