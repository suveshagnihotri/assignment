package com.nira.android;

import android.app.Application;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.manaschaudhari.android_mvvm.adapters.ViewModelBinder;
import com.manaschaudhari.android_mvvm.utils.BindingUtils;
import com.nexmo.sdk.NexmoClient;
import com.nexmo.sdk.core.client.ClientBuilderException;
import com.nexmo.sdk.verify.client.VerifyClient;
import com.nira.android.utils.Config;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class MyApplication extends Application {

    private VerifyClient verifyClient;

    public VerifyClient getVerifyClient() {
        return this.verifyClient;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //-- MVVM --
        BindingUtils.setDefaultBinder(new ViewModelBinder() {

            @Override
            public void bind(ViewDataBinding viewDataBinding, ViewModel viewModel) {
                viewDataBinding.setVariable(BR.vm,viewModel);
            }
        });
        //Realm
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(config);
        acquireVerifyClient();
    }

    public void acquireVerifyClient() {
        if (TextUtils.isEmpty(Config.NexmoAppId) || TextUtils.isEmpty(Config.NexmoSharedSecretKey)) {
            Log.e("Suvesh", "You must supply valid appId and sharedSecretKey, provided by Nexmo");
            return;
        }

        // Acquire the NexmoClient with all the necessary parameters.
        Context context = getApplicationContext();
        NexmoClient nexmoClient = null;
        try {
            nexmoClient = new NexmoClient.NexmoClientBuilder()
                    .context(context)
                    .applicationId(Config.NexmoAppId)
                    .sharedSecretKey(Config.NexmoSharedSecretKey)
                    .build();
        } catch (ClientBuilderException e) {
            e.printStackTrace();
            return;
        }
        this.verifyClient = new VerifyClient(nexmoClient);
    }
}
