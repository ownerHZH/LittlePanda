package com.jerry.littlepanda.video;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.Utils.LoadingDialog;
import com.jerry.littlepanda.Utils.StringUtils;
import com.jerry.littlepanda.domain.OutputJson;
import com.jerry.littlepanda.domain.Video;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.domain.YiDianNewsResponse;
import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoEntry;
import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoItem;
import com.jerry.littlepanda.domain.inmobi.InmobiApiResponseObject;
import com.jerry.littlepanda.network.API;
import com.jerry.littlepanda.network.BaseFragment;
import com.jerry.littlepanda.readrss.NewsChannel;
import com.jerry.littlepanda.smileyloadingview.SmileyHeaderView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class VideoFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<YiDianNewsItem> items=new ArrayList<YiDianNewsItem>();
    private MyvideoRecyclerViewAdapter adapter;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    //FrameLayout videoFullContainer;
    //ListVideoUtil listVideoUtil;
    int lastVisibleItem;
    int firstVisibleItem;
    private LinearLayoutManager layoutManager;
    private int page=1;
    boolean mFull;
    private final static String TAG="VideoFragment";
    private VideoChannel tempVideoChannel;//记录当前的播放Channel，为了上拉加载更多使用

    public DfTouTiaoVideoEntry getDfTouTiaoVideoEntry() {
        return dfTouTiaoVideoEntry;
    }

    public void setDfTouTiaoVideoEntry(DfTouTiaoVideoEntry dfTouTiaoVideoEntry) {
        this.dfTouTiaoVideoEntry = dfTouTiaoVideoEntry;
    }

    private DfTouTiaoVideoEntry dfTouTiaoVideoEntry;
    private final static int MSG_REFRESH=0;
    private final static int MSG_LOADMORE=1;
    public final static int MSG_INIT=20000;
    public final static int MSG_YIDIAN_LOADMORE=20001;
    public final static int MSG_DONGFANG_REFRESH=20002;
    public final static int MSG_DONGFANG_LOADMORE=20003;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VideoFragment newInstance(int columnCount) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        Log.e("Block=","newInstance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        Log.e("Block=","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("Block=","onCreateView");
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        xRefreshView = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_list);
        adapter=new MyvideoRecyclerViewAdapter(items, mListener,getContext());
        // Set the adapter
        Context context = view.getContext();
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(true);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(getContext()));
        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);


        xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView,newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(MyvideoRecyclerViewAdapter.ViewHolder.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if(!mFull) {
                            GSYVideoPlayer.releaseAllVideos();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(final boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getVideos(MSG_REFRESH);
                        MiStatInterface.recordCountEvent("VideoFragment_Request_Video", "VideoFragment_Request_Video_Refresh");
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        getVideos(MSG_LOADMORE);
                        MiStatInterface.recordCountEvent("VideoFragment_Request_Video", "VideoFragment_Request_Video_LoadMore");
                    }
                }, 1000);

            }
        });

        //初始化获取数据
        getVideos(MSG_INIT);

        return view;
    }

    /**
     * 通过不同的消息获取视频信息
     * @param msg
     *   MSG_INIT
     *   MSG_REFRESH
     *   MSG_LOADMORE
     */
    private void getVideos(int msg) {

        //Log.e(TAG,msg+"");
        GetInitParas getInitParas = new GetInitParas(msg).invoke();
        int cstart = getInitParas.getCstart();
        int cend = getInitParas.getCend();
        String str = getInitParas.getStr();

        //long random = System.currentTimeMillis() % 2;

        otherVideoSource(msg, cstart, cend, str);

    }

    private void mainVideoSource(final int msg) {
        API.getIntoAdsApi().getVideos(page,15)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        /*
                        if(msg==MSG_INIT)
                        {
                            LoadingDialog.show(getActivity());
                        }*/
                    }

                    @Override
                    public void onNext(@NonNull String s) {

                        //Log.e("Before=BASE64=",s);
                        String ss= StringUtils.decodeData(s);
                        //Log.e("After=BASE64=",ss);
                        OutputJson jsonObj=(OutputJson)StringUtils.fromJson(ss, OutputJson.class);
                        List<YiDianNewsItem> yiDianNewsItems=new ArrayList<YiDianNewsItem>();
                        if(null!=jsonObj&&null!=jsonObj.getData()&&jsonObj.getData()!="")
                        {
                            List<Video> videos= StringUtils.jsonToList(jsonObj.getData(),Video.class);
                            if(null!=videos&&videos.size()>0)
                            {
                                for(int i=0;i<videos.size();i++)
                                {
                                    Video dfItem=videos.get(i);
                                    if(null!=dfItem&&null!=dfItem.getVideo_url()&&dfItem.getVideo_url()!="")
                                    {
                                        yiDianNewsItems.add(dfItem.convertToVideoItem());
                                    }
                                }
                                items.addAll(yiDianNewsItems);
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        /*
                        if(msg==MSG_INIT)
                        {
                            LoadingDialog.dismiss();//初始Loading加载框退出
                        }
                        xRefreshView.stopRefresh();
                        xRefreshView.stopLoadMore(true);
                        */

                        //otherVideoSource(msg, cstart, cend, str);
                    }

                    @Override
                    public void onComplete() {
                        /*
                        if(msg==MSG_INIT)
                        {
                            LoadingDialog.dismiss();//初始Loading加载框退出
                        }
                        xRefreshView.stopRefresh();
                        xRefreshView.stopLoadMore(true);
                        */
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void otherVideoSource(final int msg, int cstart, int cend, String str) {
        io.reactivex.Observable<YiDianNewsResponse> yiDianOb=
                API.getYiDianZiXunApi().search(""+cstart,""+cend, NewsChannel.VIDEO.getChannelId(),false, 1, "pc", 5, "yidian", str)
                .compose(compose(this.<YiDianNewsResponse>bindToLifecycle()));

        PrepareDFVideoParams prepareDFVideoParams=new PrepareDFVideoParams(page);
        //设置视频类型
        if(msg==MSG_INIT)
        {
            //初始化时随机播放一个Channel
            VideoChannel vc=VideoChannel.getRandomVideoChannel();
            prepareDFVideoParams.setCategoryId(vc.getChannelId());
            tempVideoChannel=vc;
        }else if(msg==MSG_REFRESH)
        {
            //刷新依次获取Channel
            VideoChannel videoChannel=VideoChannel.allTypeQueue.poll();
            prepareDFVideoParams.setCategoryId(videoChannel.getChannelId());
            VideoChannel.allTypeQueue.offer(videoChannel);
            tempVideoChannel=videoChannel;
        }else
        {
            //加载更多使用刷新的或者初始化的Channel
            prepareDFVideoParams.setCategoryId(tempVideoChannel.getChannelId());
        }
        io.reactivex.Observable<DfTouTiaoVideoEntry> dfTouTiaoOb=
                API.getDfTouTiaoApi().getVideos(prepareDFVideoParams.invoke().getParam())
                .compose(compose(this.<DfTouTiaoVideoEntry>bindToLifecycle()));


        yiDianOb.zipWith(dfTouTiaoOb, new BiFunction<YiDianNewsResponse, DfTouTiaoVideoEntry, List<YiDianNewsItem>>() {
            @Override
            public List<YiDianNewsItem> apply(@NonNull YiDianNewsResponse yiDianNewsResponse, @NonNull DfTouTiaoVideoEntry dfTouTiaoVideoEntry) throws Exception {
                return shuffleVideos(yiDianNewsResponse, dfTouTiaoVideoEntry);
            }
              }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<YiDianNewsItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //Log.e(TAG,"onSubscribe");
                //初始Loading加载框出现
                if(msg==MSG_INIT)
                {
                    LoadingDialog.show(getActivity());
                }
            }

            @Override
            public void onNext(@NonNull List<YiDianNewsItem> yiDianNewsItems) {
                /*
                if(null!=yiDianNewsItems)
                {
                    Log.e(TAG,"onNext="+yiDianNewsItems.size());
                }else{
                    Log.e(TAG,"onNext=0");
                }*/

                if(msg==MSG_INIT)
                {
                    items.clear();
                    items.addAll(yiDianNewsItems);
                }else if(msg==MSG_REFRESH)
                {
                    items.clear();
                    items.addAll(yiDianNewsItems);
                }else if(msg==MSG_LOADMORE)
                {
                    items.addAll(yiDianNewsItems);
                }

                //Log.e(TAG,"onNext="+items.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //Log.e(TAG,"onError="+e.getMessage());
                if(msg==MSG_INIT)
                {
                    LoadingDialog.dismiss();//初始Loading加载框退出
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore(true);

            }

            @Override
            public void onComplete() {
                //Log.e(TAG,"onComplete");
                if(msg==MSG_INIT)
                {
                    LoadingDialog.dismiss();//初始Loading加载框退出
                }
                xRefreshView.stopRefresh();
                xRefreshView.stopLoadMore(true);
                adapter.notifyDataSetChanged();

                mainVideoSource(msg);
            }
        });
    }

    /*
    综合返回的数据
     */
    @android.support.annotation.NonNull
    private List<YiDianNewsItem> shuffleVideos(@NonNull YiDianNewsResponse yiDianNewsResponse, @NonNull DfTouTiaoVideoEntry dfTouTiaoVideoEntry) {
        setDfTouTiaoVideoEntry(dfTouTiaoVideoEntry);//保存上一个返回实例
        List<DfTouTiaoVideoItem> dfItems=dfTouTiaoVideoEntry.getData();
        List<YiDianNewsItem> videoItems=new ArrayList<YiDianNewsItem>();
        if(null!=dfItems&&dfItems.size()>0)
        {
            for(int i=0;i<dfItems.size();i++)
            {
                DfTouTiaoVideoItem dfItem=dfItems.get(i);
                if(null!=dfItem)
                {
                    videoItems.add(dfItem.convertToVideoItem());
                }
            }
        }
        List<YiDianNewsItem> tempvideoItems=yiDianNewsResponse.getResult();
        if(null!=tempvideoItems&&tempvideoItems.size()>0)
        {
            for(int j=0;j<tempvideoItems.size();j++)
            {
                if(null!=tempvideoItems.get(j)&&tempvideoItems.get(j).equals("video_live"))
                {
                    videoItems.add(tempvideoItems.get(j));
                }
            }
        }
        Collections.shuffle(videoItems);//乱序
        return videoItems;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onVideoListFragmentInteraction(YiDianNewsItem item);
    }

    public boolean onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(getActivity())) {
            return true;
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(VideoFragment.this.getActivity(), "VideoFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        MiStatInterface.recordPageEnd();
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
    }

    /**
     * 准备获取东方头条所需的数据参数
     */
    private class PrepareDFVideoParams {
        private int page;
        private Map<String, String> param;

        //设置视频类型
        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        private String categoryId="799999";//默认类型

        public PrepareDFVideoParams(int page) {
            this.page = page;
        }

        public Map<String, String> getParam() {
            return param;
        }

        public PrepareDFVideoParams invoke() {
            param = new HashMap();
            param.put("categoryId",categoryId);
            param.put("count","20");
            if(null!=dfTouTiaoVideoEntry&&dfTouTiaoVideoEntry.getNewkey()!=null)
            {
                param.put("startkey",dfTouTiaoVideoEntry.getEndkey());
                param.put("newkey",dfTouTiaoVideoEntry.getNewkey());
            }else
            {
                param.put("startkey","null");
                param.put("newkey","null");
            }

            param.put("pgnum",page+"");
            param.put("param","TouTiao\tDFTTAndroid\t"+
                    DeviceTool.getDeviceToolInstance().getDeviceId()+
                    "\tbaidusjzs170827\tDFTT\t1.8.5\tAndroid"+
                    DeviceTool.getDeviceToolInstance().getSystemVersion()+
                    "\tnull\t010805\t"+
                    DeviceTool.getDeviceToolInstance().getAndroidId().toLowerCase()+"");
            param.put("position","北京");
            param.put("iswifi","wifi");
            return this;
        }
    }

    /**
     * 根据消息获取相应的请求需要用到的初始化数据
     * 页数变化， 起始位置，时间戳
     */
    private class GetInitParas {
        private int msg;
        private String str;
        private int cstart;
        private int cend;

        public GetInitParas(int msg) {
            this.msg = msg;
        }

        public String getStr() {
            return str;
        }

        public int getCstart() {
            return cstart;
        }

        public int getCend() {
            return cend;
        }

        public GetInitParas invoke() {
            if(msg==MSG_INIT)
            {
                page=1;
            }else if(msg==MSG_REFRESH)
            {
                page=1;
            }else if(msg==MSG_LOADMORE)
            {
                page++;
            }

            long time=System.currentTimeMillis();//获取系统时间的10位的时间戳
            str = String.valueOf(time);
            cstart = 10 * (page - 1);
            cend = 10 * page;
            return this;
        }
    }
}
