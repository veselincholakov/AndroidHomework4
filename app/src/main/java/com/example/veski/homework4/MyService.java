package com.example.veski.homework4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by veski on 25.9.2016 г..
 */

public class MyService extends Service {

    IBinder binder = new ServiceBinder();
    IServiceCommunication callback = null;
    String mLastHour;
    MyReceiver receiver;
    int percentageDrop = 0;
    public class ServiceBinder extends Binder {

        MyService getService() {
            return MyService.this;
        }

    }

    public void setServiceCallback(IServiceCommunication callback){
        updateInfo();
        if(callback != null && mLastHour != null){
            this.callback = callback;
            this.callback.updateTextViewInfo(mLastHour);
        }
    }

    private void updateInfo(){
        Calendar c = Calendar.getInstance();
        mLastHour = String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
        Toast.makeText(this,"Service was killed", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this,"Connection established!",Toast.LENGTH_SHORT).show();

        return binder;
    }
}