package com.jerry.littlepanda;

import android.*;
import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.crazysunj.cardslideview.CardViewPager;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.domain.weather.Now;
import com.jerry.littlepanda.domain.weather.Suggestion;
import com.jerry.littlepanda.domain.weather.Weather;
import com.jerry.littlepanda.domain.weather.WeatherMap;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;
import com.jerry.littlepanda.ireader.utils.SystemBarUtils;
import com.jerry.littlepanda.location.LocationService;
import com.jerry.littlepanda.network.API;
import com.jerry.littlepanda.readrss.BaseObserver;
import com.jerry.littlepanda.readrss.MyCardHandler;
import com.jerry.littlepanda.readrss.NewsChannel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.jerry.littlepanda.APPAplication.getContext;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private CardViewPager viewPager;
    private RelativeLayout wallpaper;
    public final static int CARDONCLICK = 1234;
    private MyCardHandler myCardHandler;
    private TextView splash_tv_skip;
    private TextView temperature,weathertext,uv,dressing,car_washing;
    private ImageView weathericon;
    WallpaperManager wallpaperManager;
    private LocationService locationService;
    private final static String TAG="Splash=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        SystemBarUtils.transparentStatusBar(this);

        //判断是否允许Splash
        if (SharedPreUtils.getInstance().getBoolean(Constants.ENABLE_SPLASH, false)) {
            jumpToMainActivity();
        } else {
            setContentView(R.layout.activity_splash);

            //初始化View
            initView();

            //获取卡片新闻信息
            getYiDianZiXunNews();

            //获取经纬度信息
            getLngAndLat();

            //获取天气信息
            getWeather();

            //获取生活指数信息
            getLifeSuggestion();

            //保存Loacal IP
            DeviceTool.getDeviceToolInstance().getNetIp();
        }
    }

    private void initView() {
        wallpaper = (RelativeLayout) findViewById(R.id.wallpaper);
        splash_tv_skip = (TextView) findViewById(R.id.splash_tv_skip);
        splash_tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMainActivity();
                MiStatInterface.recordCountEvent("Button_Click", "Button_Skip_click");
            }
        });

        //setWallPaper(wallpaper);

        temperature=(TextView) findViewById(R.id.temperature);
        weathertext=(TextView) findViewById(R.id.weathertext);
        weathericon=(ImageView) findViewById(R.id.weathericon);

        car_washing=(TextView) findViewById(R.id.car_washing);
        uv=(TextView) findViewById(R.id.uv);
        dressing=(TextView) findViewById(R.id.dressing);

        //setWallPaper(wallpaper);

        viewPager = (CardViewPager) findViewById(R.id.viewpager);
        myCardHandler = new MyCardHandler(SplashActivity.this);

    }

    /**
     * 获取天气信息
     * 从保存的经纬度获取信息，默认北京市经纬度
     */
    private void getWeather() {
        API.getWeatherApi()
                .getWeather(Constants.WEATHER_API_KEY
                        , SharedPreUtils.getInstance().getString(Constants.LATITUDE,"39.54")
                                +":"
                                +SharedPreUtils.getInstance().getString(Constants.LONTITUDE,"116.23"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //Log.e(TAG,"Weather onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Weather weather) {

                        //更新UI信息
                        updateWeatherOnUI(weather);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //Log.e(TAG,"Weather onError"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Log.e(TAG,"Weather onComplete");
                    }
                });
    }

    /**
     * 获取生活指数 相关信息
     */
    private void getLifeSuggestion() {
        API.getWeatherApi()
                .getLifeSuggestion(Constants.WEATHER_API_KEY
                        , SharedPreUtils.getInstance().getString(Constants.LATITUDE,"39.54")
                                +":"
                                +SharedPreUtils.getInstance().getString(Constants.LONTITUDE,"116.23"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //Log.e(TAG,"Weather onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Weather weather) {

                        //更新UI信息
                        updateWeatherOnUI(weather);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //Log.e(TAG,"Weather onError"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Log.e(TAG,"Weather onComplete");
                    }
                });
    }

    /**
     * 更新天气到UI
     * @param weather
     */
    private void updateWeatherOnUI(@NonNull Weather weather) {
        if(null!=weather
                &&null!=weather.getResults()
                &&null!=weather.getResults().get(0))
        {
            if(null!=weather.getResults().get(0).getNow())
            {
                Now now=weather.getResults().get(0).getNow();
                WeatherMap weatherMap=WeatherMap.getWeatherMap(Integer.parseInt(now.getCode()));
                //Log.e(TAG,now.toString());
                temperature.setText(now.getTemperature()+"℃");
                weathertext.setText(weatherMap.getCode_name_zh());
                weathericon.setImageResource(weatherMap.getIcon());
            }

            if(null!=weather.getResults().get(0).getSuggestion())
            {
                Suggestion suggestion=weather.getResults().get(0).getSuggestion();
                car_washing.setText("洗车："+suggestion.getCarWashing().getBrief());
                dressing.setText("穿着："+suggestion.getDressing().getBrief());
                uv.setText("紫外线："+suggestion.getUv().getBrief());
            }

        }
    }

    //跳转到主界面
    private void jumpToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    /**
     * 随机获取卡片新闻信息
     */
    private String channelName = NewsChannel.getRandomNewsChannel().getChannelId();
    private void getYiDianZiXunNews() {

        long time = System.currentTimeMillis();//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        int cstart = 0;
        int cend = 20;

        API.getYiDianZiXunApi().search(
                "" + cstart,
                "" + cend,
                channelName,
                true,
                1,
                "pc",
                5,
                "web_yidian",
                str)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver(getContext()) {
                    @Override
                    protected void onHandleSuccess(List<YiDianNewsItem> t) {
                        if (null != t) {
                            for (int i = 0; i < t.size(); i++) {
                                if (t.get(i) == null ||
                                        t.get(i).getUrl() == null ||
                                        t.get(i).getUrl().trim() == "" ||
                                        t.get(i).getTitle() == null ||
                                        t.get(i).getTitle().trim() == "" ||
                                        t.get(i).getImage() == null ||
                                        t.get(i).getImage().trim() == "" ||
                                        t.get(i).getImage_urls() == null ||
                                        t.get(i).getImage_urls().size() <= 0) {
                                    t.remove(i);
                                }
                            }
                            viewPager.bind(getSupportFragmentManager(), myCardHandler, t);
                        } else {
                            jumpToMainActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        jumpToMainActivity();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    public void setWallPaper(View layout) {
        layout.setBackgroundResource(R.drawable.wallpaper);
        /*
        // 获取壁纸管理器
        if(null==wallpaperManager)
        {
            wallpaperManager = WallpaperManager
                    .getInstance(APPAplication.getContext());
        }
          // 获取当前壁纸
          Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        if (null!=wallpaperDrawable)
        {
            Bitmap bitmap = drawableToBitmap(wallpaperDrawable);
            WallpaperDrawable wd = new WallpaperDrawable();
            wd.setBitmap(bitmap);
            layout.setBackground(wallpaperDrawable);
        }else
        {
            layout.setBackgroundResource(R.drawable.wallpaper);
        }
        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        jumpToMainActivity();
        MiStatInterface.recordCountEvent("Button_Click", "Button_BackPressed_click");
    }

    @Override
    protected void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(SplashActivity.this, "SplashActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MiStatInterface.recordPageEnd();
    }

    @Override
    protected void onDestroy() {
        if (null != myCardHandler) {
            myCardHandler = null;
        }
        super.onDestroy();
        System.gc();
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    static class WallpaperDrawable extends Drawable {


        Bitmap mBitmap;
        int mIntrinsicWidth;
        int mIntrinsicHeight;

        public void setBitmap(Bitmap bitmap) {

            mBitmap = bitmap;

            if (mBitmap == null)

                return;

            mIntrinsicWidth = mBitmap.getWidth();

            mIntrinsicHeight = mBitmap.getHeight();

        }

        @Override

        public void draw(Canvas canvas) {

            if (mBitmap == null) return;

            int width = canvas.getWidth();

            int height = canvas.getHeight();
            int x = (width - mIntrinsicWidth) / 2;
            int y = (height - mIntrinsicHeight) / 2;
            canvas.drawBitmap(mBitmap, x, y, null);
        }

        @Override

        public int getOpacity() {

            return android.graphics.PixelFormat.OPAQUE;

        }

        @Override

        public void setAlpha(int alpha) {

            // Ignore

        }


        @Override

        public void setColorFilter(ColorFilter cf) {

            // Ignore

        }
    }

    /**
     * 获取经纬度
     * @return
     */
    private void getLngAndLat() {
        RxPermissions rxPermissions = new RxPermissions(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Log.e(TAG,"GPS 检查");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.ACCESS_COARSE_LOCATION},127);
            }
        }

        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        //Log.e(TAG,"GPS granted");
                        // I can control the camera now
                        // -----------location config ------------
                        locationService = ((APPAplication) getApplication()).locationService;
                        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
                        locationService.registerListener(mListener);
                        //注册监听
                        int type = getIntent().getIntExtra("from", 0);
                        if (type == 0) {
                            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                        } else if (type == 1) {
                            locationService.setLocationOption(locationService.getOption());
                        }
                        locationService.start();// 定位SDK
                    } else {
                        // Oups permission denied
                        Toast.makeText(this, "无法获取地址位置信息", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        if(null!=locationService)
        {
            if(null!=mListener)
            {
                locationService.unregisterListener(mListener); //注销掉监听
            }
            locationService.stop(); //停止定位服务
        }

        super.onStop();
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                //StringBuffer sb = new StringBuffer(256);
                //sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */

                /*
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.e(TAG,sb.toString());
                */

                //保存本地位置信息
                saveLocation(location);
            }
        }

    };

    /**
     * 保存定位信息到本地
     * @param location
     */
    private void saveLocation(BDLocation location) {
        SharedPreUtils.getInstance().putString(Constants.LATITUDE,new Double(location.getLatitude()).toString());
        SharedPreUtils.getInstance().putString(Constants.LONTITUDE,new Double(location.getLongitude()).toString());
        SharedPreUtils.getInstance().putString(Constants.COUNTRY,location.getCountry());
        SharedPreUtils.getInstance().putString(Constants.CITY,location.getCity());
        SharedPreUtils.getInstance().putString(Constants.DISTRICT,location.getDistrict());
        SharedPreUtils.getInstance().putString(Constants.STREET,location.getStreet());
        SharedPreUtils.getInstance().putString(Constants.ADDR,location.getAddrStr());
    }
}
