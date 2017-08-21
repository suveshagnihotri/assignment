package com.nira.android.viewmodels;

import android.content.Context;

import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.db.RealmController;
import com.nira.android.db.models.UniqueCodeUser;
import com.nira.android.utils.ActivityRouter;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class ProfileViewModel implements ViewModel {
    private  Context context;
    public   String name;
    public  String score;
    public  String companyName;
    public  String status;
    private  UniqueCodeUser uniqueCodeUser;
    public  ProfileViewModel(Context context,String code,String email,String phone){
        this.context=context;
        if(code!=null)
            uniqueCodeUser = RealmController.getUser(code);
        else if(email !=null)
            uniqueCodeUser = RealmController.getUserbaseOnId(email);
        else
            uniqueCodeUser = RealmController.getUserbaseOnId(phone);

        name=uniqueCodeUser.getName();
        companyName= uniqueCodeUser.getCompanyName();
        score= "Score :"+uniqueCodeUser.getScore()+"%";
        if(uniqueCodeUser.getScore()< 10){
            status= "You are blocked because your score is below 10";
        }else{
            status= "You are allowed because your score is above 10";
        }
    }



    public  void openAboutUs(){
        ActivityRouter.navigateToAboutUs(context);
    }
}
