package kr.sjsoft.cameracontrolexample;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Daniel Kreider on 01/02/18.
 */

public class DeviceAdministrator extends DeviceAdminReceiver {

    @Override
    public void onDisabled(Context context, Intent intent) {

        super.onDisabled(context, intent);
        Log.d("Camera", "Disable Camera");

    }

    @Override
    public void onEnabled(Context context, Intent intent) {

        super.onEnabled(context, intent);
        Log.d("Camera", "Enable Camera");

    }
}