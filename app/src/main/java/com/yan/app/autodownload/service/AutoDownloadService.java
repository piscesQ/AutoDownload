package com.yan.app.autodownload.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.yan.app.autodownload.utils.ConfigUtils;

import java.util.List;

public class AutoDownloadService extends AccessibilityService {
    public static final String TAG = "AutoDownloadService";
    public static AutoDownloadService singleService;

    public AutoDownloadService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();

//        Log.d(TAG, "eventType = " + eventType);
//        Log.d(TAG, "event = " + event.toString());

        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                handleAutoDownload();
                Toast.makeText(this,"BaiduNetdisk -- TYPE_WINDOW_STATE_CHANGED",Toast.LENGTH_SHORT).show();
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:    //通知栏
                event.get
                handleAutoDownload();
                break;
        }
    }

    private void handleAutoDownload() {
        AccessibilityNodeInfo nodeInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            nodeInfo = getRootInActiveWindow();
        }
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("全部重新下载");
            for(AccessibilityNodeInfo node : list){
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "baiduyun service destory");
        singleService = null;
        //发送广播，已经断开辅助服务
        Intent intent = new Intent(ConfigUtils.ACTION_SERVICE_DISCONNECT);
        sendBroadcast(intent);
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "baiduyun service interrupt");
        Toast.makeText(this, "中断baiduyun服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        singleService = this;
        //发送广播，已经连接上了
        Intent intent = new Intent(ConfigUtils.ACTION_SERVICE_CONNECT);
        sendBroadcast(intent);
        Toast.makeText(this, "已连接baiduyun服务", Toast.LENGTH_SHORT).show();
    }
}
