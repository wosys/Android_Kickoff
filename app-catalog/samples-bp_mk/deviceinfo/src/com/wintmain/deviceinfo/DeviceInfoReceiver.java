package com.wintmain.deviceinfo;

import static com.android.internal.telephony.TelephonyIntents.SECRET_CODE_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceInfoReceiver extends BroadcastReceiver {
    private static final String SECRET_CODE = "0000";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SECRET_CODE_ACTION.equals(intent.getAction())) {
            String host = intent.getData() != null ? intent.getData().getHost() : null;
            if (SECRET_CODE.equals(host)) {
                Intent i = new Intent(context, DeviceInfo.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
