package com.thetechsolutions.whodouconsumer.Home.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.FriendDT;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ProviderDT;
import com.thetechsolutions.whodouconsumer.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_home_list)
public class HomeListFriendAdapter extends ItemViewHolder<FriendDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.service_name)
    TextView service_name;

    @ViewId(R.id.address)
    TextView address;

    @ViewId(R.id.chat_icon)
    ImageView chat_icon;

    @ViewId(R.id.call_icon)
    ImageView call_icon;

    @ViewId(R.id.container)
    RelativeLayout container;


    static Activity activity;

    FriendDT item;

    public HomeListFriendAdapter(View view) {
        super(view);
    }

    static int pos, positioninfo;

    public static Class<HomeListFriendAdapter> newInstance(Activity _activity, int _pos) {
        activity = _activity;
        pos = _pos;
        return HomeListFriendAdapter.class;
    }


    @Override
    public void onSetValues(FriendDT item, PositionInfo positionInfo) {
        this.item = item;
        this.positioninfo = positionInfo.getPosition();
        try {
          //  MyLogs.printinfo("cat_image " + item.getImage_url());
            //sourceImageView.
            sourceImageView.setImageURI(Uri.parse(item.getImage_url()));

        } catch (Exception e) {
            e.printStackTrace();

        }
        try {

            title.setText(item.getFirst_name() + " " + item.getLast_name());
            if (UtilityFunctions.isEmpty(item.getSub_category_title())) {
                service_name.setVisibility(View.GONE);
            } else {
                service_name.setVisibility(View.VISIBLE);
                service_name.setText(item.getSub_category_title());
            }
            address.setText(item.getAddress());
//            if (HomeMainFragment.pos==0) {
//
//                address.setText(item.getAddress());
//            } else {
//                service_name.setVisibility(View.GONE);
//                address.setText(item.getAddress());
//            }


        } catch (Exception e) {

        }


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();

        call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UtilityFunctions.directCall(activity, item.getUsername());

            }
        });

        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.openChat(activity, item.getUsername(), item.getFirst_name() + " " + item.getLast_name(), item.getImage_url(), item.getIs_register_user(), HomeMainFragment.pos);

                // activity.startActivity(ConversationActivity.createIntent(activity, "123456", "", item.getUsername(), item.getFirst_name() + " " + item.getLast_name()));

            }
        });

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (HomeMainSearchActivity.isOpenChat) {
                //  activity.startActivity(StartConversationActivity.createIntent(activity, "123456", "12345678"));

//                    if (positioninfo == 0) {
//                        activity.startActivity(ConversationActivity.createIntent(activity, "123456", "", "12345678", item.getFirst_name() + " " + item.getLast_name()));
//
//                    } else {
                //     activity.startActivity(ConversationActivity.createIntent(activity, "123456", "", item.getUsername(), item.getFirst_name() + " " + item.getLast_name()));

                //}

                //   StartConversationActivity a = new StartConversationActivity();
                //  a.openChatDirectly();


//                } else {
//                    ListenerController.openFriendProfileActivity(activity, pos, item.getUsername());
//                }
                ListenerController.openFriendProfileActivity(activity, pos, item.getUsername());
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
        void onButtonClicked(ProviderDT datetype);
    }

}
