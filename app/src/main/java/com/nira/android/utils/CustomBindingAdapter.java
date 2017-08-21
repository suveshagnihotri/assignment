package com.nira.android.utils;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Suvesh on 21/08/2017 AD.
 */

public class CustomBindingAdapter {
    @BindingAdapter(value = {"app:errorText"})
    public static void setErrorText(TextView view, String errMsg) {
        if (errMsg != null) {
            view.setText(errMsg);
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter(value = {"app:visible"})
    public static void setVisible(View view, Boolean visible) {
        if(visible != null && visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
