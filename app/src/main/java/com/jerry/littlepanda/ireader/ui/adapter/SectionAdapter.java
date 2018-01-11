package com.jerry.littlepanda.ireader.ui.adapter;

import com.jerry.littlepanda.ireader.model.bean.SectionBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.SectionHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.BaseListAdapter;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-4-16.
 */

public class SectionAdapter extends BaseListAdapter<SectionBean> {
    @Override
    protected IViewHolder<SectionBean> createViewHolder(int viewType) {
        return new SectionHolder();
    }
}
