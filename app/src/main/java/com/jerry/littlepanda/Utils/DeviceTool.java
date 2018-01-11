package com.jerry.littlepanda.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.domain.NetIP;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;
import com.jerry.littlepanda.network.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jerry.hu on 31/07/17.
 */

public class DeviceTool {
    private Context context;
    private TelephonyManager tm;

    private static DeviceTool deviceTool=null;

    private DeviceTool() {
        this.context = APPAplication.getContext();
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static DeviceTool getDeviceToolInstance() {

        if(deviceTool == null){
            synchronized (DeviceTool.class){
                if (deviceTool == null){
                    deviceTool = new DeviceTool();
                }
            }
        }
        return deviceTool;
    }

    /**
     * 获取设备的UUID
     *
     * @param context
     * @return
     */
    public String getDeviceUUID(Context context) {

        UUID deviceUuid = new UUID(getAndroidId().hashCode(), ((long) getDeviceId().hashCode() << 32) | getSimSerialNumber().hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 获取设备DeviceId IMEI
     *
     * @return
     */
    public String getDeviceId() {
        return "" + tm.getDeviceId();
    }

    /**
     * 获取设备SIM卡序列号
     *
     * @return
     */
    public String getSimSerialNumber() {
        return "" + tm.getSimSerialNumber();
    }

    /**
     * 获取设备的Android ID
     *
     * @return
     */
    public String getAndroidId() {
        String androidId;
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /*
   * 设备的软件版本号：
   * 例如：the IMEI/SV(software version) for GSM phones.
   * Return null if the software version is not available.
   */
    public String getDeviceSoftwareVersion() {
        return tm.getDeviceSoftwareVersion();//String
    }

    /*
   * 手机号：
   * GSM手机的 MSISDN.
   * Return null if it is unavailable.
   */
    public String getPhoneNumber() {
        return tm.getLine1Number();
    }

    /*
      * 当前使用的网络类型：
      * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
        NETWORK_TYPE_GPRS     GPRS网络  1
        NETWORK_TYPE_EDGE     EDGE网络  2
        NETWORK_TYPE_UMTS     UMTS网络  3
        NETWORK_TYPE_HSDPA    HSDPA网络  8
        NETWORK_TYPE_HSUPA    HSUPA网络  9
        NETWORK_TYPE_HSPA     HSPA网络  10
        NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
        NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
        NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
        NETWORK_TYPE_1xRTT    1xRTT网络  7
  */
    public int getNetworkType() {
        return tm.getNetworkType();//int
    }

    /*
     * 手机类型：
     * 例如： PHONE_TYPE_NONE  无信号
       PHONE_TYPE_GSM   GSM信号
       PHONE_TYPE_CDMA  CDMA信号
   */
    public int getPhoneType() {
        return tm.getPhoneType();//int
    }

    /*
   * 服务商名称：
   * 例如：中国移动、联通
   * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
   */
    public String getSimOperatorName() {
        return tm.getSimOperatorName();//String
    }

    /**
     * make true current connect service is wifi
     *
     * @return
     */
    public boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否连接网络 WIFI和Network
     * @return
     */
    public boolean checkNetworkConnection() {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 判断Network是否已经连接
     * @return
     */
    public boolean isMobileConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   //获取移动网络信息
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isAvailable();    //getState()方法是查询是否连接了数据网络
        }
        return false;
    }

    /**
     * 获取浏览器 User-Agent
     * @return
     */
    public String getUserAgent()
    {
        WebView webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        String ua = settings.getUserAgentString();
        return ua;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取当前应用程序的包名
     *
     * @return 返回包名
     */
    public String getAppPackageName() {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
    /**
     * 获取程序 图标
     *
     * @param packname 应用包名
     * @return
     */
    public Drawable getAppIcon(String packname){
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            //获取到应用信息
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取程序的版本号
     *
     * @param packname
     * @return
     */
    public String getAppVersion(String packname){
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(packname, 0);
            return packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return packname;
    }


    /**
     * 获取程序的名字
     *
     * @param packname
     * @return
     */
    public String getAppName(String packname){
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return packname;
    }
    /*
     * 获取程序的权限
     */
    public String[] getAllPermissions(String packname){
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo =    pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
            //获取到所有的权限
            return packinfo.requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }


    /**
     * 获取程序的签名
     *
     * @param packname
     * @return
     */
    public String getAppSignature(String packname){
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            //获取当前应用签名
            return packinfo.signatures[0].toCharsString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return packname;
    }

    /**
     *
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *  Thread thread = new Thread(){
     *        @Override
              protected String doInBackground(Void... params){

              };
     *  }
     *  thread .start();
     *
     * @return String
     */
    public void getNetIp(){

        API.getIPApi().getIP("utf-8")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //Log.e("IP=","IP onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        // var returnCitySN = {"cip": "101.38.155.247", "cid": "CN", "cname": "CHINA"};
                        if(null!=s&&!s.equals(""))
                        {
                            String jsonStr=s.replace("var returnCitySN =","").replace(";","").trim();
                            if(null!=jsonStr&&!jsonStr.equals(""))
                            {
                                try{
                                    NetIP netIP= new GsonBuilder()
                                            .disableHtmlEscaping()
                                            .create().fromJson(jsonStr, NetIP.class);
                                    //Log.e("IP=","onNext:"+netIP.toString());
                                    if(null!=netIP)
                                    {
                                        SharedPreUtils.getInstance().putString(Constants.LOCALIP,netIP.getCip());
                                    }
                                }catch(IllegalStateException e){

                                }

                            }
                        }
                        //Log.e("IP=","onNext:"+s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //Log.e("IP=","onError："+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Log.e("IP=","onComplete");
                    }
                });

        /*
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                URL infoUrl = null;
                String line = "";
                InputStream inStream = null;
                try {
                    infoUrl = new URL(params[0].toString());
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        while ((line = reader.readLine()) != null)
                            strber.append(line + "\n");
                        inStream.close();
                        // 从反馈的结果中提取出IP地址
                        int start = strber.indexOf("{");
                        int end = strber.indexOf("}");
                        String json = strber.substring(start, end + 1);
                        if (json != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                line = jsonObject.optString("cip");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        return line;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return line;
            }

            @Override
            protected void onPostExecute(Object o) {
                SharedPreUtils.getInstance().putString(Constants.LOCALIP,o.toString());
                super.onPostExecute(o);
            }
        }.execute("http://pv.sohu.com/cityjson?ie=utf-8");
        */
    }

    /**
     * 获取屏幕的宽高
     * @return 【width,height】
     */
    public int[] getScreenSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    /**
     * 获取手机的MAC地址
     *
     * @return
     */
    public String getMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return macSerial;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }


    // 判断是否是第一次启动程序 利用 SharedPreferences 将数据保存在本地
    public boolean isFristRun() {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "share", MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            //保存数据 （第三步）
            editor.putBoolean("isFirstRun", false);
            //提交当前数据 （第四步）
            editor.commit();
            return true;
        }
    }

    /**
     * 格式化Json数据
     * @param strJson
     * @return
     */
    public String stringToJSON(String strJson) {
        // 计数tab的个数
        int tabNum = 0;
        StringBuffer jsonFormat = new StringBuffer();
        int length = strJson.length();

        char last = 0;
        for (int i = 0; i < length; i++) {
            char c = strJson.charAt(i);
            if (c == '{') {
                tabNum++;
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            }
            else if (c == '}') {
                tabNum--;
                jsonFormat.append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
                jsonFormat.append(c);
            }
            else if (c == ',') {
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            }
            else if (c == ':') {
                jsonFormat.append(c + " ");
            }
            else if (c == '[') {
                tabNum++;
                char next = strJson.charAt(i + 1);
                if (next == ']') {
                    jsonFormat.append(c);
                }
                else {
                    jsonFormat.append(c + "\n");
                    jsonFormat.append(getSpaceOrTab(tabNum));
                }
            }
            else if (c == ']') {
                tabNum--;
                if (last == '[') {
                    jsonFormat.append(c);
                }
                else {
                    jsonFormat.append("\n" + getSpaceOrTab(tabNum) + c);
                }
            }
            else {
                jsonFormat.append(c);
            }
            last = c;
        }
        return jsonFormat.toString();
    }

    // 是空格还是tab
    private String getSpaceOrTab(int tabNum) {
        StringBuffer sbTab = new StringBuffer();
        for (int i = 0; i < tabNum; i++) {
            sbTab.append('\t');
        }
        return sbTab.toString();
    }

    public boolean isGoodJson(String json) {
        if (null==json||json.equals("")) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            new JSONObject(json);
            return true;
        } catch (JsonParseException e) {
            //logger.error("bad json: " + json);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * s上传用户信息
     */
    public void uploadDevice()
    {
        /*
        //上传设备信息
        //final DeviceTool deviceTool=DeviceTool.getDeviceToolInstance(MainActivity.this);
        final String ua=deviceTool.getUserAgent();
        if(deviceTool.isFristRun()&&deviceTool.checkNetworkConnection())
        {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    UniqueUser uu=new UniqueUser();
                    String pkhname=deviceTool.getAppPackageName();
                    uu.setBundle_id(pkhname);
                    int[] wh=deviceTool.getScreenSize();
                    uu.setDevice_h(wh[1]+"");
                    uu.setDevice_w(wh[0]+"");
                    Map<String, String> hashmap=new HashMap<>();
                    uu.setDevice_ip(deviceTool.getNetIp());
                    uu.setAndroid_id(deviceTool.getAndroidId());

                    uu.setImei(deviceTool.getDeviceId());
                    uu.setUseragent(ua);
                    uu.setAndroid_version(deviceTool.getSystemVersion());
                    uu.setDevice_model(deviceTool.getDeviceBrand());
                    uu.setBundle_id(pkhname);
                    uu.setApp_version(deviceTool.getAppVersion(pkhname));
                    uu.setCarrier(deviceTool.getSimOperatorName());

                    hashmap.put("uniqueUser",new GsonBuilder().create().toJson(uu));

                    NetWork.postObjectApi(Constants.UPLOAD_DEVICE_API, hashmap, new ResponseListener<OutputJson>() {
                        @Override
                        public void onResponse(OutputJson response) {
                            Log.e(Constants.LOG_TAG,response.getMessage()+"=="+response.getStatus());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(Constants.LOG_TAG,error.getMessage()+"");
                        }
                    });
                }
            });
            thread .start();

        }*/
    }

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /**
     * 判断是否点击过快
     * @return true 就是处理点击事件
     */
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
