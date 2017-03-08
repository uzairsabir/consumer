package com.thetechsolutions.whodouconsumer;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.data.notification.NotificationManager;
import org.vanguardmatrix.engine.utils.GCMController;
import org.vanguardmatrix.engine.utils.MyLogs;

import me.leolin.shortcutbadger.ShortcutBadger;

public class GcmIntentService extends IntentService {


    //public static final String CREATE_PAYMENT_REQUEST = "create_payment_request";
    public static final String APPOINTMENT_STATUS = "appointment_status";
    public static final String VENDOR_COSUMER_LINK = "vendor_consumer_link";
    public static final String CONSUMER_VENDOR_PROFILE = "consumer_vendor_profile";
    public static final String VENDOR_CONSUMER_PROFILE = "vendor_consumer_profile";
    public static final String CONSUMER_FRIEND_LINK = "consumer_friend_link";

    public static final String CONSUMER_VENDOR_LINK = "vendorConsumerLink";
    public static final String CREATE_CONSUMER_VENDOR_PROFILE = "createConsumerVendorProfile";
    public static final String VENDOR_FRIEND_LINK = "createConsumerFriendLink";
    public static final String CREATE_VENDOR_CONSUMER_PROFILE = "createVendorConsumerProfile";

    public static final String CREATE_APPOINTMENT = "createAppointment";
    public static final String UPDATE_APPOINTMENT = "updateAppointmentStatusByVendor";

