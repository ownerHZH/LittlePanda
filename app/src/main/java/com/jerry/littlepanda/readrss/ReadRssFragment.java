package com.jerry.littlepanda.readrss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.utils.LogUtils;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.Utils.LoadingDialog;
import com.jerry.littlepanda.Utils.StringUtils;
import com.jerry.littlepanda.domain.EntryImage;
import com.jerry.littlepanda.domain.OutputJson;
import com.jerry.littlepanda.domain.SyndEntry;
import com.jerry.littlepanda.domain.TravelEntity;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.jerry.littlepanda.domain.YiDianNewsResponse;
import com.jerry.littlepanda.domain.inmobi.Ad;
import com.jerry.littlepanda.domain.inmobi.InmobiApiResponseObject;
import com.jerry.littlepanda.domain.inmobi.Screenshots;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;
import com.jerry.littlepanda.network.API;
import com.jerry.littlepanda.network.BaseFragment;
import com.jerry.littlepanda.smileyloadingview.SmileyHeaderView;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadRssFragment.OnReadRssFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadRssFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadRssFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //初始化的message
    private static final int INIT_SIGNAL = 0;
    //刷新的message
    private static final int REFRESH_SIGNAL = 1;
    //加载更多的message
    private static final int LOADMORE_SIGNAL = 2;

    private long lastRefreshTime;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<SyndEntry> feed=new ArrayList<SyndEntry>();
    private ListView listView;
    private XRefreshView refreshView;
    private ListFeedAdapter adapter;
    private boolean isViewCreated=false;
    private Long severpage=1L;//服务端的页数
    private Long dbpage=1L;//本地数据库的页数
    private Long pagesize=15L;//每页的个数
    private int page=1;//页数

    private OnReadRssFragmentInteractionListener mListener;

    public ReadRssFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadRssFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadRssFragment newInstance(String param1, String param2) {
        ReadRssFragment fragment = new ReadRssFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_read_rss, container, false);
        refreshView = (XRefreshView) v.findViewById(R.id.custom_view);
        listView=(ListView) v.findViewById(R.id.listView);
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            public void onItemClick( AdapterView<?> parentView, View childView, int position, long id )
            {
                //adapter.click( position );
                if(DeviceTool.isFastClick()){
                    SyndEntry item=adapter.getItem(position);

                    if(null!=item.getLink()&&item.getLink()!="")
                    {
                        //Log.e("Jump BrowserActivity","url="+item.getLink());
                        //Log.e("Jump BrowserActivity","getDescription="+item.getDescription());
                        Intent intent = new Intent(getActivity(), BrowserActivity.class);
                        //intent.setData(Uri.parse(item.getUrl()==null?"":item.getUrl()));
                        intent.putExtra("url", item.getLink());
                        intent.putExtra("desc", item.getDescription());
                        intent.putExtra("source", item.getSource());
                        getActivity().startActivity(intent);

                        if(item.getSource()=="Inmobi")
                        {
                            //上报点击
                            API.getIntoAdsApi().fireBeacons(item.getComments(),
                                    DeviceTool.getDeviceToolInstance().getUserAgent(),
                                    SharedPreUtils.getInstance().getString(Constants.LOCALIP))
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onNext(@NonNull String s) {

                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            Log.e("上报点击=",e.getMessage());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    }
                }
            }
        } );
        //refreshView.setPinnedTime(1000);
        // 设置是否可以下拉刷新
        refreshView.setPullRefreshEnable(true);
        // 设置上次刷新的时间
        refreshView.restoreLastRefreshTime(lastRefreshTime);
        refreshView.enableReleaseToLoadMore(true);
        refreshView.setAutoLoadMore(true);
        refreshView.setMoveForHorizontal(true);
        // 设置是否可以上拉加载
        refreshView.setPullLoadEnable(true);
        refreshView.setCustomHeaderView(new SmileyHeaderView(getContext()));
        refreshView.setCustomFooterView(new XRefreshViewFooter(getActivity()));
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        //refreshView.setMoveHeadWhenDisablePullRefresh(true);
        // 设置时候可以自动刷新
        //refreshView.setAutoRefresh(true);

        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("TAG","onRefresh");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getYiDianZiXunNews(REFRESH_SIGNAL);
                            }
                        }){}.start();

                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getYiDianZiXunNews(LOADMORE_SIGNAL);
                    }
                }, 2000);
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
                if (direction > 0) {
                    Log.v("TAG","onRelease》0");
                } else {
                    Log.v("TAG","onRelease《=0");
                }
            }
        });

        refreshView.setOnAbsListViewScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.i("onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                LogUtils.i("onScroll");
            }
        });

        getYiDianZiXunNews(INIT_SIGNAL);

        return v;
    }

    /**
     * 加载一点资讯新闻
     * @param msg
     *    INIT_SIGNAL    初始化
     *    REFRESH_SIGNAL 刷新
     *    LOADMORE_SIGNAL 加载更多
     */
    private String channelName=NewsChannel.BEST.getChannelId();
    private void getYiDianZiXunNews(int msg) {

        //初始化加载数据
        if(msg==INIT_SIGNAL)
        {
            channelName=NewsChannel.getRandomNewsChannel().getChannelId();
            page=1;
            LoadingDialog.show(getActivity());//初始Loading加载框出现
        }else if(msg==REFRESH_SIGNAL)
        {
            NewsChannel tempNewsChannel=NewsChannel.allTypeQueue.poll();
            NewsChannel.allTypeQueue.offer(tempNewsChannel);
            channelName=tempNewsChannel.getChannelId();
            page=1;
        }else{
            page++;
        }

        long time=System.currentTimeMillis();//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        int cstart=10*(page-1);
        int cend=10*page;

        API.getYiDianZiXunApi().search(
                ""+cstart,
                ""+cend,
                channelName,
                true,
                1,
                "pc",
                5,
                "web_yidian",
                str).compose(compose(this.<YiDianNewsResponse>bindToLifecycle())).subscribe(new BaseObserver(getContext()) {
            @Override
            protected void onHandleSuccess(List<YiDianNewsItem> t) {
                // 保存用户信息等操作
                if(msg==INIT_SIGNAL||msg==REFRESH_SIGNAL)
                {
                    feed=convertNews(t);
                }else
                {
                    if(null!=feed)
                    {
                        feed.addAll(convertNews(t));
                    }else
                    {
                        feed=new ArrayList<SyndEntry>(convertNews(t));
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                LoadingDialog.dismiss();//初始Loading加载框退出
                refreshView.stopRefresh();
                refreshView.stopLoadMore();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                handleOnComplete(msg);
            }
        });
    }

    //相应动作的处理
    public void handleOnComplete(int msg)
    {
        switch (msg){
            case INIT_SIGNAL:
                Log.e("TAG","初始化数据准备完毕");
                adapter=new ListFeedAdapter(getContext(),feed);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                LoadingDialog.dismiss();//初始Loading加载框退出
                MiStatInterface.recordCountEvent("RSSFragment_Request_RSS", "RSSFragment_Request_RSS_Init");
                break;
            case REFRESH_SIGNAL:
                //刷新数据
                refreshView.stopRefresh();
                lastRefreshTime = refreshView.getLastRefreshTime();
                adapter=new ListFeedAdapter(getContext(),feed);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                MiStatInterface.recordCountEvent("RSSFragment_Request_RSS", "RSSFragment_Request_RSS_Refresh");
                break;
            case LOADMORE_SIGNAL:
                //加载更多数据
                if(null!=adapter)
                {
                    adapter.notifyDataSetChanged();
                }else
                {
                    adapter=new ListFeedAdapter(getContext(),feed);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                refreshView.stopLoadMore();
                MiStatInterface.recordCountEvent("RSSFragment_Request_RSS", "RSSFragment_Request_RSS_LoadMore");
                break;
        }
        //获取Inmobi API广告
        handleInmobiAds();
        //handleYouji();
    }

    private void handleYouji(){
        API.getIntoAdsApi().getTravel(page,2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        //Log.e("Before=BASE64=",s);
                        String ss=StringUtils.decodeData(s);
                        //Log.e("After=BASE64=",ss);
                        OutputJson jsonObj=(OutputJson)StringUtils.fromJson(ss, OutputJson.class);
                        List<TravelEntity> travelEntities=StringUtils.jsonToList(jsonObj.getData(), TravelEntity.class);
                        if(null!=travelEntities&&travelEntities.size()>0){
                            for(int i=0;i<travelEntities.size();i++){
                                TravelEntity travelEntity=travelEntities.get(i);

                                SyndEntry syndEntry=new SyndEntry();
                                syndEntry.setLink("http://www.youji.com/");
                                syndEntry.setSource("游记");

                                syndEntry.setTitle(travelEntity.getTravel_title());
                                syndEntry.setDescription(travelEntity.getTravel_detail());
                                syndEntry.setPubDate("");

                                if(null!=travelEntity.getTravel_images()
                                        &&null!=travelEntity.getTravel_images().split(",")
                                        &&travelEntity.getTravel_images().split(",").length>0){
                                    List<EntryImage> imglist=new ArrayList<EntryImage>();

                                    EntryImage enimg=new EntryImage();
                                    enimg.setEntryId(syndEntry);
                                    enimg.setImageLink(travelEntity.getTravel_images().split(",")[0]);
                                    imglist.add(enimg);

                                    syndEntry.setImages(imglist);
                                }


                                if(null!=feed&&feed.size()>0)
                                {
                                    feed.add(i+3+((page-1)*10),syndEntry);
                                }else{
                                    feed.add(syndEntry);
                                }

                            }
                            adapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handleInmobiAds() {
        API.getIntoAdsApi().getInmobiAds(DeviceTool.getDeviceToolInstance().getAndroidId(),
                DeviceTool.getDeviceToolInstance().getDeviceId(),
                DeviceTool.getDeviceToolInstance().getUserAgent(),
                SharedPreUtils.getInstance().getString(Constants.LOCALIP))

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                //Log.e("Before=BASE64=",s);
                String ss=StringUtils.decodeData(s);
                //Log.e("After=BASE64=",ss);
                OutputJson jsonObj=(OutputJson)StringUtils.fromJson(ss, OutputJson.class);
                InmobiApiResponseObject object=(InmobiApiResponseObject)StringUtils.fromJson(jsonObj.getData(),InmobiApiResponseObject.class);
                //Log.e("After=object=",object.toString());
                if(null!=object)
                {
                    List<Ad> ads=object.getAds();
                    if(null!=ads)
                    {
                        for(int i=0;i<ads.size();i++)
                        {
                            Ad ad=ads.get(i);
                            if(null!=ad)
                            {
                                SyndEntry syndEntry=new SyndEntry();
                                syndEntry.setLink(ad.getLandingPage());
                                syndEntry.setSource("Inmobi");

                                syndEntry.setTitle(ad.getPubContent().getTitle());
                                syndEntry.setDescription(ad.getPubContent().getDescription());
                                syndEntry.setPubDate("");

                                //放置展示beacon
                                List<String> str18s=ad.getEventTracking().get18().getUrls();
                                if(null!=str18s)
                                {
                                    for(int x=0;x<str18s.size();x++)
                                    {
                                        if(str18s.get(x).contains("m=18"))
                                        {
                                            syndEntry.setAuthor(str18s.get(x));
                                            break;
                                        }
                                    }
                                }

                                //放置点击beacon
                                List<String> str8s=ad.getEventTracking().get8().getUrls();
                                if(null!=str8s)
                                {
                                    for(int y=0;y<str8s.size();y++)
                                    {
                                        if(str8s.get(y).contains("c.w.inmobi.com"))
                                        {
                                            syndEntry.setComments(str8s.get(y));
                                            break;
                                        }
                                    }
                                }


                                Screenshots screenshots=ad.getPubContent().getScreenshots();
                                if(null!=screenshots)
                                {
                                    List<EntryImage> imglist=new ArrayList<EntryImage>();

                                    EntryImage enimg=new EntryImage();
                                    enimg.setEntryId(syndEntry);
                                    enimg.setImageLink(screenshots.getUrl());
                                    imglist.add(enimg);

                                    syndEntry.setImages(imglist);
                                }

                                if(null!=feed&&feed.size()>0)
                                {
                                    feed.add(i+3+((page-1)*10),syndEntry);
                                }else{
                                    feed.add(syndEntry);
                                }

                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //数据转换
    public List<SyndEntry> convertNews(List<YiDianNewsItem> t)
    {
        List<SyndEntry> items=new ArrayList<>();
        if(null!=t)
        {
            for(int i=0;i<t.size();i++)
            {
                YiDianNewsItem item=t.get(i);
                //Log.e("YiDianNewsItem",""+item.getContent_type());
                if(null!=item&&item.getCtype()!=null&&
                        (item.getCtype().toLowerCase().equals("news")
                                ||item.getCtype().toLowerCase().equals("picture")
                                ||item.getCtype().toLowerCase().equals("joke")
                                ||item.getCtype().toLowerCase().equals("video_live")))
                {
                    SyndEntry entry=new SyndEntry();
                    //如果没有落地页链接就传一个图片的地址
                    if(null!=item.getUrl()&&item.getUrl()!="")
                    {
                        entry.setLink(item.getUrl());
                        //entry.setLink("http://www.yidianzixun.com/article/"+item.getDocid());
                    }else if(item.getCtype().toLowerCase().equals("joke")){
                        //如果是段子栏目，就组装一个页面链接
                        entry.setLink("http://www.yidianzixun.com/article/"+item.getDocid());
                    }

                    entry.setSource(item.getSource());
                    entry.setTitle(item.getTitle());
                    entry.setDescription(item.getSummary());
                    entry.setPubDate(item.getDate());
                    //没有多图地址就存一张图地址
                    if(item.getImage_urls()!=null&&item.getImage_urls().size()>0)
                    {
                        List<EntryImage> imglist=new ArrayList<EntryImage>();
                        for(int j=0;j<item.getImage_urls().size();j++)
                        {
                            EntryImage enimg=new EntryImage();
                            enimg.setEntryId(entry);
                            enimg.setImageLink(item.getImage_urls().get(j));
                            imglist.add(enimg);
                        }
                        entry.setImages(imglist);
                    }else if(item.getCtype().toLowerCase().equals("picture"))
                    {
                        //没有Url的就展示大图 传2张一样的图片
                        List<EntryImage> imglist=new ArrayList<EntryImage>();

                        EntryImage enimg=new EntryImage();
                        enimg.setEntryId(entry);
                        enimg.setImageLink(item.getImage());
                        imglist.add(enimg);

                        EntryImage enimg2=new EntryImage();
                        enimg2.setEntryId(entry);
                        enimg2.setImageLink(item.getImage());
                        imglist.add(enimg2);

                        entry.setImages(imglist);

                        entry.setLink("http://www.yidianzixun.com/channel/u241");
                    }

                    items.add(entry);
                }
            }
        }

        return items;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onReadRssFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnReadRssFragmentInteractionListener) {
            mListener = (OnReadRssFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 获取数据
     * 先从数据库查找，数据库没有，就从网上下载，缓存到本地数据库
     */
    public void getDate()
    {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnReadRssFragmentInteractionListener {
        // TODO: Update argument type and name
        void onReadRssFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(ReadRssFragment.this.getActivity(), "ReadRssFragment");
    }

    @Override
    public void onStop() {
        super.onStop();
        MiStatInterface.recordPageEnd();
    }

}
