package com.jerry.littlepanda.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("enableReader")
    @Expose
    private Boolean enableReader;
    @SerializedName("enableSplash")
    @Expose
    private Boolean enableSplash;

    public Boolean getEnableReader() {
        return enableReader;
    }

    public void setEnableReader(Boolean enableReader) {
        this.enableReader = enableReader;
    }

    public Boolean getEnableSplash() {
        return enableSplash;
    }

    public void setEnableSplash(Boolean enableSplash) {
        this.enableSplash = enableSplash;
    }

}