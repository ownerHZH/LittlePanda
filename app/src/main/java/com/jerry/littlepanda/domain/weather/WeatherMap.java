package com.jerry.littlepanda.domain.weather;

import com.jerry.littlepanda.R;

/**
 * Created by jerry.hu on 15/10/17.
 */

public enum WeatherMap {

    CODE0(0,"晴","Sunny",R.drawable.weather0),
    CODE1(1,"晴","Clear",R.drawable.weather1),
    CODE2(2,"晴","Fair",R.drawable.weather2),
    CODE3(3,"晴","Fair",R.drawable.weather3),
    CODE4(4,"多云","Cloudy",R.drawable.weather4),
    CODE5(5,"晴间多云","Partly Cloudy",R.drawable.weather5),
    CODE6(6,"晴间多云","Partly Cloudy",R.drawable.weather6),
    CODE7(7,"大部多云","Mostly Cloudy",R.drawable.weather7),
    CODE8(8,"大部多云","Mostly Cloudy",R.drawable.weather8),
    CODE9(9,"阴","Overcast",R.drawable.weather9),
    CODE10(10,"阵雨","Shower",R.drawable.weather10),
    CODE11(11,"雷阵雨","Thundershower",R.drawable.weather11),
    CODE12(12,"雷阵雨伴有冰雹","Thundershower with Hail",R.drawable.weather12),
    CODE13(13,"小雨","Light Rain",R.drawable.weather13),
    CODE14(14,"中雨","Moderate Rain",R.drawable.weather14),
    CODE15(15,"大雨","Heavy Rain",R.drawable.weather15),
    CODE16(16,"暴雨","Storm",R.drawable.weather16),
    CODE17(17,"大暴雨","Heavy Storm",R.drawable.weather17),
    CODE18(18,"特大暴雨","Severe Storm",R.drawable.weather18),
    CODE19(19,"冻雨","Ice Rain",R.drawable.weather19),
    CODE20(20,"雨夹雪","Sleet",R.drawable.weather20),
    CODE21(21,"阵雪","Snow Flurry",R.drawable.weather21),
    CODE22(22,"小雪","Light Snow",R.drawable.weather22),
    CODE23(23,"中雪","Moderate Snow",R.drawable.weather23),
    CODE24(24,"大雪","Heavy Snow",R.drawable.weather24),
    CODE25(25,"暴雪","Snowstorm",R.drawable.weather25),
    CODE26(26,"浮尘","Dust",R.drawable.weather26),
    CODE27(27,"扬沙","Sand",R.drawable.weather27),
    CODE28(28,"沙尘暴","Duststorm",R.drawable.weather28),
    CODE29(29,"强沙尘暴","Sandstorm",R.drawable.weather29),
    CODE30(30,"雾","Foggy",R.drawable.weather30),
    CODE31(31,"霾","Haze",R.drawable.weather31),
    CODE32(32,"风","Windy",R.drawable.weather32),
    CODE33(33,"大风","Blustery",R.drawable.weather33),
    CODE34(34,"飓风","Hurricane",R.drawable.weather34),
    CODE35(35,"热带风暴","Tropical Storm",R.drawable.weather35),
    CODE36(36,"龙卷风","Tornado",R.drawable.weather36),
    CODE37(37,"冷","Cold",R.drawable.weather37),
    CODE38(38,"热","Hot",R.drawable.weather38),
    CODE99(99,"未知","Unknown",R.drawable.weather99)
    ;
    private int code;
    private String code_name_zh;
    private String code_name_en;
    private int icon;

    public int getCode() {
        return code;
    }

    public String getCode_name_zh() {
        return code_name_zh;
    }

    public String getCode_name_en() {
        return code_name_en;
    }

    public int getIcon() {
        return icon;
    }

    WeatherMap(int code, String code_name_zh, String code_name_en, int icon) {
        this.code = code;
        this.code_name_zh = code_name_zh;
        this.code_name_en = code_name_en;
        this.icon = icon;
    }

   public static WeatherMap getWeatherMap(int code)
    {
        WeatherMap weatherMap=null;
        switch(code){
            case 0:
                weatherMap=CODE0;
                break;
            case 1:
                weatherMap=CODE1;
                break;
            case 02:
                weatherMap=CODE2;
                break;
            case 3:
                weatherMap=CODE3;
                break;
            case 4:
                weatherMap=CODE4;
                break;
            case 5:
                weatherMap=CODE5;
                break;
            case 6:
                weatherMap=CODE6;
                break;
            case 7:
                weatherMap=CODE7;
                break;
            case 8:
                weatherMap=CODE8;
                break;
            case 9:
                weatherMap=CODE9;
                break;
            case 10:
                weatherMap=CODE10;
                break;
            case 11:
                weatherMap=CODE11;
                break;
            case 12:
                weatherMap=CODE12;
                break;
            case 13:
                weatherMap=CODE13;
                break;
            case 14:
                weatherMap=CODE14;
                break;
            case 15:
                weatherMap=CODE15;
                break;
            case 16:
                weatherMap=CODE16;
                break;
            case 17:
                weatherMap=CODE17;
                break;
            case 18:
                weatherMap=CODE18;
                break;
            case 19:
                weatherMap=CODE19;
                break;
            case 20:
                weatherMap=CODE20;
                break;
            case 21:
                weatherMap=CODE21;
                break;
            case 22:
                weatherMap=CODE22;
                break;
            case 23:
                weatherMap=CODE23;
                break;
            case 24:
                weatherMap=CODE24;
                break;
            case 25:
                weatherMap=CODE25;
                break;
            case 26:
                weatherMap=CODE26;
                break;
            case 27:
                weatherMap=CODE27;
                break;
            case 28:
                weatherMap=CODE28;
                break;
            case 29:
                weatherMap=CODE29;
                break;
            case 30:
                weatherMap=CODE30;
                break;
            case 31:
                weatherMap=CODE31;
                break;
            case 32:
                weatherMap=CODE32;
                break;
            case 33:
                weatherMap=CODE33;
                break;
            case 34:
                weatherMap=CODE34;
                break;
            case 35:
                weatherMap=CODE35;
                break;
            case 36:
                weatherMap=CODE36;
                break;
            case 37:
                weatherMap=CODE37;
                break;
            case 38:
                weatherMap=CODE38;
                break;
            default:
                weatherMap=CODE99;
                break;
        }
        return weatherMap;
    }
}
