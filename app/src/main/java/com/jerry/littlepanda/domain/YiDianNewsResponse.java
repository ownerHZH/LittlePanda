package com.jerry.littlepanda.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry.hu on 21/08/17.
 */

public class YiDianNewsResponse implements Serializable {
    String status;
    long code;
    List<YiDianNewsItem> result;
    int fresh_count;
    int disable_op;

    public List<YiDianNewsItem> getResult() {
        return result;
    }

    public void setResult(List<YiDianNewsItem> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }


    public int getFresh_count() {
        return fresh_count;
    }

    public void setFresh_count(int fresh_count) {
        this.fresh_count = fresh_count;
    }

    public int getDisable_op() {
        return disable_op;
    }

    public void setDisable_op(int disable_op) {
        this.disable_op = disable_op;
    }
}
