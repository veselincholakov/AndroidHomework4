package com.example.veski.homework4;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IServiceCommunication{

    MyReceiver receiver = null;

    final Context ctx = this;
    Intent serviceIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method will be used to update the information contained in the service
     */
    @Override
    public void updateTextViewInfo(String info){

        int a = 5;
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(info);
    }
    public void registerBroadcastReceiver(View view){

    }

    public void releaseBroadcastReceiver(View view){

    }

    public void onServiceStopped(View view){

        if(isMyServiceRunning(MyService.class)){
            unbindService(conn);
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }

    public void onServiceStarted(View view){
        if(!isMyServiceRunning(MyService.class)){
            serviceIntent = new Intent(this, MyService.class);
            bindService(serviceIntent,conn,Context.BIND_AUTO_CREATE);
            startService(serviceIntent);
        }else{
            serviceIntent = new Intent(this, MyService.class);
            bindService(serviceIntent,conn,Context.BIND_AUTO_CREATE);
            Toast.makeText(ctx,"Service has been created already!",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.ServiceBinder binder = (MyService.ServiceBinder)service;
            binder.getService().setServiceCallback(MainActivity.this);
            Toast.makeText(ctx, "successfully connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };




}
