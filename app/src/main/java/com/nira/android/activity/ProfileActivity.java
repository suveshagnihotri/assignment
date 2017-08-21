package com.nira.android.activity;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.R;
import com.nira.android.viewmodels.ProfileViewModel;
import com.suvesh.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class ProfileActivity extends BaseActivity {
    public  static final String  CODE = "code";
    public  static final String  EMIAL = "email";

    @NotNull
    @Override
    protected ViewModel createViewModel() {
        return new ProfileViewModel(ProfileActivity.this,getIntent().getStringExtra(CODE),getIntent().getStringExtra(EMIAL));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }
}
