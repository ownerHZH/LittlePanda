package com.jerry.littlepanda.ireader.ui.activity;

import android.support.v7.widget.Toolbar;

import com.jerry.littlepanda.R;
import com.jerry.littlepanda.ireader.ui.base.BaseActivity;

/**
 * Created by newbiechen on 17-5-26.
 */

public class CommunityActivity extends BaseActivity{

    @Override
    protected int getContentId() {
        return R.layout.activity_community;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("社区");
    }
}
