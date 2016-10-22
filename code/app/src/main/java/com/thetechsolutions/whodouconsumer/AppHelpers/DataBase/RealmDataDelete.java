package com.thetechsolutions.whodouconsumer.AppHelpers.DataBase;

import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendsProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;

import org.vanguardmatrix.engine.utils.MyLogs;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Uzair on 8/6/2016.
 */
public class RealmDataDelete {

//    public static void deleteConsumerProviderLink(String providerName, int is_provider) {
//        final Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        try {
//            ConsumerProviderDT result = realm.where(ConsumerProviderDT.class).equalTo("provider_friend_number", providerName).equalTo("is_provider", is_provider).findFirst();
//
//            result.deleteFromRealm();
//
//        } catch (Exception e) {
//
//        }
//        realm.commitTransaction();
//
//
//    }

    public static void deleteHomeDTByPos(int tab_pos) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            RealmResults<ProviderDT> result = realm.where(ProviderDT.class).equalTo("tab_pos", tab_pos).findAll();
            result.deleteAllFromRealm();
            realm.commitTransaction();
        } catch (RealmException e) {
            e.printStackTrace();
            realm.cancelTransaction();

        }

        //MyLogs.printinfo("cancel_tran ");


    }

    public static void deleteCompleteTableRecord(Class requestedClass) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {


            realm.delete(requestedClass);

        } catch (Exception e) {

        }
        realm.commitTransaction();


    }


    public static void deleteContactOldRecord(String firstName, String lastName, String providerName) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            ContactDT result = realm.where(ContactDT.class).equalTo("Firstname", firstName).equalTo("Lastname", lastName).contains("Numbers", providerName).findFirst();

            result.deleteFromRealm();

        } catch (Exception e) {

        }
        realm.commitTransaction();


    }

    public static void deleteHomeItem(String id, int pos) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            MyLogs.printinfo("id  "+id+" pos "+pos);

            if (pos == 0) {
                ProviderDT result = realm.where(ProviderDT.class).equalTo("username",id).findFirst();


                result.deleteFromRealm();
            } else if (pos == 1) {
                FriendDT result = realm.where(FriendDT.class).equalTo("username",id).findFirst();


                result.deleteFromRealm();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        realm.commitTransaction();


    }
}
