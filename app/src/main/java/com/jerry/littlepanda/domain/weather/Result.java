
package com.jerry.littlepanda.domain.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("now")
    @Expose
    private Now now;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;

    @SerializedName("suggestion")
    @Expose
    private Suggestion suggestion;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

}
