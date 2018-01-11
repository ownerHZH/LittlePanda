
package com.jerry.littlepanda.domain.inmobi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InmobiApiResponseObject {

    @SerializedName("ads")
    @Expose
    private List<Ad> ads = null;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
