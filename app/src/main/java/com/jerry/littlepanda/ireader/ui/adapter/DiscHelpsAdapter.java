package com.jerry.littlepanda.ireader.ui.adapter;

import android.content.Context;

import com.jerry.littlepanda.ireader.model.bean.BookHelpsBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.DiscHelpsHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;
import com.jerry.littlepanda.ireader.widget.adapter.WholeAdapter;

/**
 * Created by newbiechen on 17-4-21.
 */

public class DiscHelpsAdapter extends WholeAdapter<BookHelpsBean>{

    public DiscHelpsAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookHelpsBean> createViewHolder(int viewType) {
        return new DiscHelpsHolder();
    }
}
