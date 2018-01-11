package com.jerry.littlepanda.ireader.ui.adapter.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.jerry.littlepanda.R;
import com.jerry.littlepanda.ireader.ui.base.adapter.ViewHolderImpl;

/**
 * Created by newbiechen on 17-5-19.
 */

public class ReadBgHolder extends ViewHolderImpl<Drawable> {

    private View mReadBg;
    private ImageView mIvChecked;

    @Override
    public void initView() {
        mReadBg = findById(R.id.read_bg_view);
        mIvChecked = findById(R.id.read_bg_iv_checked);
    }

    @Override
    public void onBind(Drawable data, int pos) {
        mReadBg.setBackground(data);
        mIvChecked.setVisibility(View.GONE);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_read_bg;
    }

    public void setChecked(){
        mIvChecked.setVisibility(View.VISIBLE);
    }
}
