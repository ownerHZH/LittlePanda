package com.jerry.littlepanda.readrss;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.domain.YiDianNewsResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver
 * Created by jaycee on 2017/6/23.
 */
public abstract class BaseObserver implements Observer<YiDianNewsResponse> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        if(context!=null)
        {
            this.mContext = context.getApplicationContext();
        }else
        {
            this.mContext= APPAplication.getContext();
        }

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(YiDianNewsResponse value) {
        if (value.getCode()==0) {
            List<YiDianNewsItem> t = value.getResult();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getStatus());
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

    protected abstract void onHandleSuccess(List<YiDianNewsItem> t);

    protected void onHandleError(String msg) {
        if(null!=mContext)
        {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
