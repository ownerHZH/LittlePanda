package com.jerry.littlepanda.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry.hu on 21/07/17.
 * 一点资讯的新闻返回
 * String newsUrl= "http://www.yidianzixun.com/home/q/news_list_for_channel?channel_id=best&cstart="+start+"&cend="+cend+"&infinite=true&refresh=1&__from__=pc&multi=5&appid=web_yidian&_="+str;
 */

public class YiDianNewsItem implements Serializable {

    private static final long serialVersionUID = 56423421313L;

    String date;
    String title;
    String docid;
    String meta;
    String ctype;
    int dtype;
    String impid;
    String pageid;
    String itemid;
    String channel_id;
    int display_flag;
    String summary;
    List<String> image_urls;
    String source;
    String url;
    String duration;
    String b_native;
    String video_url;
    int like;
    int up;
    int down;
    int comment_count;
    boolean auth;
    boolean is_gov;
    String content_type;
    boolean b_political;
    String image;
    List<String> vsct_show;
    boolean feedback_forbidden;
    int title_sn;
    List<String> dislike_reasons;
    String category;

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getB_native() {
        return b_native;
    }

    public void setB_native(String b_native) {
        this.b_native = b_native;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public List<String> getVsct_show() {
        return vsct_show;
    }

    public void setVsct_show(List<String> vsct_show) {
        this.vsct_show = vsct_show;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getImpid() {
        return impid;
    }

    public void setImpid(String impid) {
        this.impid = impid;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDtype() {
        return dtype;
    }

    public void setDtype(int dtype) {
        this.dtype = dtype;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getDisplay_flag() {
        return display_flag;
    }

    public void setDisplay_flag(int display_flag) {
        this.display_flag = display_flag;
    }

    public boolean isFeedback_forbidden() {
        return feedback_forbidden;
    }

    public void setFeedback_forbidden(boolean feedback_forbidden) {
        this.feedback_forbidden = feedback_forbidden;
    }

    public int getTitle_sn() {
        return title_sn;
    }

    public void setTitle_sn(int title_sn) {
        this.title_sn = title_sn;
    }

    public List<String> getDislike_reasons() {
        return dislike_reasons;
    }

    public void setDislike_reasons(List<String> dislike_reasons) {
        this.dislike_reasons = dislike_reasons;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isB_political() {
        return b_political;
    }

    public void setB_political(boolean b_political) {
        this.b_political = b_political;
    }

    public List<String> getImage_urls() {

        String imgurl="http://i1.go2yd.com/image.php?type=thumbnail_336x216&url=";
        List<String> imagelist=new ArrayList<>();
        if(null!=image_urls&&image_urls.size()>0)
        {
            for(int i=0;i<image_urls.size();i++)
            {
                String img=image_urls.get(i);
                imagelist.add(imgurl+img);
            }
        }

        return imagelist;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        String imgurl="http://i1.go2yd.com/image.php?type=thumbnail_336x216&url="+image;
        return imgurl;
    }

    public String getImage(boolean isVideo) {
        //String imgurl="http://i1.go2yd.com/image.php?type=thumbnail_336x216&url="+image;
        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean is_gov() {
        return is_gov;
    }

    public void setIs_gov(boolean is_gov) {
        this.is_gov = is_gov;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    @Override
    public String toString() {
        return "YiDianNewsItem{" +
                "title='" + title + '\'' +
                ", image_urls=" + image_urls +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}


