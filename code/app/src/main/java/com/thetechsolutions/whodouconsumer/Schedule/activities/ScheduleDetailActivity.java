package com.thetechsolutions.whodouconsumer.Schedule.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kyleduo.switchbutton.SwitchButton;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouconsumer.R;
import com.thetechsolutions.whodouconsumer.Schedule.adapters.DurationListAdapter;
import com.thetechsolutions.whodouconsumer.Schedule.controller.ScheduleController;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.Calendar;

import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ScheduleDetailActivity extends FragmentActivityController implements MethodGenerator,
        View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    static Activity activity;

    TextView title_name, location_name;
    SimpleDraweeView fresco_view;
    TextView appointment_date, duration;
    Button accept_btn, reschedule_btn, decline_btn;
    static int id, tab_position;
    RelativeLayout switch_button_container;
    AutoCompleteTextView auto_com_cutomer_name;
    RelativeLayout item_container;
    static String title, providerUserName;
    SwitchButton switch_button;
    String selectedDateTime, appointmentId, appointmentStatus;
    EditText description;

    long calId = 0;

    String vendorId;
    String sqlDateTime;
    String callMessgae = "1";
    String durationS = "";


    public static Intent createIntent(Activity _activity, int _id, int _tab_pos, String _title, String _providerUserName) {
        activity = _activity;
        id = _id;
        tab_position = _tab_pos;
        title = _title;
        providerUserName = _providerUserName;
        return new Intent(activity, ScheduleDetailActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, title, true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
        title_name = (TextView) findViewById(R.id.title_name);
        location_name = (TextView) findViewById(R.id.address);
        appointment_date = (TextView) findViewById(R.id.appointment_date);
        duration = (TextView) findViewById(R.id.duration);
        description = (EditText) findViewById(R.id.description);

        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);

        accept_btn = (Button) findViewById(R.id.accept_btn);
        reschedule_btn = (Button) findViewById(R.id.reschedule_btn);
        decline_btn = (Button) findViewById(R.id.decline_btn);
        switch_button_container = (RelativeLayout) findViewById(R.id.switch_button_container);

        auto_com_cutomer_name = (AutoCompleteTextView) findViewById(R.id.auto_com_cutomer_name);
        item_container = (RelativeLayout) findViewById(R.id.item_container);
        switch_button = (SwitchButton) findViewById(R.id.switch_button);

        accept_btn.setOnClickListener(this);
        reschedule_btn.setOnClickListener(this);
        decline_btn.setOnClickListener(this);
        appointment_date.setOnClickListener(this);
        duration.setOnClickListener(this);

    }

    @Override
    public void viewUpdate() {


        if (id != 0) {
            ScheduleDT item_detail = RealmDataRetrive.getScheduleDetail(id);
            if (item_detail.getCall_message() == 1) {
                switch_button.setChecked(true);
            } else {
                switch_button.setChecked(false);
            }

            title_name.setText(item_detail.getVendor_name());
            location_name.setText("");
            appointment_date.setText(item_detail.getAppointmentDate() + " @ " + UtilityFunctions.formatteSqlTime(UtilityFunctions.converMillisToDate(item_detail.getAppointment_date_time(), "yyyy-MM-dd HH:mm:ss")));
            duration.setText(item_detail.getEstimated_duration() + "");
            fresco_view.setImageURI(Uri.parse(item_detail.getSub_category_image_url()));
            description.setText(item_detail.getDescription());
            appointmentId = "" + item_detail.getAppointment_id();
            appointmentStatus = item_detail.getStatus();
            vendorId = "" + item_detail.getVendor_id();
            sqlDateTime = UtilityFunctions.converMillisToDate(item_detail.getAppointment_date_time(), "yyyy-MM-dd HH:mm:ss");
            durationS = item_detail.getEstimated_duration();
            try {
                calId = Long.parseLong(item_detail.getCalendar_guid());
            } catch (Exception e) {

            }


        }

        if (tab_position == 1) {
            accept_btn.setVisibility(View.GONE);
            decline_btn.setVisibility(View.GONE);
            reschedule_btn.setVisibility(View.GONE);
            switch_button_container.setVisibility(View.GONE);
            appointment_date.setEnabled(false);
            description.setEnabled(false);
            duration.setEnabled(false);

        } else if (tab_position == 2) {
            auto_com_cutomer_name.setVisibility(View.VISIBLE);
            item_container.setVisibility(View.GONE);
            reschedule_btn.setVisibility(View.GONE);
            accept_btn.setVisibility(View.VISIBLE);
            accept_btn.setText("Submit");

            if (!UtilityFunctions.isEmpty(providerUserName)) {
                ProviderDT item = RealmDataRetrive.getHomeItemDetail(providerUserName, 0);
                auto_com_cutomer_name.setText("" + item.getFirst_name() + " " + item.getLast_name());
                vendorId = "" + item.getId();
                auto_com_cutomer_name.setEnabled(false);
            }
            //   final String[] names = new String[]{"Ricky", "Aubery", "David"};
            ArrayList<String> providers_name = new ArrayList<>();
            for (ProviderDT item : RealmDataRetrive.getHomeList(0)) {
                providers_name.add(item.getFirst_name() + " " + item.getLast_name());
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    activity,
                    R.layout.item_auto_complete,
                    R.id.suggested_text,
                    providers_name);
            auto_com_cutomer_name.setAdapter(adapter);
            auto_com_cutomer_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> view, View arg1, int position, long arg3) {
                    vendorId = "" + RealmDataRetrive.getHomeList(0).get(position).getId();

                }
            });
        }

        if (switch_button.isChecked()) {
            switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_green)));

        } else {
            switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_red)));

        }

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_green)));
                    callMessgae = "1";
                } else {
                    switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_red)));
                    callMessgae = "0";
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept_btn:
                validator(false);
                break;
            case R.id.reschedule_btn:
                validator(true);
                break;
            case R.id.decline_btn:
                break;
            case R.id.duration:
                callHoursDialogue(activity);
                break;
            case R.id.appointment_date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(activity.getResources().getColor(R.color.who_do_u_blue));
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        sqlDateTime = year + "-" + (Integer.parseInt("" + monthOfYear) + 1) + "-" + dayOfMonth;
        selectedDateTime = UtilityFunctions.getMonthFromInt(monthOfYear + 1) + " " + UtilityFunctions.ordinal(dayOfMonth) + ", " + year;
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE), false);
        tpd.setAccentColor(activity.getResources().getColor(R.color.who_do_u_blue));
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String amPm = "";
        if (view.getIsCurrentlyAmOrPm() == 1) {
            amPm = "PM";
        } else {
            amPm = "AM";
        }
        if (minute < 10) {
            selectedDateTime += "  " + hourOfDay + ":0" + minute + " " + amPm;
        } else {
            selectedDateTime += "  " + hourOfDay + ":" + minute + " " + amPm;
        }
        sqlDateTime += " " + hourOfDay + ":" + minute + ":" + second;
        appointment_date.setText(selectedDateTime);
    }

    public void callHoursDialogue(final Activity activity) {
        final Dialog categoryDialoge = new Dialog(activity);
        categoryDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        categoryDialoge.setContentView(R.layout.dialoge_listview);
        categoryDialoge.show();
        Window window = categoryDialoge.getWindow();
        window.setLayout(AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH) - 100, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 1000);
        DynamicListView listView = (DynamicListView) categoryDialoge.findViewById(R.id.listview);
        RelativeLayout loadingContainer = (RelativeLayout) categoryDialoge.findViewById(R.id.loading_container);
        TextView title = (TextView) categoryDialoge.findViewById(R.id.title_cat);

        loadingContainer.setVisibility(View.GONE);
        EasyAdapter easyAdapter;


