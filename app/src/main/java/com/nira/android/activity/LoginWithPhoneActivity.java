package com.nira.android.activity;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.R;
import com.nira.android.viewmodels.LoginWithPhoneViewModel;
import com.suvesh.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class LoginWithPhoneActivity extends BaseActivity{

    @NotNull
    @Override
    protected ViewModel createViewModel() {
        return new LoginWithPhoneViewModel(LoginWithPhoneActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_with_phone;
    }
}
