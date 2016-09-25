package com.example.veski.homework4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;


/**
 * Created by veski on 25.9.2016 г..
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);

    }

}
