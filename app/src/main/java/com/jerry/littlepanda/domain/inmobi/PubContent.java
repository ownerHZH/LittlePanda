
package com.jerry.littlepanda.domain.inmobi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PubContent {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private Icon icon;
    @SerializedName("screenshots")
    @Expose
    private Screenshots screenshots;
    @SerializedName("landingURL")
    @Expose
    private String landingURL;
    @SerializedName("cta")
    @Expose
    private String cta;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Screenshots getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(Screenshots screenshots) {
        this.screenshots = screenshots;
    }

    public String getLandingURL() {
        return landingURL;
    }

    public void setLandingURL(String landingURL) {
        this.landingURL = landingURL;
    }

    public String getCta() {
        return cta;
    }

    public void setCta(String cta) {
        this.cta = cta;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
