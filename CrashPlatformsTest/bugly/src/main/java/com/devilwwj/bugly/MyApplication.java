package com.devilwwj.bugly;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * com.devilwwj.bugly
 * Created by devilwwj on 16/7/3.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this.getApplicationContext(), "900029763", true);
    }
}
