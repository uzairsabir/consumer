package com.thetechsolutions.whodouconsumer.Pay.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kyleduo.switchbutton.SwitchButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.thetechsolutions.whodouconsumer.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.PayDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.Pay.controller.PayController;
import com.thetechsolutions.whodouconsumer.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import eu.siacs.conversations.Config;
import eu.siacs.conversations.entities.Account;
import eu.siacs.conversations.entities.Contact;
import eu.siacs.conversations.entities.Conversation;
import eu.siacs.conversations.entities.Message;
import eu.siacs.conversations.services.XmppConnectionService;
import eu.siacs.conversations.ui.XmppActivity;
import eu.siacs.conversations.xmpp.jid.InvalidJidException;
import eu.siacs.conversations.xmpp.jid.Jid;
import io.realm.RealmResults;

/**
 * Created by Uzair on 7/12/2016.
 */
public class PayDetailActivity extends XmppActivity implements MethodGenerator, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, XmppConnectionService.OnShowErrorToast {

    static Activity activity;

    TextView title_name, service_name, location_name, default_gateway, receipt, service_date;
    SimpleDraweeView fresco_view;
    EditText amount, description;
    SwitchButton switch_button;
    Button payment_btn, mark_pay_btn;
    static int id, tab_pos;
    RelativeLayout top_item;
    AutoCompleteTextView auto_com_cutomer_name;
    static String title, providerUserName, contact_number;
    MaterialSpinner spinner;
    String selectedDateTime;
    String vendorId;
    String sqlDateTime;
    String callMessgae = "1";
    String paymentId;
    ImageView call_icon, chat_icon;
    String vendor_paypal_id = "";

    public static Intent createIntent(Activity _activity, int _id, int _tab_pos, String _title, String _providerUserName) {
        activity = _activity;
        id = _id;
        title = _title;
        tab_pos = _tab_pos;
        providerUserName = _providerUserName;
        return new Intent(activity, PayDetailActivity.class);

    }

    @Override
    protected void refreshUiReal() {

    }

    @Override
    protected void onBackendConnected() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        activity = this;
        TitleBarController.getInstance(activity).setTitleBar(activity, title, true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();
        try {
            getActionBar().hide();
        } catch (Exception e) {

        }

    }

    @Override
    public void viewInitialize() {
        title_name = (TextView) findViewById(R.id.title_name);
        service_name = (TextView) findViewById(R.id.service_name);
        location_name = (TextView) findViewById(R.id.address);
        amount = (EditText) findViewById(R.id.amount);
        service_date = (TextView) findViewById(R.id.service_date);
        description = (EditText) findViewById(R.id.description);
        default_gateway = (TextView) findViewById(R.id.default_gateway);
        call_icon = (ImageView) findViewById(R.id.call_icon);
        chat_icon = (ImageView) findViewById(R.id.chat_icon);
        fresco_view = (SimpleDraweeView) findViewById(R.id.fresco_view);
        switch_button = (SwitchButton) findViewById(R.id.switch_button);
        payment_btn = (Button) findViewById(R.id.payment_btn);
        mark_pay_btn = (Button) findViewById(R.id.mark_pay_btn);
        top_item = (RelativeLayout) findViewById(R.id.top_item);
        payment_btn.setOnClickListener(this);
        mark_pay_btn.setOnClickListener(this);

        auto_com_cutomer_name = (AutoCompleteTextView) findViewById(R.id.auto_com_cutomer_name);
        receipt = (TextView) findViewById(R.id.receipt);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);

        // MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);


        spinner.setItems("Paid", "PayPal", "Card");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

        service_date.setOnClickListener(this);

        AppController.initLibrary(activity);


