package com.jerry.littlepanda.network.api;

import com.jerry.littlepanda.domain.weather.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jerry.hu on 15/10/17.
 */

public interface IPApi {


    @GET("/cityjson")
    Observable<String> getIP(@Query("ie") String ie);

}
