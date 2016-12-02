package com.thetechsolutions.whodouconsumer.Home.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.models.ContactModel;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataDelete;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendsProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.Home.adapters.HomeListFriendsProviderAdapter;
import com.thetechsolutions.whodouconsumer.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouconsumer.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.customviews.FullLengthListView;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.PermissionHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.io.File;
import java.util.ArrayList;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfig;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeFriendProfileActivity extends FragmentActivityController implements MethodGenerator {

    public static Activity activity;
    SimpleDraweeView fresco_view;
    TextView title_name, service_name, address, email_edit, city_edit, mobile_edit, zip_edit,
            email_edit_text, city_edit_text, mobile_edit_text, zip_edit_text;

    EditText cell_no, email, city_state, zip_code;
    ImageView dollar_icon, calendar_icon, chat_icon, call_icon;
    Button save_btn, delete_btn, edit_profile;

    static int tab_pos, subCatId;
    static String providerName;
    String text_first_name, text_last_name,
            text_city_state, text_zip_codes, text_email;
    String imageUrl;
    RelativeLayout list_container;
    ArrayList<FriendsProviderDT> friendsProviderDTs;
    EasyAdapter easyAdapter;
    FullLengthListView dynamicListView;
    LinearLayout top_lay;

    public static Intent createIntent(Activity _activity, int _tab_pos, String _providerName) {
        activity = _activity;
        tab_pos = _tab_pos;
        providerName = _providerName;
        return new Intent(activity, HomeFriendProfileActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, "Profile", true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();
        UtilityFunctions.hideKeyboard(activity, email);
    }

    @Override
    public void viewInitialize() {
        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);

        title_name = (TextView) findViewById(R.id.title_name);
        service_name = (TextView) findViewById(R.id.service_name);
        address = (TextView) findViewById(R.id.address);

        mobile_edit = (TextView) findViewById(R.id.mobile_edit);
        email_edit = (TextView) findViewById(R.id.email_edit);
        city_edit = (TextView) findViewById(R.id.city_edit);
        zip_edit = (TextView) findViewById(R.id.zip_edit);

        mobile_edit_text = (TextView) findViewById(R.id.mobile_edit_text);
        email_edit_text = (TextView) findViewById(R.id.email_edit_text);
        city_edit_text = (TextView) findViewById(R.id.city_edit_text);
        zip_edit_text = (TextView) findViewById(R.id.zip_edit_text);

        cell_no = (EditText) findViewById(R.id.cell_no);
        email = (EditText) findViewById(R.id.email_name);
        city_state = (EditText) findViewById(R.id.city_state);
        zip_code = (EditText) findViewById(R.id.zip_code);


        dollar_icon = (ImageView) findViewById(R.id.dollar_icon);
        calendar_icon = (ImageView) findViewById(R.id.calendar_icon);
        chat_icon = (ImageView) findViewById(R.id.chat_icon);
        call_icon = (ImageView) findViewById(R.id.call_icon);

        save_btn = (Button) findViewById(R.id.save_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);
        dynamicListView = (FullLengthListView) findViewById(R.id.dynamiclistview);
        list_container = (RelativeLayout) findViewById(R.id.list_container);
        edit_profile = (Button) findViewById(R.id.edit_profile);
        //MyLogs.printinfo("tab_pos " + tab_pos);

        top_lay = (LinearLayout) findViewById(R.id.top_lay);

        if (HomeMainFragment.pos == 1) {
            calendar_icon.setVisibility(View.GONE);
            service_name.setVisibility(View.GONE);
            dollar_icon.setVisibility(View.GONE);

            MyLogs.printinfo("tab_position ");
        }
        if (tab_pos == 2) {
            save_btn.setVisibility(View.GONE);
            delete_btn.setVisibility(View.GONE);

        }

        listeners();


    }

    private void listeners() {


        mobile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cell_no.setEnabled(true);
                cell_no.setFocusable(true);
                UtilityFunctions.showSoftKeyboard(activity, cell_no);

            }
        });
        email_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setEnabled(true);
                email.requestFocus();
                email.setFocusable(true);
                UtilityFunctions.showSoftKeyboard(activity, email);


            }
        });
        city_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_state.setEnabled(true);
                city_state.setFocusable(true);
                UtilityFunctions.showSoftKeyboard(activity, city_state);

            }
        });

        zip_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zip_code.setEnabled(true);
                zip_code.setFocusable(true);
                UtilityFunctions.showSoftKeyboard(activity, zip_code);
                //   MyLogs.printinfo("click_cname");
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeMainFragment.pos == 0) {
                    ProviderDT item_detail = RealmDataRetrive.getProviderDetail(providerName, 0);
                    text_first_name = item_detail.getFirst_name();
                    text_last_name = item_detail.getLast_name();
                    text_city_state = city_state.getText().toString();
                    text_email = email.getText().toString();
                    text_zip_codes = zip_code.getText().toString();
                    subCatId = item_detail.getSub_category_id();
                } else if (HomeMainFragment.pos == 1) {
                    FriendDT item_detail = RealmDataRetrive.getFriendDetail(providerName);
                    text_first_name = item_detail.getFirst_name();
                    text_last_name = item_detail.getLast_name();
                    text_city_state = city_state.getText().toString();
                    text_email = email.getText().toString();
                    text_zip_codes = zip_code.getText().toString();
                    subCatId = item_detail.getSub_category_id();
                } else if (HomeMainFragment.pos == 2) {
                    FriendsProviderDT item_detail = RealmDataRetrive.getFriendProviderDetail(providerName);
                    text_first_name = item_detail.getFirst_name();
                    text_last_name = item_detail.getLast_name();
                    text_city_state = city_state.getText().toString();
                    text_email = email.getText().toString();
                    text_zip_codes = zip_code.getText().toString();
                    subCatId = item_detail.getSub_category_id();
                }


                new updateVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }
        });
    }

    @Override
    public void viewUpdate() {

        loadData();


    }

    public void loadData() {

        if (HomeMainFragment.pos == 0) {
            final ProviderDT item_detail = RealmDataRetrive.getProviderDetail(providerName, 0);

            if (item_detail != null) {
                title_name.setText(item_detail.getFirst_name() + " " + item_detail.getLast_name());
                service_name.setText(item_detail.getSub_category_title());
                address.setText(item_detail.getAddress());
                cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item_detail.getMobile_number_1()));
                email.setText(item_detail.getEmail_1());
                fresco_view.setImageURI(item_detail.getImage_url());
                zip_code.setText(item_detail.getZip_code());
                city_state.setText(item_detail.getCity());

                try {
                    if (!item_detail.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                        email_edit_text.setVisibility(View.INVISIBLE);
                        city_edit_text.setVisibility(View.INVISIBLE);
                        mobile_edit_text.setVisibility(View.INVISIBLE);
                        zip_edit_text.setVisibility(View.INVISIBLE);

                        email.setEnabled(false);
                        email.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        zip_code.setEnabled(false);
                        zip_code.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        city_state.setEnabled(false);
                        city_state.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        save_btn.setVisibility(View.GONE);
                    } else {
                        email.setFocusable(true);
                        zip_code.setFocusable(true);
                        city_state.setFocusable(true);
                    }
                } catch (Exception e) {

                }


                dollar_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListenerController.openPaymentDetail(activity, 0, 3, "Create Payment", item_detail.getUsername());
                    }
                });
                calendar_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListenerController.openScheduleDetail(activity, 0, 2, "Create Appointment", item_detail.getUsername());
                    }
                });

                chat_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppController.openChat(activity, item_detail.getUsername(), item_detail.getFirst_name() + " " + item_detail.getLast_name(), item_detail.getImage_url(), item_detail.getIs_register_user(), HomeMainFragment.pos);

                    }
                });
                call_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UtilityFunctions.directCall(activity, item_detail.getUsername());
                    }
                });

                fresco_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item_detail.getCreated_by().equalsIgnoreCase(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                            if (PermissionHandler.isStoragePermissionGranted(activity)) {

                                EasyImage.openChooserWithGallery(activity, "Profile Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                            }
                        } else {
                            UtilityFunctions.showFullImage(activity, item_detail.getFirst_name() + " " + item_detail.getLast_name(), item_detail.getImage_url(), null, 0);

                        }
                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new callConsumerVendorDelete("" + item_detail.getUsername()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                });

            }

        } else if (HomeMainFragment.pos == 1) {
            final FriendDT item_detail = RealmDataRetrive.getFriendDetail(providerName);
            if (item_detail != null) {
                title_name.setText(item_detail.getFirst_name() + " " + item_detail.getLast_name());
                service_name.setText(item_detail.getSub_category_title());
                address.setText(item_detail.getAddress());
                cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item_detail.getMobile_number_1()));
                email.setText(item_detail.getEmail_1());
                fresco_view.setImageURI(item_detail.getImage_url());
                zip_code.setText(item_detail.getZip_code());
                city_state.setText(item_detail.getCity());

                try {
                    if (!item_detail.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                        email_edit_text.setVisibility(View.INVISIBLE);
                        city_edit_text.setVisibility(View.INVISIBLE);
                        mobile_edit_text.setVisibility(View.INVISIBLE);
                        zip_edit_text.setVisibility(View.INVISIBLE);

                        email.setEnabled(false);
                        email.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        zip_code.setEnabled(false);
                        zip_code.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        city_state.setEnabled(false);
                        city_state.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        save_btn.setVisibility(View.GONE);
                    } else {
                        email.setFocusable(true);
                        zip_code.setFocusable(true);
                        city_state.setFocusable(true);
                    }
                } catch (Exception e) {

                }


                dollar_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListenerController.openPaymentDetail(activity, 0, 3, "Create Payment", item_detail.getUsername());
                    }
                });
                calendar_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListenerController.openScheduleDetail(activity, 0, 2, "Create Appointment", item_detail.getUsername());
                    }
                });

                chat_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppController.openChat(activity, item_detail.getUsername(), item_detail.getFirst_name() + " " + item_detail.getLast_name(), item_detail.getImage_url(), item_detail.getIs_register_user(), HomeMainFragment.pos);

                    }
                });
                call_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UtilityFunctions.directCall(activity, item_detail.getUsername());
                    }
                });

                fresco_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item_detail.getCreated_by().equalsIgnoreCase(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                            if (PermissionHandler.isStoragePermissionGranted(activity)) {

                                EasyImage.openChooserWithGallery(activity, "Profile Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                            }
                        } else {
                            UtilityFunctions.showFullImage(activity, item_detail.getFirst_name() + " " + item_detail.getLast_name(), item_detail.getImage_url(), null, 0);

                        }
                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new callConsumerVendorDelete("" + item_detail.getUsername()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                });


                friendsProviderDTs = new ArrayList<>();
                friendsProviderDTs.addAll(RealmDataRetrive.getFriendsProviderByUserName(item_detail.getUsername()));
                new getFriendsProviderList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }

        } else if (HomeMainFragment.pos == 2) {
            final FriendsProviderDT item_detail = RealmDataRetrive.getFriendProviderDetail(providerName);
            if (item_detail != null) {
                title_name.setText(item_detail.getFirst_name() + " " + item_detail.getLast_name());
                service_name.setText(item_detail.getSub_category_title());
                address.setText(item_detail.getAddress());
                cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, item_detail.getMobile_number_1()));
                email.setText(item_detail.getEmail_1());
                fresco_view.setImageURI(item_detail.getImage_url());
                zip_code.setText(item_detail.getZip_code());
                city_state.setText(item_detail.getCity());

                try {
                    if (!item_detail.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                        email_edit_text.setVisibility(View.INVISIBLE);
                        city_edit_text.setVisibility(View.INVISIBLE);
                        mobile_edit_text.setVisibility(View.INVISIBLE);
                        zip_edit_text.setVisibility(View.INVISIBLE);

                        email.setEnabled(false);
                        email.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        zip_code.setEnabled(false);
                        zip_code.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        city_state.setEnabled(false);
                        city_state.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
                        save_btn.setVisibility(View.GONE);
                    } else {
                        email.setFocusable(true);
                        zip_code.setFocusable(true);
                        city_state.setFocusable(true);
                    }
                } catch (Exception e) {

                }


                dollar_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                calendar_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListenerController.openScheduleDetail(activity, 0, 2, "Create Appointment", item_detail.getUsername());
                    }
                });

                chat_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppController.openChat(activity, item_detail.getUsername(), item_detail.getFirst_name() + " " + item_detail.getLast_name(), item_detail.getImage_url(), item_detail.getIs_register_user(), HomeMainFragment.pos);

                    }
                });
                call_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UtilityFunctions.directCall(activity, item_detail.getUsername());
                    }
                });

                fresco_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item_detail.getCreated_by().equalsIgnoreCase(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {
                            if (PermissionHandler.isStoragePermissionGranted(activity)) {

                                EasyImage.openChooserWithGallery(activity, "Profile Photo", EasyImageConfig.REQ_PICK_PICTURE_FROM_GALLERY);
                            }
                        } else {
                            UtilityFunctions.showFullImage(activity, item_detail.getFirst_name() + " " + item_detail.getLast_name(), item_detail.getImage_url(), null, 0);

                        }
                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new callConsumerVendorDelete("" + item_detail.getUsername()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                });

            }

        }

    }

    public class updateVendor extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                HomeMainController.updateProvider(providerName, text_first_name, text_last_name, "", text_city_state, "", "", text_email,
                        text_zip_codes, subCatId + "", HomeMainFragment.pos, imageUrl);


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
                onSuccessHandling();
                //onSuccessHandling();
                AppController.showToast(activity, activity.getResources().getString(R.string.updated_successfully));
            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    private void onSuccessHandling() {
        try {
            AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, HomeMainFragment.pos, false);
        } catch (Exception e) {

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                try {
                    fresco_view.setImageURI(Uri.fromFile(new File(imageFile.getPath())));
                } catch (Exception e) {

                }

                imageUrl = imageFile.getAbsolutePath();
                //onPhotoReturned(imageFile);
            }
        });
    }

    private class callConsumerVendorDelete extends AsyncTask<String, Void, Boolean> {


        String providername = "";


        public callConsumerVendorDelete(String _providername) {

            providername = _providername;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (HomeMainFragment.pos == 0) {
                    return ContactModel.checkDeleteConsumerVendorLink(providername);
                } else {
                    return ContactModel.checkDeleteConsumerFriendLink(providername);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            AppController.hideDialoge();

            if (result) {

                RealmDataDelete.deleteHomeItem(providername, HomeMainFragment.pos);

                AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, HomeMainFragment.pos, true);


            }


        }
    }

    public HomeListFriendsProviderAdapter.Listener mListener = new HomeListFriendsProviderAdapter.Listener() {

        @Override
        public void onButtonClicked(FriendsProviderDT person) {
            refreshAdapters();
        }
    };

    public void refreshAdapters() {
        easyAdapter.notifyDataSetChanged();
        dynamicListView.invalidate();
    }


    private class getFriendsProviderList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (friendsProviderDTs.size() > 0) {
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
                ArrayList<FriendsProviderDT> tempList = new ArrayList<>();
                tempList = AppController.filterFriendsProvider(friendsProviderDTs);
                if (tempList.size() > 0) {
                    // MyLogs.printinfo("tempList " + tempList.size());

                    list_container.setVisibility(View.VISIBLE);
                    easyAdapter = new EasyAdapter<>(
                            activity,
                            HomeListFriendsProviderAdapter.newInstance(activity),
                            tempList, mListener);
                    AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                    animationAdapter.setAbsListView(dynamicListView);
                    dynamicListView.setAdapter(animationAdapter);
                    dynamicListView.setExpanded(true);
                    top_lay.setVisibility(View.GONE);
                    edit_profile.setVisibility(View.VISIBLE);
                    int dpValue = 230; // margin in dips
                    int dpvalue_2 = 10;
                    float d = activity.getResources().getDisplayMetrics().density;
                    int margin = (int) (dpValue * d);
                    int margin_2 = (int) (dpvalue_2 * d);// margin in pixels
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(margin_2, margin, 0, 0);
                    mobile_edit.setLayoutParams(lp);
                    edit_profile.setText("View Detail");

                } else {
                    list_container.setVisibility(View.GONE);
                    top_lay.setVisibility(View.VISIBLE);
                    edit_profile.setText("View Providers");
                }

                edit_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list_container.getVisibility() == View.VISIBLE) {
                            list_container.setVisibility(View.GONE);
                            top_lay.setVisibility(View.VISIBLE);
                            edit_profile.setText("View Providers");

                        } else {
                            list_container.setVisibility(View.VISIBLE);
                            top_lay.setVisibility(View.GONE);
                            edit_profile.setText("View Detail");
                        }
                    }
                });

            } else {


            }

        }

    }
}
