package com.jerry.littlepanda.domain.dftoutiao;

import java.util.List;

/**
 * Created by jerry.hu on 28/08/17.
 */

public class DfTouTiaoVideoEntry {
    String endkey;
    String newkey;
    List<DfTouTiaoVideoItem> data;

    public String getEndkey() {
        return endkey;
    }

    public void setEndkey(String endkey) {
        this.endkey = endkey;
    }

    public String getNewkey() {
        return newkey;
    }

    public void setNewkey(String newkey) {
        this.newkey = newkey;
    }

    public List<DfTouTiaoVideoItem> getData() {
        return data;
    }

    public void setData(List<DfTouTiaoVideoItem> data) {
        this.data = data;
    }

}
