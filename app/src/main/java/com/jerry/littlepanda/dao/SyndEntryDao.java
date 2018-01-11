package com.jerry.littlepanda.dao;

/**
 * Created by jerry.hu on 12/08/17.
 */

import android.content.Context;

import com.jerry.littlepanda.domain.SyndEntry;

/**
 * 操作SyndEntry数据表的Dao类，封装这操作SyndEntry表的所有操作
 * 通过DatabaseHelper类中的方法获取ORMLite内置的DAO类进行数据库中数据的操作
 * <p>
 * 调用dao的create()方法向表中添加数据
 * 调用dao的delete()方法删除表中的数据
 * 调用dao的update()方法修改表中的数据
 * 调用dao的queryForAll()方法查询表中的所有数据
 */
public class SyndEntryDao extends BaseDao<SyndEntry> {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    //private Dao<SyndEntry, Integer> dao;

    public SyndEntryDao(Context context) {
        super(context);
        this.context = context;
    }

}
