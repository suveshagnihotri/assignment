package com.nira.android.db;

import android.util.Log;

import com.nira.android.db.models.UniqueCodeUser;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class RealmController {

    public static void addUserInDB(String id, String name, String companyName, String score,String uniqueCode) {
        UniqueCodeUser user = new UniqueCodeUser();
        user.setId(id);
        user.setCompanyName(companyName);
        user.setName(name);
        user.setScore(score);
        user.setUniqueCode(uniqueCode);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
        Log.d("Suvesh","Add ho gya");
    }

    public static List<UniqueCodeUser> getAllUser() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UniqueCodeUser> results = realm.where(UniqueCodeUser.class).findAll();
        return results;
    }

    public  static UniqueCodeUser getUser(String uniqueCode){
        Realm realm = Realm.getDefaultInstance();
        UniqueCodeUser user = realm.where(UniqueCodeUser.class).equalTo("uniqueCode" ,uniqueCode).findFirst();
        if(user!=null){
            return  user;
        }else{
            return  null;
        }
    }

    public  static UniqueCodeUser getUserbaseOnId(String uniqueCode){
        Realm realm = Realm.getDefaultInstance();
        UniqueCodeUser user = realm.where(UniqueCodeUser.class).equalTo("id" ,uniqueCode).findFirst();
        if(user!=null){
            return  user;
        }else{
            return  null;
        }
    }
}
