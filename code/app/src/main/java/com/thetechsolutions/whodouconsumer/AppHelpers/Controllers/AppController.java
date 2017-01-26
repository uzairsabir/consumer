package com.thetechsolutions.whodouconsumer.AppHelpers.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.paypal.android.MEP.PayPal;
import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendsProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.R;

import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.datatypes.PhoneContact;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.ui.ConversationActivity;
import eu.siacs.conversations.utils.FriendNames;
import eu.siacs.conversations.utils.UIHelper;
import io.realm.RealmResults;

/**
 * Created by Uzair on 12/19/2015.
 */
public class AppController {

    static MaterialDialog loaderDialoge;


    public static void insertDummpyContent() {
        // RealmDataInsert.insertHome(null);
        // RealmDataInsert.insertSchedule(null);
        //RealmDataInsert.insertPay(null);
        // RealmDataInsert.insertChat(null);
        //RealmDataInsert.insertSettingsPreference(null);
        RealmDataInsert.insertSearchIn();
        AppPreferences.setBoolean(AppPreferences.PREF_IS_DUMY_CONTENT_INSERT, true);

    }

    public static void showToast(Activity activity, String text) {
        SuperActivityToast.create(activity, new Style(), Style.TYPE_STANDARD)
                .setText(text)
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(activity.getResources().getColor(R.color.app_text_blue_new_tr50))
                .setAnimations(Style.ANIMATIONS_POP).show();
    }

    public static void showDialoge(Activity activity) {

        MyLogs.printinfo("show_dialoge");
        loaderDialoge = new MaterialDialog.Builder(activity)
                .title("Loading")
                .content("Please wait...")
                .progress(true, 0)
                .widgetColor(activity.getResources().getColor(R.color.who_do_u_blue))
                .backgroundColor(activity.getResources().getColor(R.color.white))
                .titleColor(activity.getResources().getColor(R.color.black))
                .contentColor(activity.getResources().getColor(R.color.who_do_u_medium_grey))
                .show();
    }

    public static void hideDialoge() {

        try {
            loaderDialoge.dismiss();
        } catch (Exception e) {

        }

    }

