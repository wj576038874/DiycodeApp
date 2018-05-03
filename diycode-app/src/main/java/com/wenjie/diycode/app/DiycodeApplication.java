package com.wenjie.diycode.app;

import android.app.Application;
import android.content.Context;


/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.app
 * @FileName: com.wenjie.diycode.app.DiycodeApplication.java
 * @Author: wenjie
 * @Date: 2017-08-07 14:49
 * @Description:
 * @Version:
 */
public class DiycodeApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
