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
                Toast.makeText(this,"BaiduNetdisk -- TYPE_WINDOW_STATE_CHANGED",Toast.LENGTH_SHORT).show();
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:    //通知栏
                List<CharSequence> charSequenceList = event.getText();
                for(CharSequence sequence : charSequenceList){
                    String keyword = sequence.toString();
                    if(keyword.equals("=================")){    //TODO inflate string
                        //TODO filter download failure notify
                    }
                }
                handleAutoDownload();
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
