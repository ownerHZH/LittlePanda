package com.jerry.littlepanda.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.video.VideoFragment.OnListFragmentInteractionListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import static com.jerry.littlepanda.video.MyvideoRecyclerViewAdapter.ViewHolder.TAG;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyvideoRecyclerViewAdapter extends BaseRecyclerAdapter<MyvideoRecyclerViewAdapter.ViewHolder> {

    private final List<YiDianNewsItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    protected Context context = null;

    public List<YiDianNewsItem> getmValues() {
        return mValues;
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

    public MyvideoRecyclerViewAdapter(List<YiDianNewsItem> items, OnListFragmentInteractionListener listener,Context context) {
        mValues = items;
        mListener = listener;
        this.context=context;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, boolean isItem) {
        holder.mItem = mValues.get(position);

        holder.imageCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageValue(holder.imageCover,holder.mItem.getImage(true));
        if (holder.imageCover.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) holder.imageCover.getParent();
            viewGroup.removeView(holder.imageCover);
        }

        holder.videoTitle.setVisibility(View.VISIBLE);
        holder.videoSource.setVisibility(View.VISIBLE);
        holder.videoTitle.setText(holder.mItem.getTitle());
        holder.videoSource.setText(holder.mItem.getSource());

        holder.gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(holder.imageCover)
                .setUrl(holder.mItem.getVideo_url())
                .setVideoTitle(holder.mItem.getTitle())
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setStandardVideoAllCallBack(new VideoListener() {

                    @Override
                    public void onClickStartIcon(String url, Object... objects) {
                        holder.videoTitle.setVisibility(View.GONE);
                        holder.videoSource.setVisibility(View.GONE);
                    }
                    @Override
                    public void onClickStartThumb(String url, Object... objects) {
                        holder.videoTitle.setVisibility(View.GONE);
                        holder.videoSource.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        holder.videoTitle.setVisibility(View.VISIBLE);
                        holder.videoSource.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        holder.videoTitle.setVisibility(View.GONE);
                        holder.videoSource.setVisibility(View.GONE);
                        if (!holder.gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(false);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                }).build(holder.gsyVideoPlayer);


        //增加title
        holder.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        holder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        holder.gsyVideoPlayer.getFullscreenButton().setVisibility(View.GONE);
        holder.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resolveFullBtn(holder.gsyVideoPlayer);
            }
        });
    }

    public void insert(YiDianNewsItem item, int position) {
        insert(mValues, item, position);
    }

    public void remove(int position) {
        remove(mValues, position);
    }

    public void clear() {
        clear(mValues);
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

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        //((VideoItemCoverVideo)standardGSYVideoPlayer).setFullScreenPlay();
    }


    @Override
    public int getAdapterItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final static String TAG = "ViewHolder";
        public final StandardGSYVideoPlayer gsyVideoPlayer;
        public final ImageView imageCover;
        public final GSYVideoOptionBuilder gsyVideoOptionBuilder;
        public final TextView videoTitle,videoSource;
        public final View mView;
        public YiDianNewsItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageCover=new ImageView(view.getContext());
            gsyVideoPlayer=(VideoItemCoverVideo)view.findViewById(R.id.video_item_player);
            videoTitle =(TextView)view.findViewById(R.id.video_title);
            videoSource=(TextView)view.findViewById(R.id.video_soruce);
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        }

        @Override
        public String toString() {
            return super.toString() + " '" ;
        }
    }
}
