package com.jerry.littlepanda.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.domain.EntryImage;
import com.jerry.littlepanda.domain.OutputJson;
import com.jerry.littlepanda.domain.SyndEntry;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.domain.YiDianNewsResponse;
import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoEntry;
import com.jerry.littlepanda.video.VideoFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * Created by jerry.hu on 18/07/17.
 */

public class NetWork {
    private static String TAG = "NetWork";
    public static String getHttpData(String baseUrl){
        return getHttpData(baseUrl, "GET", "", null);
    }
    public static String postHttpData(String baseUrl, String reqData){
        return getHttpData(baseUrl, "POST", reqData, null);
    }
    public static String postHttpData(String baseUrl, String reqData, HashMap<String, String> propertys){
        return getHttpData(baseUrl, "POST", reqData, propertys);
    }
    /**
     * 获取赛事信息
     * @return
     */
    public static String getHttpData(String baseUrl, String method, String reqData, HashMap<String, String> propertys){
        String data = "", str;
        PrintWriter outWrite = null;
        InputStream inpStream = null;
        BufferedReader reader = null;
        HttpURLConnection urlConn = null;
        try{
            URL url = new URL(baseUrl);
            urlConn = (HttpURLConnection)url.openConnection();
            //启用gzip压缩
            urlConn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConn.setRequestMethod(method);
            urlConn.setDoOutput(true);
            urlConn.setConnectTimeout(3000);
            if(propertys != null && !propertys.isEmpty()){
                Iterator<Map.Entry<String, String>> props = propertys.entrySet().iterator();
                Map.Entry<String, String> entry;
                while (props.hasNext()){
                    entry = props.next();
                    urlConn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            outWrite = new PrintWriter(urlConn.getOutputStream());
            outWrite.print(reqData);
            outWrite.flush();
            urlConn.connect();
            //获取数据流
            inpStream = urlConn.getInputStream();
            String encode = urlConn.getHeaderField("Content-Encoding");
            //如果通过gzip
            if(encode !=null && encode.indexOf("gzip") != -1){
                Log.v(TAG, "get data :" + encode);
                inpStream = new GZIPInputStream(inpStream);
            }else if(encode != null && encode.indexOf("deflate") != -1){
                inpStream = new InflaterInputStream(inpStream);
            }
            reader = new BufferedReader(new InputStreamReader(inpStream));
            while((str = reader.readLine()) != null){
                data += str;
            }
        }catch (MalformedURLException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }finally{
            if(reader !=null && urlConn!=null){
                try {
                    outWrite.close();
                    inpStream.close();
                    reader.close();
                    urlConn.disconnect();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        Log.d(TAG, "[Http data]["+baseUrl+"]:" + data);
        return data;
    }
    /**
     * 获取Image信息
     * @return
     */
    public static Bitmap getBitmapData(String imgUrl){
        Bitmap bmp = null;
        Log.d(TAG, "get imgage:"+imgUrl);
        InputStream inpStream = null;
        try{
            HttpGet http = new HttpGet(imgUrl);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = (HttpResponse)client.execute(http);
            HttpEntity httpEntity = response.getEntity();
            BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
            //获取数据流
            inpStream = bufferedHttpEntity.getContent();
            bmp = BitmapFactory.decodeStream(inpStream);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            if(inpStream !=null){
                try {
                    inpStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return bmp;
    }
    /**
     * 获取url的InputStream
     * @param urlStr
     * @return
     */
    public static InputStream getInputStream(String urlStr){
        Log.d(TAG, "get http input:"+urlStr);
        InputStream inpStream = null;
        try{
            HttpGet http = new HttpGet(urlStr);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = (HttpResponse)client.execute(http);
            HttpEntity httpEntity = response.getEntity();
            BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
            //获取数据流
            inpStream = bufferedHttpEntity.getContent();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            if(inpStream !=null){
                try {
                    inpStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return inpStream;
    }

}
