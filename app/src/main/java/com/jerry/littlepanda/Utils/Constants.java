package com.jerry.littlepanda.Utils;

import com.jerry.littlepanda.domain.YiDianNewsItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by jerry.hu on 01/08/17.
 */

public class Constants {

    // user your appid the key.
    public static final String XIAOMI_PUSH_APP_ID = "2882303761517608352";
    // user your appid the key.
    public static final String XIAOMI_PUSH_APP_KEY = "5461760828352";

    public static final String BAIDU_API_KEY="DPBI6GTgxGpa9oRKL05Od7oRrrULZCZx";

    public static final String WEATHER_API_KEY="uyaxtfvkjwfg0c4h";

    //天气接口
    public static final String WEATHER_API_URL="https://api.seniverse.com/";

    //获取IP的接口
    public static final String IP_API_URL="http://pv.sohu.com";

    //自己的服务器地址
    public static final String INTOADS_URL="http://47.104.98.179:8080/";

    public static final String YIDIANZIXUNBASEURL="http://www.yidianzixun.com";

    public static final String ENABLE_READER_TAB="IsEnableReader";
    public static final String ENABLE_SPLASH="IsEnableSplash";

    //保存定位的位置信息
    public static final String LATITUDE="latitude";
    public static final String LONTITUDE="lontitude";
    public static final String COUNTRY="country";
    public static final String CITY="city";
    public static final String DISTRICT="district";
    public static final String STREET="street";
    public static final String ADDR="address";
    public static final String LOCALIP="localip";


    //视频的队列
    //public static final Queue<List<YiDianNewsItem>> videoQueue=new LinkedList<List<YiDianNewsItem>>();


    public static final Boolean isDebug=true;          //是否是测试模式

    public static final String VOLLEY_TAG= "VOLLEY_TAG";
    public static final String LOG_TAG="jerry.android=";
    public static final String APIHOST="http://192.168.13.206:8080/ssm_project/api/v1";
    public static final String UPLOAD_DEVICE_API=APIHOST+"/addDevice";//更新用户信息的接口
    public static final String GETRSS=APIHOST+"/getrss?pageNum="; //获取信息流的接口
    public static final String GETDETAIL=APIHOST+"/detail?id="; //获取信息流的详细页面

    public static final String NEWS_FREE_HOT_WORDS="http://api.avatardata.cn/ActNews/LookUp?key=6796fddd04c34372897283b64549fbcd";
    public static final String NEWS_FREE_HOT_WORDS_FEED="http://api.avatardata.cn/ActNews/Query?key=6796fddd04c34372897283b64549fbcd&keyword=";
}
