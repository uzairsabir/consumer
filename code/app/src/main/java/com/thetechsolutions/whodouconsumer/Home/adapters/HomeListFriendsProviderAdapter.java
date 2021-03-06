package com.thetechsolutions.whodouconsumer.Home.adapters;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouconsumer.AppHelpers.Contacts.models.ContactModel;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendsProviderDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeFriendProfileActivity;
import com.thetechsolutions.whodouconsumer.Home.activities.HomeMainActivity;
import com.thetechsolutions.whodouconsumer.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouconsumer.Home.model.HomeModel;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_home_friend_provider)
public class HomeListFriendsProviderAdapter extends ItemViewHolder<FriendsProviderDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.friends_name)
    TextView friends_name;

    @ViewId(R.id.service_name)
    TextView service_name;

    @ViewId(R.id.address)
    TextView address;

    @ViewId(R.id.container)
    RelativeLayout container;

    @ViewId(R.id.add_btn)
    TextView add_btn;

    boolean isdeleteRequire;


    static Activity activity;
    FriendsProviderDT item;


    public HomeListFriendsProviderAdapter(View view) {
        super(view);
    }

    public static Class<HomeListFriendsProviderAdapter> newInstance(Activity _activity) {
        activity = _activity;


        return HomeListFriendsProviderAdapter.class;
    }


    @Override
    public void onSetValues(FriendsProviderDT item, PositionInfo positionInfo) {
        // MyLogs.printinfo("item_pay " + item.getImage_url());
        sourceImageView.setImageURI(Uri.parse(item.getImage_url()));
        title.setText(item.getFirst_name() + " " + item.getLast_name());
        service_name.setText(item.getSub_category_title());
        address.setText(item.getAddress());
        friends_name.setText("(" + item.getFriend_name() + ")");
        this.item = item;

        try {


            if (RealmDataRetrive.getProviderDetail(getItem().getUsername(), 0) != null) {
                add_btn.setText("Remove");
                add_btn.setTextColor(activity.getResources().getColor(R.color.color_red));
                add_btn.setBackground(activity.getResources().getDrawable(R.drawable.bg_red_with_round_edges_center_white));
                isdeleteRequire = true;
            } else {
                add_btn.setText("Add");
                add_btn.setTextColor(activity.getResources().getColor(R.color.who_do_u_blue));
                add_btn.setBackground(activity.getResources().getDrawable(R.drawable.bg_blue_with_round_edges_center_white));

                isdeleteRequire = false;
            }

        } catch (Exception e) {

        }


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();


        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyLogs.printinfo("username " + activity);
                HomeMainFragment.pos = 2;
//                if (activity == null) {
//                    activity = HomeFriendProfileActivity.activity;
//                }
                ListenerController.openFriendProfileActivity(activity, 2, item.getUsername());
                //ListenerController.openFriendProfileActivity(activity, 0, getItem().getId());
            }
        });

        //   MyLogs.printinfo("get_uid"+item);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new callConsumerVendorDelete(item.getUsername(), String.valueOf(item.getId()), item.getFirst_name(), item.getLast_name(), isdeleteRequire).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }
        });

        sourceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilityFunctions.showFullImage(activity, item.getFirst_name() + " " + item.getLast_name(), item.getImage_url(), null, 0);
            }
        });


    }


    public interface Listener {
        void onButtonClicked(FriendsProviderDT datetype);
    }

    private class callConsumerVendorDelete extends AsyncTask<String, Void, Boolean> {


        String providername = "", providerId, fname, lname;
        boolean isDeleteRequire;


        public callConsumerVendorDelete(String _providername, String _providerId, String _fname, String _lname, boolean _isDeleteRequire) {

            providername = _providername;
            isDeleteRequire = _isDeleteRequire;
            providerId = _providerId;
            fname = _fname;
            lname = _lname;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                if (activity instanceof HomeMainActivity) {
                    AppController.showDialoge(activity);
                } else {
                    AppController.showDialoge(HomeFriendProfileActivity.activity);
                }

            } catch (Exception e) {
                AppController.showDialoge(HomeFriendProfileActivity.activity);
            }


        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (isDeleteRequire) {
                    return ContactModel.checkDeleteConsumerVendorLink(providername);
                } else {
                    return HomeModel.createConsumerProviderLink(providerId, fname, lname, providername, 2);
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


//                if (isDeleteRequire) {
//                    RealmDataDelete.deleteConsumerProviderLink(providername, 2);
//                }


                if (AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, 0, false) == 0) {
                    //MyLogs.printinfo("refresh_fragment");
                    try {
                        ((HomeFriendProfileActivity) HomeFriendProfileActivity.activity).loadData();
//                        activity.finish();
//                        try {
//                            HomeMainActivity.activity.finish();
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            ListenerController.openHomeActivity(activity, 0);
//                        } catch (Exception e) {
//
//                        }
                    } catch (Exception e) {

                    }
                    try {
                        HomeMainFragment.fragment.loadData();
                    } catch (Exception e) {

                    }

                }


            }


        }
    }


    public void refreshAdapter() {
        Listener listener = getListener(Listener.class);
        if (listener != null) {
            listener.onButtonClicked(item);

        }
    }


}
