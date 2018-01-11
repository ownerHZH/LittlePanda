package com.jerry.littlepanda.readrss;

import java.security.KeyRep;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jerry.hu on 14/09/17.
 */

public enum NewsChannel {

    BEST("首页","best","news"),
    SHIJIEFENGJING("世界风景","m205483","news"),
    JIANSHENNVSHEN("健身女神","t19432","news"),
    JIANSHEN("健身","u0","news"),
    ZENGJI("增肌","e588920","news"),
    PAOBU("跑步","u11885","news"),
    CHANGPAO("长跑","e1113645","news"),
    SANWENG("散文","e207252","news"),
    KANOUMEI("看欧美","v32433","video_live"),
    SHIZHITV("视知TV","m140499","video_live"),
    BUERDASHU("不二大叔","m126869","news"),
    AMUSCLE("AMuscle","m1647","video_live"),
    OUMEIYULE("欧美娱乐","sc44","news"),
    BAT("BAT","u7114","news"),
    MINGXING("明星","sc36","news"),
    HUGO("HUGO","m137088","news"),
    KANKANNEWS("看看新闻","m153325","news"),
    JIEMIANXINWEN("界面","m7089","news"),
    BEIJINGRIBAOGONGDAO("北京日报公道","m87879","news"),
    WANGLUOXINWENLIANBO("网络新闻联播","m14381","news"),
    RENMINRIBAOPINGLUN("人民日报评论","m80422","news"),
    GUANGMINGRIBAO("光明日报","m14384","news"),
    CANKAOXIAOXI("参考消息","m182163","news"),
    YANGLAN("杨澜","m391627","news"),
    LIKAIFU("李开复","m134914","news"),
    YUMINHONG("俞敏洪","m183315","news"),
    NNAZHUANGWANG("男装网","m187804","news"),
    YUDAN("于丹","m204073","news"),
    LINKIN("LinkIn","m193229","news"),
    DEYUNSHE("德云社","m129068","news"),
    TAODUANFANG("陶短房","m137804","news"),
    ZHONGGUOCAIJINGBAODAO("中国财经报道","m128821","news"),
    CHUANGXINGONGCHANG("创新工场","m128794","news"),
    TENGXUNYANJIUYUAN("腾讯研究院","m130162","news"),
    NIUTANQIN("牛弹琴","m60354","news"),
    JINRITUCAO("今日吐槽","m78187","news"),
    DANSHENWANG("单身汪","m68918","news"),
    ZHONGGUOXINWENZHOUKAN("中国新闻周刊","m100106","news"),
    JILISISHIJIEJILU("吉利斯世界纪录","m115063","news"),
    SHOUXIZHANLUEGUAN("首席战略官","m4975","news"),
    SHANGWUYINSHUGUAN("商务印书馆","m82798","news"),
    ERGENG("二更","m18043","news"),
    HOT("要闻","hot","news"),
    JOKE("段子","u12131","joke"),
    QICHE("汽车","c11","news"),
    SHEHUI("社会","c9","news"),
    YULE("娱乐","c3","news"),
    JUNSHI("军事","c7","news"),
    TIYU("体育","c2","news"),
    SIKEZUQIU("肆客足球","m81241","news"),
    NBALANQIUDIANTANG("NBA篮球殿堂","m7794","news"),
    ONFIRE("OnFire","m67553","news"),
    NBA("NBA","sc4","news"),
    CAIJING("财经","c5","news"),
    KEJI("科技","c6","news"),
    GUOKEWANG("果壳网","m126507","news"),
    SANSHILIUKE("36氪","m198311","news"),
    DAXIANGGONGHUI("大象公会","m88969","news"),
    TAIMEITI("钛媒体","m110526","news"),
    JIKEGONGYUAN("极客公园","m42144","news"),
    IFAN("爱范儿","m35420","news"),
    HUXIU("虎嗅","m143901","news"),
    PINGWEST("PingWest","m519","news"),
    LANJINGTMT("蓝鲸TMT网","m9003","news"),
    GONGSIMIWEN("公司秘闻","m207723","news"),
    IHEIMA("I黑马","m52957","news"),
    LEIDICHUWANG("雷帝触网","m93756","news"),
    SHUZIWEIBA("数字尾巴","m38303","news"),
    SHUMA("数码","sc20","news"),
    MEINV("美女","u241","picture"),
    JIANKANG("健康","c16","news"),
    SHISHANG("时尚","c15","news"),
    GAOXIAO("搞笑","s10671","news"),
    LVYOU("旅游","c17","news"),
    AI("人工智能","u6838","news"),
    GUPIAO("股票","u13386","news"),
    CHENXUYUAN("程序员","e21110","news"),
    CHANPINGJINGLI("产品经理","u5203","news"),
    YIDONGHULIAN("移动互联","sc47","news"),
    HULIANWANGJINRONG("互联网金融","u6874","news"),
    DIANSHANG("电商","sc45","news"),
    HULIANWANGCHUANGYE("互联网创业","u8453","news"),
    SHEJIAOWANGLUO("社交网络","sc46","news"),
    CHUANGYETOUZI("创业投资","t587","news"),
    O2O("O2O","t5650","news"),
    BIGDATA("大数据","u704","news"),
    HULIANWANGSIWEI("互联网思维","u8154","news"),
    IT("IT","sc18","news"),
    IPHONE("iPhone","t1198","news"),
    PHONE("手机","sc19","news"),
    DONGMAN("动漫","u75","news"),
    HUWAI("户外","u408","news"),
    DIANJINGTOUTIAO("电竞头条","m102756","news"),


