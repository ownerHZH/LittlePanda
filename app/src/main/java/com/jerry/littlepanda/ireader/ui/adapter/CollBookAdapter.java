package com.jerry.littlepanda.ireader.ui.adapter;

import com.jerry.littlepanda.ireader.model.bean.CollBookBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.CollBookHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;
import com.jerry.littlepanda.ireader.widget.adapter.WholeAdapter;

/**
 * Created by newbiechen on 17-5-8.
 */

public class CollBookAdapter extends WholeAdapter<CollBookBean> {

    @Override
    protected IViewHolder<CollBookBean> createViewHolder(int viewType) {
        return new CollBookHolder();
    }

}
