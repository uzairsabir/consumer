package com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes;

import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ScheduleDT extends RealmObject {

    public ScheduleDT() {

    }

    @PrimaryKey
    private int appointment_id = 0;
    private int consumer_id = 0;
    private int vendor_id = 0;
    private int sub_category_id = 0;
    private int category_id = 0;

    private String consumer_name = "";
    private String vendor_name = "";
    private String sub_categor_title = "";
    private String sub_category_image_url = "";
    private String category_title = "";
    private String category_image_url = "";
    private String status = "";
    private String description = "";
    private int call_message;
    private String consumer_device_type = "";
    private String calendar_guid = "";

    private long appointment_date_time;
    private String estimated_duration;
    private String vendor_calendar_guid;
    private String consumer_image_url;
    private String vendor_image_url;
    private String consumer_address;
    private String vendor_address;
    private String consumer_zip_code;
    private String vendor_zip_code;
    private String consumer_username;
    private String vendor_username;

    public int getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(int consumer_id) {
        this.consumer_id = consumer_id;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getConsumer_name() {
        return consumer_name;
    }

    public void setConsumer_name(String consumer_name) {
        this.consumer_name = consumer_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getSub_categor_title() {
        return sub_categor_title;
    }

    public void setSub_categor_title(String sub_categor_title) {
        this.sub_categor_title = sub_categor_title;
    }

    public String getSub_category_image_url() {
        return sub_category_image_url;
    }

    public void setSub_category_image_url(String sub_category_image_url) {
        this.sub_category_image_url = sub_category_image_url;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getConsumer_device_type() {
        return consumer_device_type;
    }

    public void setConsumer_device_type(String consumer_device_type) {
        this.consumer_device_type = consumer_device_type;
    }

    public String getCalendar_guid() {
        return calendar_guid;
    }

    public void setCalendar_guid(String calendar_guid) {
        this.calendar_guid = calendar_guid;
    }

    public long getAppointment_date_time() {
        return appointment_date_time;
    }

    public void setAppointment_date_time(long appointment_date_time) {
        this.appointment_date_time = appointment_date_time;
    }


    public String getAppointmentDate() {
        try {
            // MyLogs.printinfo("appointment_date_time "+this.appointment_date_time);
            return UtilityFunctions.formatteSqlDate(UtilityFunctions.converMillisToDate(this.appointment_date_time, "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {

        }

        return "";
    }

    public String getEstimated_duration() {
        String hour = "", minutes = "";
        if (estimated_duration.contains(".")) {

            String a[] = estimated_duration.split("\\.");

            MyLogs.printinfo("estimated_durt " + a.length);
            if (!UtilityFunctions.isEmpty(a[0])) {
                if (a[0].equals("1")) {
                    hour = a[0] + " hour ";
                } else {
                    hour = a[0] + " hours ";
                }

            }
            if (!UtilityFunctions.isEmpty(a[1])) {
                if (a[1].equals("5")) {
                    minutes = "30 minutes ";
                }

            }
            return hour + " " + minutes;

        } else {
            if (estimated_duration.equals("1")) {
                hour = estimated_duration + " hour ";
            } else {
                hour = estimated_duration + " hours ";
            }
        }
        return hour;
    }

    public void setEstimated_duration(String estimated_duration) {
        this.estimated_duration = estimated_duration;
    }

    public String getAppointmentTime() {
        try {
           // MyLogs.printinfo("this.estimated_duration " + this.estimated_duration);
            //long a = (Long.parseLong(this.estimated_duration)*1000) * 1000 * 60;
            //long b = this.appointment_date_time + a;
           // MyLogs.printinfo("this.estimated_duration_test " + this.appointment_date_time + " : " + a);
         //   String diff = UtilityFunctions.formatteSqlTime(UtilityFunctions.converMillisToDate(b, "yyyy-MM-dd HH:mm:ss"));
            return UtilityFunctions.formatteSqlTime(UtilityFunctions.converMillisToDate(this.appointment_date_time, "yyyy-MM-dd HH:mm:ss")) +" (" + getEstimated_duration() + ".)";
        } catch (Exception e) {

        }

        return "";

    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }


    public int getCall_message() {
        return call_message;
    }

    public void setCall_message(int call_message) {
        this.call_message = call_message;
    }


    public String getVendor_calendar_guid() {
        return vendor_calendar_guid;
    }

    public void setVendor_calendar_guid(String vendor_calendar_guid) {
        this.vendor_calendar_guid = vendor_calendar_guid;
    }

    public String getConsumer_image_url() {
        try {
            MyLogs.printinfo("cat_url " + this.sub_category_image_url);
            if (!(consumer_image_url.contains(".jpeg") || consumer_image_url.contains(".png") || consumer_image_url.contains(".jpg"))) {

                return "res:///" + R.drawable.com_facebook_profile_picture_blank_square;


            }
//            else if (!image_url.contains(".jpeg") || !image_url.contains(".png")) {
//                return this.subcategory_image_url;
//            }
        } catch (Exception e) {
            return "";

        }

        return consumer_image_url;
    }

    public void setConsumer_image_url(String consumer_image_url) {
        this.consumer_image_url = consumer_image_url;
    }

    public String getVendor_image_url() {
        try {
            MyLogs.printinfo("cat_url " + this.sub_category_image_url);
            if (!(vendor_image_url.contains(".jpeg") || vendor_image_url.contains(".png") || vendor_image_url.contains(".jpg"))) {
                if (!(sub_category_image_url.contains(".jpeg") || sub_category_image_url.contains(".png") || sub_category_image_url.contains(".jpg"))) {

                    return "res:///" + R.drawable.com_facebook_profile_picture_blank_square;
                } else {
                    return this.sub_category_image_url;
                }

            }
//            else if (!image_url.contains(".jpeg") || !image_url.contains(".png")) {
//                return this.subcategory_image_url;
//            }
        } catch (Exception e) {
            return "";

        }
        return vendor_image_url;
    }

    public void setVendor_image_url(String vendor_image_url) {
        this.vendor_image_url = vendor_image_url;
    }

    public String getConsumer_address() {
        try {
            if (UtilityFunctions.isEmpty(this.consumer_address)) {
                return this.consumer_zip_code;
            }

        } catch (Exception e) {

        }

        return consumer_address;
    }

    public void setConsumer_address(String consumer_address) {
        this.consumer_address = consumer_address;
    }

    public String getVendor_address() {
        try {
            if (UtilityFunctions.isEmpty(this.vendor_address)) {
                return this.vendor_zip_code;
            }

        } catch (Exception e) {

        }
        return vendor_address;
    }

    public void setVendor_address(String vendor_address) {
        this.vendor_address = vendor_address;
    }

    public String getConsumer_zip_code() {
        return consumer_zip_code;
    }

    public void setConsumer_zip_code(String consumer_zip_code) {
        this.consumer_zip_code = consumer_zip_code;
    }

    public String getVendor_zip_code() {
        return vendor_zip_code;
    }

    public void setVendor_zip_code(String vendor_zip_code) {
        this.vendor_zip_code = vendor_zip_code;
    }


    public String getConsumer_username() {
        return consumer_username;
    }

    public void setConsumer_username(String consumer_username) {
        this.consumer_username = consumer_username;
    }

    public String getVendor_username() {
        return vendor_username;
    }

    public void setVendor_username(String vendor_username) {
        this.vendor_username = vendor_username;
    }
}

