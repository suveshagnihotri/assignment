package com.nira.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nexmo.sdk.verify.client.VerifyClient;
import com.nexmo.sdk.verify.event.SearchListener;
import com.nexmo.sdk.verify.event.UserObject;
import com.nexmo.sdk.verify.event.UserStatus;
import com.nexmo.sdk.verify.event.VerifyClientListener;
import com.nira.android.MyApplication;
import com.nira.android.R;
import com.nira.android.utils.ActivityRouter;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class LoginWithPhoneActivity extends AppCompatActivity{

    public static final String TAG = LoginWithPhoneActivity.class.getSimpleName();

    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);
        unbinder = ButterKnife.bind(this);

        final InputMethodManager inputMethodManager = (InputMethodManager)LoginWithPhoneActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

        final EditText phoneNumber_et = (EditText) findViewById(R.id.number_et);
        phoneNumber_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                // If DONE or Enter were pressed, validate the input.
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    inputMethodManager.hideSoftInputFromWindow(phoneNumber_et.getWindowToken(), 0);
                    Editable phoneNumber = phoneNumber_et.getText();
                    if (TextUtils.isEmpty(phoneNumber.toString()) || phoneNumber.toString().length() < 5)
                        phoneNumber_et.setError("Please enter correct");
                    return true;
                }
                return false;
            }
        });

        Button sign_btn = (Button) findViewById(R.id.signin_bv);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(phoneNumber_et.getWindowToken(), 0);
                final String prefix = "IN";
                Editable phoneNumberEdit = phoneNumber_et.getText();

                if(phoneNumberEdit != null) {
                    final String phoneNumber = phoneNumberEdit.toString();
                    if(TextUtils.isEmpty(prefix) || TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 5)
                        phoneNumber_et.setError("Please enter correct");
                    else {
                        initiateGetUserStatus(prefix, phoneNumber);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        final MyApplication application = (MyApplication) LoginWithPhoneActivity.this.getApplication();
        application.getVerifyClient().addVerifyListener(new VerifyClientListener() {
            @Override
            public void onVerifyInProgress(final VerifyClient verifyClient, final UserObject userObject) {
                Log.d(TAG, "onVerifyInProgress");
                ActivityRouter.navigateToVerification(LoginWithPhoneActivity.this);
                // When verification is in progress we can jump to the next screen that allows PIN code input.
            }

            @Override
            public void onUserVerified(final VerifyClient verifyClient, final UserObject userObject) {
                Log.d(TAG, "This User is already verified!");
            }

            @Override
            public void onError(final VerifyClient verifyClient, final com.nexmo.sdk.verify.event.VerifyError errorCode, final UserObject userObject) {
                Log.d(TAG, "onError " + errorCode);
                // If  the verification is already in progress, switch to CheckCodeFragment.

            }

            @Override
            public void onException(final IOException exception) {
                Log.d(TAG, "onException " + exception.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        final MyApplication application = (MyApplication) LoginWithPhoneActivity.this.getApplication();
        application.getVerifyClient().removeVerifyListeners();
    }

    private void initiateGetUserStatus(final String prefix, final String phoneNumber) {
        final MyApplication application = (MyApplication) LoginWithPhoneActivity.this.getApplication();

        application.getVerifyClient().getUserStatus(prefix, phoneNumber, new SearchListener() {
            @Override
            public void onUserStatus(final UserStatus userStatus) {
                Log.d(TAG, "onUserStatus " + userStatus.toString());
                ActivityRouter.naviagateToSelectCompany(LoginWithPhoneActivity.this,null,phoneNumber);
                if(userStatus != UserStatus.USER_VERIFIED) {
                    application.getVerifyClient().getVerifiedUser(prefix, phoneNumber);
                }
            }

            @Override
            public void onError(final com.nexmo.sdk.verify.event.VerifyError errorCode, final String errorMessage) {
                Log.d(TAG, "onSearchError " + errorCode);
            }

            @Override
            public void onException(IOException exception) {
                Log.d(TAG, "onException " + exception.getMessage());
            }
        });
    }
}
