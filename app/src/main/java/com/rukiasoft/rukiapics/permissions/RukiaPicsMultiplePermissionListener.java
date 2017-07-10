package com.rukiasoft.rukiapics.permissions;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.ui.ui.presenters.MainFragmentPresenter;
import com.rukiasoft.rukiapics.utilities.LogHelper;

import java.util.List;

/**
 * Created by iRoll on 28/1/17.
 */

public class RukiaPicsMultiplePermissionListener implements MultiplePermissionsListener {
    public static final String TAG = LogHelper.makeLogTag(RukiaPicsMultiplePermissionListener.class);

    private final PermissionMethods permissionMethods;
    private MainFragmentPresenter presenter;

    public RukiaPicsMultiplePermissionListener(Activity _activity, MainFragmentPresenter presenter) {
        permissionMethods = new PermissionMethods(_activity);
        this.presenter = presenter;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            if(response.getRequestedPermission().getName().equals(android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                presenter.saveImageToGallery();
            }
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                   PermissionToken token) {
        permissionMethods.showPermissionRationale(token, R.string.permission_explanation);
    }
}