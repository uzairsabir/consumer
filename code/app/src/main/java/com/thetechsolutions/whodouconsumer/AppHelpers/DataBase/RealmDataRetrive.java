package com.thetechsolutions.whodouconsumer.AppHelpers.DataBase;

import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ChatDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendsProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.PayDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.SearchInDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.SettingsPreferenceDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.VendorProfileDT;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeMainActivity;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Uzair on 8/6/2016.
 */
public class RealmDataRetrive {

    public static RealmResults<ProviderDT> getProvider() {



        final Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};


        return realm.where(ProviderDT.class).findAllSorted(a, b).distinct("username");
        // return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).findAllSorted(a, b).distinct("username");

    }

    public static RealmResults<FriendDT> getFriendList() {

        try {
      //      AppController.saveChatNamesAvatar(HomeMainActivity.activity);
        } catch (Exception e) {

        }

        final Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};


        return realm.where(FriendDT.class).findAllSorted(a, b).distinct("username");
        // return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).findAllSorted(a, b).distinct("username");

    }

    public static RealmResults<FriendsProviderDT> getFriendsProvider() {


        final Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};

        return realm.where(FriendsProviderDT.class).findAllSorted(a, b).distinct("username");

    }

    public static RealmResults<FriendsProviderDT> getFriendsProviderByUserName(String friendUserName) {


        final Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};

        return realm.where(FriendsProviderDT.class).equalTo("friend_number",friendUserName).findAllSorted(a, b).distinct("username");

    }



    public static RealmResults<ProviderDT> getHomeSearchList(String keyword, int tabId, int cat_id, int sub_cat_id) {

        MyLogs.printinfo("cat_id " + cat_id + ": " + sub_cat_id);

        final Realm realm = Realm.getDefaultInstance();
        String[] a = {"first_name", "last_name"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};

        if (UtilityFunctions.isEmpty(keyword)) {

            if (cat_id == 0 && sub_cat_id == 0) {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).findAllSorted(a, b);

            } else if (cat_id == 0) {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            } else if (sub_cat_id == 0) {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("category_id", cat_id).findAllSorted(a, b);

            } else {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("category_id", cat_id).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            }
        } else {
            if (cat_id == 0 && sub_cat_id == 0) {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).findAllSorted(a, b);

            } else if (cat_id == 0) {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            } else if (sub_cat_id == 0) {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).equalTo("category_id", cat_id).findAllSorted(a, b);

            } else {
                return realm.where(ProviderDT.class).equalTo("tab_pos", tabId).equalTo("first_name", keyword, Case.INSENSITIVE).or().equalTo("last_name", keyword, Case.INSENSITIVE).equalTo("category_id", cat_id).equalTo("sub_category_id", sub_cat_id).findAllSorted(a, b);

            }
        }

    }

    public static ProviderDT getProviderDetail(String provider_user_name, int is_provider) {

        final Realm realm = Realm.getDefaultInstance();
        MyLogs.printinfo(" provider_user_name "+provider_user_name );
        return realm.where(ProviderDT.class).equalTo("username", provider_user_name).findFirst();

    }

    public static FriendsProviderDT getFriendProviderDetail(String provider_user_name) {

        final Realm realm = Realm.getDefaultInstance();
        MyLogs.printinfo(" provider_user_name "+provider_user_name );
        return realm.where(FriendsProviderDT.class).equalTo("username", provider_user_name).findFirst();

    }

    public static FriendDT getFriendDetail(String provider_user_name) {

        final Realm realm = Realm.getDefaultInstance();
        MyLogs.printinfo(" provider_user_name "+provider_user_name );
        return realm.where(FriendDT.class).equalTo("username", provider_user_name).findFirst();

    }


    public static RealmResults<ScheduleDT> getScheduleList(int tabId) {


        final Realm realm = Realm.getDefaultInstance();

        // MyLogs.printinfo("scheduleList " + tabId + "  " + realm.where(ScheduleDT.class).findAll().size());

        if (tabId == 0) {
            return realm.where(ScheduleDT.class).greaterThan("appointment_date_time", System.currentTimeMillis()).notEqualTo("status", "rejected").findAllSorted("appointment_date_time", Sort.ASCENDING);
        } else if (tabId == 1) {
            return realm.where(ScheduleDT.class).lessThanOrEqualTo("appointment_date_time", System.currentTimeMillis()).notEqualTo("status", "rejected").findAllSorted("appointment_date_time", Sort.DESCENDING);
        }

        return null;

    }

    public static ScheduleDT getScheduleDetail(int item_id) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ScheduleDT.class).equalTo("appointment_id", item_id).findFirst();

    }

    public static RealmResults<PayDT> getPayList(int tabId) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(PayDT.class).findAll();

    }

    public static PayDT getPayDetail(int item_id) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(PayDT.class).equalTo("id", item_id).findFirst();

    }

    public static RealmResults<ChatDT> getChatList() {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatDT.class).findAll();

    }

    public static RealmResults<ChatDT> getChatSearch(String keyword) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ChatDT.class).contains("title", keyword, Case.INSENSITIVE).findAll();

    }

    public static RealmResults<SettingsPreferenceDT> getSettingsPreferenceList() {

        final Realm realm = Realm.getDefaultInstance();
        MyLogs.printinfo(" group " + realm.where(SettingsPreferenceDT.class).findAll().size());
        return realm.where(SettingsPreferenceDT.class).findAll().distinct("group");

    }

    public static RealmResults<SettingsPreferenceDT> getSettingsPreferenceItemList(String group_name) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(SettingsPreferenceDT.class).equalTo("group", group_name).findAll();

    }


    public static RealmResults<ContactDT> getContactList() {

        final Realm realm = Realm.getDefaultInstance();
        String[] a = {"Firstname", "Lastname"};
        Sort[] b = {Sort.ASCENDING, Sort.ASCENDING};
        return realm.where(ContactDT.class).findAllSorted(a, b).distinct("Numbers");

    }

    public static RealmResults<ContactDT> getContactListToSendServer() {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ContactDT.class).equalTo("SendedToServer", 0).findAll();

    }

    public static ProfileDT getProfile() {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ProfileDT.class).findFirst();

    }

