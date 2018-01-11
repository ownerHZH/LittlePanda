package com.jerry.littlepanda.video;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoEntry;
import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoItem;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver implements Observer<DfTouTiaoVideoEntry> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(DfTouTiaoVideoEntry value) {
        if (value.getData()!=null) {
            List<DfTouTiaoVideoItem> t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError("暂无数据");
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError:" + e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(List<DfTouTiaoVideoItem> t);

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
