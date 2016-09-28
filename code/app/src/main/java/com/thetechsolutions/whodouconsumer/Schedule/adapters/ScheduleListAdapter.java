package com.thetechsolutions.whodouconsumer.Schedule.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouconsumer.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouconsumer.R;
import com.thetechsolutions.whodouconsumer.Schedule.fragments.ScheduleMainFragment;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Uzair on 3/25/2016.
 */
@LayoutId(R.layout.item_schedule_list)
public class ScheduleListAdapter extends ItemViewHolder<ScheduleDT> {


    @ViewId(R.id.fresco_view)
    SimpleDraweeView sourceImageView;

    @ViewId(R.id.title_name)
    TextView title;

    @ViewId(R.id.appointment_date)
    TextView appointment_date;

    @ViewId(R.id.appointment_time)
    TextView appointment_time;

    @ViewId(R.id.container)
    RelativeLayout container;


    static Activity activity;


    public ScheduleListAdapter(View view) {
        super(view);
    }

    public static Class<ScheduleListAdapter> newInstance(Activity _activity) {
        activity = _activity;

        return ScheduleListAdapter.class;
    }


    @Override
    public void onSetValues(ScheduleDT item, PositionInfo positionInfo) {
        sourceImageView.setImageURI(Uri.parse(item.getSub_category_image_url()));
        title.setText(item.getVendor_name());
        appointment_date.setText(item.getAppointmentDate());
        appointment_time.setText(item.getAppointmentTime());


    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ListenerController.openScheduleDetail(activity, getItem().getAppointment_id(), ScheduleMainFragment.tab_pos, "Appointment Detail","");
            }
        });


    }


    public interface Listener {
        void onButtonClicked(ScheduleDT datetype);
    }

}