//    public static ProviderDT getHomeProfile(String username, int tab_pos) {
//
//        final Realm realm = Realm.getDefaultInstance();
//        return realm.where(ProviderDT.class).equalTo("username", username).equalTo("tab_pos", tab_pos).findFirst();
//
//    }

    public static VendorProfileDT getVendorProfileTemp(String username, int tab_pos) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(VendorProfileDT.class).equalTo("username", username).equalTo("tab_pos", tab_pos).findFirst();

    }

    public static RealmResults<VendorCategoryDT> getCategoryList(int subCatID) {

        if (subCatID == 0) {
            final Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).findAll().distinct("category");
        } else {

            final Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).equalTo("subcategory_id", subCatID).findAll().distinct("category");
        }


    }

    public static RealmResults<VendorCategoryDT> getSubCategoryList(int catID) {

        if (catID == 0) {

            final Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).findAll().distinct("subcategory");

        } else {

            final Realm realm = Realm.getDefaultInstance();
            return realm.where(VendorCategoryDT.class).equalTo("category_id", catID).findAll().distinct("subcategory");
        }


    }

    public static RealmResults<SearchInDT> getSearchInList(boolean notRequireProvider) {
        final Realm realm = Realm.getDefaultInstance();
        if (notRequireProvider) {
            return realm.where(SearchInDT.class).notEqualTo("id", 3).findAll();
        } else {
            return realm.where(SearchInDT.class).findAll();
        }


    }

    public static ContactDT getContactDetail(String username) {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ContactDT.class).contains("Numbers", username).findFirst();

    }


    public static int getContactMaxId() {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ContactDT.class).max("id").intValue();

    }

    public static RealmResults<ProviderDT> getHomeListOfMyProviderAndMyFriends() {

        final Realm realm = Realm.getDefaultInstance();
        return realm.where(ProviderDT.class).notEqualTo("tab_pos", 2).distinct("username");

    }


}
