package com.thetechsolutions.whodouconsumer.Home.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouconsumer.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

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
    Button save_btn;

    static int tab_pos, subCatId;
    static String providerName;
    String text_first_name, text_last_name,
            text_city_state, text_zip_codes, text_email;


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

        //MyLogs.printinfo("tab_pos " + tab_pos);

        if (HomeMainFragment.pos == 1) {
            calendar_icon.setVisibility(View.GONE);
            service_name.setVisibility(View.GONE);
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
                ProviderDT item_detail = RealmDataRetrive.getHomeItemDetail(providerName, HomeMainFragment.pos);
                text_first_name = item_detail.getFirst_name();
                text_last_name = item_detail.getLast_name();
                text_city_state = city_state.getText().toString();
                text_email = email.getText().toString();
                text_zip_codes = zip_code.getText().toString();
                subCatId = item_detail.getSub_category_id();

                new updateVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }
        });
    }

    @Override
    public void viewUpdate() {
        // MyLogs.printinfo("userName " + providerName + " : " + tab_pos);
        final ProviderDT item_detail = RealmDataRetrive.getHomeItemDetail(providerName, HomeMainFragment.pos);
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
                        text_zip_codes, subCatId + "", HomeMainFragment.pos);


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


}
