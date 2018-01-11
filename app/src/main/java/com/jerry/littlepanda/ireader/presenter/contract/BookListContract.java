package com.jerry.littlepanda.ireader.presenter.contract;

import com.jerry.littlepanda.ireader.model.bean.BookListBean;
import com.jerry.littlepanda.ireader.model.flag.BookListType;
import com.jerry.littlepanda.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by newbiechen on 17-5-1.
 */

public interface BookListContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookListBean> beans);
        void finishLoading(List<BookListBean> beans);
        void showLoadError();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookList(BookListType type, String tag, int start, int limited);
        void loadBookList(BookListType type, String tag, int start, int limited);
    }
}
