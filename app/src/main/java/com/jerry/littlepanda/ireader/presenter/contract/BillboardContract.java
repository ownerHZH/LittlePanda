package com.jerry.littlepanda.ireader.presenter.contract;

import com.jerry.littlepanda.ireader.model.bean.packages.BillboardPackage;
import com.jerry.littlepanda.ireader.ui.base.BaseContract;

/**
 * Created by newbiechen on 17-4-23.
 */

public interface BillboardContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BillboardPackage beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadBillboardList();
    }
}
