package com.jerry.littlepanda.video;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jerry.hu on 03/10/17.
 */

public enum VideoChannel {
    TUIJIAN(1,"推荐","799999"),
    ZHIBO(2,"直播","400001"),
    YULE(3,"娱乐","700010"),
    GAOXIAO(4,"搞笑","700001"),
    YINGSHI(5,"影视","700021"),
    YOUXI(6,"游戏","700017"),
    TIYU(7,"体育","700006"),
    QICHE(8,"汽车","700007"),
    JILUPIAN(9,"纪录片","700004"),
    KEJI(10,"科技","700008"),
    ZIXUN(11,"资讯","700003"),
    PAIKE(12,"拍客","700002"),
    GONGYI(13,"公益","700005"),
    CAIJING(14,"财经","700009"),
    YUANCHUANG(15,"原创","700011"),
    LVYOU(16,"旅游","700013"),
    SHISHANG(17,"时尚","700014"),
    QINGZI(18,"亲子","700015"),
    JIAOYU(19,"教育","700016"),
    SHENGHUO(20,"生活","700018"),
    SHIPIN(21,"视频","shipin")
    ;
    private String channelTitle;
    private String channelId;
    private int incId;

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getChannelId() {
        return channelId;
    }

    public int getIncId() {
        return incId;
    }

    VideoChannel(int incId,String channelTitle, String channelId) {
        this.channelTitle = channelTitle;
        this.channelId = channelId;
        this.incId = incId;
    }
    public static Queue<VideoChannel> allTypeQueue=new LinkedList<VideoChannel>();
    static{
        allTypeQueue.offer(KEJI);
        allTypeQueue.offer(ZIXUN);
        allTypeQueue.offer(SHIPIN);
        allTypeQueue.offer(ZHIBO);
        allTypeQueue.offer(YULE);
        allTypeQueue.offer(GAOXIAO);
        allTypeQueue.offer(YINGSHI);
        allTypeQueue.offer(YOUXI);
        allTypeQueue.offer(TIYU);
        allTypeQueue.offer(TUIJIAN);
        allTypeQueue.offer(QICHE);
        allTypeQueue.offer(JILUPIAN);
        allTypeQueue.offer(PAIKE);
        allTypeQueue.offer(GONGYI);
        allTypeQueue.offer(CAIJING);
        allTypeQueue.offer(YUANCHUANG);
        allTypeQueue.offer(LVYOU);
        allTypeQueue.offer(SHISHANG);
        allTypeQueue.offer(QINGZI);
        allTypeQueue.offer(JIAOYU);
        allTypeQueue.offer(SHENGHUO);

    }

    /**
     * 　　*   随机返回一个枚举值
     * 　　@return a   random   enum   value.
     */
    public static VideoChannel getRandomVideoChannel() {
        long random = System.currentTimeMillis() % 21;
        switch ((int) random) {
            case 0:
                return TUIJIAN;
            case 1:
                return ZHIBO;
            case 2:
                return YULE;
            case 3:
                return GAOXIAO;
            case 4:
                return YINGSHI;
            case 5:
                return YOUXI;
            case 6:
                return TIYU;
            case 7:
                return QICHE;
            case 8:
                return JILUPIAN;
            case 9:
                return KEJI;
            case 10:
                return ZIXUN;
            case 11:
                return PAIKE;
            case 12:
                return GONGYI;
            case 13:
                return CAIJING;
            case 14:
                return YUANCHUANG;
            case 15:
                return LVYOU;
            case 16:
                return SHISHANG;
            case 17:
                return QINGZI;
            case 18:
                return JIAOYU;
            case 19:
                return SHENGHUO;
            case 20:
                return SHIPIN;
            default:
                return SHIPIN;
        }
    }
}
