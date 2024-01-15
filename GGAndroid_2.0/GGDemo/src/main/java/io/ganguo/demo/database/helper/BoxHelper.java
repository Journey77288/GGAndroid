package io.ganguo.demo.database.helper;


import java.util.List;

import io.ganguo.demo.database.callback.CrudCallBack;
import io.ganguo.demo.database.helper.base.BaseBoxHelper;
import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

/**
 * Created by leo on 2017/10/16.
 * 数据库处理工具类
 */
public class BoxHelper extends BaseBoxHelper implements CrudCallBack {

    private BoxHelper() {
    }


    /**
     * 单例
     *
     * @return
     */
    public static BoxHelper get() {
        return LazyHolder.HOLDER;
    }

    /**
     * 懒加载
     */
    public static class LazyHolder {
        static BoxHelper HOLDER = new BoxHelper();
    }


    /**
     * 写入一条数据到数据库
     *
     * @param clazz
     * @param data
     * @return
     */
    @Override
    public <T> List<T> putAll(Class<T> clazz, List<T> data) {
        Box<T> box = box(clazz);
        if (data != null) {
            box.put(data);
        }
        return data;
    }

    /**
     * 写入一条数据到数据库
     *
     * @param clazz
     * @param data
     * @return
     */
    @Override
    public <T> T put(Class<T> clazz, T data) {
        Box<T> box = box(clazz);
        if (data != null) {
            box.put(data);
        }
        return data;
    }


    /**
     * 删除一条数据
     *
     * @param clazz
     * @param data
     * @return
     */
    @Override
    public <T> void delete(Class<T> clazz, T data) {
        Box<T> box = box(clazz);
        if (data != null) {
            box.remove(data);
        }
    }

    /**
     * 删除全部数据数据
     *
     * @param clazz
     * @return
     */
    @Override
    public <T> void deleteAll(Class<T> clazz) {
        Box<T> box = box(clazz);
        box.removeAll();
    }

    /**
     * 删除全部数据数据
     *
     * @param clazz
     * @return
     */
    @Override
    public <T> void deleteAll(Class<T> clazz, List<T> data) {
        Box<T> box = box(clazz);
        box.remove(data);
    }

    /**
     * 根据id删除某一条数据
     *
     * @param clazz
     * @return
     */
    @Override
    public <T> void delete(Class<T> clazz, long id) {
        Box<T> box = box(clazz);
        box.remove(id);

    }

    /**
     * 根据Class类型查询数据
     *
     * @param clazz
     * @return
     */
    @Override
    public <T> List<T> query(Class<T> clazz) {
        QueryBuilder<T> queryBuilder = box(clazz).query();
        return query(queryBuilder);
    }

    /**
     * 根据id读取一条数据库数据，前提是该数据库已有该条数据，并且已知id的情况下才能用
     *
     * @param clazz
     * @param dataId
     * @return
     */
    @Override
    public <T> T query(Class<T> clazz, long dataId) {
        T data = box(clazz).get(dataId);
        return data;
    }

    /**
     * 根据条件,查询出所有数据
     *
     * @param queryBuilder
     * @return
     */
    @Override
    public <T> List<T> query(QueryBuilder<T> queryBuilder) {
        Query<T> query = queryBuilder.build();
        return query.find();
    }

    /**
     * 根据条件,查询出所有数据中的第一条数据
     *
     * @param queryBuilder
     * @return
     */
    @Override
    public <T> T queryFirst(QueryBuilder<T> queryBuilder) {
        Query<T> query = queryBuilder.build();
        return query.findFirst();
    }

    /**
     * 根据Class类型查询第一条数据
     *
     * @param clazz
     * @return
     */
    @Override
    public <T> T queryFirst(Class<T> clazz) {
        QueryBuilder<T> queryBuilder = box(clazz).query();
        return queryFirst(queryBuilder);
    }


    /**
     * 分页查询数据
     *
     * @param queryBuilder
     * @return
     */
    @Override
    public <T> List<T> query(long offset, long limit, QueryBuilder<T> queryBuilder) {
        Query<T> query = queryBuilder.build();
        return query.find(offset, limit);
    }


    /**
     * 创建添加查询数据条件对象
     *
     * @param clazz
     * @return
     */
    public <S> QueryBuilder<S> queryBuilder(Class<S> clazz) {
        QueryBuilder<S> queryBuilder = box(clazz).query();
        return queryBuilder;
    }


}

