package com.jerry.littlepanda.ireader.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jerry.littlepanda.*;
import com.jerry.littlepanda.ireader.ui.base.BaseTabActivity;
import com.jerry.littlepanda.ireader.ui.dialog.SexChooseDialog;
import com.jerry.littlepanda.ireader.ui.fragment.BookShelfFragment;
import com.jerry.littlepanda.ireader.ui.fragment.CommunityFragment;
import com.jerry.littlepanda.ireader.ui.fragment.FindFragment;
import com.jerry.littlepanda.ireader.utils.Constant;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseTabActivity{
    /*************Constant**********/
    private static final int WAIT_INTERVAL = 2000;
    /***************Object*********************/
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    /*****************Params*********************/
    private boolean isPrepareFinish = false;

    @Override
    protected int getContentId() {
        return R.layout.activity_base_tab;
    }

    /**************init method***********************/
    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        //toolbar.setLogo(R.mipmap.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("小说");
    }

    @Override
    protected List<Fragment> createTabFragments() {
        initFragment();
        return mFragmentList;
    }

    private void initFragment(){
        Fragment bookShelfFragment = new BookShelfFragment();
        Fragment communityFragment = new CommunityFragment();
        Fragment discoveryFragment = new FindFragment();
        mFragmentList.add(bookShelfFragment);
        mFragmentList.add(communityFragment);
        mFragmentList.add(discoveryFragment);
    }

    @Override
    protected List<String> createTabTitles() {
        String [] titles = getResources().getStringArray(R.array.nb_fragment_title);
        return Arrays.asList(titles);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //性别选择框
        showSexChooseDialog();
    }

    private void showSexChooseDialog(){
        String sex = SharedPreUtils.getInstance()
                .getString(Constant.SHARED_SEX);
        if (sex.equals("")){
            mVp.postDelayed(()-> {
                Dialog dialog = new SexChooseDialog(this);
                dialog.show();
            },500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class<?> activityCls = null;
        switch (id) {
            case R.id.action_search:
                activityCls = SearchActivity.class;
                break;
            default:
                break;
        }
        if (activityCls != null){
            Intent intent = new Intent(this, activityCls);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (menu != null && menu instanceof MenuBuilder){
            try {
                Method method = menu.getClass().
                        getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu,true);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(MainActivity.this, "ReaderMainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MiStatInterface.recordPageEnd();
    }
}
