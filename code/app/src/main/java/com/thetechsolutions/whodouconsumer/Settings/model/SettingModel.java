package com.thetechsolutions.whodouconsumer.Settings.model;

import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.ServiceUrl;
import com.thetechsolutions.whodouconsumer.Home.model.HomeModel;
import com.thetechsolutions.whodouconsumer.Settings.fragments.SettingProfileFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.webservice.WebService;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;

/**
 * Created by Uzair on 10/12/2016.
 */
public class SettingModel {

    public static boolean updateProfile(String providerName, String first_name, String last_name,
                                        String user_address, String user_city, String user_state, String user_country,
                                        String email_address, String zip_code, String subcategory_id, String imageUrl, int pos
    ) {
        String id = String.valueOf(RealmDataRetrive.getProfile().getUsername());
        String user_id = AppPreferences.getString(AppPreferences.PREF_USER_ID);
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("myuserId", user_id));
        params.add(new BasicNameValuePair("user_type", "consumer"));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        params.add(new BasicNameValuePair("user_address", user_address));
        params.add(new BasicNameValuePair("user_city", user_city));
        params.add(new BasicNameValuePair("user_state", user_state));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("email_address", email_address));
        params.add(new BasicNameValuePair("zip_code", zip_code));
        params.add(new BasicNameValuePair("subcategory_id", ""));
//        if (pos == 1) {
//
//            params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
//        } else {
//            params.add(new BasicNameValuePair("subcategory_id", subcategory_id));
//            params.add(new BasicNameValuePair("user_type", AppConstants.VENDOR_TYPE));
//        }
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_profile, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                if (!UtilityFunctions.isEmpty(imageUrl)) {

                    SettingProfileFragment.imageUrl = "";
                    return HomeModel.uploadImage(user_id, "consumer", imageUrl, true);

                } else {
                    try {
                        RealmDataInsert.insertProfile(resultJson.getJSONArray(AppConstants.BODY));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }


            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean updatePreference(String preference_id, String preference_value) {
        String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("my_user_id", id));
        params.add(new BasicNameValuePair("preferences_type_id", preference_id));
        params.add(new BasicNameValuePair("new_value", preference_value));
        params.add(new BasicNameValuePair("my_user_type", AppConstants.APP_TYPE));

        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_preference, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {
                try {
                    RealmDataInsert.insertSettingsPreference(resultJson.getJSONArray(AppConstants.BODY));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return true;
            }


        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


}
