package com.thetechsolutions.whodouconsumer.Signup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.controllers.ContactsController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

/**
 * Created by Uzair on 12/18/2015.
 */
public class ContactSyncActivity extends FragmentActivityController {

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    public static Activity activity;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, ContactSyncActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_progress);
        activity = this;
        viewsInitialize();

       // MyLogs.printinfo("profile "+RealmDataRetrive.getProfile());
        updateViews();
    }

    private void updateViews() {
        if (UtilityFunctions.checkInternet(activity)) {
            ContactsController.getInstance().syncContact(activity);
            AppController.registerDevice(activity);
        } else {
            UtilityFunctions.showToast_onCenter(activity.getString(R.string.internetoffstatus), activity);
        }
    }

    private void viewsInitialize() {


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();


        } else {
            UtilityFunctions.showToast_onCenter("Press once again to exit", activity);

        }
        back_pressed = System.currentTimeMillis();

    }


}
