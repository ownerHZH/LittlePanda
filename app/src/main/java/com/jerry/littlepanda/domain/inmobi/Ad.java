
package com.jerry.littlepanda.domain.inmobi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("pubContent")
    @Expose
    private PubContent pubContent;
    @SerializedName("landingPage")
    @Expose
    private String landingPage;
    @SerializedName("beaconUrl")
    @Expose
    private String beaconUrl;
    @SerializedName("eventTracking")
    @Expose
    private EventTracking eventTracking;
    @SerializedName("isApp")
    @Expose
    private Boolean isApp;

    public PubContent getPubContent() {
        return pubContent;
    }

    public void setPubContent(PubContent pubContent) {
        this.pubContent = pubContent;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(String landingPage) {
        this.landingPage = landingPage;
    }

    public String getBeaconUrl() {
        return beaconUrl;
    }

    public void setBeaconUrl(String beaconUrl) {
        this.beaconUrl = beaconUrl;
    }

    public EventTracking getEventTracking() {
        return eventTracking;
    }

    public void setEventTracking(EventTracking eventTracking) {
        this.eventTracking = eventTracking;
    }

    public Boolean getIsApp() {
        return isApp;
    }

    public void setIsApp(Boolean isApp) {
        this.isApp = isApp;
    }

}
