package com.jerry.littlepanda.network.api;

import com.jerry.littlepanda.domain.YiDianNewsResponse;
import com.jerry.littlepanda.domain.dftoutiao.DfTouTiaoVideoEntry;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jerry.hu on 08/11/17.
 */

public interface IntoAdsApi {

    //http://10.14.44.63:8080/ssm_project/api/v1/getinmobiads?deviceid=9d1af64130e0d2d5&imei=864279039582676&ua=Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1&ip=106.39.48.50
    @GET("/ssm_project/api/v1/getinmobiads")
    Observable<String> getInmobiAds(@Query("deviceId") String deviceid,
                                          @Query("imei") String imei,
                                          @Query("ua") String ua,
                                          @Query("ip") String ip);

    //http://192.168.99.106:8080/ssm_project/api/v1/fireinmobibeacons?beacon=http://c.w.inmobi.com/c.asm/C/&ua=Mozilla/5.0 (Linux; Android 7.1.1; vivo X9 Build/NMF26F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36&ip=61.49.105.27
    @GET("/ssm_project/api/v1/fireinmobibeacons")
    Observable<String> fireBeacons(
            @Query("beacon") String beacon,
            @Query("ua") String ua,
            @Query("ip") String ip
    );

    @GET("/ssm_project/api/v1/getvideo")
    Observable<String> getVideos(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    @GET("/ssm_project/api/v1/gettravel")
    Observable<String> getTravel(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

}
