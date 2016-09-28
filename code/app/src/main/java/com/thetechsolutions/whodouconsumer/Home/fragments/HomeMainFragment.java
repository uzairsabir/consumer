package com.thetechsolutions.whodouconsumer.Home.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.Home.adapters.HomeFriendsProviderListAdapter;
import com.thetechsolutions.whodouconsumer.Home.adapters.HomeListAdapter;
import com.thetechsolutions.whodouconsumer.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.ArrayList;

import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeMainFragment extends Fragment implements View.OnClickListener {

    public static HomeMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";
    public static final String ARG_SECTION_POSITION = "ARG_SECTION_POSITION";

    ProgressActivity progressActivity;
    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;

    ArrayList<ProviderDT> providerDTs;
    public static int pos;

    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new HomeMainFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, HomeMainController.getIntroLayout(sectionNumber));
        args.putInt(ARG_SECTION_POSITION, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(getArguments().getInt(ARG_SECTION), container, false);
            progressActivity = (ProgressActivity) rootView.findViewById(R.id.progressActivity);
            dynamicListView = (DynamicListView) rootView.findViewById(R.id.dynamiclistview);

        } catch (Exception e) {
            e.printStackTrace();
        }


        loadData();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void loadData() {
        if (getArguments().getInt(ARG_SECTION_POSITION) == 0) {

            providerDTs = new ArrayList<>();
            providerDTs.addAll(RealmDataRetrive.getHomeList(0));
            new getProviderList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (getArguments().getInt(ARG_SECTION_POSITION) == 1) {
            providerDTs = new ArrayList<>();
            providerDTs.addAll(RealmDataRetrive.getHomeList(1));

            new getFriendsList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } else if (getArguments().getInt(ARG_SECTION_POSITION) == 2) {


            providerDTs = new ArrayList<>();
            providerDTs.addAll(RealmDataRetrive.getHomeList(2));

            new getFriendsProviderList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }


    }


    private class getProviderList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (providerDTs.size() > 0)
                    return true;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                progressActivity.showContent();
                easyAdapter = new EasyAdapter<>(
                        activity,
                        HomeListAdapter.newInstance(activity, 0),
                        providerDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {

                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        "Get Started by adding your providers! Just click on the + button above to being tailoring whodou to meet your needs.");


            }

        }
    }

    private class getFriendsList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (providerDTs.size() > 0) {
                    return true;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                progressActivity.showContent();
                easyAdapter = new EasyAdapter<>(
                        activity,
                        HomeListAdapter.newInstance(activity, 0),
                        providerDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        "Add your friends and see who they trust to get things done! Just click on the + button above to invite your friends to join you.");


            }

        }
    }

    private class getFriendsProviderList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (providerDTs.size() > 0) {
                    return true;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                progressActivity.showContent();
                easyAdapter = new EasyAdapter<>(
                        activity,
                        HomeFriendsProviderListAdapter.newInstance(activity),
                        providerDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        "Add your friends and see who they trust to get things done! Just click on the + button above to invite your friends to join you.");


            }

        }

    }

    public HomeFriendsProviderListAdapter.Listener mListener = new HomeFriendsProviderListAdapter.Listener() {

        @Override
        public void onButtonClicked(ProviderDT person) {
            refreshAdapters();
        }
    };

    public void refreshAdapters() {
        easyAdapter.notifyDataSetChanged();
        dynamicListView.invalidate();
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            pos = getArguments().getInt(ARG_SECTION_POSITION);
            MyLogs.printinfo("is_visible"+pos);
        }
    }
}
