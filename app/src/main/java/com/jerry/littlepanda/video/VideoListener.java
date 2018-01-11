package com.jerry.littlepanda.video;

import android.util.Log;

import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;

/**
 * Created by shuyu on 2016/11/23.
 */

public class VideoListener implements StandardVideoAllCallBack {

     private static final String TAG="VideoListener";

    //加载成功，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onPrepared(String url, Object... objects) {
        Log.e(TAG,"onPrepared");
    }

    //点击了开始按键播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickStartIcon(String url, Object... objects) {
        Log.e(TAG,"onClickStartIcon");
    }

    //点击了错误状态下的开始按键，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickStartError(String url, Object... objects) {
        Log.e(TAG,"onClickStartError");
    }

    //点击了播放状态下的开始按键--->停止，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickStop(String url, Object... objects) {
        Log.e(TAG,"onClickStop");
    }

    //点击了全屏播放状态下的开始按键--->停止，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickStopFullscreen(String url, Object... objects) {
        Log.e(TAG,"onClickStopFullscreen");
    }

    //点击了暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickResume(String url, Object... objects) {
        Log.e(TAG,"onClickResume");
    }

    //点击了全屏暂停状态下的开始按键--->播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickResumeFullscreen(String url, Object... objects) {
        Log.e(TAG,"onClickResumeFullscreen");
    }

    //点击了空白弹出seekbar，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickSeekbar(String url, Object... objects) {
        Log.e(TAG,"onClickSeekbar");
    }

    //点击了全屏的seekbar，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickSeekbarFullscreen(String url, Object... objects) {
        Log.e(TAG,"onClickSeekbarFullscreen");
    }

    //播放完了，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onAutoComplete(String url, Object... objects) {
        Log.e(TAG,"onAutoComplete");
    }

    //进去全屏，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        Log.e(TAG,"onEnterFullscreen");
    }

    //退出全屏，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        Log.e(TAG,"onQuitFullscreen");
    }

    //进入小窗口，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onQuitSmallWidget(String url, Object... objects) {
        Log.e(TAG,"onQuitSmallWidget");
    }

    //退出小窗口，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onEnterSmallWidget(String url, Object... objects) {
        Log.e(TAG,"onEnterSmallWidget");
    }

    //触摸调整声音，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onTouchScreenSeekVolume(String url, Object... objects) {
        Log.e(TAG,"onTouchScreenSeekVolume");
    }

    //触摸调整进度，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onTouchScreenSeekPosition(String url, Object... objects) {
        Log.e(TAG,"onTouchScreenSeekPosition");
    }

    //触摸调整亮度，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onTouchScreenSeekLight(String url, Object... objects) {
        Log.e(TAG,"onTouchScreenSeekLight");
    }

    //播放错误，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onPlayError(String url, Object... objects) {
        Log.e(TAG,"onPlayError");
    }

    //点击了空白区域开始播放，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickStartThumb(String url, Object... objects) {
        Log.e(TAG,"onClickStartThumb");
    }

    //点击了播放中的空白区域，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickBlank(String url, Object... objects) {
        Log.e(TAG,"onClickBlank");
    }

    //点击了全屏播放中的空白区域，objects[0]是title，object[1]是当前所处播放器（全屏或非全屏）
    @Override
    public void onClickBlankFullscreen(String url, Object... objects) {
        Log.e(TAG,"onClickBlankFullscreen");
    }
}