    public static void insertIntoContact(String first_name, String last_name, String user_city, String zip_code, String user_country, String providerName, String email_address) {


        int id = RealmDataRetrive.getContactMaxId() + 1;
        ArrayList<PhoneContact> arrayList = new ArrayList<>();
        PhoneContact phoneContact = new PhoneContact();
        phoneContact.setFirst_name(first_name);
        phoneContact.setLast_name(last_name);
        phoneContact.setCity(user_city);
        phoneContact.setContact_id(id + 1);
        phoneContact.setZip(zip_code);
        phoneContact.setCountry(user_country);
        try {
            JSONObject numberJson = new JSONObject();
            numberJson.put("primary_number", providerName);
            phoneContact.setNumber(numberJson.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            JSONObject emailJson = new JSONObject();
            emailJson.put("primary_email", email_address);
            phoneContact.setEmail(emailJson.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
        // MyLogs.printinfo("arrayList_contact  " + phoneContact.getContact_id());
        arrayList.add(phoneContact);


        RealmDataInsert.insertContact(arrayList);
    }

    public static void saveChatList(Activity activity, List<Conversation> list) {

        // GsonBuilder gsonb = new GsonBuilder();
        try {
            UtilityFunctions.deleteFile("CalEvents");
        } catch (Exception e) {

        }
        FileOutputStream fos = null;
        try {

            fos = activity.openFileOutput("CalEvents", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList<Conversation> getChatList(Activity activity) {

        FileInputStream fis;
        ArrayList<Conversation> returnlist = null;
        try {
            fis = activity.openFileInput("CalEvents");
            ObjectInputStream ois = new ObjectInputStream(fis);
            returnlist = (ArrayList<Conversation>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnlist;

    }

    public static void openChat(final Activity activity, final String contactNumber, final String contactName, String contactAvatar, String isRegistered, final int vendorOrconsumer) {
        ProfileDT profileDT = RealmDataRetrive.getProfile();
        if (vendorOrconsumer == 0) {
            MyLogs.printinfo("chating with vendor");
        } else {
            MyLogs.printinfo("chating with consumer");
        }
        if (isRegistered.equals("0")) {

            loaderDialoge = new MaterialDialog.Builder(activity)
                    .title("Not a registered user on App")
                    .content("Would you like to sent Invitation to join App ?")
                    .contentGravity(GravityEnum.CENTER)
                    .positiveText("Yes")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            AsynGetDataController.getInstance().sendInvitation(activity,contactNumber,contactName,vendorOrconsumer);
                            showToast(activity, "Invitation has been sent");
                        }
                    })
                    .widgetColor(activity.getResources().getColor(R.color.who_do_u_blue))
                    .backgroundColor(activity.getResources().getColor(R.color.white))
                    .titleColor(activity.getResources().getColor(R.color.black))
                    .contentColor(activity.getResources().getColor(R.color.who_do_u_medium_grey))
                    .show();
        } else {
            if (vendorOrconsumer == 0) {

                activity.startActivity(ConversationActivity.createIntent(activity, profileDT.getUsername() + "_c", profileDT.getImage_url(), contactNumber + "_v", contactName, contactAvatar));

            } else {

                activity.startActivity(ConversationActivity.createIntent(activity, profileDT.getUsername() + "_c", profileDT.getImage_url(), contactNumber + "_c", contactName, contactAvatar));

            }

        }


    }


    public static void saveChatNamesAvatar(Activity activity) {

        // GsonBuilder gsonb = new GsonBuilder();

        RealmResults<ProviderDT> providerDTs = RealmDataRetrive.getProvider();
        RealmResults<FriendDT> friendDTs = RealmDataRetrive.getFriendList();
        List<FriendNames> list = new ArrayList<>();

        if (providerDTs.size() > 0) {

            for (ProviderDT providerDT : providerDTs) {
                FriendNames item = new FriendNames();
                item.setDisplayName(providerDT.getFirst_name() + " " + providerDT.getLast_name());
                item.setDisplayAvatar(providerDT.getImage_url());
                item.setUsername(providerDT.getUsername());
                list.add(item);
            }
        }

        if (friendDTs.size() > 0) {
            for (FriendDT providerDT : friendDTs) {
                FriendNames item = new FriendNames();
                item.setDisplayName(providerDT.getFirst_name() + " " + providerDT.getLast_name());
                item.setDisplayAvatar(providerDT.getImage_url());
                item.setUsername(providerDT.getUsername());
                list.add(item);
            }

        }
        try {
            UtilityFunctions.deleteFile(UIHelper.FILE_NAME);
        } catch (Exception e) {

        }
        FileOutputStream fos = null;
        try {

            fos = activity.openFileOutput(UIHelper.FILE_NAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        if (RealmDataRetrive.getHomeListOfMyProviderAndMyFriends().size() > 0) {
//            List<FriendNames> list = new ArrayList<>();
//            FriendNames item = new FriendNames();
//            for (ProviderDT providerDT : RealmDataRetrive.getHomeListOfMyProviderAndMyFriends()) {
//                item.setDisplayName(providerDT.getFirst_name() + " " + providerDT.getLast_name());
//                item.setDisplayAvatar(providerDT.getImage_url());
//                item.setUsername(providerDT.getUsername());
//                list.add(item);
//            }
//            try {
//                UtilityFunctions.deleteFile(UIHelper.FILE_NAME);
//            } catch (Exception e) {
//
//            }
//            FileOutputStream fos = null;
//            try {
//
//                fos = activity.openFileOutput(UIHelper.FILE_NAME, Context.MODE_PRIVATE);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            ObjectOutputStream oos = null;
//            try {
//                oos = new ObjectOutputStream(fos);
//                oos.writeObject(list);
//                oos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

    }

    public static String getCompleteAddress() {
        // MyLogs.printinfo("complete_address " + WebService.callCurlRequest("http://maps.googleapis.com/maps/api/geocode/json?address=" + 77379 + "&sensor=true").getJSONArray("results").getJSONObject(0).getString("formatted_address"));

        return "";
    }

    public static void returnIntent(Activity activity, String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", message);
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }

    public static boolean getActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //  String result=data.getStringExtra("result");
                return true;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                return false;
                //Write your code if there's no result
            }
        }
        return false;
    }

    public static ArrayList<Conversation> removeDuplicates(ArrayList<Conversation> l) {
        Set<Conversation> s = new TreeSet<Conversation>(new Comparator<Conversation>() {

            @Override
            public int compare(Conversation o1, Conversation o2) {
                if (o1.getJid().toBareJid().toString().equalsIgnoreCase(o2.getJid().toBareJid().toString()))
                    // ... compare the two object according to your requirements
                    return 0;
                else {
                    return 1;
                }
            }
        });
        s.addAll(l);
        final ArrayList<Conversation> newList = new ArrayList(s);
        MyLogs.printinfo("newList " + newList.size());
        return newList;
    }

    public static ArrayList<Conversation> removeNew(ArrayList<Conversation> list) {
        ArrayList<Conversation> arrayList = new ArrayList<>();
        HashSet<Conversation> hashSet = new HashSet<Conversation>();
        hashSet.addAll(list);
        arrayList.addAll(hashSet);
        return arrayList;
    }

    public static ArrayList<FriendsProviderDT> filterFriendsProvider(ArrayList<FriendsProviderDT> friendsProviders) {

        ArrayList<FriendsProviderDT> filterList = new ArrayList<>();
        // Realm realmObject= Realm.getDefaultInstance();
        // realmObject.setAutoRefresh(true);


        RealmResults<ProviderDT> providerDTs = RealmDataRetrive.getProvider();
//        for(ProviderDT object2: providerDTs){
//            MyLogs.printinfo("provider_username "+object2.getUsername() +" : "+object2.getFirst_name()+" "+object2.getLast_name());
//        }

        if (RealmDataRetrive.getProvider().size() > 0) {

            boolean found = false;
            for (FriendsProviderDT object1 : friendsProviders) {

                for (ProviderDT object2 : providerDTs) {

                    if (object1.getUsername().equals(object2.getUsername())) {
                        found = true;

                        //also do something
                    }
                }
                if (!found) {
                    filterList.add(object1);
                    //do something
                }
                found = false;
            }


            return filterList;

            // return finalUniq=filterList;
        }
        return friendsProviders;
    }

    public static void initLibrary(final Activity activity) {
        try {
            new Runnable() {
                public void run() {
                    PayPal pp = PayPal.getInstance();

                    if (pp == null) {  // Test to see if the library is already initialized

                        // This main initialization call takes your Context, AppID, and target server
                        pp = PayPal.initWithAppID(activity, AppConstants.PAYPAL_SANDBOX_ID, PayPal.ENV_SANDBOX);

                        // Required settings:

                        // Set the language for the library
                        pp.setLanguage("en_US");

                        // Some Optional settings:

                        // Sets who pays any transaction fees. Possible values are:
                        // FEEPAYER_SENDER, FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and FEEPAYER_SECONDARYONLY
                        pp.setFeesPayer(PayPal.FEEPAYER_PRIMARYRECEIVER);

                        // true = transaction requires shipping
                        pp.setShippingEnabled(false);


                    }

                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
