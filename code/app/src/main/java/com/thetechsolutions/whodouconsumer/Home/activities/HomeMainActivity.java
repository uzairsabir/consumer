package com.thetechsolutions.whodouconsumer.Home.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouconsumer.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouconsumer.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouconsumer.Home.adapters.HomeMainPagerAdapter;
import com.thetechsolutions.whodouconsumer.R;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.PagerSlidingTabStrip;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeMainActivity extends FragmentActivityController implements MethodGenerator {

    public static Activity activity;
    public ViewPager pager;
    HomeMainPagerAdapter adapter;
    PagerSlidingTabStrip tapStrip;
    public static int tab_pos, tmp_pos;

    public static Intent createIntent(Activity _activity, int _tab_pos) {
        activity = _activity;
        tab_pos = _tab_pos;
        return new Intent(activity, HomeMainActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_and_tab_strip);
        activity = this;
        viewInitialize();
        viewUpdate();


        TitleBarController.getInstance(activity).setTitleBar(activity, "Home", false, true, false);

        try {
            EasyImage.configuration(this)
                    .setImagesFolderName("Whodou") //images folder name, default is "EasyImage"
                    .saveInRootPicturesDirectory();
        } catch (Exception e) {

        }


//        MyLogs.printinfo("providerList " + RealmDataRetrive.getProvider(0));
//
//
//        TessBaseAPI baseApi = new TessBaseAPI();
//
//
//        baseApi.init("/storage/emulated/0/Pictures/tessdata/", "eng");

//
//        TessBaseAPI baseApi = new TessBaseAPI();
//        baseApi.init("/storage/emulated/0/Pictures/tessdata/", "eng"); // myDir + "/tessdata/eng.traineddata" must be present
        //       baseApi.setImage(UtilityFunctions.getBitmap("storage/emulated/0/Pictures/tessdata/1ce72845-884f-43aa-80d0-8ad23e7b77f3-1188557854.jpg"));
//
        //       String recognizedText = baseApi.getUTF8Text(); // Log or otherwise display this string...
//
//       MyLogs.printinfo("recognizedText " + recognizedText);
//        baseApi.end();


        BottomMenuController.getInstance(activity).setBottomMenu(activity);

    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);


    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            AppController.saveChatNamesAvatar(HomeMainActivity.activity);
        } catch (Exception e) {

        }
        try {
            AsynGetDataController.getInstance().syncHomeData(activity);

        } catch (Exception e) {

        }
        //BottomMenuController.getInstance(activity).setBottomMenu(activity);


    }


    @Override
    public void viewUpdate() {

        adapter = new HomeMainPagerAdapter(
                getSupportFragmentManager(), activity, 3);
        pager.setAdapter(adapter);
        pager.setCurrentItem(tab_pos);
        tapStrip.setViewPager(pager);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                MyLogs.printinfo("result_get");

            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                MyLogs.printinfo("imageFile " + imageFile.getPath());


            }
        });
    }


}
