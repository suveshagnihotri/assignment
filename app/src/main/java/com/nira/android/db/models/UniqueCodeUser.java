package com.nira.android.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Suvesh on 20/08/2017 AD.
 */

public class UniqueCodeUser extends RealmObject {

    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Required
    private String name;

    private String companyName;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Required
    private  String score;

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    private String uniqueCode;
}
