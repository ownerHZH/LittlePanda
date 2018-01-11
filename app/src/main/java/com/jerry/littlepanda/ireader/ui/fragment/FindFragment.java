package com.jerry.littlepanda.ireader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jerry.littlepanda.R;
import com.jerry.littlepanda.ireader.model.bean.SectionBean;
import com.jerry.littlepanda.ireader.model.flag.FindType;
import com.jerry.littlepanda.ireader.ui.activity.BillboardActivity;
import com.jerry.littlepanda.ireader.ui.activity.BookListActivity;
import com.jerry.littlepanda.ireader.ui.activity.BookSortActivity;
import com.jerry.littlepanda.ireader.ui.adapter.SectionAdapter;
import com.jerry.littlepanda.ireader.ui.base.BaseFragment;
import com.jerry.littlepanda.ireader.widget.itemdecoration.DashItemDecoration;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by newbiechen on 17-4-15.
 */

public class FindFragment extends BaseFragment {
    /******************view************************/
    @BindView(R.id.find_rv_content)
    RecyclerView mRvContent;
    /*******************Object***********************/
    SectionAdapter mAdapter;

    @Override
    protected int getContentId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setUpAdapter();
    }

    private void setUpAdapter(){
        ArrayList<SectionBean> sections = new ArrayList<>();
        for (FindType type : FindType.values()){
            sections.add(new SectionBean(type.getTypeName(),type.getIconId()));
        }

        mAdapter = new SectionAdapter();
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DashItemDecoration());
        mRvContent.setAdapter(mAdapter);
        mAdapter.addItems(sections);
    }


    @Override
    protected void initClick() {
        mAdapter.setOnItemClickListener(
                (view,pos) -> {
                    FindType type = FindType.values()[pos];
                    Intent intent;
                    //跳转
                    switch (type){
                        case TOP:
                            intent = new Intent(getContext(),BillboardActivity.class);
                            startActivity(intent);
                            break;
                        case SORT:
                            intent = new Intent(getContext(), BookSortActivity.class);
                            startActivity(intent);
                            break;
                        case TOPIC:
                            intent = new Intent(getContext(), BookListActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
        );

    }

    @Override
    public void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(FindFragment.this.getActivity(), "FindFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        MiStatInterface.recordPageEnd();
    }
}
