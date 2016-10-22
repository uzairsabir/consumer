package com.thetechsolutions.whodouconsumer.AppHelpers.WebService;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ServiceUrl {

    public static String call_user_existense = ServiceConfig.getPublicUrl() + "v2/checkExistingUser";
    public static String call_sign_up = ServiceConfig.getPublicUrl() + "v2/signupUser";
    public static String call_code_verification = ServiceConfig.getPublicUrl() + "v2/verifySignup";
    public static String call_vendor_categories = ServiceConfig.getPublicUrl() + "v2/vendorCategories";
    public static String call_update_profile = ServiceConfig.getPublicUrl() + "v2/updateUserProfile";
    public static String call_sync_contact = ServiceConfig.getPublicUrl() + "v2/syncContacts";
    public static String call_get_profile = ServiceConfig.getPublicUrl() + "v2/updateUserProfile";
    public static String call_create_provider = ServiceConfig.getPublicUrl() + "v2/createConsumerVendorProfile";
    public static String call_create_consumer_provider_link = ServiceConfig.getPublicUrl() + "v2/consumerVendorLink";
    public static String call_delete_consumer_vendor_link = ServiceConfig.getPublicUrl() + "v2/deleteConsumerVendorLink";
    public static String call_update_provider = ServiceConfig.getPublicUrl() + "v2/updateConsumerVendorProfile";
    public static String call_get_my_providers = ServiceConfig.getPublicUrl() + "v2/getMyConsumerList";
    public static String call_create_consumer_friend_link = ServiceConfig.getPublicUrl() + "v2/createConsumerFriendLink";
    public static String call_delete_consumer_friend_link = ServiceConfig.getPublicUrl() + "v2/deleteConsumerFriendLink";
    public static String call_get_my_friends = ServiceConfig.getPublicUrl() + "v2/consumerListById";
    public static String call_get_my_friends_providers = ServiceConfig.getPublicUrl() + "v2/friendVendorList";
    public static String call_existense_vendor_consumer = ServiceConfig.getPublicUrl() + "v2/checkExistingVendorConsumer";
    public static String call_create_appointment = ServiceConfig.getPublicUrl() + "v2/createAppointment";
    public static String call_update_appointment = ServiceConfig.getPublicUrl() + "v2/updateConsumerAppointment";
    public static String call_get_appointment = ServiceConfig.getPublicUrl() + "v2/getConsumerAppointments";
    public static String call_update_profile_image = ServiceConfig.getPublicUrl() + "v2/updateConsumerVendorProfileImageByUserid";
    public static String call_get_preference = ServiceConfig.getPublicUrl() + "v2/userPreferencesByUserId";
    public static String call_update_preference = ServiceConfig.getPublicUrl() + "v2/updatePreferences";
}
