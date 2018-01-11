package com.jerry.littlepanda.manager;

import android.content.Context;

import com.jerry.littlepanda.dao.EntryImageDao;
import com.jerry.littlepanda.dao.SyndEntryDao;
import com.jerry.littlepanda.domain.SyndEntry;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by jerry.hu on 12/08/17.
 * DaoManager 获取各个dao
 */

public class DataManagerService {

    private Context context;
    private static DataManagerService instance;
    private Queue<List<SyndEntry>> rssQueue;

    // 获取本类单例对象的方法
    public static synchronized DataManagerService getInstance(Context context) {
        if (instance == null) {
            synchronized (DataManagerService.class) {
                if (instance == null) {
                    instance = new DataManagerService(context);
                }
            }
        }
        return instance;
    }

    /**
     * 信息流队列
     * @return
     */
    public synchronized Queue<List<SyndEntry>> getRssQueue(){
        if (rssQueue == null) {
            synchronized (DataManagerService.class) {
                if (rssQueue == null) {
                    rssQueue = new LinkedList<List<SyndEntry>>();
                }
            }
        }
        return rssQueue;
    }

    private DataManagerService(Context context)
    {
        this.context=context;
    }

    public SyndEntryDao provideSyndEntryDao()
    {
        return new SyndEntryDao(context);
    }

    public EntryImageDao provideEntryImageDao()
    {
        return new EntryImageDao(context);
    }
}
