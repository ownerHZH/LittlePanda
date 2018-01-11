package com.jerry.littlepanda.readrss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.Utils.HtmlTextView;
import com.jerry.littlepanda.domain.EntryImage;
import com.jerry.littlepanda.domain.SyndEntry;
import com.jerry.littlepanda.ireader.utils.SharedPreUtils;
import com.jerry.littlepanda.network.API;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jerry.hu on 15/07/17.
 */

public class ListFeedAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<SyndEntry> feed;

    public ListFeedAdapter(Context context,List<SyndEntry> feed)
    {
        if(context!=null)
        {
            this.inflater=LayoutInflater.from(context);
            this.context=context;
            this.feed=feed;
        }

    }

    @Override
    public int getCount() {
        if(feed!=null)
        {
            return feed.size();
        }
        return 0;
    }

    @Override
    public SyndEntry getItem(int index) {
        if(feed!=null)
        {
            return feed.get(index);
        }

        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void click( int position )
    {
        if(DeviceTool.isFastClick())
        {
            SyndEntry syndEntry=getItem( position );
            String uri = syndEntry.getLink();
            //String uri = "http://www.meipai.com/media/823693972";

            if(null!=uri&&uri!="")
            {
                Intent webIntent=new Intent();
                webIntent.setData(Uri.parse( uri ) );
                webIntent.setClass(context,BrowserActivity.class);
                //Intent webIntent = new Intent( "android.i ntent.action.VIEW", );
                context.startActivity( webIntent );
            }

            if(syndEntry.getSource()=="Inmobi")
            {
                //上报点击
                API.getIntoAdsApi().fireBeacons(syndEntry.getComments(),
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

    @Override
    public View getView(int index, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(convertView ==null)
        {
            holder =new ViewHolder();
            convertView = inflater.inflate(R.layout.list_feed_item,null);
            holder.imgcontainer = (LinearLayout)convertView.findViewById(R.id.imgcontainer);
            holder.img1 = (ImageView)convertView.findViewById(R.id.image1);
            holder.img2 = (ImageView)convertView.findViewById(R.id.image2);
            holder.img3 = (ImageView)convertView.findViewById(R.id.image3);
            holder.img4 = (ImageView)convertView.findViewById(R.id.image4);
            holder.img5 = (ImageView)convertView.findViewById(R.id.image5);
            holder.pimg5= (FrameLayoutScale)convertView.findViewById(R.id.pimage5);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.desc = (HtmlTextView)convertView.findViewById(R.id.desc);
            holder.contentsource = (TextView)convertView.findViewById(R.id.contentsource);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        SyndEntry entry = getItem( index );
        //holder.img.setImageResource(entry.);
        holder.title.setText(entry.getTitle());
        holder.contentsource.setText(entry.getSource());
        //Log.e("TAG=",entry.getDescription());
        Collection<EntryImage> coll= entry.getImages();
        List<EntryImage> images=null;
         if(null!=coll)
         {
             images=new ArrayList<EntryImage>(coll);
             //Log.e("TAG=","images="+images.toString());
         }
         if(images!=null&&images.size()>0) {
                int size=images.size();
                //Log.e("TAG=","size="+size);
                if(size>0)
                {
                    holder.desc.setVisibility(View.GONE);

                    if(size==1)
                    {
                        int rd=Math.random()>0.5?1:0;
                        if(rd==1)
                        {
                            //展示一个图片 左图右文
                            holder.imgcontainer.setVisibility(View.GONE);
                            holder.pimg5.setVisibility(View.GONE);
                            holder.img5.setVisibility(View.GONE);
                            holder.img1.setVisibility(View.VISIBLE);
                            //Log.e("TAG=","img1="+images.get(0));
                            //ImageLoader.getInstance().displayImage(images.get(0),holder.img1);
                            setImageValue(holder.img1, images.get(0).getImageLink());
                        }else
                        {
                            //展示一张大图
                            holder.imgcontainer.setVisibility(View.GONE);
                            holder.img1.setVisibility(View.GONE);
                            holder.desc.setVisibility(View.GONE);
                            holder.pimg5.setVisibility(View.VISIBLE);
                            holder.img5.setVisibility(View.VISIBLE);
                            setImageValue(holder.img5, images.get(0).getImageLink());
                        }
                    }else if(size==2){
                        //展示一张大图
                        holder.imgcontainer.setVisibility(View.GONE);
                        holder.img1.setVisibility(View.GONE);
                        holder.desc.setVisibility(View.GONE);
                        holder.pimg5.setVisibility(View.VISIBLE);
                        holder.img5.setVisibility(View.VISIBLE);
                        setImageValue(holder.img5, images.get(0).getImageLink());
                    } else if(size>=3)
                    {
                        //展示三图
                        holder.imgcontainer.setVisibility(View.VISIBLE);
                        holder.pimg5.setVisibility(View.GONE);
                        holder.img5.setVisibility(View.GONE);
                        holder.img1.setVisibility(View.GONE);

                        setImageValue(holder.img2, images.get(0).getImageLink());
                        setImageValue(holder.img3, images.get(1).getImageLink());
                        setImageValue(holder.img4, images.get(2).getImageLink());
                    }
                }
            }else{
                holder.imgcontainer.setVisibility(View.GONE);
                holder.pimg5.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
                holder.img1.setVisibility(View.GONE);
                holder.desc.setVisibility(View.VISIBLE);
                holder.desc.setHtmlFromString(entry.getDescription(),false);
            }

        if(entry.getSource()=="Inmobi")
        {
            //上报展示
            API.getIntoAdsApi().fireBeacons(entry.getAuthor(),
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
                            Log.e("上报展示=",e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        return convertView;
    }

    private void setImageValue(ImageView img, String value) {

        APPAplication.setImageValue(img,value);
        /*
        //处理下拉图片错位
        img.setTag(value);
        if(value.startsWith("data:image/png;base64,")){
            value=value.replaceFirst("data:image/png;base64,","");
            byte[] decodedString = Base64.decode(value, Base64.DEFAULT);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,options);
            img.setImageBitmap(bitMap);
        } else{
            if(value.startsWith("//"))
            {
                value=value.replaceFirst("//","http://");
            }
            ImageAware imageAware = new ImageViewAware(img, false);
            ImageLoader.getInstance().displayImage(value,imageAware,options,new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (!imageUri.equals(view.getTag().toString())) {
                        // 设置默认图片
                        ((ImageView)view).setImageResource(R.drawable.loading_wait);
                    }else
                    {
                        ((ImageView)view).setImageBitmap(loadedImage);
                    }
                }
            });
        }*/

    }

    static class ViewHolder
    {
        public LinearLayout imgcontainer;
        public ImageView img1,img2,img3,img4,img5;
        public FrameLayoutScale pimg5;
        public TextView title;
        public HtmlTextView desc;
        public TextView contentsource;
    }
}

