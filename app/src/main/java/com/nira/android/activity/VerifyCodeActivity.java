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
import android.widget.Toast;

import com.nexmo.sdk.verify.client.VerifyClient;
import com.nexmo.sdk.verify.event.UserObject;
import com.nexmo.sdk.verify.event.VerifyClientListener;
import com.nira.android.MyApplication;
import com.nira.android.R;

import java.io.IOException;

/**
 * Created by Suvesh on 21/08/2017 AD.
 */

public class VerifyCodeActivity extends AppCompatActivity {

    public static final String TAG = VerifyCodeActivity.class.getSimpleName();
    private static final int MIN_CODE_SIZE = 6;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        final MyApplication application = (MyApplication) VerifyCodeActivity.this.getApplication();
        final InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        final EditText code_et = (EditText) findViewById(R.id.code_et);
        code_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // If DONE or Enter were pressed, validate the input.
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    inputMethodManager.hideSoftInputFromWindow(code_et.getWindowToken(), 0);
                    Editable codeEdit = code_et.getText();
                    if (TextUtils.isEmpty(codeEdit.toString()) || codeEdit.toString().length() < MIN_CODE_SIZE)
                        code_et.setError("Please enter otp");
                    return true;
                }
                return false;
            }
        });

        Button confirm_btn = (Button) findViewById(R.id.confirm_bv);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(code_et.getWindowToken(), 0);
                Editable codeEdit = code_et.getText();

                if (codeEdit != null) {
                    String pinCode = codeEdit.toString();
                    if (TextUtils.isEmpty(pinCode) || pinCode.length() < MIN_CODE_SIZE)
                        code_et.setError("Error");
                    else
                        application.getVerifyClient().checkPinCode(pinCode);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        final MyApplication application = (MyApplication) VerifyCodeActivity.this.getApplication();
        application.getVerifyClient().addVerifyListener(new VerifyClientListener() {
            @Override
            public void onVerifyInProgress(final VerifyClient verifyClient, final UserObject userObject) {
            }

            @Override
            public void onUserVerified(final VerifyClient verifyClient, final UserObject userObject) {
                Log.d(TAG, "onUserVerified ");
                Toast.makeText(VerifyCodeActivity.this,"Sucess",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(final VerifyClient verifyClient, final com.nexmo.sdk.verify.event.VerifyError errorCode, final UserObject userObject) {
                Log.d(TAG, "onError " + errorCode);
            }

            @Override
            public void onException(final IOException exception) {
                Log.d(TAG, "onException " + exception.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication application = (MyApplication) VerifyCodeActivity.this.getApplication();
        if (application.getVerifyClient() != null)
            application.getVerifyClient().removeVerifyListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        final MyApplication application = (MyApplication) VerifyCodeActivity.this.getApplication();
        application.getVerifyClient().removeVerifyListeners();
    }
}
