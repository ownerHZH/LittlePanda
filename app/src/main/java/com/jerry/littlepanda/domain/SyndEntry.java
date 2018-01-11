package com.jerry.littlepanda.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jerry.littlepanda.Utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jerry.hu on 15/07/17.
 */

/**
 * UserBean实体类，存储数据库中user表中的数据
 * <p>
 * 注解：
 * DatabaseTable：通过其中的tableName属性指定数据库名称
 * DatabaseField：代表数据表中的一个字段
 * ForeignCollectionField：一对多关联，表示一个UserBean关联着多个ArticleBean（必须使用ForeignCollection集合）
 * <p>
 * 属性：
 * id：当前字段是不是id字段（一个实体类中只能设置一个id字段）
 * columnName：表示当前属性在表中代表哪个字段
 * generatedId：设置属性值在数据表中的数据是否自增
 * useGetSet：是否使用Getter/Setter方法来访问这个字段
 * canBeNull：字段是否可以为空，默认值是true
 * unique：是否唯一
 * defaultValue：设置这个字段的默认值
 * http://www.cnblogs.com/itgungnir/p/6210949.html
 */

@DatabaseTable(tableName = "t_sourceentry") // 指定数据表的名称
public class SyndEntry {

    public static final String ENTRY_ID="id";
    public static final String SOURCE_ID="sid";
    public static final String ENTRY_TITLE="title";
    public static final String ENTRY_LINK="link";
    public static final String ENTRY_DESC="description";
    public static final String ENTRY_PUBDATE="pubDate";
    public static final String ENTRY_GUID="guid";
    public static final String ENTRY_AUTHOR="author";
    public static final String ENTRY_CATEGORY="category";
    public static final String ENTRY_COMMENTS="comments";
    public static final String ENTRY_ENCLOSURE="enclosure";
    public static final String ENTRY_SOURCE="source";

    @DatabaseField(generatedId = true, columnName = ENTRY_ID, useGetSet = true)
	int id=0;
    @DatabaseField(columnName = SOURCE_ID, useGetSet = true, canBeNull = true)
	int sid=0;
    @DatabaseField(columnName = ENTRY_TITLE, useGetSet = true, canBeNull = true)
    String title="";
    @DatabaseField(columnName = ENTRY_LINK, useGetSet = true, canBeNull = true)
    String link="";
    @DatabaseField(columnName = ENTRY_DESC, useGetSet = true, canBeNull = true)
    String description="";
    @DatabaseField(columnName = ENTRY_PUBDATE, useGetSet = true, canBeNull = true)
    String pubDate="";
    @DatabaseField(columnName = ENTRY_GUID, useGetSet = true, canBeNull = true)
    String guid="";
    @DatabaseField(columnName = ENTRY_AUTHOR, useGetSet = true, canBeNull = true)
    String author="";
    @DatabaseField(columnName = ENTRY_CATEGORY, useGetSet = true, canBeNull = true)
    String category="";
    @DatabaseField(columnName = ENTRY_COMMENTS, useGetSet = true, canBeNull = true)
    String comments="";
    @DatabaseField(columnName = ENTRY_ENCLOSURE, useGetSet = true, canBeNull = true)
    String enclosure="";//可选的。允许将一个媒体文件导入一个项中。
    @DatabaseField(columnName = ENTRY_SOURCE, useGetSet = true, canBeNull = true)
    String source="";//可选的。为此项目指定一个第三方来源。
    @ForeignCollectionField(eager = true)
    Collection<EntryImage> images;

    /*
    List<EntryImage> imagess;

    public List<EntryImage> getImagess() {
        return imagess;
    }

    public void setImagess(List<EntryImage> imagess) {
        this.imagess = imagess;
    }
    */

    public Collection<EntryImage> getImages() {
        return images;
    }

    public void setImages(Collection<EntryImage> images) {
        this.images = images;
    }

