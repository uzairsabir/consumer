package com.thetechsolutions.whodouconsumer.Pay.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.paypal.android.MEP.PayPal;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.Pay.adapters.PayMainPagerAdapter;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.PagerSlidingTabStrip;

/**
 * Created by Uzair on 7/12/2016.
 */
public class PayMainActivity extends FragmentActivityController implements MethodGenerator {

    static Activity activity;
    public ViewPager pager;
    PayMainPagerAdapter adapter;
    PagerSlidingTabStrip tapStrip;

    public static Intent createIntent(Activity _activity) {
        activity = _activity;
        return new Intent(activity, PayMainActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_and_tab_strip);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, "Payments", false, true, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);



    }

    @Override
    public void viewUpdate() {

        adapter = new PayMainPagerAdapter(
                getSupportFragmentManager(), activity);
        pager.setAdapter(adapter);
        tapStrip.setViewPager(pager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppController.getActivityResult(requestCode, resultCode, data)) {
            viewUpdate();
        }
    }


}
