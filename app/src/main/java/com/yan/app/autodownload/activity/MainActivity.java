package com.yan.app.autodownload.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yan.app.autodownload.R;
import com.yan.app.autodownload.fragment.MainFragment;
import com.yan.app.autodownload.receiver.ConnectReceiver;
import com.yan.app.autodownload.utils.ConfigUtils;

public class MainActivity extends AppCompatActivity {

    private ConnectReceiver connectReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectReceiver = new ConnectReceiver();

        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, mainFragment);
        fragmentTransaction.commitAllowingStateLoss();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConfigUtils.ACTION_SERVICE_CONNECT);
        intentFilter.addAction(ConfigUtils.ACTION_SERVICE_DISCONNECT);
        registerReceiver(connectReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectReceiver);
    }
}
