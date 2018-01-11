package com.jerry.littlepanda.ireader.presenter.contract;

import com.jerry.littlepanda.ireader.model.bean.BookCommentBean;
import com.jerry.littlepanda.ireader.model.flag.BookDistillate;
import com.jerry.littlepanda.ireader.model.flag.BookSort;
import com.jerry.littlepanda.ireader.model.flag.CommunityType;
import com.jerry.littlepanda.ireader.ui.base.BaseContract;

import java.util.List;

/**
 * Created by newbiechen on 17-4-20.
 */

public interface DiscCommentContact {

    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BookCommentBean> beans);
        void finishLoading(List<BookCommentBean> beans);
        void showErrorTip();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void firstLoading(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void refreshComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void loadingComment(CommunityType block, BookSort sort, int start, int limited, BookDistillate distillate);
        void saveComment(List<BookCommentBean> beans);
    }
}
