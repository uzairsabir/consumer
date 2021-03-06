package com.thetechsolutions.whodouconsumer.Schedule.controller;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.WebserviceModel;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

/**
 * Created by Uzair on 7/16/2016.
 */
public class ScheduleController {

    private static ScheduleController ourInstance = new ScheduleController();


    public static ScheduleController getInstance() {
        return ourInstance;
    }

    private ScheduleController() {
    }


    public static int getIntroLayout(int position) {
        if (position == 0) {
            return R.layout.fragment_listview_progress_activity;
        } else
            return R.layout.fragment_listview_progress_activity;

    }

    public void createAppointments(Activity activity, String vendorid,
                                   String appointmentdatetime,
                                   String duration,
                                   String description,
                                   String call_message,
                                   String calendarId) {
        new createAppointments(activity, vendorid, appointmentdatetime, duration, description, call_message, calendarId).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void updateAppointments(Activity activity, String appointmentId,
                                   String appointmentdatetime,
                                   String duration,
                                   String description,
                                   String status,
                                   String calendarId) {
        new updateAppointments(activity, appointmentId, appointmentdatetime, duration, description, status, calendarId).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class createAppointments extends AsyncTask<String, Void, Integer> {


        Activity activity;
        String vendorid, appointmentdatetime, duration, description, call_message, calendarId;

        public createAppointments(Activity _activity, String _vendorid,
                                  String _appointmentdatetime,
                                  String _duration,
                                  String _description,
                                  String _call_message,
                                  String _calendarId) {
            activity = _activity;
            vendorid = _vendorid;
            appointmentdatetime = _appointmentdatetime;
            duration = _duration;
            description = _description;
            call_message = _call_message;
            calendarId = _calendarId;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                if (WebserviceModel.createAppointments(vendorid, appointmentdatetime, duration, description, call_message, calendarId))

                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            AppController.hideDialoge();

            if (result == 0) {
                AppController.returnIntent(activity, "done");
                UtilityFunctions.showToast_onCenter("Appointment has been created.",activity);

            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
            }


        }


    }

    private class updateAppointments extends AsyncTask<String, Void, Integer> {


        Activity activity;
        String appointmentid, appointmentdatetime, duration, description, status, calendarId;

        public updateAppointments(Activity _activity, String _appointmentid,
                                  String _appointmentdatetime,
                                  String _duration,
                                  String _description,
                                  String _status,
                                  String _calendarId) {
            activity = _activity;
            appointmentid = _appointmentid;
            appointmentdatetime = _appointmentdatetime;
            duration = _duration;
            description = _description;
            status = _status;
            calendarId = _calendarId;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);

        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                if (WebserviceModel.updateAppointments(appointmentid, appointmentdatetime, duration, description, status, calendarId))

                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            AppController.hideDialoge();

            if (result == 0) {
                AppController.returnIntent(activity, "done");
                UtilityFunctions.showToast_onCenter("Appointment has been updated.",activity);
            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
            }


        }


    }


}
