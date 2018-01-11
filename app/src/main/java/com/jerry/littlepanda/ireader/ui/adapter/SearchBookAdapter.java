package com.jerry.littlepanda.ireader.ui.adapter;

import com.jerry.littlepanda.ireader.model.bean.packages.SearchBookPackage;
import com.jerry.littlepanda.ireader.ui.adapter.view.SearchBookHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.BaseListAdapter;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-6-2.
 */

public class SearchBookAdapter extends BaseListAdapter<SearchBookPackage.BooksBean>{
    @Override
    protected IViewHolder<SearchBookPackage.BooksBean> createViewHolder(int viewType) {
        return new SearchBookHolder();
    }
}
