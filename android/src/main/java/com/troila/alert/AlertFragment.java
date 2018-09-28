package com.troila.alert;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.troila.customealert.CustomDialog;

import javax.annotation.Nullable;

public class AlertFragment extends DialogFragment implements DialogInterface.OnClickListener {
    static final String KEY_TITLE = "title";
    static final String KEY_ICON = "icon";
    static final String KEY_CONTENT = "content";
    static final String KEY_LEFT_BUTTON = "leftButton";
    static final String KEY_LEFT_BUTTON_COLOR = "leftButtonColor";
    static final String KEY_LEFT_BUTTON_SIZE = "leftButtonSize";
    static final String KEY_RIGHT_BUTTON = "rightButton";
    static final String KEY_RIGHT_BUTTON_COLOR = "rightButtonColor";
    static final String KEY_RIGHT_BUTTON_SIZE = "rightButtonSize";

    private final @Nullable
    RNTroilaAlertModule.AlertListener mListener;

    public AlertFragment() {
        mListener = null;
    }

    @SuppressLint("ValidFragment")
    public AlertFragment(@Nullable RNTroilaAlertModule.AlertListener listener, Bundle arguments) {
        mListener = listener;
        setArguments(arguments);
    }

    public static Dialog createDialog(
            Context activityContext, Bundle arguments, DialogInterface.OnClickListener fragment) {
        CustomDialog.Builder customBuilder = new CustomDialog.Builder(activityContext);
        if(arguments.containsKey(KEY_TITLE)){
            customBuilder.setTitle(arguments.getString(KEY_TITLE));
        }
        if(arguments.containsKey(KEY_CONTENT)){
            customBuilder.setMessage(arguments.getString(KEY_CONTENT));
        }
        if(arguments.containsKey(KEY_ICON)){
            customBuilder.setIconType(arguments.getString(KEY_ICON));
        }
        if(arguments.containsKey(KEY_LEFT_BUTTON)){
            customBuilder.setPositiveButton(arguments.getString(KEY_LEFT_BUTTON), fragment);
            if(arguments.containsKey(KEY_LEFT_BUTTON_COLOR)){
                customBuilder.setButtonLeftColor(arguments.getString(KEY_LEFT_BUTTON_COLOR));
            }
            if(arguments.containsKey(KEY_LEFT_BUTTON_SIZE)){
                customBuilder.setButtonLeftSize(arguments.getInt(KEY_LEFT_BUTTON_SIZE));
            }
        }
        if(arguments.containsKey(KEY_RIGHT_BUTTON)){
            customBuilder.setNegativeButton(arguments.getString(KEY_RIGHT_BUTTON), fragment);
            if(arguments.containsKey(KEY_RIGHT_BUTTON_COLOR)){
                customBuilder.setButtonRightColor(arguments.getString(KEY_RIGHT_BUTTON_COLOR));
            }
            if(arguments.containsKey(KEY_RIGHT_BUTTON_SIZE)){
                customBuilder.setButtonRightSize(arguments.getInt(KEY_RIGHT_BUTTON_SIZE));
            }
        }
        return customBuilder.create();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialog(getActivity(), getArguments(), this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mListener != null) {
            mListener.onClick(dialog, which);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) {
            mListener.onDismiss(dialog);
        }
    }
}
