package com.nira.android.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.utils.ActivityRouter;
import com.nira.android.utils.Utils;

import rx.Observable;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class LoginViewModel implements ViewModel {

    private  Context context;
    public final ObservableField<Boolean> errorDisplay = new ObservableField<>(false);
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> passward = new ObservableField<>();
    public final ObservableField<String> emailError = FieldUtils.toField(Observable.combineLatest(FieldUtils.toObservable(email), FieldUtils.toObservable(errorDisplay),
            (field, errorDisplay) -> errorDisplay && (field == null || field.length() == 0) || !Utils.isValidEmail(field) ? "Please enter your email" : null));
    public final ObservableField<String> passwardError = FieldUtils.toField(Observable.combineLatest(FieldUtils.toObservable(passward), FieldUtils.toObservable(errorDisplay),
            (field, errorDisplay) -> errorDisplay && (field == null || field.length() == 0) ? "Please enter your passward" : null));
    public  LoginViewModel(Context context){
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }
    private FirebaseAuth mAuth;

    private   boolean validate(){
        errorDisplay.set(true);
        Boolean valid = true;
        valid = valid && (emailError.get()== null);
        valid = valid && (passwardError.get()== null);
        return valid;
    }

    private  void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)context, task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        ActivityRouter.naviagateToSelectCompany(context,email,null);
                    }else
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();

                });
    }

    public  void initLogin(){
        if(validate())
            login(email.get().toString(),passward.get().toString());
    }

    public  void navigateToLoginPhone(){
        ActivityRouter.navigateToLoginPhone(context);
    }

    public  void naviagateToUniqueCode(){
        ActivityRouter.navigateToLoginUnique(context);
    }



}
