package com.jerry.littlepanda.ireader.ui.adapter;

import com.jerry.littlepanda.ireader.model.bean.BookSortBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.BookSortHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.BaseListAdapter;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-4-23.
 */

public class BookSortAdapter extends BaseListAdapter<BookSortBean>{

    @Override
    protected IViewHolder<BookSortBean> createViewHolder(int viewType) {
        return new BookSortHolder();
    }
}
