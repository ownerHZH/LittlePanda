package com.jerry.littlepanda.domain.weather;

/**
 * Created by jerry.hu on 16/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("car_washing")
    @Expose
    private CarWashing carWashing;
    @SerializedName("dressing")
    @Expose
    private Dressing dressing;
    @SerializedName("flu")
    @Expose
    private Flu flu;
    @SerializedName("sport")
    @Expose
    private Sport sport;
    @SerializedName("travel")
    @Expose
    private Travel travel;
    @SerializedName("uv")
    @Expose
    private Uv uv;

    public CarWashing getCarWashing() {
        return carWashing;
    }

    public void setCarWashing(CarWashing carWashing) {
        this.carWashing = carWashing;
    }

    public Dressing getDressing() {
        return dressing;
    }

    public void setDressing(Dressing dressing) {
        this.dressing = dressing;
    }

    public Flu getFlu() {
        return flu;
    }

    public void setFlu(Flu flu) {
        this.flu = flu;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public Uv getUv() {
        return uv;
    }

    public void setUv(Uv uv) {
        this.uv = uv;
    }

}
