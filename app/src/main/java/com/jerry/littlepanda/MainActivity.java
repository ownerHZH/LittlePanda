package com.jerry.littlepanda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.jerry.littlepanda.Utils.BottomNavigationViewHelper;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;
import com.jerry.littlepanda.mytool.MyToolFragment;
import com.jerry.littlepanda.readrss.ReadRssFragment;
import com.jerry.littlepanda.video.VideoFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.mistatistic.sdk.MiStatInterface;


public class MainActivity extends AppCompatActivity implements
        ReadRssFragment.OnReadRssFragmentInteractionListener,
        VideoFragment.OnListFragmentInteractionListener,
        MyToolFragment.OnFragmentInteractionListener{


    private static final int MSG_FRAGMENT_RSS = 1;
    private static final int MSG_FRAGMENT_VIDEO=2;
    private static final int MSG_FRAGMENT_ME=3;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //阅读
                    changeFragment(MSG_FRAGMENT_RSS);
                    MiStatInterface.recordCountEvent("Button_Click", "Button_Rss_click");
                    return true;
                case R.id.navigation_video:
                    //视频
                    changeFragment(MSG_FRAGMENT_VIDEO);
                    MiStatInterface.recordCountEvent("Button_Click", "Button_Video_click");
                    return true;

                case R.id.navigation_novel:
                    //changeFragment(MSG_FRAGMENT_NOVEL);
                    Intent intent = new Intent(MainActivity.this, com.jerry.littlepanda.ireader.ui.activity.MainActivity.class);
                    startActivity(intent);
                    MiStatInterface.recordCountEvent("Button_Click", "Button_Novel_click");
                    //navigation.getMenu().findItem(lastfragment).setEnabled(true);
                    return true;
                case R.id.navigation_me:
                    //我的工具箱
                    changeFragment(MSG_FRAGMENT_ME);
                    MiStatInterface.recordCountEvent("Button_Click", "Button_Me_click");
                    return true;

            }
            return false;
        }

    };


    Fragment fragment = null;
    boolean isReadRssFragment=false,isPulled=false;//是否点击了这个tab
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }

        /*
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
                */

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        changeFragment(MSG_FRAGMENT_RSS);

        //mTextMessage = (TextView) findViewById(R.id.message);
        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        requestReadPhonePermission();//获取用户数据

        XiaomiUpdateAgent.update(this,false); //自动更新，第二个参数为true时使用沙盒环境，否则使用线上环境

        //判断是否允许小说Tab,默认不允许 主要通过推送进行配置
        if(!SharedPreUtils.getInstance().getBoolean(Constants.ENABLE_READER_TAB,false))
        {
            navigation.getMenu().findItem(R.id.navigation_novel).setVisible(false);
            navigation.getMenu().removeItem(R.id.navigation_novel);
        }

    }

    //private static final int REQUEST_READ_PHONE_STATE=111;
    private void requestReadPhonePermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.WRITE_SETTINGS},127);
            }
        }

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_SETTINGS)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        final DeviceTool deviceTool=DeviceTool.getDeviceToolInstance();
                        deviceTool.uploadDevice();
                    } else {
                        // Oups permission denied
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // EasyPermissions handles the request result.
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }




    //更换Fragment
    private void changeFragment(int index)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, getInstanceByIndex(index));
        transaction.commit();

    }

    public Fragment getInstanceByIndex(int index)
    {
        isReadRssFragment=false;
        switch (index) {
            case MSG_FRAGMENT_RSS:
                //阅读
                fragment = ReadRssFragment.newInstance("","");
                isReadRssFragment=true;
                break;
            case MSG_FRAGMENT_VIDEO:
                //视频
                fragment =VideoFragment.newInstance(1);
                break;
            case MSG_FRAGMENT_ME:
                //工具箱
                fragment=MyToolFragment.newInstance("","");
                break;
        }
        return fragment;
    }

    //ReadRssFragment的交互接口
    @Override
    public void onReadRssFragmentInteraction(Uri uri) {
       //阅读页面的回调
    }

    @Override
    public void onVideoListFragmentInteraction(YiDianNewsItem item) {
        //视频页面的回调
    }

    @Override
    protected void onStop() {
        super.onStop();
        //当Activity停止运行后，取消Activity的所有网络请求
        //APPAplication.getRequestQueue().cancelAll(Constants.VOLLEY_TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(MainActivity.this, "MainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MiStatInterface.recordPageEnd();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    //保存点击的时间
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                onBackPressed();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //MyTool Interaction
    }
}
