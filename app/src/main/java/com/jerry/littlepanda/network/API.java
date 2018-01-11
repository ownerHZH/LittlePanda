package com.jerry.littlepanda.network;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.network.api.DfTouTiaoApi;
import com.jerry.littlepanda.network.api.IPApi;
import com.jerry.littlepanda.network.api.IntoAdsApi;
import com.jerry.littlepanda.network.api.WeatherApi;
import com.jerry.littlepanda.network.api.YiDianZiXunApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by jerry.hu on 04/09/17.
 */

public class API {
    private static YiDianZiXunApi yiDianZiXunApi;
    private static DfTouTiaoApi dfTouTiaoApi;
    private static WeatherApi weatherApi;
    private static IPApi ipApi;
    private static IntoAdsApi intoAdsApi;

    private static final long TIMEOUT = 30;
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            // 添加通用的Header
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    //builder.addHeader("token", "123");
                    builder.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");

                    return chain.proceed(builder.build());
                }
            })
            /*
            这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
            出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
             */

            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("Ads= API",message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))

            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(buildGson());
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static YiDianZiXunApi getYiDianZiXunApi() {
        if (yiDianZiXunApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.YIDIANZIXUNBASEURL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            yiDianZiXunApi = retrofit.create(YiDianZiXunApi.class);
        }
        return yiDianZiXunApi;
    }

    public static DfTouTiaoApi getDfTouTiaoApi() {
        if (dfTouTiaoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient.Builder().build())
                    .baseUrl("http://video.dftoutiao.com")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)

                    .build();
            dfTouTiaoApi = retrofit.create(DfTouTiaoApi.class);
        }
        return dfTouTiaoApi;
    }

    public static WeatherApi getWeatherApi()
    {
        if (weatherApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.WEATHER_API_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)

                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }

    public static IPApi getIPApi()
    {
        if (ipApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.IP_API_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)

                    .build();
            ipApi = retrofit.create(IPApi.class);
        }
        return ipApi;
    }

    public static IntoAdsApi getIntoAdsApi()
    {
        if(intoAdsApi==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.INTOADS_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)

                    .build();
            intoAdsApi= retrofit.create(IntoAdsApi.class);
        }
        return intoAdsApi;
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
                //.registerTypeAdapter(YiDianNewsResponse.class, new YiDianZiXunNewsTypeAdapter())
                .create();
    }
}
