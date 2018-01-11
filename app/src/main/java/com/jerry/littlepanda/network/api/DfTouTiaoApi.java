package com.jerry.littlepanda.network.api;

import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoEntry;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by jerry.hu on 16/09/17.
 */

public interface DfTouTiaoApi {
    @FormUrlEncoded
    @POST("/app_video/getvideos")
    Observable<DfTouTiaoVideoEntry> getVideos(
            @FieldMap Map<String, String> map
    );
}
