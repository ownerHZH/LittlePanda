package com.jerry.littlepanda.domain.dftoutiao;

import com.jerry.littlepanda.domain.YiDianNewsItem;

import java.util.List;

/**
 * Created by jerry.hu on 28/08/17.
 */

public class DfTouTiaoVideoItem {
    String comment_count;
    String date;
    String desc;
    String dfh_headpic;
    String  dfh_nickname;
    String dfh_uid;
    String filesize;
    String hotnews;
    String  isJian;
    String  isliveshow;
    String isnxw;
    String isoriginal;
    String ispicnews;
    String  isrecom;
    String  isvideo;
    List<LbImg> lbimg;
    String midTabKey;
    List<MiniImg>  miniimg;
    String miniimg_size;
    String nodownvote;
    String  noupvote;
    String picnums;
    String praisecnt;
    String preload;
    String recommendtype;
    String recommendurl;
    String rowkey;
    String source;
    String suptop;
    String titledisplay;
    String topic;
    String tramplecnt;
    String type;
    String url;
    String urlfrom;
    String urlpv;
    String video_link;
    String videoalltime;
    String videonews;

    public YiDianNewsItem convertToVideoItem()
    {
        YiDianNewsItem videoItem=new YiDianNewsItem();
        String imageurl="";
        if(lbimg!=null)
        {
            imageurl=lbimg.get(0)!=null?lbimg.get(0).getSrc():"";
        }
        if(null==imageurl&&"".equals(imageurl)&&miniimg!=null)
        {
            imageurl=miniimg.get(0)!=null?miniimg.get(0).getSrc():"";
        }
        videoItem.setImage(imageurl);
        videoItem.setTitle(topic);
        videoItem.setVideo_url(video_link);
        videoItem.setSource(source);

        return videoItem;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDfh_headpic() {
        return dfh_headpic;
    }

    public void setDfh_headpic(String dfh_headpic) {
        this.dfh_headpic = dfh_headpic;
    }

    public String getDfh_nickname() {
        return dfh_nickname;
    }

    public void setDfh_nickname(String dfh_nickname) {
        this.dfh_nickname = dfh_nickname;
    }

    public String getDfh_uid() {
        return dfh_uid;
    }

    public void setDfh_uid(String dfh_uid) {
        this.dfh_uid = dfh_uid;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getHotnews() {
        return hotnews;
    }

    public void setHotnews(String hotnews) {
        this.hotnews = hotnews;
    }

    public String getIsJian() {
        return isJian;
    }

    public void setIsJian(String isJian) {
        this.isJian = isJian;
    }

    public String getIsliveshow() {
        return isliveshow;
    }

    public void setIsliveshow(String isliveshow) {
        this.isliveshow = isliveshow;
    }

    public String getIsnxw() {
        return isnxw;
    }

    public void setIsnxw(String isnxw) {
        this.isnxw = isnxw;
    }

    public String getIsoriginal() {
        return isoriginal;
    }

    public void setIsoriginal(String isoriginal) {
        this.isoriginal = isoriginal;
    }

    public String getIspicnews() {
        return ispicnews;
    }

    public void setIspicnews(String ispicnews) {
        this.ispicnews = ispicnews;
    }

    public String getIsrecom() {
        return isrecom;
    }

    public void setIsrecom(String isrecom) {
        this.isrecom = isrecom;
    }

    public String getIsvideo() {
        return isvideo;
    }

    public void setIsvideo(String isvideo) {
        this.isvideo = isvideo;
    }

    public List<LbImg> getLbimg() {
        return lbimg;
    }

    public void setLbimg(List<LbImg> lbimg) {
        this.lbimg = lbimg;
    }

    public String getMidTabKey() {
        return midTabKey;
    }

    public void setMidTabKey(String midTabKey) {
        this.midTabKey = midTabKey;
    }

    public List<MiniImg> getMiniimg() {
        return miniimg;
    }

    public void setMiniimg(List<MiniImg> miniimg) {
        this.miniimg = miniimg;
    }

    public String getMiniimg_size() {
        return miniimg_size;
    }

    public void setMiniimg_size(String miniimg_size) {
        this.miniimg_size = miniimg_size;
    }

    public String getNodownvote() {
        return nodownvote;
    }

    public void setNodownvote(String nodownvote) {
        this.nodownvote = nodownvote;
    }

    public String getNoupvote() {
        return noupvote;
    }

    public void setNoupvote(String noupvote) {
        this.noupvote = noupvote;
    }

    public String getPicnums() {
        return picnums;
    }

    public void setPicnums(String picnums) {
        this.picnums = picnums;
    }

    public String getPraisecnt() {
        return praisecnt;
    }

    public void setPraisecnt(String praisecnt) {
        this.praisecnt = praisecnt;
    }

    public String getPreload() {
        return preload;
    }

    public void setPreload(String preload) {
        this.preload = preload;
    }

    public String getRecommendtype() {
        return recommendtype;
    }

    public void setRecommendtype(String recommendtype) {
        this.recommendtype = recommendtype;
    }

    public String getRecommendurl() {
        return recommendurl;
    }

    public void setRecommendurl(String recommendurl) {
        this.recommendurl = recommendurl;
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSuptop() {
        return suptop;
    }

    public void setSuptop(String suptop) {
        this.suptop = suptop;
    }

    public String getTitledisplay() {
        return titledisplay;
    }

    public void setTitledisplay(String titledisplay) {
        this.titledisplay = titledisplay;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTramplecnt() {
        return tramplecnt;
    }

    public void setTramplecnt(String tramplecnt) {
        this.tramplecnt = tramplecnt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlfrom() {
        return urlfrom;
    }

    public void setUrlfrom(String urlfrom) {
        this.urlfrom = urlfrom;
    }

    public String getUrlpv() {
        return urlpv;
    }

    public void setUrlpv(String urlpv) {
        this.urlpv = urlpv;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getVideoalltime() {
        return videoalltime;
    }

    public void setVideoalltime(String videoalltime) {
        this.videoalltime = videoalltime;
    }

    public String getVideonews() {
        return videonews;
    }

    public void setVideonews(String videonews) {
        this.videonews = videonews;
    }
}
