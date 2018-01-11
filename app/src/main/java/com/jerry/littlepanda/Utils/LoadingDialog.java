package com.jerry.littlepanda.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.jerry.littlepanda.R;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by jerry.hu on 20/08/17.
 */

public class LoadingDialog {

    // 本类的单例实例
    //private static LoadingDialog instance;
    private static Dialog progressDialog;
    /*
    // 获取本类单例对象的方法
    public static synchronized LoadingDialog getInstance(Activity context) {
        if (instance == null) {
            synchronized (LoadingDialog.class) {
                if (instance == null) {
                    instance = new LoadingDialog(context);
                }
            }
        }
        return instance;
    }*/

    //private LoadingDialog(){}
   // private LoadingDialog(Activity context){this.context=context;}

    public static void show(Activity context)
    {
        if(progressDialog==null||!progressDialog.isShowing()||!context.isFinishing()||context!=null)
        {
            progressDialog = new Dialog(context,R.style.progress_dialog);
            progressDialog.setContentView(R.layout.loading_dialog_layout);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            //msg.setText("卖力加载中");
            GifImageView gifImageView1 = (GifImageView) progressDialog.findViewById(R.id.gifiv);
            try {
                // 如果加载的是gif动图，第一步需要先将gif动图资源转化为GifDrawable
                // 将gif图资源转化为GifDrawable
                GifDrawable gifDrawable = new GifDrawable(context.getResources(), R.drawable.lodingview2);

                // gif1加载一个动态图gif
                gifImageView1.setImageDrawable(gifDrawable);


                // 如果是普通的图片资源，就像Android的ImageView set图片资源一样简单设置进去即可。
                // gif2加载一个普通的图片（如png，bmp，jpeg等等）
                //gifImageView2.setImageResource(R.drawable.ic_launcher);
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.show();
        }
    }

    public static void dismiss()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            //progressDialog.cancel();
            progressDialog=null;
        }
    }

}
