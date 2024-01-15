package io.ganguo.demo.database.callback;

import java.util.List;

import io.objectbox.query.QueryBuilder;


/**
 * Created by leo on 2017/10/16.
 */

public interface CrudCallBack {


    /**
     * 写入一组列表数据
     *
     * @return
     */
    <T> List<T> putAll(Class<T> clazz, List<T> data);


    /**
     * 更新和写入数据，都是put方法，所以都是调用writeData
     * 更新数据库数据的逻辑如下：
     * 1、从数据库查询出该条数据对象
     * 2、使用该数据对象进行数据更新
     * 3、写入到数据库
     *
     * @return
     */
    <T> T put(Class<T> clazz, T data);

    /**
     * 删除一条数据
     *
     * @return
     */
    <T> void delete(Class<T> clazz, T data);

    /**
     * 删除一条数据
     *
     * @return
     */
    <T> void deleteAll(Class<T> clazz);

    /**
     * 删除全部数据数据
     *
     * @param clazz
     * @return
     */
    <T> void deleteAll(Class<T> clazz, List<T> data);


    /**
     * 删除一条数据
     *
     * @param clazz
     * @param id
     */
    <T> void delete(Class<T> clazz, long id);

    /**
     * 根据Class类型查询数据
     *
     * @return
     */
    <T> List<T> query(Class<T> clazz);

    /**
     * 根据id读取一条数据库数据，前提是该数据库已有该条数据，并且已知id的情况下才能用
     *
     * @return
     */
    <T> T query(Class<T> clazz, long dataId);


    /**
     * 根据queryBuilder查询数据
     *
     * @return
     */
    <T> List<T> query(QueryBuilder<T> queryBuilder);

    /**
     * 分页查询数据
     *
     * @param queryBuilder
     * @return
     */
    <T> List<T> query(long offset, long limit, QueryBuilder<T> queryBuilder);

    /**
     * 根据条件查询第一条数据
     *
     * @return
     */
    <T> T queryFirst(QueryBuilder<T> queryBuilder);

    /**
     * 根据Class类型查询第一条数据
     *
     * @return
     */
    <T> T queryFirst(Class<T> clazz);

}
