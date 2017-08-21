package com.nira.android.activity;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.R;
import com.nira.android.viewmodels.AboutUsViewModel;
import com.suvesh.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Suvesh on 21/08/2017 AD.
 */

public class AboutUsActivity extends BaseActivity {
    @NotNull
    @Override
    protected ViewModel createViewModel() {
        return new AboutUsViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }
}