    public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        /*
        if (description.length() > 20) {
            return description.substring(0, 19) + "...";
        }*/
        return description;
    }

    public void setDescription(String description) {
    	
        this.description = description;
        //setImages(StringUtils.getImageUrl(description));
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	@Override
	public String toString() {
		return "SyndEntry [id=" + id
				+ ", images=" + images + "]";
	}

	//Json传格式化为list
	public static List<SyndEntry> parseJson(String data)
    {
        List<SyndEntry> syndEntrys = new ArrayList<SyndEntry>();

        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(data);
        if(el!=null&&el.isJsonArray())
        {
            JsonArray jsonArray=el.getAsJsonArray();
            if(jsonArray!=null&&jsonArray.size()>0)
            {
                for(int i=0;i<jsonArray.size();i++)
                {
                    el=jsonArray.get(i);
                    if(el!=null&&el.isJsonObject())
                    {
                        SyndEntry syndEntry=new SyndEntry();
                        JsonObject jsonObject=el.getAsJsonObject();

                        int id=0;
                        if(jsonObject.get("id")!=null)
                        {
                            id=jsonObject.get("id").getAsInt();
                        }

                        int sid=0;
                        if(jsonObject.get("sid")!=null)
                        {
                            sid=jsonObject.get("sid").getAsInt();
                        }

                        String title="";
                        if(jsonObject.get("title")!=null)
                        {
                            title=jsonObject.get("title").getAsString();
                        }
                        String link="";
                        if(jsonObject.get("link")!=null)
                        {
                            link=jsonObject.get("link").getAsString();
                        }

                        String description="";
                        if(jsonObject.get("description")!=null)
                        {
                            description=jsonObject.get("description").getAsString();
                            //Base64解密
                            description= StringUtils.decodeData(description);
                            //description=Constants.GETDETAIL+id;//把详细页面的服务器地址传给描述字段
                        }

                        String pubDate="";
                        if(jsonObject.get("pubDate")!=null){
                            pubDate=jsonObject.get("pubDate").getAsString();
                        }
                        String guid="";
                        if(jsonObject.get("guid")!=null)
                        {
                            guid=jsonObject.get("guid").getAsString();
                        }
                        String author="";
                        if(jsonObject.get("author")!=null){
                            author=jsonObject.get("author").getAsString();
                        }

                        String category="";
                        if(jsonObject.get("category")!=null){
                            category=jsonObject.get("category").getAsString();
                        }
                        String comments="";
                        if(jsonObject.get("comments")!=null){
                            comments=jsonObject.get("comments").getAsString();
                        }

                        String enclosure="";
                        if(jsonObject.get("enclosure")!=null)
                        {
                            enclosure=jsonObject.get("enclosure").getAsString();
                        }
                        String source="";
                        if(jsonObject.get("source")!=null){
                            source=jsonObject.get("source").getAsString();
                        }
                        syndEntry.setId(id);
                        syndEntry.setSid(sid);
                        syndEntry.setTitle(title);
                        syndEntry.setLink(link);
                        syndEntry.setDescription(description);
                        syndEntry.setPubDate(pubDate);
                        syndEntry.setGuid(guid);
                        syndEntry.setAuthor(author);
                        syndEntry.setCategory(category);
                        syndEntry.setComments(comments);
                        syndEntry.setEnclosure(enclosure);
                        syndEntry.setSource(source);

                        Collection<EntryImage> foreignCollection2=new ArrayList<>();
                        //List<EntryImage> images=new ArrayList<EntryImage>();
                        JsonElement imagesEl=jsonObject.get("images");
                        if(imagesEl!=null&&imagesEl.isJsonArray())
                        {
                            JsonArray imagesJsonArray=imagesEl.getAsJsonArray();
                            if(imagesJsonArray!=null&&imagesJsonArray.size()>0)
                            {
                                for(int j=0;j<imagesJsonArray.size();j++)
                                {
                                    JsonElement imageEl=imagesJsonArray.get(j);
                                    if(imageEl.isJsonObject())
                                    {
                                        EntryImage entryImage=new EntryImage();
                                        JsonObject imageJsonObject=imageEl.getAsJsonObject();
                                        int imageId=0;
                                        if(imageJsonObject.get("imageId")!=null){
                                            imageId=imageJsonObject.get("imageId").getAsInt();
                                        }
                                        int entryId=0;
                                        if(imageJsonObject.get("entryId")!=null)
                                        {
                                            entryId=imageJsonObject.get("entryId").getAsInt();
                                        }
                                        String imageLink="";
                                        if(imageJsonObject.get("imageLink")!=null)
                                        {
                                            imageLink=imageJsonObject.get("imageLink").getAsString();
                                        }
                                        entryImage.setEntryId(syndEntry);
                                        entryImage.setImageId(imageId);
                                        entryImage.setImageLink(imageLink);
                                        //Log.e(Constants.LOG_TAG,"=="+imageLink);
                                        foreignCollection2.add(entryImage);
                                    }
                                }
                                syndEntry.setImages(foreignCollection2);
                            }
                        }
                        syndEntrys.add(syndEntry);
                    }
                }
            }

        }
        return syndEntrys;
    }

    /**
     * //从服务器拉取数据
     * @param pageNum 页数
     * @param pageSize 每页的大小
     * @param handler
     * @param signal Handle send的message
     * @return

    public static List<SyndEntry> pullFromSever(int pageNum, int pageSize, final Handler handler, final int signal){
        final List<SyndEntry> syndEntrys=new ArrayList<SyndEntry>();
        NetWork.getObjectApi(Constants.GETRSS+pageNum+"&pageSize="+pageSize, new ResponseListener<OutputJson>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.LOG_TAG,"error="+error.getMessage()+"");
                handler.sendEmptyMessage(signal);
            }

            @Override
            public void onResponse(OutputJson response) {
                //Log.e(Constants.LOG_TAG,"=="+response.toString());
                if(response.getStatus()==0)
                {
                    syndEntrys.addAll(SyndEntry.parseJson(response.getData()));
                }
                handler.sendEmptyMessageDelayed(signal,1000);
                //handler.sendEmptyMessage(signal);
            }
        });
        return syndEntrys;
    }*/

    /**
     * 从服务器拉取并保存到数据库
     * @param context
     * @param page
     * @param size

    public static void pullFromSeverAndStore(final Context context, int page, int size)
    {
        NetWork.getObjectApi(Constants.GETRSS+page+"&pageSize="+size, new ResponseListener<OutputJson>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.LOG_TAG,"error="+error.getMessage()+"");
                //getFreeNewsAndStore(context);
            }

            @Override
            public void onResponse(OutputJson response) {
                Log.e(Constants.LOG_TAG,"=="+response.toString());
                if(response.getStatus()==0)
                {
                    List<SyndEntry> syndEntrys =SyndEntry.parseJson(response.getData());
                    deleteMoreFeeds(context);//先判断是否有超过100条
                    if(null!=syndEntrys&&syndEntrys.size()>0)
                    {
                        for(int i=0;i<syndEntrys.size();i++)
                        {
                            //Log.e(Constants.LOG_TAG,"=="+syndEntrys.get(i).toString());
                            //先查找是否已经存储了咨询，去重
                            List<SyndEntry> existEntrys=DataManagerService.getInstance(context)
                                    .provideSyndEntryDao()
                                    .queryByColumn(SyndEntry.ENTRY_LINK,syndEntrys.get(i).getLink());
                            if(existEntrys!=null&&existEntrys.size()>0)
                            {
                                continue;
                            }else
                            {
                                //插入数据库
                                DataManagerService.getInstance(context)
                                        .provideSyndEntryDao()
                                        .add(syndEntrys.get(i));

                                if(syndEntrys.get(i)!=null
                                        &&syndEntrys.get(i).getImages()!=null
                                        &&syndEntrys.get(i).getImages().size()>0)
                                {
                                    for(EntryImage entryImage:syndEntrys.get(i).getImages())
                                    {
                                        DataManagerService.getInstance(context)
                                                .provideEntryImageDao().add(entryImage);
                                    }
                                }

                            }
                        }
                    }else
                    {
                        //数据返回空，证明服务端没有数据了，就重头开始拉取
                        SyndEntry.page=1;
                        //getFreeNewsAndStore(context);
                    }

                }
            }
        });
    }*/

    /**
     * 确保数据库里面只有100条信息流
     * @param context

    public static void deleteMoreFeeds(Context context){
        //删除数据库里面多的数据，保留100条
        long count=DataManagerService.getInstance(context)
                .provideSyndEntryDao()
                .getCountOf();
        Log.e(Constants.LOG_TAG,"onStop=delete="+count);
        if(count>100)
        {
            DataManagerService.getInstance(context)
                    .provideSyndEntryDao()
                    .deleteList(
                            DataManagerService.getInstance(context)
                                    .provideSyndEntryDao()
                                    .selectAllByPage(1L,count-100)
                    );

        }
    }*/

    /*

    static int page=1;//服务器拉取的页数
    private static ArrayBlockingQueue<List<SyndEntry>> transFinishedBlockingQueue
            = new ArrayBlockingQueue<List<SyndEntry>>(1);

    public static List<SyndEntry> poll(final Context context) {
         final List<SyndEntry> syndEntrys=DataManagerService.getInstance(context).provideSyndEntryDao()
                .selectAllByPage(1L,15L);
        if(null!=syndEntrys&&syndEntrys.size()>0)
        {
            Log.e("SyndEntry","null!=syndEntrys");
            new Thread(new Runnable() {
                @Override
                public void run() {

                    long count = DataManagerService.getInstance(context)
                            .provideSyndEntryDao()
                            .getCountOf();
                    if (count > 45)
                    {
                        List<SyndEntry> copySyndEntrys = new ArrayList<SyndEntry>(syndEntrys);
                        DataManagerService.getInstance(context).provideSyndEntryDao()
                                .deleteList(copySyndEntrys);
                    }

                    pullFromSeverAndStore(context,page++,15);
                }
            }){}.start();
        }else
        {
            Log.e("SyndEntry","yiDianNewsRequest_GET");
            NetWork.yiDianNewsRequest_GET(transFinishedBlockingQueue,1);
            try {
                List<SyndEntry> backuplist=transFinishedBlockingQueue.poll(5, TimeUnit.SECONDS ); //等待线程读，由于没有内容，所以阻塞。;\
                if(null!=backuplist&&backuplist.size()>0)
                {
                    return backuplist;
                }else{
                    pullFromSeverAndStore(context,page++,15);
                    List<SyndEntry> backuplist2=DataManagerService.getInstance(context).provideSyndEntryDao()
                            .selectAllByPage(1L,15L);
                    return backuplist2;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return syndEntrys;
    }

    public static void offer(final Context context)
    {
        pullFromSeverAndStore(context,page++,15);
    }*/


    /*
    //获取公共信息 通过API
    public static void getFreeNews()
    {
        final List<SyndEntry> syndEntrys=new ArrayList<SyndEntry>();
        NetWork.getObjectApiWithOutBase64(Constants.NEWS_FREE_HOT_WORDS,new TypeToken<HotWords<String>>(){}.getType(), new ResponseListener<HotWords<String>>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.LOG_TAG,"error="+error.getMessage()+"");
                transFinishedBlockingQueue.offer(new ArrayList<SyndEntry>(syndEntrys));
            }

            @Override
            public void onResponse(HotWords<String> response) {
                //Log.e(Constants.LOG_TAG,"=="+response.toString());
                if(response.getError_code()==0)
                {
                    for(int i=0;i<response.getResult().size();i++)
                    {
                        String keyword=response.getResult().get(i);
                        NetWork.getObjectApiWithOutBase64(Constants.NEWS_FREE_HOT_WORDS_FEED+keyword,new TypeToken<HotWords<HotWordsFeed>>(){}.getType(), new ResponseListener<HotWords<HotWordsFeed>>(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.e(Constants.LOG_TAG,"error="+error.getMessage()+"");
                            }

                            @Override
                            public void onResponse(HotWords<HotWordsFeed> response) {
                                //Log.e(Constants.LOG_TAG,"=="+response.toString());
                                if(response.getError_code()==0)
                                {
                                    List<HotWordsFeed> hotWordsFeedList=response.getResult();
                                    if(hotWordsFeedList!=null&&hotWordsFeedList.size()>0)
                                    {
                                        HotWordsFeed hotWordsFeed=response.getResult().get(0);
                                        if(null!=hotWordsFeed)
                                        {
                                            SyndEntry syndEntry=new SyndEntry();
                                            syndEntry.setSource(hotWordsFeed.getSrc());
                                            syndEntry.setDescription(hotWordsFeed.getContent());
                                            syndEntry.setTitle(hotWordsFeed.getTitle());
                                            String img=hotWordsFeed.getImg();
                                            if(null!=img&&img!="")
                                            {
                                                EntryImage entryImage=new EntryImage();
                                                entryImage.setImageLink(img);
                                                List<EntryImage> imageslistt=new ArrayList<EntryImage>();
                                                imageslistt.add(entryImage);
                                                syndEntry.setImages(imageslistt);
                                            }
                                            syndEntry.setLink(hotWordsFeed.getUrl());
                                            syndEntrys.add(syndEntry);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
                CountDownTimer cdt = new CountDownTimer(4000, 4000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //tv_hello.setText(millisUntilFinished + "");
                    }
                    @Override
                    public void onFinish() {
                        Log.e("线程=","timer执行");
                        transFinishedBlockingQueue.offer(new ArrayList<SyndEntry>(syndEntrys));
                    }
                };
                cdt.start();
            }
        });
    }
    */

    /*
    public static void getFreeNewsAndStore(final Context context)
    {
        //final List<SyndEntry> syndEntrys=new ArrayList<SyndEntry>();
        NetWork.getObjectApiWithOutBase64(Constants.NEWS_FREE_HOT_WORDS,new TypeToken<HotWords<String>>(){}.getType(), new ResponseListener<HotWords<String>>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.LOG_TAG,"error="+error.getMessage()+"");
                //transFinishedBlockingQueue.offer(new ArrayList<SyndEntry>(syndEntrys));
            }

            @Override
            public void onResponse(HotWords<String> response) {
                //Log.e(Constants.LOG_TAG,"=="+response.toString());
                if(response.getError_code()==0)
                {
                    for(int i=0;i<response.getResult().size();i++)
                    {
                        String keyword=response.getResult().get(i);
                        NetWork.getObjectApiWithOutBase64(Constants.NEWS_FREE_HOT_WORDS_FEED+keyword,new TypeToken<HotWords<HotWordsFeed>>(){}.getType(), new ResponseListener<HotWords<HotWordsFeed>>(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.e(Constants.LOG_TAG,"error="+error.getMessage()+"");
                            }

                            @Override
                            public void onResponse(HotWords<HotWordsFeed> response) {
                                //Log.e(Constants.LOG_TAG,"=="+response.toString());
                                if(response.getError_code()==0)
                                {
                                    List<HotWordsFeed> hotWordsFeedList=response.getResult();
                                    if(hotWordsFeedList!=null&&hotWordsFeedList.size()>0)
                                    {
                                        HotWordsFeed hotWordsFeed=response.getResult().get(0);
                                        if(null!=hotWordsFeed)
                                        {
                                            SyndEntry syndEntry=new SyndEntry();
                                            syndEntry.setSource(hotWordsFeed.getSrc());
                                            syndEntry.setDescription(hotWordsFeed.getContent());
                                            syndEntry.setTitle(hotWordsFeed.getTitle());
                                            syndEntry.setLink(hotWordsFeed.getUrl());
                                            String img=hotWordsFeed.getImg();
                                            if(null!=img&&img!="")
                                            {
                                                EntryImage entryImage=new EntryImage();
                                                entryImage.setImageLink(img);
                                                List<EntryImage> imageslistt=new ArrayList<EntryImage>();
                                                imageslistt.add(entryImage);
                                                syndEntry.setImages(imageslistt);
                                                entryImage.setEntryId(syndEntry);
                                            }

                                            List<SyndEntry> existEntrys=DataManagerService.getInstance(context)
                                                    .provideSyndEntryDao()
                                                    .queryByColumn(SyndEntry.ENTRY_LINK,syndEntry.getLink());
                                            if(existEntrys!=null&&existEntrys.size()>0)
                                            {
                                                //continue;
                                            }else{
                                                //插入数据库
                                                DataManagerService.getInstance(context)
                                                        .provideSyndEntryDao()
                                                        .add(syndEntry);

                                                if(syndEntry!=null
                                                        &&syndEntry.getImages()!=null
                                                        &&syndEntry.getImages().size()>0)
                                                {
                                                    for(EntryImage entryImage:syndEntry.getImages())
                                                    {
                                                        DataManagerService.getInstance(context)
                                                                .provideEntryImageDao().add(entryImage);
                                                    }
                                                }
                                            }


                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    */
    
    
}

