package com.yan.app.autodownload.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yan.app.autodownload.MainApplication;

/**
 * @author YanBin
 * @version V1.0
 * @project AutoDownload
 * @Description Class Description
 * @date 2016/2/2
 */
public class ConfigUtils {
    public static final String NAME = "config_sp";
    public static final String SWITCH_DOWNLOAD = "KEY_ENABLE_DOWNLOAD";
    public static final String EDIT_PREFERENCE = "KEY_EDIT";
    public static final String LIST_PREFERENCE = "KEY_LIST";

    public static final String ACTION_SERVICE_CONNECT = "com.yan.app.autodownload.CONNECT";
    public static final String ACTION_SERVICE_DISCONNECT = "com.yan.app.autodownload.DISCONNECT";
    public static final String SERVICE_ACTION = "com.yan.app.service.BAIDUYUN_DOWNLOAD";

    private static SharedPreferences sp = MainApplication.getInstance().getSharedPreferences(ConfigUtils.NAME, Context.MODE_PRIVATE);

    public static boolean getSwitchDownload(){
        return sp.getBoolean(ConfigUtils.SWITCH_DOWNLOAD,true);
    }

    public static void getEditPreference(){

    }

    public static void getListPreference(){

    }
}
