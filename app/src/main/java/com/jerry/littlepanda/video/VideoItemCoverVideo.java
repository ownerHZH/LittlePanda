package com.jerry.littlepanda.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jerry.littlepanda.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;

/**
 * 带封面
 * Created by guoshuyu on 2017/9/3.
 */

public class VideoItemCoverVideo extends StandardGSYVideoPlayer {

    ImageView mCoverImage;

    String mUrl;

    int mDefaultRes;

    public VideoItemCoverVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VideoItemCoverVideo(Context context) {
        super(context);
    }

    public VideoItemCoverVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //显示图片的配置
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .showImageOnLoading(R.drawable.video_loading_wait) //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.drawable.video_loading_wait)//设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.video_loading_wait)  //设置图片加载/解码过程中错误时候显示的图片
            .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
            .build();

    @Override
    protected void init(Context context) {
        super.init(context);
        mCoverImage = (ImageView) findViewById(R.id.thumbImage);
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_cover;
    }

    public void loadCoverImage(String url, int res) {
        mUrl = url;
        mDefaultRes = res;
        setImageValue(mCoverImage,url);
    }

    private void setImageValue(ImageView img, String value) {
        //处理下拉图片错位
        img.setTag(value);
        ImageAware imageAware = new ImageViewAware(img, false);
        ImageLoader.getInstance().displayImage(value,imageAware,options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (null!=view.getTag()&&!imageUri.equals(view.getTag().toString())) {
                    // 设置默认图片
                    ((ImageView)view).setImageResource(R.drawable.video_loading_wait);
                }else
                {
                    ((ImageView)view).setImageBitmap(loadedImage);
                }
            }
        });
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        VideoItemCoverVideo sampleCoverVideo = (VideoItemCoverVideo) gsyBaseVideoPlayer;
        sampleCoverVideo.loadCoverImage(mUrl, mDefaultRes);
        return gsyBaseVideoPlayer;
    }

    public void setFullScreenPlay()
    {
        Log.e("VideoItemCoverVideo","setFullScreenPlay");
        if (!mHadPlay) {
            return;
        }
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        changeTextureViewShowType();
        if (mTextureView != null)
            mTextureView.requestLayout();
    }
}
