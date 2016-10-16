package com.thetechsolutions.whodouconsumer.Home.controllers;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeCreateNewContactActivity;
import com.thetechsolutions.whodouconsumer.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouconsumer.Home.model.HomeModel;
import com.thetechsolutions.whodouconsumer.R;

/**
 * Created by Uzair on 7/16/2016.
 */
public class HomeMainController {

    public static int getIntroLayout(int position) {
        if (position == 0) {
            return R.layout.fragment_listview_progress_activity;
        } else
            return R.layout.fragment_listview_progress_activity;

    }

    public static boolean createProvider(String providerName, String first_name, String last_name,
                                         String user_address, String user_city, String user_state, String user_country,
                                         String email_address, String zip_code, String subcategory_id,
                                         String is_registered_user,String imageUrl) {


        return HomeModel.createProvider(providerName, first_name, last_name,
                user_address, user_city, user_state, user_country,
                email_address, zip_code, subcategory_id, is_registered_user,imageUrl);

    }


    public static boolean createLink(String providerId, String first_name, String last_name, String providerName) {

        if (HomeCreateNewContactActivity.tab_pos == 0) {
            return HomeModel.createConsumerProviderLink(providerId, first_name, last_name, providerName, HomeCreateNewContactActivity.tab_pos);

        } else {
            return HomeModel.createConsumerFriendLink(providerId, first_name, last_name, providerName);
        }
    }

    public static boolean updateProvider(String providerName, String first_name, String last_name,
                                         String user_address, String user_city, String user_state, String user_country,
                                         String email_address, String zip_code, String subcategory_id,int pos,String imageUrl) {

        return HomeModel.updateProvider(providerName, first_name, last_name,
                user_address, user_city, user_state, user_country,
                email_address, zip_code, subcategory_id,pos,imageUrl);

    }

    public class updateImage extends AsyncTask<String, Void, Integer> {

        Activity activity;
        String user_id,user_type,image_url;
        updateImage(Activity _activity,String _user_id,String _user_type,String _image_url){
            activity=_activity;
            user_id=_user_id;
            user_type=_user_type;
            image_url=_image_url;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                //HomeModel.getMyFriendsProviders()


                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {

                AppController.showToast(activity, activity.getResources().getString(R.string.updated_successfully));
            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }



}
