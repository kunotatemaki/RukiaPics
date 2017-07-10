package com.rukiasoft.rukiapics.permissions;

import android.util.Log;

import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.rukiasoft.rukiapics.utilities.LogHelper;

/**
 * Created by iRoll on 28/1/17.
 */

public class ErrorListener  implements PermissionRequestErrorListener {
    private static final String TAG = LogHelper.makeLogTag(ErrorListener.class);
    @Override
    public void onError(DexterError error) {
        Log.e(TAG, "There was an error: " + error.toString());
    }
}
