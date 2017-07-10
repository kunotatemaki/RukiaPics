package com.rukiasoft.rukiapics.permissions;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.karumi.dexter.Dexter;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;

/**
 * Created by Roll on 10/7/17.
 */

public class PermissionsPresenter {

    private RukiaPicsMultiplePermissionListener rukiaPicsMultiplePermissionListener;
    private ErrorListener errorListener;

    public void createPermissionListeners(Activity activity, MainFragmentPresenter presenter) {
        rukiaPicsMultiplePermissionListener = new RukiaPicsMultiplePermissionListener(activity, presenter);
        errorListener = new ErrorListener();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void askForAllPermissions(Activity activity){
        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(rukiaPicsMultiplePermissionListener)
                .withErrorListener(errorListener)
                .check();
    }


}
