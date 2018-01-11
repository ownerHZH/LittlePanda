package com.jerry.littlepanda.mytool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.domain.DeviceInfoEntity;
import com.jerry.littlepanda.ireader.ui.base.BaseActivity;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfoActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<DeviceInfoEntity> items=new ArrayList<DeviceInfoEntity>();
    private LinearLayoutManager layoutManager;
    private DeviceInfoActivityAdapter mAdapter;

    private final static int INITDATA=120;

    @Override
    protected int getContentId() {
        return R.layout.activity_device_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setEnableSliding(true);//允许左滑退出

        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //setContentView(R.layout.activity_device_info);

        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("设备信息");

    }


    public void initData()
    {
        DeviceInfoEntity item0=new DeviceInfoEntity("Android Device ID",
                DeviceTool.getDeviceToolInstance().getAndroidId());
        items.add(item0);

        DeviceInfoEntity item1=new DeviceInfoEntity("IMEI",
                DeviceTool.getDeviceToolInstance().getDeviceId());
        items.add(item1);

        DeviceInfoEntity item2=new DeviceInfoEntity("Local IP Address",
                SharedPreUtils.getInstance().getString(Constants.LOCALIP));
        items.add(item2);

        DeviceInfoEntity item3=new DeviceInfoEntity("SIM Card Serial",
                DeviceTool.getDeviceToolInstance().getSimSerialNumber());
        items.add(item3);

        DeviceInfoEntity item4=new DeviceInfoEntity("Phone",
                DeviceTool.getDeviceToolInstance().getDeviceBrand()+" "
                        +DeviceTool.getDeviceToolInstance().getSystemModel()+" "
                        +"Android "+DeviceTool.getDeviceToolInstance().getSystemVersion()+" "
        );
        items.add(item4);

        DeviceInfoEntity item5=new DeviceInfoEntity("User Agent",
                DeviceTool.getDeviceToolInstance().getUserAgent());
        items.add(item5);

        DeviceInfoEntity item6=new DeviceInfoEntity("Screen Size",
                " 宽："+DeviceTool.getDeviceToolInstance().getScreenSize()[0]
                        +"  高："+DeviceTool.getDeviceToolInstance().getScreenSize()[1]);
        items.add(item6);

        DeviceInfoEntity item7=new DeviceInfoEntity("Wi-Fi Mac Address",
                DeviceTool.getDeviceToolInstance().getMac());
        items.add(item7);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        //mAdapter.notifyDataSetChanged();
        initData();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter=new DeviceInfoActivityAdapter(items,this);
        recyclerView.setAdapter(mAdapter);

    }

    /**
     * 跳转到这个Activity
     * @param mActivity
     */
    public static void jumpToDeviceInfoActivity(Activity mActivity)
    {
        Intent intent = new Intent(mActivity, DeviceInfoActivity.class);
        //intent.setData(Uri.parse(item.getUrl()==null?"":item.getUrl()));
        //intent.putExtra("url", item.getLink());
        //intent.putExtra("desc", item.getDescription());
        //intent.putExtra("source", item.getSource());
        mActivity.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                finish();
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