    VIDEO("视频","u13746","video_live")
    ;

    private String channelTitle;
    private String channelId;
    private String cType;

    public static Queue<NewsChannel> allTypeQueue=new LinkedList<NewsChannel>();
    static{
        allTypeQueue.offer(JIANSHENNVSHEN);
        allTypeQueue.offer(HULIANWANGCHUANGYE);
        allTypeQueue.offer(SHEJIAOWANGLUO);
        allTypeQueue.offer(CHUANGYETOUZI);
        allTypeQueue.offer(O2O);
        allTypeQueue.offer(BIGDATA);
        allTypeQueue.offer(HULIANWANGSIWEI);
        allTypeQueue.offer(IT);
        allTypeQueue.offer(IPHONE);
        allTypeQueue.offer(CHANGPAO);
        allTypeQueue.offer(KANOUMEI);
        allTypeQueue.offer(BAT);
        allTypeQueue.offer(BEIJINGRIBAOGONGDAO);
        allTypeQueue.offer(OUMEIYULE);
        allTypeQueue.offer(WANGLUOXINWENLIANBO);
        allTypeQueue.offer(RENMINRIBAOPINGLUN);
        allTypeQueue.offer(MINGXING);
        allTypeQueue.offer(GUANGMINGRIBAO);
        allTypeQueue.offer(SANWENG);
        allTypeQueue.offer(CANKAOXIAOXI);
        allTypeQueue.offer(YANGLAN);
        allTypeQueue.offer(LIKAIFU);
        allTypeQueue.offer(YUMINHONG);
        allTypeQueue.offer(NNAZHUANGWANG);
        allTypeQueue.offer(YUDAN);
        allTypeQueue.offer(LINKIN);
        allTypeQueue.offer(SHIJIEFENGJING);
        allTypeQueue.offer(KANKANNEWS);
        allTypeQueue.offer(JIEMIANXINWEN);
        allTypeQueue.offer(JIANSHEN);
        allTypeQueue.offer(ZENGJI);
        allTypeQueue.offer(PAOBU);
        allTypeQueue.offer(DEYUNSHE);
        allTypeQueue.offer(TAODUANFANG);
        allTypeQueue.offer(ZHONGGUOCAIJINGBAODAO);
        allTypeQueue.offer(CHUANGXINGONGCHANG);
        allTypeQueue.offer(TENGXUNYANJIUYUAN);
        allTypeQueue.offer(NIUTANQIN);
        allTypeQueue.offer(JINRITUCAO);
        allTypeQueue.offer(DANSHENWANG);
        allTypeQueue.offer(ZHONGGUOXINWENZHOUKAN);
        allTypeQueue.offer(JILISISHIJIEJILU);
        allTypeQueue.offer(SHOUXIZHANLUEGUAN);
        allTypeQueue.offer(SHANGWUYINSHUGUAN);
        allTypeQueue.offer(ERGENG);
        allTypeQueue.offer(HOT);
        allTypeQueue.offer(JOKE);
        allTypeQueue.offer(QICHE);
        allTypeQueue.offer(SHEHUI);
        allTypeQueue.offer(YULE);
        allTypeQueue.offer(JUNSHI);
        allTypeQueue.offer(TIYU);
        allTypeQueue.offer(SIKEZUQIU);
        allTypeQueue.offer(NBALANQIUDIANTANG);
        allTypeQueue.offer(ONFIRE);
        allTypeQueue.offer(NBA);
        allTypeQueue.offer(CAIJING);
        allTypeQueue.offer(KEJI);
        allTypeQueue.offer(GUOKEWANG);
        allTypeQueue.offer(SANSHILIUKE);
        allTypeQueue.offer(DAXIANGGONGHUI);
        allTypeQueue.offer(TAIMEITI);
        allTypeQueue.offer(JIKEGONGYUAN);
        allTypeQueue.offer(IFAN);
        allTypeQueue.offer(HUXIU);
        allTypeQueue.offer(PINGWEST);
        allTypeQueue.offer(LANJINGTMT);
        allTypeQueue.offer(GONGSIMIWEN);
        allTypeQueue.offer(IHEIMA);
        allTypeQueue.offer(LEIDICHUWANG);
        allTypeQueue.offer(SHUZIWEIBA);
        allTypeQueue.offer(SHUMA);
        allTypeQueue.offer(MEINV);
        allTypeQueue.offer(JIANKANG);
        allTypeQueue.offer(SHISHANG);
        allTypeQueue.offer(GAOXIAO);
        allTypeQueue.offer(LVYOU);
        allTypeQueue.offer(AI);
        allTypeQueue.offer(GUPIAO);
        allTypeQueue.offer(CHENXUYUAN);
        allTypeQueue.offer(CHANPINGJINGLI);
        allTypeQueue.offer(YIDONGHULIAN);
        allTypeQueue.offer(HULIANWANGJINRONG);
        allTypeQueue.offer(DIANSHANG);
        allTypeQueue.offer(PHONE);
        allTypeQueue.offer(DONGMAN);
        allTypeQueue.offer(HUWAI);
        allTypeQueue.offer(DIANJINGTOUTIAO);
        allTypeQueue.offer(SHIZHITV);
        allTypeQueue.offer(BUERDASHU);
        allTypeQueue.offer(AMUSCLE);

        allTypeQueue.offer(BEST);
    }

