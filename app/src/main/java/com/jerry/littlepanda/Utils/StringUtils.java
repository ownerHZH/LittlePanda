package com.jerry.littlepanda.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jerry.littlepanda.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jerry.hu on 18/07/17.
 */

public class StringUtils {

    public static StringBuffer filterImages(String compatText){
        StringBuffer uList = new StringBuffer();
        if(!TextUtils.isEmpty(compatText)&&compatText.contains("<img")){
            /*
            //get img src
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*[‘\"]([^‘\"]+)[‘\"][^>]*>");//<img[^<>]*src=[\‘\"]([0-9A-Za-z.\\/]*)[\‘\"].(.*?)>");
            Matcher m = p.matcher(compatText);
            String searchAttrib = "src";
            String regxpForTagAttrib = searchAttrib + "\\s*=\\s*[\"|‘]http://([^\"|‘]+)[\"|‘]";//"=[\"|‘]([^[\"|‘]]+)[\"|‘]";
            Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
            while(m.find()){
                Matcher matcherForAttrib = patternForAttrib.matcher(m.group());
                if (matcherForAttrib.find()) {
                    //Log.e("TAG=",matcherForAttrib.group(1));
                    uList.append("<img src=\"https://" +matcherForAttrib.group(1)+"\">");
                }
            }*/

            String reg = "<img.*?>";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(compatText);//sendString为网页源码
//使用find()方法查找第一个匹配的对象
            boolean result = matcher.find();
//使用循环找出 html里所有的img标签
            int i=1;
            while(result&&i<4) {
                //继续查找下一个匹配对象
                //Log.e("TAG=",matcher.group());
                uList.append(matcher.group());
                result = matcher.find();
                i++;
            }
        }
        return  uList;
    }

    /***
     * 获取ImageUrl地址
     *
     * @param HTML
     * @return
     */
    public static List<String> getImageUrl(String HTML) {
        /*获取img整个tag
        Matcher matcher = Pattern.compile("<img.*src=(.*?)[^>]*?>").matcher(HTML);
        List<String> listImgUrl = new ArrayList<String>();
        //最多只获取三张图片
        int i=0;
        while (matcher.find()&&i<4) {
            i++;
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
        */
        //只获取src中的url链接
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(HTML);
        int i=0;
        while (m_image.find()&&i<4) {
            i++;
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    //Json反序列化为列表
    public static <T> ArrayList<T> jsonToList(String json, Class<T> classOfT) {
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjs = new GsonBuilder()
                .disableHtmlEscaping()
                .create().fromJson(json, type);

        ArrayList<T> listOfT = new ArrayList<>();
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(new Gson().fromJson(jsonObj, classOfT));
        }

        return listOfT;
    }

    public static <T> Object fromJson(String src,Class<T> clazz)
    {
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.fromJson(src, clazz);
    }

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decodeBase64(inputData.getBytes("utf-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    public static String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encodeBase64(inputData.getBytes("utf-8")), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
