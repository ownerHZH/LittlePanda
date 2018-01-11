package com.jerry.littlepanda.Utils;

import android.content.Context;
import android.content.res.Resources;

import com.jerry.littlepanda.R;

/**
 * Created by jerry.hu on 26/08/17.
 * https://www.zhihu.com/question/41108584/answer/89621912
 */

public class ADFilterTool {
    public static boolean hasAd(Context context, String url){
        Resources res= context.getResources();
        String[]adUrls =res.getStringArray(R.array.adBlockUrl);
        for(String adUrl :adUrls){
            if(url.contains(adUrl)){
                return true;
            }
        }
        return false;
    }

    public static String getClearAdDivJs(Context context){
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockUrl);
        for(int i=0;i<adDivs.length;i++){
            js += "var adDiv"+i+"= document.getElementById('"+adDivs[i]+"');if(adDiv"+i+" != null)adDiv"+i+".parentNode.removeChild(adDiv"+i+");";
        }
        /*
        for(int i=0;i<adDivs.length;i++){
            js += "var adDiv"+i+"= document.querySelector('"+"."+adDivs[i]+"');if(adDiv"+i+" != null)adDiv"+i+".parentNode.removeChild(adDiv"+i+");";
        }*/
        return js;
    }

    public static String getClearAdDivJsByClass(Context context){
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockUrl);

        for(int i=0;i<adDivs.length;i++){
            js += "var adDiv"+i+"= document.querySelector('"+"."+adDivs[i]+"');if(adDiv"+i+" != null)adDiv"+i+".parentNode.removeChild(adDiv"+i+");";
        }
        return js;
    }
}