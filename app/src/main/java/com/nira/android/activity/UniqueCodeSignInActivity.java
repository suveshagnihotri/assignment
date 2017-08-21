package com.nira.android.activity;

import android.os.Bundle;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.R;
import com.nira.android.viewmodels.UniqueCodeViewModel;
import com.suvesh.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class UniqueCodeSignInActivity extends BaseActivity {
    @NotNull
    @Override
    protected ViewModel createViewModel() {
        return new UniqueCodeViewModel(UniqueCodeSignInActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_uniquecode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
