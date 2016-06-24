package com.devilwwj.uploadlibtojcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devilwwj.utils.MyUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyUtils.print("巫山老妖");
    }
}
