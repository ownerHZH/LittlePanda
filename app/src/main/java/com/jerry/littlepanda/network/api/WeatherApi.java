package com.jerry.littlepanda.network.api;

import com.jerry.littlepanda.domain.weather.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jerry.hu on 15/10/17.
 */

public interface WeatherApi {

    /**
     * location
     所查询的位置
     参数值范围：
     城市ID 例如：location=WX4FBXXFKE4F
     城市中文名 例如：location=北京
     省市名称组合 例如：location=辽宁朝阳、location=北京朝阳
     城市拼音/英文名 例如：location=beijing（如拼音相同城市，可在之前加省份和空格，例：shanxi yulin）
     经纬度 例如：location=39.93:116.40（格式是 纬度:经度，英文冒号分隔）
     IP地址 例如：location=220.181.111.86（某些IP地址可能无法定位到城市）
     “ip”两个字母 自动识别请求IP地址，例如：location=ip
     * @param key
     * @param location
     * @return
     */

    @GET("/v3/weather/now.json")
    Observable<Weather> getWeather(@Query("key") String key,
                                   @Query("location") String location);

    @GET("/v3/life/suggestion.json")
    Observable<Weather> getLifeSuggestion(@Query("key") String key,
                                   @Query("location") String location);
}
