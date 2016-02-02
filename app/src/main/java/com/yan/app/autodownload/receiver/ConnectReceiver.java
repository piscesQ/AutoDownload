package com.yan.app.autodownload.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yan.app.autodownload.utils.ConfigUtils;

public class ConnectReceiver extends BroadcastReceiver {
    public ConnectReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ConfigUtils.ACTION_SERVICE_CONNECT)){
            Toast.makeText(context, "Baidu Netdisk Connected ", Toast.LENGTH_SHORT).show();
        }else if(action.equals(ConfigUtils.ACTION_SERVICE_DISCONNECT)){
            Toast.makeText(context, "Baidu Netdisk Disconnected ", Toast.LENGTH_SHORT).show();
        }
    }
}
