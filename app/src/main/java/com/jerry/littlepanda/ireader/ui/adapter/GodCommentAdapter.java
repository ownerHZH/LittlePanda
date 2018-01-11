package com.jerry.littlepanda.ireader.ui.adapter;

import com.jerry.littlepanda.ireader.model.bean.CommentBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.CommentHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.BaseListAdapter;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-4-29.
 */

public class GodCommentAdapter extends BaseListAdapter<CommentBean>{
    @Override
    protected IViewHolder<CommentBean> createViewHolder(int viewType) {
        return new CommentHolder(true);
    }
}
