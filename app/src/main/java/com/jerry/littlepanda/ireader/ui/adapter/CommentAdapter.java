package com.jerry.littlepanda.ireader.ui.adapter;

import android.content.Context;

import com.jerry.littlepanda.ireader.model.bean.CommentBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.CommentHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;
import com.jerry.littlepanda.ireader.widget.adapter.WholeAdapter;

/**
 * Created by newbiechen on 17-4-29.
 */

public class CommentAdapter extends WholeAdapter<CommentBean> {

    public CommentAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<CommentBean> createViewHolder(int viewType) {
        return new CommentHolder(false);
    }
}