//        final RealmResults<SearchInDT> catList = RealmDataRetrive.getSearchInList(true);

        final ArrayList<String> list = new ArrayList<>();
        list.add("30 minutes");
        list.add("1 hour");
        list.add("2 hours");
        list.add("3 hours");
        list.add("4 hours");
        list.add("5 hours");
        list.add("6 hours");
        list.add("1 day");
        list.add("2 days");
        list.add("3 days");
        list.add("4 days");
        list.add("5 days");
        list.add("1 week");
        list.add("2 weeks");
        list.add("1 month");
        title.setText(" Please Select Appointment Duration.");

        easyAdapter = new EasyAdapter<>(
                activity,
                DurationListAdapter.newInstance(),
                list);


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                durationS = list.get(position);
                duration.setText(durationS);
                categoryDialoge.dismiss();


            }
        });


    }

    private void validator(boolean isUpdate) {
        // MyLogs.printinfo("sqlDatetime " + sqlDateTime);
        if (UtilityFunctions.isEmpty(vendorId)) {
            UtilityFunctions.showToast_onCenter("Please select a Provider", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(sqlDateTime)) {
            UtilityFunctions.showToast_onCenter("Please select Appointment Date Time", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(durationS)) {
            durationS = "30 minutes";
        }


        try {
            calId = 0;
            //if (!isUpdate)
            // calId = UtilityFunctions.addEvent(activity, sqlDateTime, "", 1);
        } catch (Exception e) {

        }

        if (isUpdate) {

            ScheduleController.getInstance().updateAppointments(activity, appointmentId, sqlDateTime, durationS, description.getText().toString(), appointmentStatus, "" + calId);

        } else {
            ScheduleController.getInstance().createAppointments(activity, vendorId, sqlDateTime, durationS, description.getText().toString(), callMessgae, "" + calId);

        }

    }

}
