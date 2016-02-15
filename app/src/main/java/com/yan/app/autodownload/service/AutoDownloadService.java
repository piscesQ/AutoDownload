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

        Log.d(TAG, "eventType = " + eventType);
        Log.d(TAG, "event = " + event.toString());

        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                handleAutoDownload();
                Toast.makeText(this, "BaiduNetdisk -- TYPE_WINDOW_STATE_CHANGED", Toast.LENGTH_SHORT).show();
                break;
//            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:    //通知栏
//                List<CharSequence> charSequenceList = event.getText();
//                for (CharSequence sequence : charSequenceList) {
//                    String keyword = sequence.toString();
//                    Log.d(TAG, " == keyword : " + keyword);
//
//                    //网络连接后的通知栏：Text: [WiFi已经连接，所有任务准备开始]
//                    //下载失败        ：Text: [下载完成]
//
//                    if (keyword.contains("下载完成")) {    //下载失败的通知栏会出现“下载完成”
//                        Toast.makeText(this, "下载完成 - 失败", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "下载完成 - 失败");
//                    }
//                }
//                handleAutoDownload();
//                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                handleAutoDownload();
//                Toast.makeText(this, "BaiduNetdisk == TYPE_WINDOW_CONTENT_CHANGED", Toast.LENGTH_SHORT).show();
                break;

        }
    }

//    D/AutoDownloadService: event = EventType: TYPE_NOTIFICATION_STATE_CHANGED; EventTime: 7876974;
//    PackageName: com.baidu.netdisk; MovementGranularity: 0;
//    Action: 0 [ ClassName: android.app.Notification; Text: []; ContentDescription: null; ItemCount: -1;
//        CurrentItemIndex: -1; IsEnabled: false; IsPassword: false; IsChecked: false; IsFullScreen: false;
//        Scrollable: false; BeforeText: null; FromIndex: -1; ToIndex: -1; ScrollX: -1; ScrollY: -1;
//        MaxScrollX: -1; MaxScrollY: -1; AddedCount: -1; RemovedCount: -1; ParcelableData:
//        Notification(pri=0 contentView=com.baidu.netdisk/0x7f0300a4 vibrate=null sound=null
//                defaults=0x0 flags=0x2 color=0x00000000 vis=PRIVATE) ]
//    ; recordCount: 0

    private void handleAutoDownload() {
        AccessibilityNodeInfo nodeInfo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            nodeInfo = getRootInActiveWindow();
        }
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("全部重新下载");
            for (AccessibilityNodeInfo node : list) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Toast.makeText(this, "可见性：" + node.isVisibleToUser(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,  "可见性：" + node.isVisibleToUser());   // true
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
        Toast.makeText(this, "已连接baiduyun服务", Toast.LENGTH_SHORT).show();
        singleService = this;
        //发送广播，已经连接上了
        Intent intent = new Intent(ConfigUtils.ACTION_SERVICE_CONNECT);
        sendBroadcast(intent);
    }
}
