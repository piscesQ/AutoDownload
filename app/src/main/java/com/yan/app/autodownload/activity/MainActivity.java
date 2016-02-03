package com.yan.app.autodownload.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 0, 0, R.string.open_service_button);
        item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            openAccessibilityServiceSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 打开辅助服务的设置
     */
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, R.string.tips, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
