package com.jerry.littlepanda.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jerry.littlepanda.R;
import com.jerry.littlepanda.ireader.model.bean.BillBookBean;
import com.jerry.littlepanda.ireader.presenter.BillBookPresenter;
import com.jerry.littlepanda.ireader.presenter.contract.BillBookContract;
import com.jerry.littlepanda.ireader.ui.activity.BookDetailActivity;
import com.jerry.littlepanda.ireader.ui.adapter.BillBookAdapter;
import com.jerry.littlepanda.ireader.ui.base.BaseRxFragment;
import com.jerry.littlepanda.ireader.widget.RefreshLayout;
import com.jerry.littlepanda.ireader.widget.itemdecoration.DefaultItemDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by newbiechen on 17-5-3.
 */

public class BillBookFragment extends BaseRxFragment<BillBookContract.Presenter>
        implements BillBookContract.View{
    private static final String EXTRA_BILL_ID = "extra_bill_id";

    /********************/
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvContent;
    /*******************/
    private BillBookAdapter mBillBookAdapter;
    /*****************/
    private String mBillId;

    public static Fragment newInstance(String billId){
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_BILL_ID,billId);
        Fragment fragment = new BillBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    protected BillBookContract.Presenter bindPresenter() {
        return new BillBookPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBillId = getArguments().getString(EXTRA_BILL_ID);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBillBookAdapter.setOnItemClickListener(
                (view, pos) -> {
                    String bookId = mBillBookAdapter.getItem(pos).get_id();
                    BookDetailActivity.startActivity(getContext(),bookId);
                }
        );
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRefreshLayout.showLoading();
        mPresenter.refreshBookBrief(mBillId);
    }

    private void setUpAdapter(){
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DefaultItemDecoration(getContext()));
        mBillBookAdapter = new BillBookAdapter();
        mRvContent.setAdapter(mBillBookAdapter);
    }

    @Override
    public void finishRefresh(List<BillBookBean> beans) {
        mBillBookAdapter.refreshItems(beans);
    }

    @Override
    public void showError() {
        mRefreshLayout.showError();
    }

    @Override
    public void complete() {
        mRefreshLayout.showFinish();
    }
}
