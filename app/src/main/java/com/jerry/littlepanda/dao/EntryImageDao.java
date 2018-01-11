package com.jerry.littlepanda.dao;

import android.content.Context;

import com.jerry.littlepanda.domain.EntryImage;

/**
 * Created by jerry.hu on 12/08/17.
 */

public class EntryImageDao extends BaseDao<EntryImage> {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    //private Dao<EntryImage, Integer> dao;

    public EntryImageDao(Context context) {
        super(context);
        this.context = context;
    }

    /*
    // 通过条件查询文章集合（通过SyndEntry ID查找）
    public List<EntryImage> queryBySyndEntryId(int syndentry_id) {
        try {
            return dao.queryBuilder().where().eq(EntryImage.ENTRY_ID, syndentry_id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
}
