package com.jerry.littlepanda.ireader.utils;

import android.widget.Toast;

import com.jerry.littlepanda.APPAplication;

/**
 * Created by newbiechen on 17-5-11.
 */

public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(APPAplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
