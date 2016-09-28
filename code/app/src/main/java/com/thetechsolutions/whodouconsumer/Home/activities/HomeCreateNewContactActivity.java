package com.thetechsolutions.whodouconsumer.Home.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ContactDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.VendorCategoryDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.VendorProfileDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.Home.adapters.CategoryListAdapter;
import com.thetechsolutions.whodouconsumer.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.countryManager.BaseFlagFragment;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeCreateNewContactActivity extends FragmentActivityController implements MethodGenerator {

    public static Activity activity;
    SimpleDraweeView fresco_view;

    public static int tab_pos, id;
    static String provider_name;

    FormEditText first_name, last_name, cell_no,
            city_state, zip_codes, category, sub_category, email;

    String text_first_name, text_last_name,
            text_city_state, text_zip_codes, text_category, text_sub_category, text_email, provider_user_name;
    EditText edit_phone;

    TextView cat_text, sub_cat_text;

    int catId = 0, subCatId = 0, provider_id = 0;

    VendorProfileDT profileDT;
    LinearLayout country_container;

    public static Intent createIntent(Activity _activity, int _tab_pos, String _provider_name) {
        activity = _activity;
        tab_pos = _tab_pos;
        provider_name = _provider_name;
        return new Intent(activity, HomeCreateNewContactActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, "Create New Contact", true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

      //  MyLogs.printinfo("tab_pos  " + tab_pos);

    }

    @Override
    public void viewInitialize() {
        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);
        first_name = (FormEditText) findViewById(R.id.first_name);
        last_name = (FormEditText) findViewById(R.id.last_name);
        cell_no = (FormEditText) findViewById(R.id.cell_no);
        email = (FormEditText) findViewById(R.id.email);
        city_state = (FormEditText) findViewById(R.id.city_state);
        zip_codes = (FormEditText) findViewById(R.id.zip_codes);
        category = (FormEditText) findViewById(R.id.category);
        sub_category = (FormEditText) findViewById(R.id.sub_category);
        country_container = (LinearLayout) findViewById(R.id.country_container);
        edit_phone = (EditText) findViewById(R.id.phone);

        cat_text = (TextView) findViewById(R.id.cat_text);
        sub_cat_text = (TextView) findViewById(R.id.sub_cat_text);

        if (tab_pos == 1) {
            sub_category.setVisibility(View.GONE);
            category.setVisibility(View.GONE);
            cat_text.setVisibility(View.GONE);
            sub_cat_text.setVisibility(View.GONE);

        }
        // descripton_service = (FormEditText) findViewById(R.id.descripton_service);
        // website = (FormEditText) findViewById(R.id.website);
    }

    @Override
    public void viewUpdate() {
        fresco_view.setImageURI("test.jpg");
        if (!UtilityFunctions.isEmpty(provider_name))
            setProfile();
        else {
            cell_no.setVisibility(View.GONE);
            country_container.setVisibility(View.VISIBLE);
            try {
                BaseFlagFragment.initUI(activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BaseFlagFragment.initCodes(activity);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        setListner();


    }

    private void setProfile() {

        profileDT = RealmDataRetrive.getVendorProfileTemp(provider_name, ContactsMainActivity.tab_pos);
        if (profileDT != null) {
            if (!profileDT.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {

                setDiableState();

            }
            try {
                first_name.setText(profileDT.getFirst_name());
                last_name.setText(profileDT.getLast_name());
            } catch (Exception e) {

            }

            cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, profileDT.getMobile_number_1()));

            try {

                zip_codes.setText("" + profileDT.getZip_code());
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
                city_state.setText(profileDT.getCity() + " " + profileDT.getState());
            } catch (Exception e) {

            }

            try {
                category.setText(profileDT.getCategory_title());
            } catch (Exception e) {

            }
            try {
                sub_category.setText(profileDT.getSub_category_title());
            } catch (Exception e) {

            }
            try {
                fresco_view.setImageURI("" + profileDT.getImage_url());
            } catch (Exception e) {

            }
            try {
                subCatId = profileDT.getSub_category_id();
            } catch (Exception e) {

            }

            try {
                provider_id = profileDT.getId();

            } catch (Exception e) {

            }

            try {
                provider_user_name = profileDT.getUsername();
            } catch (Exception e) {

            }


        } else {
            ContactDT contactDT = RealmDataRetrive.getContactDetail(provider_name);

            try {
                first_name.setText(contactDT.getFirstname());
                last_name.setText(contactDT.getLastname());
            } catch (Exception e) {

            }
            cell_no.setText(UtilityFunctions.getFormattedNumberToDisplay(activity, provider_name));
        }
    }

    private void setDiableState() {

        email.setEnabled(false);
        city_state.setEnabled(false);
        zip_codes.setEnabled(false);
        category.setEnabled(false);
        sub_category.setEnabled(false);


        email.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        city_state.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        zip_codes.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        category.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));
        sub_category.setTextColor(activity.getResources().getColor(R.color.who_do_u_light_grey));

    }

    private void setListner() {
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryListDialogue(activity, false, category, sub_category);
            }
        });

        sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryListDialogue(activity, true, category, sub_category);
            }
        });

    }

    public void callCategoryListDialogue(Activity activity, final boolean isSubCategory,
                                         final FormEditText cat_view, final FormEditText sub_cat_view) {


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

        final RealmResults<VendorCategoryDT> subcatList = RealmDataRetrive.getSubCategoryList(catId);
        final RealmResults<VendorCategoryDT> catList = RealmDataRetrive.getCategoryList(0);
        if (isSubCategory) {
            title.setText("Select a sub category");
            easyAdapter = new EasyAdapter<>(
                    activity,
                    CategoryListAdapter.newInstance(isSubCategory),
                    subcatList);
        } else {
            title.setText("Select a category");

            easyAdapter = new EasyAdapter<>(
                    activity,
                    CategoryListAdapter.newInstance(isSubCategory),
                    catList);
        }


        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                categoryDialoge.dismiss();

                if (isSubCategory) {

                    sub_cat_view.setText(subcatList.get(position).getSubcategory());
                    subCatId = (subcatList.get(position).getSubcategory_id());

                    if (catId == 0) {
                        catId = (RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory_id());
                        cat_view.setText(RealmDataRetrive.getCategoryList(subCatId).get(0).getCategory());
                    }
                } else {
                    catId = (catList.get(position).getCategory_id());
                    cat_view.setText(catList.get(position).getCategory());

                    sub_cat_view.setText("");
                    subCatId = 0;
                }


            }
        });


    }

    public void validatorCode() {
        if (UtilityFunctions.isEmpty(provider_name)) {
            cell_no.setText(edit_phone.getText().toString());
        }
        boolean allValid = true;
        if (tab_pos == 1) {

        } else {
            FormEditText[] allFields = {first_name, cell_no};

            for (FormEditText field : allFields) {
                allValid = field.testValidity() && allValid;
            }
        }


        if (allValid) {

            if (subCatId == 0 && tab_pos == 0) {
                AppController.showToast(activity, "Sub Category is empty");

            } else {
                text_first_name = first_name.getText().toString();
                text_last_name = last_name.getText().toString();
                text_zip_codes = zip_codes.getText().toString();
                text_category = category.getText().toString();
                text_sub_category = sub_category.getText().toString();
                text_email = email.getText().toString();
                text_city_state = city_state.getText().toString();

                if (UtilityFunctions.isEmpty(provider_name)) {

                    provider_name = UtilityFunctions.getstandarizeNumber(edit_phone.getText().toString(), activity);
                    new createVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else {


                    if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_NEW)) {

                        new createVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_REGISTERED)) {

                        new createLink().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    } else if (AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED).equals(AppConstants.USER_NOT_ON_APP)) {

                        try{
                            if (profileDT.getCreated_by().equals(AppPreferences.getString(AppPreferences.PREF_USER_NUMBER))) {

                                new updateVendor().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            } else {

                                new createLink().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            }
                        }catch (Exception e){

                        }

                    }
                }
            }


        }


    }


    public class createVendor extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                HomeMainController.createProvider(provider_name, text_first_name, text_last_name, "", text_city_state, "", "", text_email,
                        text_zip_codes, subCatId + "", AppPreferences.getString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED)


                );

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

//                MyLogs.printinfo("success");

                onSuccessHandling();

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    public class createLink extends AsyncTask<String, Void, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                HomeMainController.createLink("" + provider_id, text_first_name, text_last_name, provider_user_name


                );

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

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
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

                HomeMainController.updateProvider(provider_name, text_first_name, text_last_name, "", text_city_state, "", "", text_email,
                        text_zip_codes, subCatId + "", tab_pos);


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

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    private void onSuccessHandling() {
        try {
            AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, tab_pos, true);
        } catch (Exception e) {

        }


//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", "ok");
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();

    }


}
