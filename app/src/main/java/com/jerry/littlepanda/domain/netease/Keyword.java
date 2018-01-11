
package com.jerry.littlepanda.domain.netease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keyword {

    @SerializedName("akey_link")
    @Expose
    private String akeyLink;
    @SerializedName("keyname")
    @Expose
    private String keyname;

    public String getAkeyLink() {
        return akeyLink;
    }

    public void setAkeyLink(String akeyLink) {
        this.akeyLink = akeyLink;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

}
