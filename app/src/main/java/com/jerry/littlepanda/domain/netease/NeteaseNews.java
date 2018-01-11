
package com.jerry.littlepanda.domain.netease;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeteaseNews {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("digest")
    @Expose
    private String digest;
    @SerializedName("docurl")
    @Expose
    private String docurl;
    @SerializedName("commenturl")
    @Expose
    private String commenturl;
    @SerializedName("tienum")
    @Expose
    private Integer tienum;
    @SerializedName("tlastid")
    @Expose
    private String tlastid;
    @SerializedName("tlink")
    @Expose
    private String tlink;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("keywords")
    @Expose
    private List<Keyword> keywords = null;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("newstype")
    @Expose
    private String newstype;
    @SerializedName("pics3")
    @Expose
    private List<Pics3> pics3 = null;
    @SerializedName("channelname")
    @Expose
    private String channelname;
    @SerializedName("imgurl")
    @Expose
    private String imgurl;
    @SerializedName("add1")
    @Expose
    private String add1;
    @SerializedName("add2")
    @Expose
    private String add2;
    @SerializedName("add3")
    @Expose
    private String add3;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public String getCommenturl() {
        return commenturl;
    }

    public void setCommenturl(String commenturl) {
        this.commenturl = commenturl;
    }

    public Integer getTienum() {
        return tienum;
    }

    public void setTienum(Integer tienum) {
        this.tienum = tienum;
    }

    public String getTlastid() {
        return tlastid;
    }

    public void setTlastid(String tlastid) {
        this.tlastid = tlastid;
    }

    public String getTlink() {
        return tlink;
    }

    public void setTlink(String tlink) {
        this.tlink = tlink;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNewstype() {
        return newstype;
    }

    public void setNewstype(String newstype) {
        this.newstype = newstype;
    }

    public List<Pics3> getPics3() {
        return pics3;
    }

    public void setPics3(List<Pics3> pics3) {
        this.pics3 = pics3;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }


}
