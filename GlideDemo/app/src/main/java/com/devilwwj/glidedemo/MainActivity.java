package com.devilwwj.glidedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageview);

        String internetUrl = "https://raw.githubusercontent.com/fanatic-mobile-developer-for-android/A-week-to-develop-android-app-plan/master/images/wwjblog.jpg";

        // 初步用法
        Glide.with(this).load(internetUrl).into(imageView);
    }
}
