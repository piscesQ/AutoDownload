package com.yan.app.autodownload;

import android.app.Application;

/**
 * @author YanBin
 * @version V1.0
 * @project AutoDownload
 * @Description Class Description
 * @date 2016/2/3
 */
public class MainApplication extends Application {

    private static MainApplication singleInstance;

    public static MainApplication getInstance(){
        if(singleInstance == null){
            singleInstance = new MainApplication();
        }
        return singleInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