    public static final String CREATE_PAYMENT_REQUEST = "createPaymentRequest";
    String gcmMsg;
    JSONObject gcmMsgJson;
    String TAG = "GcmIntentService";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {

                Log.i(TAG, "Received: " + extras.toString());
                sendNotification(extras);

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {

                Log.i(TAG, "Received: " + extras.toString());
                sendNotification(extras);

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                for (int i = 0; i < 5; i++) {
                    Log.i(TAG,
                            "Working... " + (i + 1) + "/5 @ "
                                    + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                    }
                }


                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

                sendNotification(extras);

                Log.i(TAG, "Received: " + extras.toString());
            }
        }

    }

    private void sendNotification(Bundle msg) {

        try {

            gcmMsg = msg.getString("message");
            gcmMsgJson = new JSONObject(gcmMsg);

            try {
                // gcmMsgJson.put(GcmNotificationsModel.N_TIME, System.currentTimeMillis());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        updateGcmIntent(gcmMsgJson);

    }


    public void updateGcmIntent(JSONObject _gcmMsgJson) {
        try {


            MyLogs.printerror("Gcm_Packet : " + _gcmMsgJson.toString());

            ShortcutBadger.applyCount(getApplicationContext(), 1);

            JSONArray jsonArray;

            //------------------------------HOME NOTIFICATION-----------------///
            try {

                jsonArray = _gcmMsgJson.getJSONArray(CONSUMER_VENDOR_LINK);

                MyLogs.printerror("Gcm_TYPE : " + CONSUMER_VENDOR_LINK);
                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {

            }
            try {

                jsonArray = _gcmMsgJson.getJSONArray(CREATE_CONSUMER_VENDOR_PROFILE);

                MyLogs.printerror("Gcm_TYPE : " + CREATE_CONSUMER_VENDOR_PROFILE);
                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {

            }

            try {

                jsonArray = _gcmMsgJson.getJSONArray(VENDOR_FRIEND_LINK);

                MyLogs.printerror("Gcm_TYPE : " + VENDOR_FRIEND_LINK);

                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {


            }
            try {

                jsonArray = _gcmMsgJson.getJSONArray(CREATE_VENDOR_CONSUMER_PROFILE);

                MyLogs.printerror("Gcm_TYPE : " + CREATE_VENDOR_CONSUMER_PROFILE);

                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {


            }

            //---------------------------------END--------------------------------------------------


            //----------------------------------APPOINTMENT_NOTIFICATION----------------------------
            try {

                jsonArray = _gcmMsgJson.getJSONArray(CREATE_APPOINTMENT);

                MyLogs.printerror("Gcm_TYPE : " + CREATE_APPOINTMENT);

                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {


            }
            try {

                jsonArray = _gcmMsgJson.getJSONArray(UPDATE_APPOINTMENT);

                MyLogs.printerror("Gcm_TYPE : " + UPDATE_APPOINTMENT);

                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {


            }

            //---------------------------------END--------------------------------------------------


            //---------------------------------PAYMENT----------------------------------------------//
            try {

                jsonArray = _gcmMsgJson.getJSONArray(CREATE_PAYMENT_REQUEST);

                MyLogs.printerror("Gcm_TYPE : " + CREATE_PAYMENT_REQUEST);

                for (int i = 0; i < jsonArray.length(); i++) {

                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Pingem", jsonArray.getJSONObject(i).getString("message"));

                }
            } catch (Exception e) {


            }

            //-------------------------------------------END----------------------------------------//

//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(APPOINTMENT_REQUEST);
//                MyLogs.printerror("GCMIntentService updateGcmIntent() : " + APPOINTMENT_REQUEST);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "New Appointment", "Your appointment request from " + jsonArray.getJSONObject(i).getString("consumer_name"));
//
//                }
//            } catch (Exception e) {
//
//
//            }
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(APPOINTMENT_STATUS);
//                MyLogs.printerror("GCMIntentService updateGcmIntent() : " + APPOINTMENT_STATUS);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Appointment", "Your appointment with" + jsonArray.getJSONObject(i).getString("consumer_name") + " has been " + jsonArray.getJSONObject(i).getString("status"));
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(VENDOR_COSUMER_LINK);
//                MyLogs.printerror("GCMIntentService updateGcmIntent() : " + VENDOR_COSUMER_LINK);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Consumer Added", jsonArray.getJSONObject(i).getString("vendor_firstName") + jsonArray.getJSONObject(i).getString("vendor_lastName") + " has added you as a consumer");
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(CONSUMER_FRIEND_LINK);
//                MyLogs.printerror("GCMIntentService updateGcmIntent() : " + CONSUMER_FRIEND_LINK);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Consumer Added", jsonArray.getJSONObject(i).getString("consumer_firstName") + jsonArray.getJSONObject(i).getString("consumer_lastName") + " has added you as a consumer");
//
//
//                }
//            } catch (Exception e) {
//
//
//            }

//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(UPDATE_PAYMENT_REQUEST_BY_ID);
//                MyLogs.printerror("GCMIntentService updateGcmIntent() : " + UPDATE_PAYMENT_REQUEST_BY_ID);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Payment", jsonArray.getJSONObject(i).getString("status"));
//
//                }
//            } catch (Exception e) {
//
//
//            }


        } catch (Exception e) {

        }

    }

//    public void updateGcmIntent(JSONObject _gcmMsgJson) {
//        try {
//
//
//            MyLogs.printerror("GCMIntentService updateGcmIntent() : " + _gcmMsgJson.toString());
//
//
//            ShortcutBadger.applyCount(getApplicationContext(), 1);
//
//            JSONArray jsonArray;
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(CREATE_PAYMENT_REQUEST);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Payment Request", jsonArray.getJSONObject(i).getString("amount"));
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(APPOINTMENT_STATUS);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Appointment", "Your appointment with " + jsonArray.getJSONObject(i).getString("vendor_name") + " has been " + jsonArray.getJSONObject(i).getString("status"));
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(VENDOR_COSUMER_LINK);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Consumer Added", jsonArray.getJSONObject(i).getString("vendor_firstName") + jsonArray.getJSONObject(i).getString("vendor_lastName") + " has added you as a consumer");
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(CONSUMER_VENDOR_PROFILE);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Consumer Profile", jsonArray.getJSONObject(i).getString("status"));
//
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(VENDOR_CONSUMER_PROFILE);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Vendor Profile", jsonArray.getJSONObject(i).getString("status"));
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//
//            try {
//
//                jsonArray = _gcmMsgJson.getJSONArray(CONSUMER_FRIEND_LINK);
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Consumer Added", jsonArray.getJSONObject(i).getString("vendor_firstName") + jsonArray.getJSONObject(i).getString("vendor_lastName") + " has added you as a consumer");
//
//
//                    // createNotification(getApplicationContext(), HomeScreenActivity.createIntent(getApplicationContext(), 3), 70, "Payment Request", jsonArray.getJSONObject(i).getString("amount"));
//
//                }
//            } catch (Exception e) {
//
//
//            }
//
//
//        } catch (Exception e) {
//
//        }
//
//    }

    public static void createNotification(Context _activity,
                                          Intent intent, int code, String title, String content) {

        android.app.NotificationManager mNotificationManager =
                (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntents = PendingIntent.getActivity(_activity, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // String name = "", ticker = "", contenttitle = "", message = "";

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(_activity.getApplicationContext());
        notifyBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(content)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntents)
                .setContentInfo("");

        mNotificationManager.notify(code, notifyBuilder.build());

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) _activity.getApplicationContext().getSystemService(ns);
        nMgr.cancelNotificaiton(code);

    }


}
