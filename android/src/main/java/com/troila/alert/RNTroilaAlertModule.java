
package com.troila.alert;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.troila.customealert.CustomDialog;
import com.troila.customealert.CustomToast;
import com.troila.customealert.ProgressDialog;
import com.troila.customealert.custoast.ToastUtils;

import javax.annotation.Nullable;

public class RNTroilaAlertModule extends ReactContextBaseJavaModule {

    private static final String FRAGMENT_TAG =
            "com.troila.alert.RNTroilaAlertModule";

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
        this.reactContext.addLifecycleEventListener(new LifecycleEventListener() {
            @Override
            public void onHostResume() {

            }

            @Override
            public void onHostPause() {

            }

            @Override
            public void onHostDestroy() {
                ToastUtils.reset();
            }
        });
    }

    @ReactMethod
    public void showAlert(ReadableMap options, Callback errorCallback, final Callback actionCallback) {
        final com.troila.alert.RNTroilaAlertModule.FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
        if (fragmentManagerHelper == null) {
            errorCallback.invoke("Tried to show an alert while not attached to an Activity");
            return;
        }

        final Bundle args = new Bundle();
        if (options.hasKey(KEY_TITLE)) {
            args.putString(AlertFragment.KEY_TITLE, options.getString(KEY_TITLE));
        }
        if (options.hasKey(KEY_CONTENT)) {
            args.putString(AlertFragment.KEY_CONTENT, options.getString(KEY_CONTENT));
        }
        if (options.hasKey(KEY_ICON)) {
            args.putString(AlertFragment.KEY_ICON, options.getString(KEY_ICON));
        }
        if (options.hasKey(KEY_LEFT_BUTTON)) {
            args.putString(AlertFragment.KEY_LEFT_BUTTON, options.getString(KEY_LEFT_BUTTON));
            if (options.hasKey(KEY_LEFT_BUTTON_COLOR)) {
                args.putString(AlertFragment.KEY_LEFT_BUTTON_COLOR,options.getString(KEY_LEFT_BUTTON_COLOR));
            }
            if (options.hasKey(KEY_LEFT_BUTTON_SIZE)) {
                args.putInt(AlertFragment.KEY_LEFT_BUTTON_SIZE,options.getInt(KEY_LEFT_BUTTON_SIZE));
            }
        }
        if (options.hasKey(KEY_RIGHT_BUTTON)) {
            args.putString(AlertFragment.KEY_RIGHT_BUTTON, options.getString(KEY_RIGHT_BUTTON));
            if (options.hasKey(KEY_RIGHT_BUTTON_COLOR)) {
                args.putString(AlertFragment.KEY_RIGHT_BUTTON_COLOR,options.getString(KEY_RIGHT_BUTTON_COLOR));
            }
            if (options.hasKey(KEY_RIGHT_BUTTON_SIZE)) {
                args.putInt(AlertFragment.KEY_RIGHT_BUTTON_SIZE,options.getInt(KEY_RIGHT_BUTTON_SIZE));
            }
        }
        if (options.hasKey(KEY_CANCELABLE)) {
            args.putBoolean(KEY_CANCELABLE, options.getBoolean(KEY_CANCELABLE));
        }

        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragmentManagerHelper.showNewAlert(args, actionCallback);
            }
        });
    }

    class AlertListener implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

        private final Callback mCallback;
        private boolean mCallbackConsumed = false;

        public AlertListener(Callback callback) {
            mCallback = callback;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (!mCallbackConsumed) {
                if (getReactApplicationContext().hasActiveCatalystInstance()) {
                    mCallback.invoke(ACTION_BUTTON_CLICKED, which);
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
        if (reactContext.getCurrentActivity() == null) {
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        String title = "";
        String icon = "none";
        if (options.hasKey(KEY_TITLE)) {
            title = options.getString(KEY_TITLE);
        }
        if (options.hasKey(KEY_ICON)) {
            icon = options.getString(KEY_ICON);
        }
        final String finalIcon = icon;
        final String finalTitle = title;
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomToast.showToast(reactContext.getCurrentActivity(), finalIcon, finalTitle);
            }
        });
    }

    @ReactMethod
    public void showLoading(String title, Callback errorCallback) {
        if (reactContext.getCurrentActivity() == null) {
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        if (progressIsShowing()) return;
        ProgressDialog.Builder progressBuilder = new ProgressDialog.Builder(reactContext.getCurrentActivity());
        if (!TextUtils.isEmpty(title)) progressBuilder.setTitle(title);
        progressDialog = progressBuilder.create();
        progressDialog.show();
    }

    @ReactMethod
    public void hideLoading(Callback errorCallback) {
        if (reactContext.getCurrentActivity() == null) {
            errorCallback.invoke("Tried to show a toast while not attached to an Activity");
            return;
        }
        try {
            if (progressIsShowing()) {
                progressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            progressDialog = null;
        }
    }

    private boolean progressIsShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    @Override
    public String getName() {
        return "RNTroilaAlert";
    }

    private class FragmentManagerHelper {
        private final @Nullable FragmentManager mFragmentManager;
        public FragmentManagerHelper(FragmentManager fragmentManager) {
            mFragmentManager = fragmentManager;
        }
        public void showNewAlert(Bundle arguments, Callback actionCallback) {
            UiThreadUtil.assertOnUiThread();

            com.troila.alert.RNTroilaAlertModule.AlertListener actionListener =
                    actionCallback != null ? new com.troila.alert.RNTroilaAlertModule.AlertListener(actionCallback) : null;
            AlertFragment alertFragment = new AlertFragment(actionListener, arguments);
            if (arguments.containsKey(KEY_CANCELABLE)) {
                alertFragment.setCancelable(arguments.getBoolean(KEY_CANCELABLE));
            }
            alertFragment.show(mFragmentManager, FRAGMENT_TAG);
        }
    }

    private @Nullable
    com.troila.alert.RNTroilaAlertModule.FragmentManagerHelper getFragmentManagerHelper() {
        Activity activity = getCurrentActivity();
        if (activity == null) {
            return null;
        }
        return new com.troila.alert.RNTroilaAlertModule.FragmentManagerHelper(activity.getFragmentManager());
    }
}