package com.jerry.littlepanda.network.api;

import com.jerry.littlepanda.domain.YiDianNewsResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jerry.hu on 04/09/17.
 */

public interface YiDianZiXunApi {

    @GET("/home/q/news_list_for_channel")
    Observable<YiDianNewsResponse> search(@Query("cstart") String cstart,
                                          @Query("cend") String cend,
                                          @Query("channel_id") String channel_id,
                                          @Query("infinite") boolean infinite,
                                          @Query("refresh") int refresh,
                                          @Query("__from__") String __from__,
                                          @Query("multi") int multi,
                                          @Query("appid") String appid,
                                          @Query("_") String xiahuaian);
    @GET("/home/q/news_list_for_keyword")
    Observable<YiDianNewsResponse> search(@Query("display") String display,
                                          @Query("cstart") String cstart,
                                          @Query("cend") String cend,
                                          @Query("word_type") String word_type,
                                          @Query("multi") int multi,
                                          @Query("appid") String appid,
                                          @Query("_") String xiahuaian);

    //https://www.yidianzixun.com/home/q/news_list_for_keyword?display=%E5%81%A5%E8%BA%AB%E5%A5%B3%E7%A5%9E&cstart=0&cend=10&word_type=token&multi=5&appid=web_yidian&_=1506073074282
}
