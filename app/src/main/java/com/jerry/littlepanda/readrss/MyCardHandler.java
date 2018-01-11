package com.jerry.littlepanda.readrss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;
import com.crazysunj.cardslideview.ElasticCardView;
import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.SplashActivity;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.domain.YiDianNewsItem;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import java.io.Serializable;

import static com.jerry.littlepanda.SplashActivity.CARDONCLICK;

/**
 * Created by jerry.hu on 30/09/17.
 */

public class MyCardHandler implements CardHandler<YiDianNewsItem>{

    private static final long serialVersionUID = 56423431313L;

    private Context context;

    public MyCardHandler(Context context){
        this.context=context;
    }

    @Override
    public View onBind(final Context context, final YiDianNewsItem data, final int position, @CardViewPager.TransformerMode int mode) {
        View view = View.inflate(context, R.layout.splash_item, null);
        TextView title,source;
        title=(TextView) view.findViewById(R.id.title);
        source=(TextView) view.findViewById(R.id.soruce);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        ElasticCardView cardView = (ElasticCardView) view.findViewById(R.id.cardview);
        final boolean isCard = mode == CardViewPager.MODE_CARD;
        cardView.setPreventCornerOverlap(isCard);
        cardView.setUseCompatPadding(isCard);

        title.setText(data.getTitle());
        source.setText(data.getSource());

        String img = data.getImage();
        if(null==img||img==""){
            img=data.getImage_urls().get(0);
        }
        APPAplication.setImageValue(imageView,img);
        //Glide.with(context).load(img).into(imageView);

        return view;
    }

    @Override
    public void onClick(YiDianNewsItem item, int position) {
        //Log.e("Click","onClick");
        if(DeviceTool.isFastClick()&&null!=item.getUrl()&&item.getUrl()!="")
        {
            Intent intent = new Intent(context, BrowserActivity.class);
            //intent.setData(Uri.parse(item.getUrl()==null?"":item.getUrl()));
            intent.putExtra("url", item.getUrl());
            intent.putExtra("desc", item.getSummary());
            intent.putExtra("source", item.getSource());
            context.startActivity(intent);
        }
        //MiStatInterface.recordCountEvent("Card_Click", "Splash_Card_click");
    }
}
