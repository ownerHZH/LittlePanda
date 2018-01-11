package com.jerry.littlepanda.ireader.ui.adapter;

import com.jerry.littlepanda.ireader.model.bean.DownloadTaskBean;
import com.jerry.littlepanda.ireader.ui.adapter.view.DownloadHolder;
import com.jerry.littlepanda.ireader.ui.base.adapter.BaseListAdapter;
import com.jerry.littlepanda.ireader.ui.base.adapter.IViewHolder;

/**
 * Created by newbiechen on 17-5-12.
 */

public class DownLoadAdapter extends BaseListAdapter<DownloadTaskBean>{

    @Override
    protected IViewHolder<DownloadTaskBean> createViewHolder(int viewType) {
        return new DownloadHolder();
    }
}
