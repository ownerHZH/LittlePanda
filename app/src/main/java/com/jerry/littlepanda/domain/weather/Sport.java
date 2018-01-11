package com.jerry.littlepanda.domain.weather;

/**
 * Created by jerry.hu on 16/10/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sport {

    @SerializedName("brief")
    @Expose
    private String brief;
    @SerializedName("details")
    @Expose
    private String details;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
