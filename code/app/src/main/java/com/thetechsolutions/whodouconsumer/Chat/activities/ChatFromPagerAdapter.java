package com.thetechsolutions.whodouconsumer.Chat.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.Chat.fragments.ChatFromMainFragment;

import java.util.ArrayList;

/**
 * Created by Uzair on 12/23/2015.
 */
public class ChatFromPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;

    private ArrayList<String> titlesList = new ArrayList<String>();

    public ChatFromPagerAdapter(FragmentManager fm, Activity activity, int pos_id) {
        super(fm);
        ArrayList<String> temp_titles = new ArrayList<>();

        if (pos_id == 0) {
            temp_titles.add(AppConstants.MY_PROVIDER);
        } else if (pos_id == 1) {
            temp_titles.add(AppConstants.MY_FRIENDS);
        } else if (pos_id == 2) {
            temp_titles.add(AppConstants.FRIENDS_PROVIDERS);
        }
        
        this.titlesList.addAll(temp_titles);
        this.activity = activity;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }

    @Override
    public int getCount() {
        return this.titlesList.size();
    }

    @Override
    public Fragment getItem(int position) {


        return ChatFromMainFragment.newInstance(position, activity);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);


    }


}
