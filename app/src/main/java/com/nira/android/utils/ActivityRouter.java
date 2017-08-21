package com.nira.android.utils;

import android.content.Context;
import android.content.Intent;

import com.nira.android.activity.AboutUsActivity;
import com.nira.android.activity.LoginActivity;
import com.nira.android.activity.LoginWithPhoneActivity;
import com.nira.android.activity.ProfileActivity;
import com.nira.android.activity.SelectCompanyActivity;
import com.nira.android.activity.UniqueCodeSignInActivity;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class ActivityRouter {

    public  static  void navigateToLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public  static  void navigateToLoginPhone(Context context){
        Intent intent = new Intent(context , LoginWithPhoneActivity.class);
        context.startActivity(intent);
    }
    public  static  void navigateToLoginUnique(Context context){
        Intent intent = new Intent(context , UniqueCodeSignInActivity.class);
        context.startActivity(intent);
    }

    public  static  void navigateToProfileActivity(Context context,String code,String email){
        Intent intent = new Intent(context , ProfileActivity.class);
        intent.putExtra(ProfileActivity.CODE,code);
        intent.putExtra(ProfileActivity.EMIAL,email);
        context.startActivity(intent);
    }

    public  static  void navigateToAboutUs(Context context){
        Intent intent = new Intent(context , AboutUsActivity.class);
        context.startActivity(intent);
    }

    public  static  void naviagateToSelectCompany(Context context,String email){
        Intent intent = new Intent(context , SelectCompanyActivity.class);
        intent.putExtra(SelectCompanyActivity.ID,email);
        context.startActivity(intent);
    }
}
