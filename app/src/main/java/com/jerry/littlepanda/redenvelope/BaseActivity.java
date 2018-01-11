package com.jerry.littlepanda.redenvelope;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jerry.littlepanda.APPAplication;

/**

 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPAplication.activityCreateStatistics(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        APPAplication.activityResumeStatistics(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        APPAplication.activityPauseStatistics(this);
    }
}