        // search_view = (MaterialSearchView) findViewById(R.id.search_view);


    }

    @Override
    public void viewUpdate() {

        if (id == 0) {
            auto_com_cutomer_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        MyLogs.printinfo("has_got_focus");
                        auto_com_cutomer_name.showDropDown();

                    }
                }
            });

            top_item.setVisibility(View.GONE);
            auto_com_cutomer_name.setVisibility(View.VISIBLE);

            //  final String[] names = new String[]{"Ricky", "Aubery", "David"};
            ArrayList<String> providers_name = new ArrayList<>();
            for (ProviderDT item : RealmDataRetrive.getProvider()) {
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
                    String selected = (String) view.getItemAtPosition(position);
                    int pos = -1;
                    RealmResults<ProviderDT> arrayList = RealmDataRetrive.getProvider();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if ((arrayList.get(i).getFirst_name() + " " + arrayList.get(i).getLast_name()).equals(selected)) {
                            pos = i;

                            vendorId = "" + arrayList.get(i).getId();
                            contact_number = arrayList.get(i).getUsername();
                            vendor_paypal_id = arrayList.get(i).getPaypal_id();
                            break;
                        }
                    }

                }
            });
            if (!UtilityFunctions.isEmpty(providerUserName)) {
                ProviderDT item = RealmDataRetrive.getProviderDetail(providerUserName, 0);
                auto_com_cutomer_name.setText("" + item.getFirst_name() + " " + item.getLast_name());
                vendorId = "" + item.getId();
                auto_com_cutomer_name.setEnabled(false);
                contact_number = providerUserName;
                vendor_paypal_id = item.getPaypal_id();


            }

        } else {

            final PayDT item_detail = RealmDataRetrive.getPayDetail(id);

            title_name.setText(item_detail.getVendor_name());
            service_name.setText(item_detail.getSub_categor_title());
            // location_name.setText("");
            description.setText(item_detail.getDescription());
            amount.setText(item_detail.getAmount());
            service_date.setText(item_detail.getDateToDisplay());
            fresco_view.setImageURI(Uri.parse(item_detail.getVendor_image_url()));
            vendorId = "" + item_detail.getVendor_id();
            sqlDateTime = UtilityFunctions.converMillisToDate(item_detail.getService_date(), "yyyy-MM-dd HH:mm:ss");
            paymentId = "" + item_detail.getId();
            if (item_detail.getRequest_receipt().equals("1")) {
                switch_button.setChecked(true);
            } else {
                switch_button.setChecked(false);
            }

            contact_number = item_detail.getVendor_username();

            call_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UtilityFunctions.directCall(activity, item_detail.getVendor_username());
                }
            });

            chat_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //AppController.openChat(activity, item_detail.getConsumer_username(), item_detail.getConsumer_name(), item_detail.getSub_category_image_url(), item_detail.getis, 0);

                }
            });
            try {
                //MyLogs.printinfo("item_detail " + item_detail.getVendor_username());
                contact_number = item_detail.getVendor_username();
            } catch (Exception e) {

            }
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
        if (tab_pos == 1) {
            switch_button.setVisibility(View.GONE);
            payment_btn.setVisibility(View.GONE);
            receipt.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            amount.setEnabled(false);
            service_date.setEnabled(false);
            description.setEnabled(false);
        } else if (tab_pos == 0) {
            amount.setEnabled(false);


        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payment_btn:
                validator(false);
                break;
            case R.id.mark_pay_btn:
                validator(true);
                break;
            case R.id.service_date:
                // MyLogs.printinfo("appointment_date");
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
        //selectedDateTime = UtilityFunctions.ordinal(dayOfMonth) + " " + UtilityFunctions.getMonthFromInt(monthOfYear + 1) + ", " + year;
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

        service_date.setText(selectedDateTime);

    }

    private void validator(boolean markAsPaid) {
        // MyLogs.printinfo("sqlDatetime " + sqlDateTime);
        if (UtilityFunctions.isEmpty(vendorId)) {
            UtilityFunctions.showToast_onCenter("Please select a Provider", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(sqlDateTime)) {
            UtilityFunctions.showToast_onCenter("Please select Service Date Time", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(amount.getText().toString())) {
            UtilityFunctions.showToast_onCenter("Please enter amount", activity);
            return;
        }
        if (UtilityFunctions.isEmpty(vendor_paypal_id) || vendor_paypal_id == null) {
            UtilityFunctions.showToast_onCenter("vendor has not been setup its paypal account", activity);
            return;
        }

        if (markAsPaid) {
            markAsPaid();
        } else {

            testPayment();
        }

//        if (spinner.getSelectedIndex() == 2) {
//
//        } else if (spinner.getSelectedIndex() == 1) {
////            new Runnable() {
////                public void run() {
////
////                }
////            };
//
//            testPayment();
//
//
//        } else {
//            markAsPaid();
//        }


    }

    @Override
    public void onShowErrorToast(int resId) {

    }

    public void sendMessage(String contactNumber, String message) {
        String accountNumber = AppPreferences.getString(AppPreferences.PREF_USER_NUMBER) + "_c";
        Log.e("prefilledJid ", " " + accountNumber + " " + contactNumber);

        Jid accountJid = null;
        try {

            accountJid = Jid.fromString(accountNumber + "@" + Config.MAGIC_CREATE_DOMAIN);

        } catch (InvalidJidException e) {
            e.printStackTrace();
        }
        Jid contactJid = null;
        try {
            contactJid = Jid.fromString(contactNumber + "@" + Config.MAGIC_CREATE_DOMAIN);
        } catch (InvalidJidException e) {
            e.printStackTrace();
        }
        if (!xmppConnectionServiceBound) {
            return;
        }

        final Account account = xmppConnectionService.findAccountByJid(accountJid);
        if (account == null) {
            return;
        }

        try {
            final Contact contact = account.getRoster().getContact(contactJid);
            Conversation conversation = null;
            if (contact.showInRoster()) {

                conversation = xmppConnectionService
                        .findOrCreateConversation(contact.getAccount(),
                                contact.getJid(), false);


            } else {
                xmppConnectionService.createContact(contact);
                //    switchToConversation(contact);

                conversation = xmppConnectionService
                        .findOrCreateConversation(contact.getAccount(),
                                contact.getJid(), false);
                ///  switchToConversation(conversation);


            }
            sendMessage(conversation, message);
        } catch (Exception e) {

        }

        return;


    }

    private void sendMessage(Conversation conversation, String messagea) {
        final String body = messagea;
        if (body.length() == 0 || conversation == null) {
            return;
        }
        final Message message;
        if (conversation.getCorrectingMessage() == null) {
            message = new Message(conversation, body, conversation.getNextEncryption());
            if (conversation.getMode() == Conversation.MODE_MULTI) {
                if (conversation.getNextCounterpart() != null) {
                    message.setCounterpart(conversation.getNextCounterpart());
                    message.setType(Message.TYPE_PRIVATE);
                }
            }
        } else {
            message = conversation.getCorrectingMessage();
            message.setBody(body);
            message.setEdited(message.getUuid());
            message.setUuid(UUID.randomUUID().toString());
            conversation.setCorrectingMessage(null);
        }
        xmppConnectionService.sendMessage(message);

    }
//
//    private void payUsingPayPal() {
//        PayPalPayment newPayment = new
//                PayPalPayment();
//        newPayment.setSubtotal(BigDecimal.valueOf(10.f));
//        newPayment.setCurrencyType("USD");
//        newPayment.setRecipient("my@email.com");
//        newPayment.setMerchantName("My Company");
//
////        PayPalReceiverDetails vendor = new PayPalReceiverDetails();
////        PayPalReceiverDetails app_owner = new PayPalReceiverDetails();
////
////        PayPalAdvancedPayment advPayment = new PayPalAdvancedPayment();
////        advPayment.setCurrencyType("USD");
////
////        vendor.setSubtotal(BigDecimal.valueOf(9.f));
////        app_owner.setSubtotal(BigDecimal.valueOf(1.f));
////        ArrayList<PayPalReceiverDetails> list = new ArrayList<>();
////        list.add(vendor);
////        list.add(app_owner);
////        advPayment.setReceivers(list);
//
//        // advPayment.getReceivers().add(vendor);
//        //advPayment.getReceivers().add(app_owner);
//
//
//        Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
//        this.startActivityForResult(paypalIntent, 1);
//    }

    private void testPayment() {
        double secondary_payment = 0.5;
        double primary_payment = Double.parseDouble(amount.getText().toString());

        //"r.alirizvi-Vendor@gmail.com"
        //    PayPalAdvancedPayment advPayment = makeChainedPayment(secondary_payment, primary_payment, "vendor@gmail.com", "uzair92ssuet92@gmail.com", title_name.getText().toString());

        MyLogs.printinfo("vendor_paypal" + vendor_paypal_id);
        PayPalAdvancedPayment advPayment = makeChainedPayment(secondary_payment, primary_payment, vendor_paypal_id, AppConstants.PAYPAL_OWNER_EMAIL, auto_com_cutomer_name.getText().toString());


        Intent checkoutIntent = PayPal.getInstance().checkout(advPayment, activity);
        startActivityForResult(checkoutIntent, 1);
    }

    private PayPalAdvancedPayment makeChainedPayment(double priceSecondary, double pricePrimary,
                                                     String primary_email, String secondary_email, String vendorName) {
        PayPalAdvancedPayment payment = new PayPalAdvancedPayment();
        payment.setCurrencyType("USD");
        //        payment.setMerchantName("PushND");
        BigDecimal bigDecimalPrimary = new BigDecimal(pricePrimary);

        PayPalReceiverDetails receiverPrimary = new PayPalReceiverDetails();
        receiverPrimary.setRecipient(primary_email);
        receiverPrimary.setMerchantName(vendorName);
        //receiverPrimary.setRecipient("adaptive_receiver_1@pushnd.com");
        receiverPrimary.setSubtotal(bigDecimalPrimary);
        receiverPrimary.setIsPrimary(true);
        payment.getReceivers().add(receiverPrimary);

        PayPalReceiverDetails receiverSecondary = new PayPalReceiverDetails();
        receiverSecondary.setRecipient(secondary_email);
        receiverSecondary.setMerchantName("Who do u");

        BigDecimal bigDecimalSecond = new BigDecimal(priceSecondary);
        receiverSecondary.setSubtotal(bigDecimalSecond);
        payment.getReceivers().add(receiverSecondary);


        return payment;
        // }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                markAsPaid();
                break;
            case Activity.RESULT_CANCELED:
                break;
            case PayPalActivity.RESULT_FAILURE:
                String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
                String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
        }
    }

    private void markAsPaid() {
        if (tab_pos == 0) {

            PayController.getInstance().updatePayment(activity, paymentId, amount.getText().toString(), description.getText().toString(), sqlDateTime, "paid", callMessgae);
            sendMessage(contact_number + "_v", "Payment Received!\n($ " + amount.getText().toString() + " On " + selectedDateTime + ")");
        } else {
            PayController.getInstance().createPayment(activity, vendorId, amount.getText().toString(), description.getText().toString(), sqlDateTime, "paid", callMessgae);
            sendMessage(contact_number + "_v", "Payment Received!\n($ " + amount.getText().toString() + " On " + selectedDateTime + ")");
        }
    }

}
