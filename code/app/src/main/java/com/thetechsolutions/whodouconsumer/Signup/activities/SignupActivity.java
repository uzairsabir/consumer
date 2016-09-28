package com.thetechsolutions.whodouconsumer.Signup.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.andreabaccega.widget.FormEditText;
import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProfileDT;
import com.thetechsolutions.whodouconsumer.HomeScreenActivity;
import com.thetechsolutions.whodouconsumer.R;
import com.thetechsolutions.whodouconsumer.Signup.controllers.SignUpAsynController;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.countryManager.BaseFlagFragment;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.PermissionHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.HashMap;
import java.util.Map;

import eu.siacs.conversations.ui.MagicCreateActivity;


/**
 * Created by Uzair on 12/6/2015.
 */

public class SignupActivity extends MagicCreateActivity implements View.OnClickListener {
    private static final int TIME_DELAY = 2000;
    public static boolean isRegisterationScreen, isCodeInputScreen;
    private static long back_pressed;
    Activity activity;
    EditText _edit_phone;

    Dialog workDialog;

    RelativeLayout bottomLay;
    LinearLayout country_container;
    String inputPhoneNumber, fName, lName, email, code1, code2, code3, code4;
    int zipCode;

    Button signup_btn;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SignupActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_number);
        activity = this;
        viewsInitialize();
        // addListeners();
        viewHandler();

        try {
            getActionBar().hide();
        } catch (Exception e) {

        }


    }

    private void viewHandler() {

        if (isRegisterationScreen) {
            callRegisterationDialog(activity);
        } else if (isCodeInputScreen) {
            callInputCodeDialog(activity);
        }

    }

    private void viewsInitialize() {

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

        bottomLay = (RelativeLayout) findViewById(R.id.bottom_lay);
        bottomLay.setVisibility(View.GONE);
        country_container = (LinearLayout) findViewById(R.id.country_container);
        _edit_phone = (EditText) findViewById(R.id.phone);
        signup_btn = (Button) findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {

        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
            try {
                HomeScreenActivity.activity.finish();


            } catch (Exception e) {

            }

        } else {
            UtilityFunctions.showToast_onCenter("Press once again to exit", activity);

        }
        back_pressed = System.currentTimeMillis();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.signup_btn:

                inputNumberValidator();


                break;


            default:
                break;
        }
    }

    public void callSignUpAsyn(String signUpType) {
        if (UtilityFunctions.checkInternet(activity)) {
            new SignUpAsync(activity, signUpType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void callRegisterationDialog(final Activity activity) {

        isRegisterationScreen = true;
        workDialog = new Dialog(activity);
        workDialog.setCancelable(false);
        workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        workDialog.setContentView(R.layout.dialoge_signup_registeration);

        final FormEditText first_Name = (FormEditText) workDialog.findViewById(R.id.first_name);
        final FormEditText last_Name = (FormEditText) workDialog.findViewById(R.id.last_name);
        final FormEditText email_address = (FormEditText) workDialog.findViewById(R.id.email_name);
        final FormEditText zip_Code = (FormEditText) workDialog.findViewById(R.id.zip_code);

//        categorySelectedText = (TextView) workDialog.findViewById(R.id.categorylist);
//        subcategorylist = (TextView) workDialog.findViewById(R.id.subcategorylist);


        Window window = workDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) - 600);


        workDialog.show();


        Button declineButton = (Button) workDialog.findViewById(R.id.cancel_btn);

        final Button acceptButton = (Button) workDialog.findViewById(R.id.signup_btn);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fName = first_Name.getText().toString();
                    lName = last_Name.getText().toString();
                    email = email_address.getText().toString();
                    zipCode = Integer.parseInt(zip_Code.getText().toString());
                } catch (Exception e) {

                }


                validatorReg(first_Name, email_address, zip_Code);
                isRegisterationScreen = false;


            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDialog.dismiss();
                isRegisterationScreen = false;
            }
        });

        if (AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_NOT_ON_APP)) {
            ProfileDT profileDTs = RealmDataRetrive.getProfile();
            if (profileDTs != null) {
                first_Name.setText(profileDTs.getFirst_name());
                last_Name.setText(profileDTs.getLast_name());
                email_address.setText(profileDTs.getEmail_1());
                try {
                    zip_Code.setText(profileDTs.getZip_code());
                } catch (Exception e) {

                }


            }

        }


    }

    public void callInputCodeDialog(final Activity activity) {

        workDialog = new Dialog(activity);
        isCodeInputScreen = true;
        workDialog.setCancelable(false);

        workDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        workDialog.setContentView(R.layout.dialoge_signup_input_code);


        final FormEditText code_1 = (FormEditText) workDialog.findViewById(R.id.code_one);
        final FormEditText code_2 = (FormEditText) workDialog.findViewById(R.id.code_two);
        final FormEditText code_3 = (FormEditText) workDialog.findViewById(R.id.code_three);
        final FormEditText code_4 = (FormEditText) workDialog.findViewById(R.id.code_four);

        code_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code_3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code_4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    UtilityFunctions.hideKeyboard(activity);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        workDialog.show();
        Window window = workDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button declineButton = (Button) workDialog.findViewById(R.id.cancel_btn);

        final Button acceptButton = (Button) workDialog.findViewById(R.id.signup_btn);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatorCode(code_1, code_2, code_3, code_4);
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workDialog.dismiss();
                isCodeInputScreen = false;
            }
        });


    }

    public class SignUpAsync extends AsyncTask<String, Void, Integer> {

        Activity _activity;
        String signup_type;

        public SignUpAsync(Activity activity, String _signup_type) {
            _activity = activity;
            signup_type = _signup_type;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);
            try {
                workDialog.dismiss();
            } catch (Exception e) {

            }

        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_NUMBER_VERIFICATION)) {

                    if (SignUpAsynController.getInstance().callUserExistence(inputPhoneNumber)) {
                        return 0;
                    }


                } else if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_REGISTRATION)) {


                    if (SignUpAsynController.getInstance().callRegisteration(email, fName, lName, zipCode))
                        return 1;

                } else if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_CODE_VERIFICATION)) {
                    if (SignUpAsynController.getInstance().callCodeVerification(activity, code1 + code2 + code3 + code4))
                        return 2;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            AppController.hideDialoge();
            try {
                workDialog.dismiss();
            } catch (Exception e) {

            }

            if (!NetworkManager.isConnected(activity)) {
                AppController.showToast(activity, "No Internet Connectivity");
            } else {

                if (result == 0) {

                    if (AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_NEW) ||
                            AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_NOT_ON_APP)) {


                        callRegisterationDialog(activity);
                    } else if (AppPreferences.getString(AppPreferences.PREF_USER_STATUS).equals(AppConstants.USER_REGISTERED)) {
                        callInputCodeDialog(activity);
                    }

                } else if (result == 1) {

                    callInputCodeDialog(activity);

                } else if (result == 2) {
                    MyLogs.printinfo("verified " + RealmDataRetrive.getProfile().getUsername() + "_c");

                    try {
                        createXmppConnection(RealmDataRetrive.getProfile().getUsername() + "_c", "1234");
                    } catch (Exception e) {

                    }


                    if (PermissionHandler.allPermissionsHandler(activity)) {

                        AppPreferences.setBoolean(AppPreferences.PREF_REGISTERATION_DONE, true);
                        AppPreferences.setBoolean(AppPreferences.PREF_IS_SIGNUP_DONE, true);
                        ListenerController.openInitProgressActivity(activity);
                        activity.finish();

                    }
                } else {

                    if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_NUMBER_VERIFICATION) || signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_REGISTRATION)) {
                        AppController.showToast(activity, "Something went wrong!");

                    } else if (signup_type.equals(AppConstants.SIGNUP_FUNCTION_TYPE_CODE_VERIFICATION)) {
                        AppController.showToast(activity, "Please enter correct code");
                        callInputCodeDialog(activity);

                    }

                }

            }
        }


    }


    private void inputNumberValidator() {
        MyLogs.printinfo("country_code " + AppPreferences.getString(AppPreferences.PREF_USER_COUNTRY_CODE));
        inputPhoneNumber = _edit_phone.getText().toString();


        if (UtilityFunctions.isEmpty(inputPhoneNumber)) {
            UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_filled), activity);
            return;
        }
        if (!UtilityFunctions.isContainSpaces(inputPhoneNumber)) {
            UtilityFunctions.showToast_onCenter(activity.getString(R.string.please_format_number), activity);
            return;
        }
        inputPhoneNumber = UtilityFunctions.getstandarizeNumber(inputPhoneNumber, activity);
        new SignUpAsync(activity, AppConstants.SIGNUP_FUNCTION_TYPE_NUMBER_VERIFICATION).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionHandler.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);


                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

                    try {

                        AppPreferences.setBoolean(AppPreferences.PREF_REGISTERATION_DONE, true);
                        AppPreferences.setBoolean(AppPreferences.PREF_IS_SIGNUP_DONE, true);
                        ListenerController.openInitProgressActivity(activity);
                        activity.finish();

                    } catch (Exception e) {


                    }

                } else {

                    UtilityFunctions.showToast_onCenter("Some Permission is Denied", activity);

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void validatorReg(FormEditText firstName, FormEditText email, FormEditText zip_code) {
        FormEditText[] allFields;

        allFields = new FormEditText[]{firstName, email, zip_code};


        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {

            callSignUpAsyn(AppConstants.SIGNUP_FUNCTION_TYPE_REGISTRATION);

        }

    }

    private void validatorCode(FormEditText _code1, FormEditText _code2, FormEditText _code3, FormEditText _code4) {
        FormEditText[] allFields = {_code1, _code2, _code3, _code4};
        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        code1 = _code1.getText().toString();
        code2 = _code2.getText().toString();
        code3 = _code3.getText().toString();
        code4 = _code4.getText().toString();

        if (allValid) {
            callSignUpAsyn(AppConstants.SIGNUP_FUNCTION_TYPE_CODE_VERIFICATION);
        } else {

        }


    }


}
