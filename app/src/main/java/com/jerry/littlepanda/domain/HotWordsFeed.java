package com.jerry.littlepanda.domain;

import java.io.Serializable;

/**
 * Created by jerry.hu on 18/08/17.
 */

public class HotWordsFeed implements Serializable {
    /*
            "title": "无语!交警查获史上最假车牌:辽B·BBBBB",
            "content": "在进一步询问过程中,驾驶人孙某承认该<em>号牌</em>是其出于好玩自己做的,并未悬挂过。但民警发现这副6个B的<em>号牌</em>明显有使用过的痕迹。根据交通法相关规定,伪造变造机动车<em>号牌</em>将被处以罚款5000元、行政拘留15日的处罚。目前,该案正在进一步审理中。  交警提醒:在高速公路上未按规定...",
            "img_width": "",
            "full_title": "无语!交警查获史上最假车牌:辽B·BBBBB",
            "pdate": "08月17日 21:38",
            "src": "南方网",
            "img_length": "",
            "img": "",
            "url": "http://news.southcn.com/community/content/2017-08/17/content_176289825_2.htm",
            "pdate_src": "2017-08-17 21:38:00"
      */
    String title;
    String content;
    String img_width;
    String full_title;
    String pdate;
    String src;
    String img_length;
    String img;
    String url;
    String pdate_src;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_width() {
        return img_width;
    }

    public void setImg_width(String img_width) {
        this.img_width = img_width;
    }

    public String getFull_title() {
        return full_title;
    }

    public void setFull_title(String full_title) {
        this.full_title = full_title;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getImg_length() {
        return img_length;
    }

    public void setImg_length(String img_length) {
        this.img_length = img_length;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPdate_src() {
        return pdate_src;
    }

    public void setPdate_src(String pdate_src) {
        this.pdate_src = pdate_src;
    }

    @Override
    public String toString() {
        return "HotWordsFeed{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", img_width='" + img_width + '\'' +
                ", full_title='" + full_title + '\'' +
                ", pdate='" + pdate + '\'' +
                ", src='" + src + '\'' +
                ", img_length='" + img_length + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", pdate_src='" + pdate_src + '\'' +
                '}';
    }
}

