package com.nira.android.viewmodels;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.Toast;

import com.manaschaudhari.android_mvvm.FieldUtils;
import com.manaschaudhari.android_mvvm.ViewModel;
import com.nira.android.db.RealmController;
import com.nira.android.db.models.UniqueCodeUser;
import com.nira.android.utils.ActivityRouter;

import rx.Observable;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class UniqueCodeViewModel implements ViewModel {

    private  Context context;
    public final ObservableField<Boolean> errorDisplay = new ObservableField<>(false);
    public final ObservableField<String> uniqueCode = new ObservableField<>();
    public final ObservableField<String> uniqueCodeError = FieldUtils.toField(Observable.combineLatest(FieldUtils.toObservable(uniqueCode), FieldUtils.toObservable(errorDisplay),
            (field, errorDisplay) -> errorDisplay && (field == null || field.length() == 0) ? "User Phone can not be null" : null));
    public  UniqueCodeViewModel(Context context){
       this.context= context;
        addAllUserWithUniqueCode();
        Log.d("UserList",RealmController.getAllUser().size()+"");
    }

    private   boolean validate(){
        errorDisplay.set(true);
        Boolean valid = true;
        valid = valid && (uniqueCodeError.get()== null);
        return valid;
    }

    private void initLogin(String code){
     UniqueCodeUser user = RealmController.getUser(code);
        if(user!=null){
            Toast.makeText(context,"Sucess",Toast.LENGTH_LONG).show();
            ActivityRouter.navigateToProfileActivity(context,code,null);
        }else{
            Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();
        }
    }

    public  void login(){
        if(validate())
            initLogin(uniqueCode.get().toLowerCase());
    }

    private void addAllUserWithUniqueCode(){
        RealmController.addUserInDB("1","user1","company1","2","niraabc123");
        RealmController.addUserInDB("2","user2","company2","4","niraabc234");
        RealmController.addUserInDB("3","user3","company3","5","niraabc456");
        RealmController.addUserInDB("4","user4","company4","6","niraabc789");
        RealmController.addUserInDB("5","user5","company5","7","nira1234");
        RealmController.addUserInDB("6","user6","company6","8","nira23456");

    }

}
