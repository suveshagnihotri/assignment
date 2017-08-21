package com.nira.android.viewmodels;

import android.content.Context;
import android.databinding.ObservableField;

import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;

import rx.Observable;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class LoginWithPhoneViewModel implements ViewModel {

    private Context context;
    public final ObservableField<Boolean> errorDisplay = new ObservableField<>(false);
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> phoneError = FieldUtils.toField(Observable.combineLatest(FieldUtils.toObservable(phone), FieldUtils.toObservable(errorDisplay),
            (field, errorDisplay) -> errorDisplay && (field == null || field.length() == 0) ? "User Phone can not be null" : null));


    public LoginWithPhoneViewModel(Context context) {
        this.context = context;
    }

    private boolean validate() {
        errorDisplay.set(true);
        Boolean valid = true;
        valid = valid && (phoneError.get() == null);
        return valid;
    }

    public void login(){
        if(validate()){

        }
    }

}
