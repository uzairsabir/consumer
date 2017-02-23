package com.thetechsolutions.whodouconsumer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeMainActivity;

import org.vanguardmatrix.engine.android.ui.helper.ManagedActivity;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Uzair on 1/26/2016.
 */
public class HomeScreenActivity extends ManagedActivity {
    public static Activity activity;
    public static boolean isSplashDone;
    ExpandableListView expandableListView;
    static int open_page;

    public static Intent createIntent(Context activity, int _id_to_open) {
        open_page = _id_to_open;
        return new Intent(activity, HomeScreenActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        ListenerController.openHomeActivity(activity, 0);
        ViewInitialize();
        ViewHandling();

        if (open_page > 0) {
            ShortcutBadger.removeCount(getApplicationContext());
            open_page = 0;
        }

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
