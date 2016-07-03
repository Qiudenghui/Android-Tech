package com.devilwwj.crashlytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.appsee.Appsee;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.devilwwj.jni.TestJNI;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("mylib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        Appsee.start(getString(R.string.com_appsee_apikey));
        try {
//            testJavaCrash();
        testNativeCrash();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        testANRCrash();
    }


    public void testJavaCrash() {
        throw new RuntimeException(
                "This Crash create for Test! You can go to Bugly see more detail!");
    }

    /**
     * 触发一个ANR异常 之前版本是用Receiver做一个ANR保证其在主线程执行，但ANR本身就可能采集失败，为了节省包体积，先改为直接执行
     */
    public void testANRCrash() {
        try {
            int trySleep = 0;
            while (trySleep++ < 30) {
                Log.d("ANR", "try main sleep for make a test anr! try:" + trySleep + "/30" + ", kill it if you don't want to wait!");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable thr) {
            thr.printStackTrace();
        }
    }


    public void testNativeCrash() {
        TestJNI testJNI = new TestJNI();
        testJNI.createANativeCrash();
    }
}
