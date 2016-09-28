package com.thetechsolutions.whodouconsumer.AppHelpers.WebService;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.controllers.ContactsController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.fragments.ContactsMainFragment;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataDelete;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.VendorProfileDT;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeCreateNewContactActivity;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeFriendProfileActivity;
import com.thetechsolutions.whodouconsumer.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouconsumer.Home.model.HomeModel;

import org.vanguardmatrix.engine.utils.MyLogs;

/**
 * Created by Uzair on 8/28/2016.
 */
public class AsynGetDataController {
    private static AsynGetDataController ourInstance = new AsynGetDataController();


    public static AsynGetDataController getInstance() {
        return ourInstance;
    }

    private AsynGetDataController() {
    }

    public void getMyProvidersOrFriends(Activity activity, int tab_pos, boolean isAutoBack) {
        if (tab_pos == 0) {
            new getMyProviders(activity, isAutoBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if (tab_pos == 1) {
            new getMyFriends(activity, isAutoBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if (tab_pos == 2) {
            new getFriendProvider(activity, isAutoBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void getSchedules(Activity activity) {
        new getAppointments(activity, "upcoming").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new getAppointments(activity, "recent").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private class getMyProviders extends AsyncTask<String, Void, Integer> {


        Activity activity;
        boolean isAutoBack;

        public getMyProviders(Activity _activity, boolean _isAutoBack) {
            activity = _activity;
            isAutoBack = _isAutoBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                if (HomeModel.getMyProviders())

                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                ContactsController.getInstance().sendContactsToServer(activity);
            } catch (Exception e) {

            }


            if (result == 0) {

                try {
                    try {
                        for (ProviderDT item : RealmDataRetrive.getHomeList(0)
                                ) {
                            try {
                                RealmDataDelete.deleteContactOldRecord(item.getFirst_name(),
                                        item.getLast_name(), item.getUsername());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                AppController.insertIntoContact(item.getFirst_name(),
                                        item.getLast_name(),
                                        item.getCity(),
                                        item.getZip_code(),
                                        item.getCountry(),
                                        item.getUsername(),
                                        item.getEmail_1());
                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    } catch (Exception e) {

                    }


                } catch (Exception e) {

                }

                try {
                    homeListRefresh(activity, isAutoBack);
                } catch (Exception e) {

                }
                try {
                    RealmDataDelete.deleteCompleteTableRecord(VendorProfileDT.class);
                } catch (Exception e) {

                }
                try {
                    HomeMainFragment.fragment.loadData();
                } catch (Exception e) {

                }

                try {
                    //  HomeMainFragment.fragment.refreshAdapters();
                } catch (Exception e) {

                }
                try {
                    ContactsMainFragment.fragment.loadData();
                } catch (Exception e) {

                }

            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
                //AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    private class getMyFriends extends AsyncTask<String, Void, Integer> {


        Activity activity;
        boolean isAutoBack;

        public getMyFriends(Activity _activity, boolean _isAutoBack) {
            activity = _activity;
            isAutoBack = _isAutoBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                if (HomeModel.getMyFriends())

                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                ContactsController.getInstance().sendContactsToServer(activity);
            } catch (Exception e) {

            }
            if (result == 0) {
                try {
                    try {
                        for (ProviderDT item : RealmDataRetrive.getHomeList(1)
                                ) {

                            RealmDataDelete.deleteContactOldRecord(item.getFirst_name(),
                                    item.getLast_name(), item.getUsername());

                            try {
                                AppController.insertIntoContact(item.getFirst_name(),
                                        item.getLast_name(),
                                        item.getCity(),
                                        item.getZip_code(),
                                        item.getCountry(),
                                        item.getUsername(),
                                        item.getEmail_1());
                            } catch (Exception e) {

                            }

                        }
                    } catch (Exception e) {

                    }


                } catch (Exception e) {

                }

                try {
                    homeListRefresh(activity, isAutoBack);
                } catch (Exception e) {

                }

                try {
                    HomeMainFragment.fragment.loadData();
                } catch (Exception e) {

                }
                try {
                    ContactsMainFragment.fragment.loadData();
                } catch (Exception e) {

                }

            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
                //AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    private class getFriendProvider extends AsyncTask<String, Void, Integer> {


        Activity activity;
        boolean isAutoBack;

        public getFriendProvider(Activity _activity, boolean _isAutoBack) {
            activity = _activity;
            isAutoBack = _isAutoBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                if (HomeModel.getMyFriendsProviders())

                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                ContactsController.getInstance().sendContactsToServer(activity);
            } catch (Exception e) {

            }
            if (result == 0) {
                try {
                    try {
                        for (ProviderDT item : RealmDataRetrive.getHomeList(1)
                                ) {

//                            try {
//                                RealmDataInsert.insertConsumerProviders(item.getUsername(), 1);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                            }
                            RealmDataDelete.deleteContactOldRecord(item.getFirst_name(),
                                    item.getLast_name(), item.getUsername());

                            try {
                                AppController.insertIntoContact(item.getFirst_name(),
                                        item.getLast_name(),
                                        item.getCity(),
                                        item.getZip_code(),
                                        item.getCountry(),
                                        item.getUsername(),
                                        item.getEmail_1());
                            } catch (Exception e) {

                            }

                        }
                    } catch (Exception e) {

                    }


                } catch (Exception e) {

                }

                try {
                    homeListRefresh(activity, isAutoBack);
                } catch (Exception e) {

                }


            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
                //AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    public void homeListRefresh(Activity activity, boolean isAutoBack) {
        try {
            AppController.hideDialoge();
            try {
                if (isAutoBack)
                    ContactsMainActivity.activity.finish();
            } catch (Exception e) {

            }
            try {

                HomeCreateNewContactActivity.activity.finish();
            } catch (Exception e) {

            }
            try {
                if (isAutoBack)
                    HomeFriendProfileActivity.activity.finish();
            } catch (Exception e) {

            }
            if (isAutoBack)
                TitleBarController.getInstance(activity).homeActivityController(activity);
        } catch (Exception e) {

        }
    }

    private class getAppointments extends AsyncTask<String, Void, Integer> {


        Activity activity;
        String appointmentStatus = "";

        public getAppointments(Activity _activity, String _appointmentStatus) {
            activity = _activity;
            appointmentStatus = _appointmentStatus;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                if (WebserviceModel.getAppointments(appointmentStatus))

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

            } else {
                MyLogs.printinfo(
                        "Error in getting my providers"
                );
                //AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

}
