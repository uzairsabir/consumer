package com.thetechsolutions.whodouconsumer.Home.model;

import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataDelete;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.ServiceUrl;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeCreateNewContactActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.webservice.WebService;

import java.util.ArrayList;

/**
 * Created by Uzair on 8/26/2016.
 */
public class HomeModel {


    public static boolean createProvider(String providerName, String first_name, String last_name,
                                         String user_address, String user_city, String user_state, String user_country,
                                         String email_address, String zip_code, String subcategory_id,
                                         String is_registered_user
    ) {
        String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("username", providerName));
        params.add(new BasicNameValuePair("user_type", AppConstants.VENDOR_TYPE));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        params.add(new BasicNameValuePair("user_address", user_address));
        params.add(new BasicNameValuePair("user_city", user_city));
        params.add(new BasicNameValuePair("user_state", user_state));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("email_address", email_address));
        params.add(new BasicNameValuePair("zip_code", zip_code));
        if (HomeCreateNewContactActivity.tab_pos == 1) {
            params.add(new BasicNameValuePair("subcategory_id", ""));
            params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
        } else {
            params.add(new BasicNameValuePair("subcategory_id", subcategory_id));
            params.add(new BasicNameValuePair("user_type", AppConstants.VENDOR_TYPE));
        }

        params.add(new BasicNameValuePair("created_by", AppPreferences.getString(AppPreferences.PREF_USER_NUMBER)));
        params.add(new BasicNameValuePair("is_registered_user", is_registered_user));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_provider, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

//                try {
//                    RealmDataInsert.insertConsumerProviders(providerName, HomeCreateNewContactActivity.tab_pos);
//                } catch (Exception e) {
//
//                }
//
//
//                try {
//                    RealmDataInsert.insertVendorProfile(resultJson.getJSONArray(AppConstants.BODY), HomeCreateNewContactActivity.tab_pos);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    AppController.insertIntoContact(first_name, last_name, user_city, zip_code, user_country, providerName, email_address);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean createConsumerProviderLink(String providerId, String first_name, String last_name, String providerName, int tab_pos

    ) {
        String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("consumer_id", id));
        params.add(new BasicNameValuePair("vendor_id", providerId));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_consumer_provider_link, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

//                try {
//                    RealmDataInsert.insertConsumerProviders(providerName, tab_pos);
//                } catch (Exception e) {
//
//                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


    public static boolean updateProvider(String providerName, String first_name, String last_name,
                                         String user_address, String user_city, String user_state, String user_country,
                                         String email_address, String zip_code, String subcategory_id,int pos
    ) {
        String id = String.valueOf(RealmDataRetrive.getProfile().getUsername());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("username", providerName));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        params.add(new BasicNameValuePair("user_address", user_address));
        params.add(new BasicNameValuePair("user_city", user_city));
        params.add(new BasicNameValuePair("user_state", user_state));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("email_address", email_address));
        params.add(new BasicNameValuePair("zip_code", zip_code));
        if (HomeCreateNewContactActivity.tab_pos == 1) {
            params.add(new BasicNameValuePair("subcategory_id", ""));
            params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
        } else {
            params.add(new BasicNameValuePair("subcategory_id", subcategory_id));
            params.add(new BasicNameValuePair("user_type", AppConstants.VENDOR_TYPE));
        }
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_update_provider, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {


                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


    public static boolean getMyProviders() {
        String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("user_type", AppConstants.VENDOR_TYPE));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_my_providers, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    try {
                        RealmDataDelete.deleteHomeDTByPos(0);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {


                        try {

                            RealmDataInsert.insertHome(resultJson.getJSONArray(AppConstants.BODY), 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
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

    public static boolean createConsumerFriendLink(String friendId, String first_name, String last_name, String providerName

    ) {
        String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("consumer_id", id));
        params.add(new BasicNameValuePair("friend_id", friendId));
        params.add(new BasicNameValuePair("first_name", first_name));
        params.add(new BasicNameValuePair("last_name", last_name));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_create_consumer_friend_link, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

//                try {
//                    RealmDataInsert.insertConsumerProviders(providerName, HomeCreateNewContactActivity.tab_pos);
//                } catch (Exception e) {
//
//                }

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean getMyFriends() {
        String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_my_friends, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {
                        try {
                            RealmDataDelete.deleteHomeDTByPos(1);
                        } catch (Exception e) {

                        }

                        try {

                            RealmDataInsert.insertHome(resultJson.getJSONArray(AppConstants.BODY), 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        try {
//                            JSONArray jsonArray = new JSONArray();
//                            jsonArray = resultJson.getJSONArray(AppConstants.BODY);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                try {
//                                    AppController.insertIntoContact(jsonArray.getJSONObject(i).getString("first_name"), jsonArray.getJSONObject(i).getString("last_name"),
//                                            jsonArray.getJSONObject(i).getString("city"), jsonArray.getJSONObject(i).getString("zip_code"),
//                                            jsonArray.getJSONObject(i).getString("country"), jsonArray.getJSONObject(i).getString("mobile_number_1"), jsonArray.getJSONObject(i).getString("email_1"));
//
//                                } catch (Exception e) {
//
//                                }
//
//
////                                try {
////                                    RealmDataInsert.insertConsumerProviders(jsonArray.getJSONObject(i).getString("mobile_number_1"), 1);
////                                } catch (Exception e) {
////
////                                }
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                    }
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

    public static boolean getMyFriendsProviders() {
        String id = String.valueOf(RealmDataRetrive.getProfile().getId());

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", id));
        params.add(new BasicNameValuePair("start_from", AppConstants.START_FROM));
        params.add(new BasicNameValuePair("total_records", AppConstants.TOTAL_RECORDS));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_my_friends_providers, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    if (resultJson.getJSONArray(AppConstants.BODY) != null) {
                        try {
                            RealmDataDelete.deleteHomeDTByPos(2);
                        } catch (Exception e) {

                        }

                        try {

                            RealmDataInsert.insertHome(resultJson.getJSONArray(AppConstants.BODY), 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        try {
//                            RealmDataInsert.insertVendorProfile(resultJson.getJSONArray(AppConstants.BODY), 2);
//
//                        } catch (JSONException e) {
//                            //   e.printStackTrace();
//                        }

//                        try {
//                            JSONArray jsonArray = new JSONArray();
//                            jsonArray = resultJson.getJSONArray(AppConstants.BODY);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                AppController.insertIntoContact(jsonArray.getJSONObject(i).getString("first_name"), jsonArray.getJSONObject(i).getString("last_name"),
//                                        jsonArray.getJSONObject(i).getString("city"), jsonArray.getJSONObject(i).getString("zip_code"),
//                                        jsonArray.getJSONObject(i).getString("country"), jsonArray.getJSONObject(i).getString("mobile_number_1"), jsonArray.getJSONObject(i).getString("email_1"));
//
//                                try {
//                                    RealmDataInsert.insertConsumerProviders(jsonArray.getJSONObject(i).getString("mobile_number_1"), 2);
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
                    }
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
