package com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class SettingsPreferenceDT extends RealmObject {

    public SettingsPreferenceDT() {

    }

    @PrimaryKey
    private int id = 0;

    @Index
    private String group_name = "";
    private String group_item_name = "";

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_item_name() {
        return group_item_name;
    }

    public void setGroup_item_name(String group_item_name) {
        this.group_item_name = group_item_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
