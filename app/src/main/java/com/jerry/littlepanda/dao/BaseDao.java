package com.jerry.littlepanda.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.jerry.littlepanda.Utils.DatabaseHelper;
import com.jerry.littlepanda.domain.EntryImage;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jerry.hu on 12/08/17.
 */

public class BaseDao<T> {
    protected Class<T> clazz;
    protected Dao<T, Integer> dao;

    public BaseDao(Context context)
    {
        Class clazz = getClass();

        while (clazz != Object.class) {
            Type t = clazz.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    this.clazz = (Class<T>) args[0];
                    break;
                }
            }
            clazz = clazz.getSuperclass();
        }
        try {
            if (DatabaseHelper.getInstance(context) == null) {
                throw new RuntimeException("No DbHelper Found!");
            }
            dao = DatabaseHelper.getInstance(context).getDao(this.clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(T t) {
        try {
            dao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量添加\
     */
    public void addList(List<T> ts){

        try {
            dao.create(ts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(T t) {
        try {
            dao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除
     */
    public void deleteList(List<T> ts){

        try {
            dao.delete(ts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(T t) {
        try {
            dao.update(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> selectAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> selectAllByPage(Long page,Long count)
    {
        try {
            return dao.queryBuilder().offset((page-1)*count).limit(count).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 通过ID查询一条数据
    public T queryById(int id) {
        T t = null;
        try {
            t = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public List<T> queryByColumn(String columnName, Object columnValue) {
        try {
            QueryBuilder builder = dao.queryBuilder();
            builder.where().eq(columnName, columnValue);
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取总的条数
     * @return
     */
    public Long  getCountOf()
    {
        long count=0L;
        try{
            count=dao.queryBuilder().countOf();
        }catch(SQLException e) {
            e.printStackTrace();
            return 0L;
        }
        return count;
    }

    /**
     * 获取第一条数据
     * @return
     */
    public T queryForFirst()
    {
        T t=null;
        try{
            t=dao.queryBuilder().queryForFirst();
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
