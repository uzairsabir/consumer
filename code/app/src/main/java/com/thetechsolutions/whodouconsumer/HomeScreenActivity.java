package com.thetechsolutions.whodouconsumer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeMainActivity;

import org.vanguardmatrix.engine.android.ui.helper.ManagedActivity;

import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.ui.ConversationActivity;

/**
 * Created by Uzair on 1/26/2016.
 */
public class HomeScreenActivity extends ManagedActivity {
    public static Activity activity;
    public static boolean isSplashDone;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        ListenerController.openHomeActivity(activity, 0);
        ViewInitialize();
        ViewHandling();


    }

    private void ViewInitialize() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableList);

    }

    private void ViewHandling() {



        if (!isSplashDone) {
            ListenerController.openSplashActivity(activity);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            HomeMainActivity.activity.finish();
        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
