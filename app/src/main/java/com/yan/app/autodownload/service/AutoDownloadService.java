package com.yan.app.autodownload.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

public class AutoDownloadService extends AccessibilityService {
    public static final String TAG = "AutoDownloadService";

    public AutoDownloadService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();

        Log.d(TAG, "eventType = " + eventType);
        Log.d(TAG, "event = " + event.toString());

        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  //通知栏
                Toast.makeText(this,"BaiduNetdisk -- TYPE_WINDOW_STATE_CHANGED",Toast.LENGTH_SHORT).show();
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
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
    public void onInterrupt() {

    }
}
