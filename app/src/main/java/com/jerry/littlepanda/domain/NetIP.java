package com.jerry.littlepanda.domain;

/**
 * Created by jerry.hu on 22/10/17.
 */

public class NetIP {
    //{"cip": "101.38.155.247", "cid": "CN", "cname": "CHINA"}
    private String cip;
    private String cid;
    private String cname;

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "NetIP{" +
                "cip='" + cip + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
