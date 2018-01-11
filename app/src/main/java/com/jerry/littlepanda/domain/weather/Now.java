
package com.jerry.littlepanda.domain.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("temperature")
    @Expose
    private String temperature;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
