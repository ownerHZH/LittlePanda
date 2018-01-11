package com.jerry.littlepanda.domain;

/**
 * Created by jerry.hu on 22/10/17.
 */

public class DeviceInfoEntity {
    private String title;
    private String detail;

    public final static String TITLE="title";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public DeviceInfoEntity(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }
}
