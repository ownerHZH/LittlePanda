
package com.jerry.littlepanda.domain.inmobi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _120 {

    @SerializedName("urls")
    @Expose
    private List<String> urls = null;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
