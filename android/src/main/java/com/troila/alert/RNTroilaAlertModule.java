
package com.troila.alert;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.troila.customealert.CustomAlert;

public class RNTroilaAlertModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNTroilaAlertModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void showToast() {
        CustomAlert customAlert=new CustomAlert(reactContext.getCurrentActivity());
        customAlert.showToast("测试");
    }

    @Override
    public String getName() {
        return "RNTroilaAlert";
    }
}