    NewsChannel(String channelTitle, String channelId, String cType) {
        this.channelTitle = channelTitle;
        this.channelId = channelId;
        this.cType = cType;
    }

    /**
     * 　　*   随机返回一个枚举值
     * 　　@return a   random   enum   value.
     */
    public static NewsChannel getRandomNewsChannel() {
        long random = System.currentTimeMillis() % 23;
        switch ((int) random) {
            case 0:
                return HOT;
            case 1:
                return SHUZIWEIBA;
            case 2:
                return IT;
            case 3:
                return GUANGMINGRIBAO;
            case 4:
                return LEIDICHUWANG;
            case 5:
                return TIYU;
            case 6:
                return CHUANGYETOUZI;
            case 7:
                return HUWAI;
            case 8:
                return YIDONGHULIAN;
            case 9:
                return TAIMEITI;
            case 10:
                return AI;
            case 11:
                return SHISHANG;
            case 12:
                return KEJI;
            case 13:
                return HUXIU;
            case 14:
                return GUOKEWANG;
            case 15:
                return GONGSIMIWEN;
            case 16:
                return DIANSHANG;
            case 17:
                return CHUANGXINGONGCHANG;
            case 18:
                return ZHONGGUOXINWENZHOUKAN;
            case 19:
                return BAT;
            case 20:
                return DAXIANGGONGHUI;
            case 21:
                return JIANSHEN;
            case 22:
                return JIKEGONGYUAN;
            default:
                return YULE;
        }
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getcType() {
        return cType;
    }
}
