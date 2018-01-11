package com.jerry.littlepanda.ireader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jerry.littlepanda.R;
import com.jerry.littlepanda.ireader.model.bean.SectionBean;
import com.jerry.littlepanda.ireader.model.flag.CommunityType;
import com.jerry.littlepanda.ireader.ui.activity.BookDiscussionActivity;
import com.jerry.littlepanda.ireader.ui.adapter.SectionAdapter;
import com.jerry.littlepanda.ireader.ui.base.BaseFragment;
import com.jerry.littlepanda.ireader.ui.base.adapter.BaseListAdapter;
import com.jerry.littlepanda.ireader.widget.itemdecoration.DashItemDecoration;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by newbiechen on 17-4-15.
 * 讨论区
 */

public class CommunityFragment extends BaseFragment implements BaseListAdapter.OnItemClickListener{
    /***************view******************/
    @BindView(R.id.community_rv_content)
    RecyclerView mRvContent;

    private SectionAdapter mAdapter;

    @Override
    protected int getContentId() {
        return R.layout.fragment_community;
    }

    /***********************************init method*************************************************/

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setUpAdapter();
    }

    private void setUpAdapter(){
        ArrayList<SectionBean> sections = new ArrayList<>();

        /*觉得采用枚举会好一些，要不然就是在Constant中创建Map类*/
        for (CommunityType type : CommunityType.values()){
            sections.add(new SectionBean(type.getTypeName(),type.getIconId()));
        }

        mAdapter = new SectionAdapter();
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DashItemDecoration());
        mRvContent.setAdapter(mAdapter);
        mAdapter.addItems(sections);
    }

    /****************************click method********************************/

    @Override
    protected void initClick() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int pos) {
        //根据类型，启动相应的Discussion区
        CommunityType type = CommunityType.values()[pos];
        BookDiscussionActivity.startActivity(getContext(),type);
    }

    @Override
    public void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(CommunityFragment.this.getActivity(), "CommunityFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        MiStatInterface.recordPageEnd();
    }
}
